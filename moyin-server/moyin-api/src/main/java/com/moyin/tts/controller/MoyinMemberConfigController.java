package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinMemberConfigItem;
import com.moyin.tts.domain.MoyinMemberConfigPrice;
import com.moyin.tts.service.IMoyinMemberConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/moyin/memberConfig")
public class MoyinMemberConfigController extends BaseController {

    @Resource
    private IMoyinMemberConfigService memberConfigService;

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PostMapping
    public AjaxResult addPrice(@RequestBody MoyinMemberConfigPrice memberConfigPrice) {
        return toAjax(memberConfigService.addPrice(memberConfigPrice));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PutMapping
    public AjaxResult editPrice(@RequestBody MoyinMemberConfigPrice memberConfigPrice) {
        return toAjax(memberConfigService.editPrice(memberConfigPrice));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @DeleteMapping
    public AjaxResult deletePrice(Long id) {
        return toAjax(memberConfigService.deletePrice(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo listPrice(MoyinMemberConfigPrice memberConfigPrice) {
        return getDataTable(memberConfigService.listPrice(memberConfigPrice));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PostMapping("/item")
    public AjaxResult addItem(@RequestBody MoyinMemberConfigItem memberConfigItem) {
        return toAjax(memberConfigService.addItem(memberConfigItem));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @DeleteMapping("/item")
    public AjaxResult deleteItem(Long id) {
        return toAjax(memberConfigService.deleteItem(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping("/item/list")
    public TableDataInfo listItem(MoyinMemberConfigItem memberConfigItem) {
        return getDataTable(memberConfigService.listItem(memberConfigItem));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PutMapping("/item")
    public AjaxResult editItem(@RequestBody MoyinMemberConfigItem memberConfigItem) {
        return toAjax(memberConfigService.editItem(memberConfigItem));
    }

}
