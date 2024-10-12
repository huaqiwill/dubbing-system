package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinLexicon;
import com.moyin.tts.service.IMoyinLexiconService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/moyin/lexicon")
public class MoyinLexiconController extends BaseController {

    @Resource
    private IMoyinLexiconService lexiconService;

    @PreAuthorize("@ss.hasPermi('moyin:lexicon:add')")
    @Log(title = "添加词库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addLexicon(@RequestBody MoyinLexicon lexicon) {
        return toAjax(lexiconService.insertLexicon(lexicon));
    }

    @PreAuthorize("@ss.hasPermi('moyin:lexicon:edit')")
    @Log(title = "编辑词库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult editLexicon(@RequestBody MoyinLexicon lexicon) {
        return toAjax(lexiconService.updateLexiconById(lexicon));
    }

    @PreAuthorize("@ss.hasPermi('moyin:lexicon:remove')")
    @Log(title = "删除词库", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult deleteLexicon(@PathVariable Long id) {
        return toAjax(lexiconService.deleteLexiconById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:lexicon:info')")
    @Log(title = "获取词库信息", businessType = BusinessType.QUERY)
    @GetMapping("/{id}")
    public AjaxResult infoLexicon(@PathVariable Long id) {
        System.out.println("请求的ID:" + id);
        return success(lexiconService.findLexiconById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:lexicon:list')")
    @Log(title = "获取词库列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo listLexicon(MoyinLexicon lexicon) {
        startPage();
        List<MoyinLexicon> list = lexiconService.listLexicon(lexicon);
        return getDataTable(list);
    }
}
