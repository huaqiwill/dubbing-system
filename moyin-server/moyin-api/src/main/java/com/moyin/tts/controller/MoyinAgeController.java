package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinAge;
import com.moyin.tts.domain.MoyinLanguage;
import com.moyin.tts.service.IMoyinAgeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/moyin/age")
public class MoyinAgeController extends BaseController {

    @Resource
    private IMoyinAgeService ageService;

    @PreAuthorize("@ss.hasPermi('moyin:age:list')")
    @Log(title = "年龄列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(MoyinAge category) {
        startPage();
        List<MoyinAge> list = ageService.selectList(category);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:age:export')")
    @Log(title = "年龄导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinAge category) {
        List<MoyinAge> list = ageService.selectList(category);
        ExcelUtil<MoyinAge> util = new ExcelUtil<>(MoyinAge.class);
        util.exportExcel(response, list, "年龄分类数据");
    }

    @PreAuthorize("@ss.hasPermi('moyin:age:query')")
    @Log(title = "年龄分类", businessType = BusinessType.QUERY)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(ageService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:age:add')")
    @Log(title = "年龄插入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinAge category) {
        return toAjax(ageService.insert(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:age:edit')")
    @Log(title = "年龄编辑", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinAge category) {
        return toAjax(ageService.updateById(category));
    }


    @PreAuthorize("@ss.hasPermi('moyin:age:remove')")
    @Log(title = "年龄删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(ageService.deleteByIds(ids));
    }
}
