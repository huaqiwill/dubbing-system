package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.poi.ExcelUtil;
import com.moyin.tts.domain.MoyinMemberCollect;
import com.moyin.tts.domain.MoyinSpeaker;
import com.moyin.tts.model.dto.SpeakerDataDTO;
import com.moyin.tts.model.vo.MoyinSpeakerJustShowVo;
import com.moyin.tts.service.IMoyinMemberCollectService;
import com.moyin.tts.service.IMoyinSpeakerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.moyin.common.utils.PageUtils.startPage;

/*
如何管理缓存：

1. 二级分页
    首次获取少量数据
2. 二次获取单条数据
    当用户点击的时候获取单条数据

Map<Key,Value>
当 服务器 启动的时候 同步缓存 Map<Key,Value> 保证和数据库数据的一致性
当 新增 的时候在 Map<Key,Value>中新增一条
当 删除 的时候在 Map<Key,Value>中移除一条
当 更新 的时候在 Map<Key,Value>中更新一条
当 获取全部 的时候直接返回 Map<Key,Value>

List<Value>
当 服务器 启动的时候 同步 所有的 List 并且设置
当 新增 的时候在 List 中新增一条，并且维护 List:all
当 删除 的时候在 List 中删除一条，并且维护 List:all
当 更新 的时候在 List 中更新一条
当 获取全部 的时候 List:all 获取全部的 Key，再通过 Key 获取每条数据，通过缓存获取每条数据并返回集合

对于权限的管理
新增 add
编辑 edit
详情
移除 remove
 */

@RestController
@RequestMapping("/moyin/speaker")
@Slf4j
public class MoyinSpeakerController extends BaseController {

    @Resource
    private IMoyinSpeakerService speakerService;

    @Resource
    private IMoyinMemberCollectService memberCollectSpeakerService;

