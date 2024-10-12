package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinCategory;
import com.moyin.tts.domain.MoyinLanguage;
import com.moyin.tts.service.IMoyinLanguageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/moyin/language")
public class MoyinLanguageController extends BaseController {

    @Resource
    private IMoyinLanguageService languageService;

    @PreAuthorize("@ss.hasPermi('moyin:language:list')")
    @Log(title = "语言分类", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo list(MoyinLanguage category) {
        startPage();
        List<MoyinLanguage> list = languageService.selectList(category);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:language:export')")
    @Log(title = "语言导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinLanguage category) {
        List<MoyinLanguage> list = languageService.selectList(category);
        ExcelUtil<MoyinLanguage> util = new ExcelUtil<>(MoyinLanguage.class);
        util.exportExcel(response, list, "语言分类数据");
    }

    @PreAuthorize("@ss.hasPermi('moyin:language:query')")
    @Log(title = "语言详情", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(languageService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:language:add')")
    @Log(title = "语言新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinLanguage category) {
        return toAjax(languageService.insert(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:language:edit')")
    @Log(title = "语言更新", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinLanguage category) {
        return toAjax(languageService.updateById(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:language:remove')")
    @Log(title = "语言移除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(languageService.deleteByIds(ids));
    }
}
