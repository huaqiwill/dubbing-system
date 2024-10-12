package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.service.IMoyinMemberSpendingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/moyin/spending")
public class MoyinMemberSpendingController extends BaseController {

    @Resource
    private IMoyinMemberSpendingService memberSpendingService;

    @PreAuthorize("@ss.hasPermi('moyin:notice:list')")
    @Log(title = "消费记录列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public void list() {

    }
}
