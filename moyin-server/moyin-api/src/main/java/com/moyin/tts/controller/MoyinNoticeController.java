package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinNotice;
import com.moyin.tts.service.IMoyinNoticeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/moyin/notice")
public class MoyinNoticeController extends BaseController {

    @Resource
    private IMoyinNoticeService noticeService;

    /**
     * 查询配音员分类列表
     */
    @PreAuthorize("@ss.hasPermi('moyin:notice:list')")
    @Log(title = "通知列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(MoyinNotice notice) {
        startPage();
        List<MoyinNotice> list = noticeService.selectList(notice);
        return getDataTable(list);
    }

    /**
     * 导出配音员分类列表
     */
    @PreAuthorize("@ss.hasPermi('moyin:notice:export')")
    @Log(title = "导出通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinNotice moyinCategory) {
        List<MoyinNotice> list = noticeService.selectList(moyinCategory);
        ExcelUtil<MoyinNotice> util = new ExcelUtil<>(MoyinNotice.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    /**
     * 获取配音员分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('moyin:notice:info')")
    @Log(title = "导出通知", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(noticeService.selectById(id));
    }

    /**
     * 新增配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:notice:add')")
    @Log(title = "配音员分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinNotice moyinCategory) {
        return toAjax(noticeService.insert(moyinCategory));
    }

    /**
     * 修改配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:notice:edit')")
    @Log(title = "配音员分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinNotice moyinCategory) {
        return toAjax(noticeService.updateById(moyinCategory));
    }

    /**
     * 删除配音员分类
     */
    @PreAuthorize("@ss.hasPermi('moyin:notice:remove')")
    @Log(title = "配音员分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(noticeService.deleteByIds(ids));
    }

}

