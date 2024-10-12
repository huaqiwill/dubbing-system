package com.moyin.tts.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.moyin.tts.domain.MoyinCategory;
import com.moyin.tts.service.IMoyinCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinDict;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.common.core.page.TableDataInfo;


@RestController
@RequestMapping("/moyin/category")
public class MoyinCategoryController extends BaseController {

    @Resource
    private IMoyinCategoryService categoryService;


    @PreAuthorize("@ss.hasPermi('moyin:category:list')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo list(MoyinCategory category) {
        startPage();
        List<MoyinCategory> list = categoryService.selectList(category);
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('moyin:category:export')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinCategory category) {
        List<MoyinCategory> list = categoryService.selectList(category);
        ExcelUtil<MoyinCategory> util = new ExcelUtil<>(MoyinCategory.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:query')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(categoryService.selectById(id));
    }


    @PreAuthorize("@ss.hasPermi('moyin:category:add')")
    @Log(title = "配音员分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinCategory category) {
        return toAjax(categoryService.insert(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinCategory category) {
        return toAjax(categoryService.updateById(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:remove')")
    @Log(title = "配音员分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(categoryService.deleteByIds(ids));
    }
}
