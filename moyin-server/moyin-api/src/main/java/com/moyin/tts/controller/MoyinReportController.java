package com.moyin.tts.controller;


import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.model.vo.UserTtsVo;
import com.moyin.tts.service.IMoyinReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moyin/report")
public class MoyinReportController extends BaseController {

    @Resource
    private IMoyinReportService reportService;

    @PreAuthorize("@ss.hasPermi('moyin:report:info')")
    @Log(title = "首页报告", businessType = BusinessType.QUERY)
    @GetMapping
    public AjaxResult info() {
        AjaxResult ajaxResult = AjaxResult.success();

        Map<String, Object> map = new HashMap<>();
        map.put("userCount", reportService.getUserCount());
        map.put("userOnlineCount", reportService.getUserOnlineCount());
        map.put("speakerCount", reportService.getSpeakerCount());
        map.put("styleCount", reportService.getEmotionCount());
        map.put("ttsCount", reportService.getTtsCount());
        map.put("ttsTextCount", reportService.getTtsTextCount());
        ajaxResult.put("data", map);

        return ajaxResult;
    }

    @PreAuthorize("@ss.hasPermi('moyin:report:getUserTodayStatistics')")
    @Log(title = "获取用户当天报告", businessType = BusinessType.QUERY)
    @GetMapping("/getUserTodayStatistics")
    public TableDataInfo getUserTodayStatistics() {
        startPage();

        List<UserTtsVo> list = reportService.getUserList();

        list.forEach(userTtsVo -> {
            // 此处bug以后需要修复 ===
            userTtsVo.setTtsCount(reportService.getTodayTtsCountByUserId(userTtsVo.getUserName()));
            // ===
            userTtsVo.setTtsRemainCount(reportService.getTodayTtsRemainCountByUserId(userTtsVo.getUserId()));
            userTtsVo.setTtsTextCount(reportService.getTodayTtsTextCountByUserId(userTtsVo.getUserId()));
        });

        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:report:getUserTotalStatistics')")
    @Log(title = "获取用户当月报告", businessType = BusinessType.QUERY)
    @GetMapping("/getUserTotalStatistics")
    public TableDataInfo getUserTotalStatistics() {

        startPage();

        List<UserTtsVo> list = reportService.getUserList();

        list.forEach(userTtsVo -> {
            // 此处bug以后需要修复 ===
            userTtsVo.setTtsCount(reportService.getMonthTtsCountByUserId(userTtsVo.getUserName()));
            // ===
            userTtsVo.setTtsRemainCount(reportService.getMonthTtsRemainCountByUserId(userTtsVo.getUserName()));
            userTtsVo.setTtsTextCount(reportService.getMonthTtsTextCountByUserId(userTtsVo.getUserName()));
        });

        return getDataTable(list);
    }
}
