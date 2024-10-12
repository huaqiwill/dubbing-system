package com.moyin.framework.web.service;

import javax.annotation.Resource;

import com.moyin.common.core.domain.AjaxResult;
import com.moyin.framework.web.utils.LoginCheckUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.moyin.common.constant.CacheConstants;
import com.moyin.common.constant.Constants;
import com.moyin.common.constant.UserConstants;
import com.moyin.common.core.domain.entity.SysUser;
import com.moyin.common.core.domain.model.LoginUser;
import com.moyin.common.core.redis.RedisCache;
import com.moyin.common.exception.ServiceException;
import com.moyin.common.exception.user.BlackListException;
import com.moyin.common.exception.user.CaptchaException;
import com.moyin.common.exception.user.CaptchaExpireException;
import com.moyin.common.exception.user.UserNotExistsException;
import com.moyin.common.exception.user.UserPasswordNotMatchException;
import com.moyin.common.utils.DateUtils;
import com.moyin.common.utils.MessageUtils;
import com.moyin.common.utils.StringUtils;
import com.moyin.common.utils.ip.IpUtils;
import com.moyin.framework.manager.AsyncManager;
import com.moyin.framework.manager.factory.AsyncFactory;
import com.moyin.framework.security.context.AuthenticationContextHolder;
import com.moyin.system.service.ISysConfigService;
import com.moyin.system.service.ISysUserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 登录校验方法
 *
 * @author moyin
 */
@Component
public class SysLoginService {

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysConfigService configService;

    @Getter
    private String errorMessage;

    @Getter
    private LoginUser loginUser;

    public String getToken() {
        return tokenService.createToken(loginUser);
    }

    public boolean getTempPerm(String username, String password) {
        return login(username, password);
    }

    public boolean login(String username, String password) {
        return login(username, password, "", "", "", false);
    }

    public boolean login(String username, String password, String code, String uuid, String userAgent) {
        return login(username, password, code, uuid, userAgent, true);
    }

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public boolean login(String username, String password, String code, String uuid, String userAgent, boolean checkCode) {
        if (checkCode) {
            // 验证码校验
            validateCaptcha(username, code, uuid);
        }

        // 登录前置校验
        loginPreCheck(username, password);
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }

        loginUser = (LoginUser) authentication.getPrincipal();

        return true;
    }

    public void recordLoginInfo(String username) {
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        recordLoginInfo(loginUser.getUserId());
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    public void validateCaptcha(String username, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha)) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }

    /**
     * 登录前置校验
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr())) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
