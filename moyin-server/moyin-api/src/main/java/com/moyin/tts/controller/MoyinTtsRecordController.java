package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinTtsRecord;
import com.moyin.tts.service.IMoyinTtsRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/moyin/ttsRecord")
public class MoyinTtsRecordController extends BaseController {

    @Resource
    private IMoyinTtsRecordService ttsRecordService;

    @PreAuthorize("@ss.hasPermi('moyin:ttsRecord:list')")
    @Log(title = "获取用户生成记录", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(MoyinTtsRecord moyinTtsRecord) {
        startPage();
        moyinTtsRecord.setCreateBy(getUsername());
        List<MoyinTtsRecord> list = ttsRecordService.ttsRecordList(moyinTtsRecord);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:ttsRecord:listAll')")
    @Log(title = "获取列表生成记录", businessType = BusinessType.QUERY)
    @GetMapping("/listAll")
    public TableDataInfo listAll(MoyinTtsRecord moyinTtsRecord) {
        startPage();
        List<MoyinTtsRecord> list = ttsRecordService.ttsRecordListAll(moyinTtsRecord);
        return getDataTable(list);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('moyin:ttsRecord:remove')")
    @Log(title = "生成记录删除", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(ttsRecordService.deleteById(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('moyin:ttsRecord:info')")
    @Log(title = "生成记录详情", businessType = BusinessType.QUERY)
    public AjaxResult info(@PathVariable Long id) {
        return AjaxResult.success(ttsRecordService.selectById(id));
    }
}
