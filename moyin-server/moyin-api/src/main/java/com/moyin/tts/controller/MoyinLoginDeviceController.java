package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinLoginDevice;
import com.moyin.tts.service.IMoyinLoginDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/moyin/loginDevice")
public class MoyinLoginDeviceController extends BaseController {

    @Resource
    private IMoyinLoginDeviceService loginDeviceService;

    @PreAuthorize("@ss.hasPermi('moyin:loginDevice:list')")
    @Log(title = "登录设备列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(MoyinLoginDevice loginDevice) {
        log.warn("查询参数：{}", loginDevice.getUserName());
        startPage();
        List<MoyinLoginDevice> list = loginDeviceService.list(loginDevice);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:loginDevice:info')")
    @Log(title = "登录设备详情", businessType = BusinessType.QUERY)
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        return success(loginDeviceService.info(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:loginDevice:remove')")
    @Log(title = "登录设备移除", businessType = BusinessType.QUERY)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(loginDeviceService.remove(id));
    }
}
