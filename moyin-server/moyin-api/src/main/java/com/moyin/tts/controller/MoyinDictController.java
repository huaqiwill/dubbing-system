package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.tts.model.vo.MoyinDictVo;
import com.moyin.tts.model.vo.MoyinEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerEmotionVo;
import com.moyin.tts.model.vo.MoyinSpeakerVo;
import com.moyin.tts.service.IMoyinDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/moyin/dict")
public class MoyinDictController extends BaseController {
    @Resource
    private IMoyinDictService dictService;

    @GetMapping("/userNameList")
    public TableDataInfo userNameList() {
        List<MoyinDictVo> list = dictService.userNameList();
        return getDataTable(list);
    }

    @GetMapping("/speakerNameList")
    public TableDataInfo speakerNameList() {
        List<MoyinDictVo> list = dictService.speakerNameList();
        return getDataTable(list);
    }

    @GetMapping("/lexiconNameList")
    public TableDataInfo lexiconNameList() {
        List<MoyinDictVo> list = dictService.lexiconNameList();
        return getDataTable(list);
    }

    @GetMapping("/domainNameList")
    public TableDataInfo domainNameList() {
        List<MoyinDictVo> list = dictService.domainNameList();
        return getDataTable(list);
    }

    @GetMapping("/languageNameList")
    public TableDataInfo languageNameList() {
        List<MoyinDictVo> list = dictService.languageNameList();
        return getDataTable(list);
    }

    @GetMapping("/emotionNameList")
    public TableDataInfo emotionNameList() {
        List<MoyinDictVo> list = dictService.emotionNameList();
        return getDataTable(list);
    }

    @GetMapping("/emotionList")
    public TableDataInfo emotionList() {
        List<MoyinEmotionVo> list = dictService.emotionList();
        return getDataTable(list);
    }

    @GetMapping("/speakerList")
    public TableDataInfo speakerList() {
        List<MoyinSpeakerVo> list = dictService.speakerList();
        return getDataTable(list);
    }

    @Log(title = "收藏列表", businessType = BusinessType.QUERY)
    @GetMapping("/getSpeakerEmotionList/{speakerId}")
    public TableDataInfo getSpeakerEmotionList(@PathVariable Long speakerId) {
        List<MoyinSpeakerEmotionVo> list = dictService.getSpeakerEmotionList(speakerId);
        return getDataTable(list);
    }
}
