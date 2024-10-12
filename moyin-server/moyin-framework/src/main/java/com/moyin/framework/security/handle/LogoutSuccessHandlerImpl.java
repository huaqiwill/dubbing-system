package com.moyin.framework.security.handle;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moyin.common.core.redis.RedisCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.alibaba.fastjson2.JSON;
import com.moyin.common.constant.Constants;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.domain.model.LoginUser;
import com.moyin.common.utils.ServletUtils;
import com.moyin.common.utils.StringUtils;
import com.moyin.framework.manager.AsyncManager;
import com.moyin.framework.manager.factory.AsyncFactory;
import com.moyin.framework.web.service.TokenService;

/**
 * 自定义退出处理类 返回成功
 *
 * @author moyin
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
//            redisCache.deleteObject("device:" + loginUser.getUserId());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, "退出成功"));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("退出成功")));
    }
}
