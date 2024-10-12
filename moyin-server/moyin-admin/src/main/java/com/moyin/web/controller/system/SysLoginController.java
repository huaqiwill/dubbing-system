package com.moyin.web.controller.system;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.moyin.common.core.domain.model.LoginUser;
import com.moyin.system.service.ISysUserService;
import com.moyin.tts.service.impl.MoyinLoginDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.moyin.common.constant.Constants;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.domain.entity.SysMenu;
import com.moyin.common.core.domain.entity.SysUser;
import com.moyin.common.core.domain.model.LoginBody;
import com.moyin.common.utils.SecurityUtils;
import com.moyin.framework.web.service.SysLoginService;
import com.moyin.framework.web.service.SysPermissionService;
import com.moyin.system.service.ISysMenuService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证
 *
 * @author moyin
 */
@RestController
public class SysLoginController {

    private static final Logger log = LoggerFactory.getLogger(SysLoginController.class);
    @Resource
    private SysLoginService loginService;

    @Resource
    private ISysMenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private MoyinLoginDeviceService loginDeviceService;

    @Resource
    private ISysUserService userService;

    @PostMapping("/getTempPerm")
    public AjaxResult getTempPerm(@RequestBody LoginBody loginBody, HttpServletRequest request) {
        AjaxResult ajaxResult = AjaxResult.success();

        if (!loginBody.getUsername().equals("dev")) {
            return AjaxResult.error("不支持的账号");
        }

        boolean result = loginService.getTempPerm(loginBody.getUsername(), loginBody.getPassword());
        if (!result) {
            return AjaxResult.error(loginService.getErrorMessage());
        }

        String token = loginService.getToken();
        ajaxResult.put(Constants.TOKEN, token);
        return ajaxResult;
    }

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody, HttpServletRequest request) {
        AjaxResult ajax = AjaxResult.success();

        String userAgentString = request.getHeader("User-Agent");
        String ipAddress = request.getRemoteAddr();

        // 生成令牌
        boolean result = loginService.login(
                loginBody.getUsername(),
                loginBody.getPassword(),
                loginBody.getCode(),
                loginBody.getUuid(),
                userAgentString);

        if (!result) {
            return AjaxResult.error(loginService.getErrorMessage());
        }

        String token = loginService.getToken();
        LoginUser loginUser = loginService.getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUserId());
        Date current = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        if (!loginDeviceService.checkUserAgent(loginUser.getUserId(), userAgentString)) {
            return AjaxResult.error("系统检测到你已经在其他设备登录，如需继续登录，请联系管理员！");
        }

        // 默认没有过期时间的账号为永久账户
        if (user.getExpirationTime() != null && user.getExpirationTime().before(current)) {
            return AjaxResult.error("系统检测到你的账号已过期，如需继续登录，请联系管理员！");
        }

        // 记录登录设备
        loginDeviceService.add(loginUser.getUserId(), userAgentString, ipAddress);

        loginService.recordLoginInfo(loginUser.getUsername());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