    @PostMapping
    @PreAuthorize("@ss.hasPermi('moyin:speaker:add')")
    @Log(title = "配音员上架", businessType = BusinessType.INSERT)
    public AjaxResult add(@RequestBody MoyinSpeaker artist) {
        return toAjax(speakerService.insert(artist));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('moyin:speaker:remove')")
    @Log(title = "配音员下架", businessType = BusinessType.DELETE)
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(speakerService.deleteById(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:edit')")
    @Log(title = "配音员编辑", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MoyinSpeaker speaker) {
        return toAjax(speakerService.updateById(speaker));
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:alias')")
    @Log(title = "配音员编辑别名", businessType = BusinessType.UPDATE)
    @PutMapping("/alias")
    public AjaxResult aliasEdit(@RequestBody MoyinSpeaker speaker) {
        if (speaker.getId() == null) {
            return error("参数ID不能为空");
        }
        return toAjax(speakerService.updateAliasById(speaker));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermi('moyin:speaker:info')")
    @Log(title = "配音员详情", businessType = BusinessType.QUERY)
    public AjaxResult getInfo(@PathVariable Long id) {
        MoyinSpeaker speaker = speakerService.selectById(id);
        return success(speaker);
    }

    /**
     * 这里使用post请求
     */
    @PreAuthorize("@ss.hasPermi('moyin:speaker:list')")
    @Log(title = "配音员列表", businessType = BusinessType.QUERY)
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody MoyinSpeaker speaker) {
        startPage();
        List<MoyinSpeaker> list = speakerService.selectList(speaker);
        return getDataTable(list);
    }

    /**
     * 这里使用get请求、前台
     */
    @PreAuthorize("@ss.hasPermi('moyin:speaker:list')")
    @Log(title = "配音员列表", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo listData(MoyinSpeaker speaker) {
        startPage();
        List<MoyinSpeaker> list = speakerService.selectList(speaker);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:listJustShow')")
    @Log(title = "前台配音员列表", businessType = BusinessType.QUERY)
    @GetMapping("/listJustShow")
    public TableDataInfo listJustShow(SpeakerDataDTO speakerDataDTO) {
        // 通过语言、领域、情绪筛选，通过名称模糊搜索
        speakerDataDTO.setUserId(getUserId());
        List<MoyinSpeakerJustShowVo> list = speakerService.selectSpeakerListBySpeakerDataDTO(speakerDataDTO);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:listAll')")
    @Log(title = "配音员列表", businessType = BusinessType.QUERY)
    @GetMapping("/listAll")
    public TableDataInfo listAll(MoyinSpeaker speaker) {
//        List<MoyinSpeaker> list = speakerService.selectListAll(speaker);
        List<MoyinSpeakerJustShowVo> list = speakerService.selectListJustShow(speaker, getUserId());
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:export')")
    @Log(title = "配音员导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MoyinSpeaker moyinCategory) {
        List<MoyinSpeaker> list = speakerService.selectList(moyinCategory);
        ExcelUtil<MoyinSpeaker> util = new ExcelUtil<>(MoyinSpeaker.class);
        util.exportExcel(response, list, "配音员分类数据");
    }

    @PostMapping("/domain")
    @Log(title = "获取收藏列表", businessType = BusinessType.QUERY)
    @PreAuthorize("@ss.hasPermi('member:collect:speaker:list')")
    public AjaxResult insertDomain(Long domainId, Long speakerId) {
        return toAjax(speakerService.insertDomain(domainId, speakerId));
    }

    @DeleteMapping("/domain")
    @Log(title = "获取收藏列表", businessType = BusinessType.QUERY)
    @PreAuthorize("@ss.hasPermi('member:collect:speaker:list')")
    public AjaxResult deleteDomain(Long domainId, Long speakerId) {
        return toAjax(speakerService.deleteDomain(domainId, speakerId));
    }

    @PostMapping("/language")
    @Log(title = "获取收藏列表", businessType = BusinessType.QUERY)
    @PreAuthorize("@ss.hasPermi('member:collect:speaker:list')")
    public AjaxResult insertLanguage(Long languageId, Long speakerId) {
        return toAjax(speakerService.insertLanguage(languageId, speakerId));
    }

    @DeleteMapping("/language")
    @Log(title = "获取收藏列表", businessType = BusinessType.QUERY)
    @PreAuthorize("@ss.hasPermi('member:collect:speaker:list')")
    public AjaxResult deleteLanguage(Long languageId, Long speakerId) {
        return toAjax(speakerService.deleteLanguage(languageId, speakerId));
    }

    @PostMapping("/emotion")
    @Log(title = "获取收藏列表", businessType = BusinessType.QUERY)
    @PreAuthorize("@ss.hasPermi('member:collect:speaker:list')")
    public AjaxResult insertEmotion(Long emotionId, Long speakerId) {
        return toAjax(speakerService.insertEmotion(emotionId, speakerId));
    }

    @DeleteMapping("/emotion")
    @Log(title = "获取收藏列表", businessType = BusinessType.QUERY)
    @PreAuthorize("@ss.hasPermi('member:collect:speaker:list')")
    public AjaxResult deleteEmotion(Long emotionId, Long speakerId) {
        return toAjax(speakerService.deleteEmotion(emotionId, speakerId));
    }

    // collect ====


    @PreAuthorize("@ss.hasPermi('moyin:speaker:collect:list')")
    @Log(title = "收藏列表", businessType = BusinessType.QUERY)
    @GetMapping("/collect/list")
    public TableDataInfo collectList() {
        List<MoyinSpeakerJustShowVo> list = speakerService.getCollectList(getUserId());
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:collect:add')")
    @Log(title = "收藏新增", businessType = BusinessType.INSERT)
    @PostMapping("/collect")
    public AjaxResult collectAdd(@RequestBody MoyinMemberCollect memberCollectSpeaker) {
        memberCollectSpeaker.setUserId(getUserId());
        return toAjax(memberCollectSpeakerService.collectSpeakerAdd(memberCollectSpeaker));
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:collect:addlist')")
    @Log(title = "收藏详情", businessType = BusinessType.QUERY)
    @GetMapping("/collect/{id}")
    public AjaxResult collectInfo(@PathVariable Long id) {
        return success(memberCollectSpeakerService.collectSpeakerDetail(id));
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:collect:remove')")
    @Log(title = "收藏移除", businessType = BusinessType.QUERY)
    @DeleteMapping("/collect/{speakerId}")
    public AjaxResult collectDelete(@PathVariable Long speakerId) {
        return success(memberCollectSpeakerService.collectSpeakerRemove(speakerId, getUserId()));
    }

    @PreAuthorize("@ss.hasPermi('member:collect:speaker:list')")
    @Log(title = "收藏编辑", businessType = BusinessType.QUERY)
    @PutMapping("/collect")
    public AjaxResult collectEdit(@RequestBody MoyinMemberCollect memberCollectSpeaker) {
        return toAjax(memberCollectSpeakerService.collectSpeakerEdit(memberCollectSpeaker));
    }

    // recent ====

    @PreAuthorize("@ss.hasPermi('moyin:speaker:recent:add')")
    @Log(title = "配音员最近新增", businessType = BusinessType.INSERT)
    @PostMapping("/recent")
    public AjaxResult recentAdd(@RequestBody MoyinSpeaker moyinSpeaker) {
        if (moyinSpeaker.getId() == null) {
            return AjaxResult.error("缺少参数ID");
        }
        speakerService.recentAdd(moyinSpeaker, getUserId());
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('moyin:speaker:recent:list')")
    @Log(title = "配音员最近列表", businessType = BusinessType.QUERY)
    @GetMapping("/recent/list")
    public TableDataInfo recentList() {
        List<MoyinSpeaker> speakers = speakerService.recentList(getUserId());
        return getDataTable(speakers);
    }

}
