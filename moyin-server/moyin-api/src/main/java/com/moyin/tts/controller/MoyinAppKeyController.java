package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinAppKey;
import com.moyin.tts.mapper.MoyinAppKeyMapper;
import com.moyin.tts.service.IMoyinAppKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/moyin/appKey")
public class MoyinAppKeyController extends BaseController {

    @Resource
    private IMoyinAppKeyService appKeyService;

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('moyin:appKey:get')")
    @Log(title = "获取魔音秘钥", businessType = BusinessType.EXPORT)
    public AjaxResult getAppKey(@PathVariable Long id) {
        return AjaxResult.success(appKeyService.findAppKeyById(id));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('moyin:appKey:remove')")
    @Log(title = "删除魔音秘钥", businessType = BusinessType.DELETE)
    public AjaxResult delete(@PathVariable Long id) {
        return AjaxResult.success(appKeyService.deleteById(id));
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermi('moyin:appKey:edit')")
    @Log(title = "修改魔音秘钥", businessType = BusinessType.UPDATE)
    public AjaxResult edit(@RequestBody MoyinAppKey appKey) {
        return toAjax(appKeyService.updateById(appKey));
    }


    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('moyin:appKey:list')")
    @Log(title = "魔音秘钥列表", businessType = BusinessType.EXPORT)
    public TableDataInfo list(MoyinAppKey appKey) {
        startPage();
        List<MoyinAppKey> list = appKeyService.list(appKey);
        return getDataTable(list);
    }

    @GetMapping("/current")
    @PreAuthorize("@ss.hasPermi('moyin:appKey:current')")
    @Log(title = "当前魔音秘钥", businessType = BusinessType.EXPORT)
    public AjaxResult currentAppKey() {
        return success(appKeyService.getCurrentAppKey());
    }


    @PostMapping
    @PreAuthorize("@ss.hasPermi('moyin:appKey:add')")
    @Log(title = "添加魔音秘钥", businessType = BusinessType.EXPORT)
    public AjaxResult add(@RequestBody MoyinAppKey appKey) {
        log.warn("拿到的值：{}", appKey);
        return toAjax(appKeyService.insert(appKey));
    }
}
