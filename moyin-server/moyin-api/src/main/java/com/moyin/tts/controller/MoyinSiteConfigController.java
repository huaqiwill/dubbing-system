package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.service.IMoyinSiteConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/moyin/websiteConfig")
public class MoyinSiteConfigController extends BaseController {

    @Resource
    private IMoyinSiteConfigService siteConfigService;

    @GetMapping
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('moyin:websiteConfig:get')")
    public AjaxResult get() {
        return toAjax(siteConfigService.getWebConfig());
    }

    @PutMapping
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('moyin:websiteConfig:edit')")
    public AjaxResult edit() {
        return toAjax(siteConfigService.getWebConfig());
    }

    @GetMapping("/getUserProtocol")
    @PreAuthorize("@ss.hasPermi('moyin:websiteConfig:getUserProtocol')")
    @Log(title = "获取用户协议", businessType = BusinessType.QUERY)
    public AjaxResult getUserProtocol() {
        return AjaxResult.success("OK", siteConfigService.getUserProtocol());
    }

    @PostMapping("/setUserProtocol")
    @PreAuthorize("@ss.hasPermi('moyin:websiteConfig:setUserProtocol')")
    @Log(title = "设置用户协议", businessType = BusinessType.QUERY)
    public AjaxResult setUserProtocol(@RequestBody String protocol) {
        return toAjax(siteConfigService.setUserProtocol(protocol));
    }

}
