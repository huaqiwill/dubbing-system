package com.moyin.tts.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.moyin.tts.domain.MoyinEmotion;
import com.moyin.tts.service.IMoyinEmotionService;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.common.core.page.TableDataInfo;


@RestController
@RequestMapping("/moyin/emotion")
public class MoyinEmotionController extends BaseController {
    @Resource
    private IMoyinEmotionService emotionService;

    @PreAuthorize("@ss.hasPermi('moyin:emotion:list')")
    @Log(title = "情绪列表", businessType = BusinessType.EXPORT)
    @GetMapping("/list")
    public TableDataInfo list(MoyinEmotion moyinEmotion) {
        startPage();
        List<MoyinEmotion> list = emotionService.selectList(moyinEmotion);
        return getDataTable(list);
    }

//    @PreAuthorize("@ss.hasPermi('moyin:emotion:list')")
//    @Log(title = "情绪列表", businessType = BusinessType.EXPORT)
//    @GetMapping("/listAll")
//    public TableDataInfo listAll(MoyinEmotion moyinEmotion) {
//        List<MoyinEmotion> list = moyinEmotionService.selectList(moyinEmotion);
//        return getDataTable(list);
//    }

    @PreAuthorize("@ss.hasPermi('moyin:emotion:export')")
    @Log(title = "情绪导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinEmotion moyinEmotion) {
        List<MoyinEmotion> list = emotionService.selectList(moyinEmotion);
        ExcelUtil<MoyinEmotion> util = new ExcelUtil<MoyinEmotion>(MoyinEmotion.class);
        util.exportExcel(response, list, "情绪情绪数据");
    }

    @PreAuthorize("@ss.hasPermi('moyin:emotion:query')")
    @Log(title = "情绪查询", businessType = BusinessType.EXPORT)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(emotionService.selectById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:emotion:add')")
    @Log(title = "情绪新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinEmotion moyinEmotion) {
        return toAjax(emotionService.insert(moyinEmotion));
    }

    @PreAuthorize("@ss.hasPermi('moyin:emotion:edit')")
    @Log(title = "情绪编辑", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinEmotion moyinEmotion) {
        return toAjax(emotionService.update(moyinEmotion));
    }

    @PreAuthorize("@ss.hasPermi('moyin:emotion:remove')")
    @Log(title = "情绪删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(emotionService.deleteByIds(ids));
    }
}
