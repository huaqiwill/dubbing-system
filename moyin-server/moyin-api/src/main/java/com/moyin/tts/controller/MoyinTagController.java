package com.moyin.tts.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.moyin.tts.domain.MoyinTag;
import com.moyin.tts.service.IMoyinTagService;
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
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.common.core.page.TableDataInfo;


@RestController
@RequestMapping("/moyin/tag")
public class MoyinTagController extends BaseController {

    @Resource
    private IMoyinTagService tagService;

    /**
     * 查询配音员分类列表
     */
    @PreAuthorize("@ss.hasPermi('moyin:tag:list')")
    @GetMapping("/list")
    @Log(title = "标签列表", businessType = BusinessType.QUERY)
    public TableDataInfo list(MoyinTag moyinCategory) {
        startPage();
        List<MoyinTag> list = tagService.selectList(moyinCategory);
        return getDataTable(list);
    }

    /**
     * 导出配音员分类列表
     */
    @PreAuthorize("@ss.hasPermi('moyin:tag:export')")
    @Log(title = "导出标签", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinTag moyinCategory) {
        List<MoyinTag> list = tagService.selectList(moyinCategory);
        ExcelUtil<MoyinTag> util = new ExcelUtil<>(MoyinTag.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    /**
     * 获取配音员分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('moyin:tag:query')")
    @GetMapping(value = "/{id}")
    @Log(title = "获取标签的信息", businessType = BusinessType.QUERY)
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tagService.selectById(id));
    }

    /**
     * 新增配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:tag:add')")
    @Log(title = "新增标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinTag moyinCategory) {
        return toAjax(tagService.insert(moyinCategory));
    }

    /**
     * 修改配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:tag:edit')")
    @Log(title = "修改标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinTag moyinCategory) {
        return toAjax(tagService.updateById(moyinCategory));
    }

    /**
     * 删除配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:tag:remove')")
    @Log(title = "删除标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tagService.deleteByIds(ids));
    }
}
