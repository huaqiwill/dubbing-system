package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.domain.MoyinMemberCollect;
import com.moyin.tts.service.IMoyinMemberCollectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/moyin/collect")
public class MoyinMemberCollectController extends BaseController {

    @Resource
    private IMoyinMemberCollectService memberCollectService;

    @PreAuthorize("@ss.hasPermi('moyin:collect:list')")
    @Log(title = "用户收藏列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(MoyinMemberCollect memberCollect) {
        startPage();
        List<MoyinMemberCollect> list = memberCollectService.collectSpeakerList(memberCollect);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:collect:info')")
    @Log(title = "用户收藏详情", businessType = BusinessType.QUERY)
    @GetMapping("/{id}")
    public AjaxResult info(@PathVariable Long id) {
        return success(memberCollectService.collectSpeakerDetail(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:collect:remove')")
    @Log(title = "用户收藏移除", businessType = BusinessType.QUERY)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(memberCollectService.collectSpeakerRemove(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:collect:add')")
    @Log(title = "用户收藏新增", businessType = BusinessType.QUERY)
    @PostMapping
    public AjaxResult add(@RequestBody MoyinMemberCollect memberCollect) {
        return toAjax(memberCollectService.collectSpeakerAdd(memberCollect));
    }

    @PreAuthorize("@ss.hasPermi('moyin:collect:edit')")
    @Log(title = "用户收藏编辑", businessType = BusinessType.QUERY)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinMemberCollect memberCollect) {
        return toAjax(memberCollectService.collectSpeakerEdit(memberCollect));
    }
}
