package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinAge;
import com.moyin.tts.service.IMoyinGenderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/moyin/gender")
public class MoyinGenderController extends BaseController {

    @Resource
    private IMoyinGenderService genderService;

    @PreAuthorize("@ss.hasPermi('moyin:category:list')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo list(MoyinAge category) {
        startPage();
        List<MoyinAge> list = genderService.selectList(category);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:export')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinAge category) {
        List<MoyinAge> list = genderService.selectList(category);
        ExcelUtil<MoyinAge> util = new ExcelUtil<>(MoyinAge.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:query')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(genderService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:add')")
    @Log(title = "配音员分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinAge category) {
        return toAjax(genderService.insert(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinAge category) {
        return toAjax(genderService.updateById(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:category:remove')")
    @Log(title = "配音员分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(genderService.deleteByIds(ids));
    }
}
