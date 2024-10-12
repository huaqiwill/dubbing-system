package com.moyin.tts.service.impl;

import com.mobvoi.openapi.client.tts.TtsClient;
import com.mobvoi.openapi.model.tts.TtsRequest;
import com.moyin.tts.constant.Constants;
import com.moyin.tts.domain.MoyinAppKey;
import com.moyin.tts.mapper.MoyinAppKeyMapper;
import com.moyin.tts.mapper.MoyinTtsMapper;
import com.moyin.tts.mapper.MoyinTtsRecordMapper;
import com.moyin.tts.model.vo.MoyinTtsResultVo;
import com.moyin.tts.model.vo.MoyinTtsRequestVo;
import com.moyin.tts.service.IMoyinTtsService;
import com.moyin.tts.utils.Sha256Util;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 31734
 */
@Slf4j
@Service
public class MoyinTtsServiceImpl implements IMoyinTtsService {

    @Resource
    private MoyinTtsMapper ttsMapper;

    @Resource
    private MoyinTtsRecordMapper ttsRecordMapper;

    @Resource
    private MoyinAppKeyMapper appKeyMapper;

    @Override
    public MoyinTtsResultVo textToSpeech(MoyinTtsRequestVo requestVo) {

        MoyinAppKey appKey = appKeyMapper.getCurrentAppKey();

        log.warn("请求的appKey {}", appKey);

        if (appKey == null) {
            appKey = MoyinAppKey.builder()
                    .appKey(Constants.APP_KEY)
                    .appSecret(Constants.APP_SECRET)
                    .build();
        }

        TtsClient client = new TtsClient(appKey.getAppKey(), appKey.getAppSecret());
        TtsRequest request = TtsRequest.builder()
                .text(requestVo.getText())
                .speaker(requestVo.getSpeaker())
                .audio_type(requestVo.getAudioType())
                .speed(requestVo.getSpeed())
                .convert(requestVo.getConvert())
                // 停顿调节需要对appkey授权后才可以使用，授权前传参无效。
                .symbol_sil(requestVo.getSymbolSil())
                // 忽略1000字符长度限制，需要对appkey授权后才可以使用
                .ignore_limit(requestVo.isIgnoreLimit())
                // 是否生成srt字幕文件，默认不开启。如果开启生成字幕，需要额外计费。生成好的srt文件地址将通过response header中的srt_address字段返回。
                .gen_srt(requestVo.isGenSrt())
                .volume(requestVo.getVolume())
                .pitch(requestVo.getPitch())
                .rate(requestVo.getRate())
                .merge_symbol(requestVo.isMergeSymbol())
                .build();

        try {
            Response response = client.tts(request);
            String contentType = response.header("Content-Type");
            if ("audio/mpeg".equals(contentType) || "audio/wav".equals(contentType)) {
                // 保存音频文件
                assert response.body() != null;
                byte[] audioBytes = response.body().bytes();

                File audioFile = new File("D:/test.mp3");
                FileUtils.writeByteArrayToFile(audioFile, audioBytes);

                byte[] srtBytes = null;
                // 下载srt字幕文件
                String srtAddress = response.header("srt_address");
                if (StringUtils.isNotBlank(srtAddress)) {
                    Response srtResponse = client.srt(srtAddress);
                    assert srtResponse.body() != null;
                    srtBytes = srtResponse.body().bytes();
                }
                client.closeWebsocket();
                return MoyinTtsResultVo.builder()
                        .audioText(requestVo.getText())
                        .audioUrl(Sha256Util.getSha256Hash(audioBytes))
                        .audioBytes(audioBytes)
                        .srtBytes(srtBytes)
                        .success(true)
                        .build();
            }

            // ContentType 为 null 或者为 "application/json"
            String result = (response.body() != null ? response.body().string() : null);
            log.error("配音出现错误 {}", result);
            return MoyinTtsResultVo.builder()
                    .success(false)
                    .errorMessage(result)
                    .build();

        } catch (Exception e) {
            log.error("配音出现错误 {}", e.getMessage());

            client.closeWebsocket();

            return null;
        }
    }


    @Override
    public String getWords(String word) {
        // https://voice-maker-pc-nodetts.moyin.com/tts/getWords?lang=zh-cn&word=人&ww_token=1581ca2346e72c2a2bd3f6a1b72e67c0
        // 创建 OkHttpClient 实例
        OkHttpClient client = new OkHttpClient();

        // 构建请求
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse("https://voice-maker-pc-nodetts.moyin.com/tts/getWords"))
                .newBuilder()
                .addQueryParameter("lang", "zh-cn")
                .addQueryParameter("word", word)
                .addQueryParameter("ww_token", "1581ca2346e72c2a2bd3f6a1b72e67c0")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        // 发送请求并处理响应
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // 获取响应体并输出
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String tnList(String word) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse("https://voice-maker-pc-nodetts.moyin.com/tts/tnList"))
                .newBuilder()
                .addQueryParameter("word", word)
                .addQueryParameter("ww_token", "1581ca2346e72c2a2bd3f6a1b72e67c0")
                .build();

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            assert response.body() != null;
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public MoyinTtsResultVo textToSpeech(String text) {
        String symbolSil = "semi_250,exclamation_300,question_250,comma_200,stop_300,pause_150,colon_200";
        MoyinTtsRequestVo moyinTtsRequestVo = MoyinTtsRequestVo.builder()
                .text(text)
                .symbolSil(symbolSil)
                .genSrt(false)
                .speed(1.0f)
                .ignoreLimit(true)
                .audioType("mp3")
                .volume(100.0f)
                .speaker("billy_meet_24k@warm")
                .build();

        return textToSpeech(moyinTtsRequestVo);
    }

    @Override
    public MoyinTtsResultVo ssmlToSpeech(String ssml) {
        String symbolSil = "semi_250,exclamation_300,question_250,comma_200,stop_300,pause_150,colon_200";
        MoyinTtsRequestVo moyinTtsRequestVo = MoyinTtsRequestVo.builder()
                .text(ssml)
                .symbolSil(symbolSil)
                .genSrt(false)
                .speed(1.0f)
                .audioType("mp3")
                .speaker("moali_meet_24k")
                .build();
        return textToSpeech(moyinTtsRequestVo);
    }


    @Override
    public int saveMoyinTts() {
        return 0;
    }


    @Override
    public int getLimitCount() {
        Integer count = ttsMapper.getLimitCount();
        if (count == null) {
            return 6000;
        }
        // 默认6000字符
        return count;
    }
}
