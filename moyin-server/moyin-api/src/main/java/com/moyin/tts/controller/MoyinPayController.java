package com.moyin.tts.controller;

import com.moyin.common.core.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class MoyinPayController extends BaseController {
    @GetMapping
    public void pay() {

    }

    @PostMapping
    public void notifyPay() {

    }


}
