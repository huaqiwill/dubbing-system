package com.moyin.tts.controller;

import com.moyin.common.annotation.Log;
import com.moyin.common.core.controller.BaseController;
import com.moyin.common.core.domain.AjaxResult;
import com.moyin.common.core.page.TableDataInfo;
import com.moyin.common.core.redis.RedisCache;
import com.moyin.common.enums.BusinessType;
import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinTtsRecord;
import com.moyin.tts.model.vo.MoyinTtsRequestVo;
import com.moyin.tts.model.vo.MoyinTtsResultVo;
import com.moyin.tts.service.IMoyinAudioService;
import com.moyin.tts.service.IMoyinTtsService;
import com.moyin.tts.service.IMoyinTtsRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 魔音配音生成接口
 *
 * @author 31734
 */
@Slf4j
@RestController
@RequestMapping("/moyin/tts")
public class MoyinTtsController extends BaseController {

    @Resource
    private IMoyinTtsService ttsService;

    @Resource
    private IMoyinAudioService audioService;

    @Resource
    private IMoyinTtsRecordService ttsRecordService;

    @Resource
    private RedisCache redisCache;

    @PreAuthorize("@ss.hasPermi('moyin:tts:remain')")
    @Log(title = "查询剩余字符", businessType = BusinessType.QUERY)
    @GetMapping("/remain")
    public AjaxResult remain() {
        AjaxResult ajaxResult = AjaxResult.success();
        String key = "tts-limit:" + getUserId();
        Integer length = 0;
        if (redisCache.hasKey(key)) {
            length = redisCache.getCacheObject(key);
        }
        int ttsLimit = ttsService.getLimitCount();
        ajaxResult.put("used", length);
        ajaxResult.put("remain", ttsLimit - length);
        ajaxResult.put("limit", ttsLimit);
        return ajaxResult;
    }

    // 获取到今日零点的剩余时间（秒）
    private int getSecondsUntilEndOfDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime midnight = now.toLocalDate().atStartOfDay().plusDays(1);
        // 每天的秒数不会超过 86400，所以转换为 int 是安全的
        return (int) Duration.between(now, midnight).getSeconds();
    }
    
    @PreAuthorize("@ss.hasPermi('moyin:tts:tts')")
    @Log(title = "开始配音", businessType = BusinessType.QUERY)
    @PostMapping
    public AjaxResult tts(@RequestBody MoyinTtsRequestVo requestVo) {
        log.warn("提交数据：{}", requestVo);

        // 计算长度
        int dubbingLength = ttsRecordService.calculateDubbingLength(requestVo.getRawText());

        String key = "tts-limit:" + getUserId();
        if (!redisCache.hasKey(key)) {
            redisCache.setCacheObject(key, dubbingLength, getSecondsUntilEndOfDay(), TimeUnit.SECONDS);
        } else {
            int length = redisCache.getCacheObject(key);
            int total = length + dubbingLength;
            if (total > ttsService.getLimitCount()) {
                return AjaxResult.error("配音字符数已达上限");
            }
            redisCache.setCacheObject(key, total);
        }

        MoyinTtsResultVo ttsResult = ttsService.textToSpeech(requestVo);

        if (ttsResult == null) {
            return error("服务器请求错误");
        }

        if (!ttsResult.isSuccess()) {
            return error(ttsResult.getErrorMessage());
        }

        audioService.saveAudioData(ttsResult.getAudioUrl(), ttsResult.getAudioBytes());

        MoyinTtsRecord record = MoyinTtsRecord.builder()
                .speaker(requestVo.getSpeaker())
                .text(requestVo.getRawText())
                .ssml(requestVo.getText())
                .createTime(DateUtils.getNowDate())
                .createBy(getUsername())
                .build();

        ttsRecordService.insert(record);

        return AjaxResult.success("OK", ttsResult.getAudioUrl());
    }

    @PreAuthorize("@ss.hasPermi('moyin:tts:ttsRecordList')")
    @Log(title = "获取生成记录", businessType = BusinessType.QUERY)
    @GetMapping("/ttsRecordList")
    public TableDataInfo ttsRecordList(MoyinTtsRecord moyinTtsRecord) {
        startPage();
        moyinTtsRecord.setCreateBy(getUsername());
        List<MoyinTtsRecord> list = ttsRecordService.ttsRecordList(moyinTtsRecord);
        return getDataTable(list);
    }

    @DeleteMapping("/ttsRecordDelete/{id}")
    @PreAuthorize("@ss.hasPermi('moyin:tts:ttsRecordDelete')")
    @Log(title = "删除生成记录", businessType = BusinessType.DELETE)
    public AjaxResult ttsRecordDelete(@PathVariable Long id) {
        return toAjax(ttsRecordService.deleteById(id));
    }

    /**
     * 下载配音
     *
     * @param url 配音定位符
     * @return 配音资源
     */
    @GetMapping("/audition/{url}")
    public ResponseEntity<byte[]> downloadAudition(@PathVariable String url) {

        byte[] data = audioService.getAudioData(url);

        HttpHeaders headers = new HttpHeaders();
        // 设置下载时的文件名
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file.wav\"");
        // 设置Content-Type为audio/mpeg
        // mp3、wav
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/wav");

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    /**
     * 下载视频
     *
     * @param url 配音定位符
     * @return 配音资源
     */
    @GetMapping("/video/{url}")
    public ResponseEntity<byte[]> downloadVideo(@PathVariable String url) {

        byte[] data = audioService.getAudioData(url);

        HttpHeaders headers = new HttpHeaders();
        // 设置下载时的文件名
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file.mp3\"");
        // 设置Content-Type为audio/mpeg
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }


    /**
     * 下载字幕
     *
     * @param url 配音定位符
     * @return 配音资源
     */
    @GetMapping("/url/{url}")
    public ResponseEntity<byte[]> downloadSrt(@PathVariable String url) {

        byte[] data = audioService.getAudioData(url);

        HttpHeaders headers = new HttpHeaders();
        // 设置下载时的文件名
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"file.mp3\"");
        // 设置Content-Type为audio/mpeg
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }


    /**
     * 获取多音字的列表信息
     *
     * @param word 字符
     * @return 获取多音字的列表信息
     */
    @GetMapping("/getWords")
    @PreAuthorize("@ss.hasPermi('moyin:tts:category')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    public AjaxResult getWords(String word) {
        return AjaxResult.success("获取成功", ttsService.getWords(word));
    }

    /**
     * 数字的读音
     *
     * @param word 数字
     * @return 数字的读音
     */
    @GetMapping("/tnList")
    @PreAuthorize("@ss.hasPermi('moyin:tts:tnList')")
    @Log(title = "配音员分类", businessType = BusinessType.EXPORT)
    public AjaxResult tnList(String word) {
        return AjaxResult.success("获取成功", ttsService.tnList(word));
    }

    /**
     * 临时保存文本内容
     */
    @PostMapping("/save")
    @PreAuthorize("@ss.hasPermi('moyin:tts:tnList')")
    @Log(title = "临时保存文本内容", businessType = BusinessType.INSERT)
    public AjaxResult save() {
        return toAjax(ttsService.saveMoyinTts());
    }

    /**
     * 查询敏感词
     *
     * @return 查询敏感词
     */
    @PostMapping("/findSensitiveWords")
    @PreAuthorize("@ss.hasPermi('moyin:tts:tnList')")
    @Log(title = "查询敏感期", businessType = BusinessType.OTHER)
    public AjaxResult findSensitiveWords() {

        return AjaxResult.success();
    }
}
