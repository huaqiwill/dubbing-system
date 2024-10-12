package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinLanguage;
import com.moyin.tts.domain.MoyinStyle;
import com.moyin.tts.service.IMoyinStyleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/moyin/style")
public class MoyinStyleController extends BaseController {

    @Resource
    private IMoyinStyleService styleService;

    @PreAuthorize("@ss.hasPermi('moyin:style:list')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo list(MoyinStyle category) {
        startPage();
        List<MoyinStyle> list = styleService.selectList(category);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:style:export')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinStyle category) {
        List<MoyinStyle> list = styleService.selectList(category);
        ExcelUtil<MoyinStyle> util = new ExcelUtil<>(MoyinStyle.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    @PreAuthorize("@ss.hasPermi('moyin:style:query')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(styleService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:style:add')")
    @Log(title = "配音员分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinStyle category) {
        return toAjax(styleService.insert(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:style:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinStyle category) {
        return toAjax(styleService.updateById(category));
    }

    @PreAuthorize("@ss.hasPermi('moyin:style:remove')")
    @Log(title = "配音员分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(styleService.deleteByIds(ids));
    }
}
