package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinSensitiveWord;
import com.moyin.tts.service.IMoyinSensitiveService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/moyin/sensitive")
public class MoyinSensitiveController extends BaseController {

    @Resource
    private IMoyinSensitiveService sensitiveService;

    @PreAuthorize("@ss.hasPermi('moyin:sensitive:add')")
    @Log(title = "添加敏感词", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinSensitiveWord sensitiveWord) {
        return toAjax(sensitiveService.insertSensitive(sensitiveWord));
    }

    @PreAuthorize("@ss.hasPermi('moyin:sensitive:edit')")
    @Log(title = "编辑敏感词", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinSensitiveWord sensitiveWord) {
        return toAjax(sensitiveService.updateSensitiveById(sensitiveWord));
    }

    @PreAuthorize("@ss.hasPermi('moyin:sensitive:remove')")
    @Log(title = "删除敏感词", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        return toAjax(sensitiveService.deleteSensitiveById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:sensitive:info')")
    @Log(title = "获取敏感词信息", businessType = BusinessType.QUERY)
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        return success(sensitiveService.findSensitiveWordById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:sensitive:list')")
    @Log(title = "获取敏感词列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(MoyinSensitiveWord sensitiveWord) {
        startPage();
        List<MoyinSensitiveWord> list = sensitiveService.listSensitiveWord(sensitiveWord);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:sensitive:identify')")
    @Log(title = "敏感词检测", businessType = BusinessType.QUERY)
    @PostMapping("/identify")
    public TableDataInfo identify(@RequestBody String text) {
        List<MoyinSensitiveWord> list = sensitiveService.identify(text);
        return getDataTable(list);
    }
}
