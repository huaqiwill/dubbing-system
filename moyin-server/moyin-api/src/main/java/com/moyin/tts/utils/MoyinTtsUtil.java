package com.moyin.tts.utils;

import com.mobvoi.openapi.client.tts.TtsClient;
import com.mobvoi.openapi.model.tts.TtsRequest;

import java.io.File;

import com.moyin.tts.constant.Constants;
import com.moyin.tts.model.vo.MoyinTtsRequestVo;
import com.moyin.tts.service.IMoyinAudioService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;


@Slf4j
public class MoyinTtsUtil {

    private static IMoyinAudioService audioService;

    @Resource
    private IMoyinAudioService audioServiceTemp;

    public MoyinTtsUtil() {
        MoyinTtsUtil.audioService = audioServiceTemp;
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        TtsClient client = new TtsClient(Constants.APP_KEY, Constants.APP_SECRET);


        String text = "清晨，合租公寓。\n" +
                "“小陆，把我的内衣拿过来。”\n" +
                "浴室内，水汽弥漫，若隐若现地映照出一道曼妙的身影。\n" +
                "“洛小雪，你丫的过分了！”\n" +
                "陆丰睡眼惺忪的从卧室里冲了出来。\n" +
                "但那曼妙的身影就像是没听到陆丰的话，还在悠然自得的哼着小曲。\n" +
                "“呼……”\n" +
                "陆丰揉了揉眼睛，无奈的泄了口气，从阳台取下一件粉色的内衣，没好气的挂在浴室的门把手上。\n" +
                "“一天到晚就不能让我安静一会，下次我去买点芥末，就涂在最中间那个点上……”\n" +
                "就在他碎碎念时，浴室的门突然打开。\n";
        String ssml = "<speak version=\"1.0\" xml:lang=\"zh-CN\" xmlns=\"http://www.w3.org/2001/10/synthesis\">9月10日，庆祝2019年<w phoneme=\"jiao4 shi1 jie2\">教师节</w>暨全国教育系统先进集体和先进个人表彰大会在京举行。<break time=\"500ms\" />习近平总书记在人民大会堂亲切会见受表彰代表，<break time=\"500ms\" />向受到表彰的先进集体和先进个人表示热烈祝贺，<break time=\"500ms\" />向全国广大<p phoneme=\"jiao4\">教</p>师和教育工作者致以节日的问候。</speak>";
        ssml = "<ssml>\n" +
                "    <with name=\"zh-CN-XiaoxuanNeural\" role=\"YoungAdultFemale\" style=\"gentle\" speed=\"1\" pitch=\"0\">\n" +
                "        <speak version=\"1.0\" xml:lang=\"zh-CN\" xmlns=\"http://www.w3.org/2001/10/synthesis\">达摩打3212122我你1212好啊!我12的梦12121212打1212</speak>\n" +
                "    </with>\n" +
                "</ssml>";

        // 文字转语音
        speaker(client, ssml, false, 1.0f, "mp3", "moali_meet_24k", "tts_sample.mp3");

        // 文字转语音，带srt字幕文件生成
//    sample(client, text, true);

        // 使用ssml格式文字转语音
//    sample(client, ssml, false);
    }

    public static void speaker(TtsClient client, MoyinTtsRequestVo ttsRequest) {

    }

    public static void speaker(TtsClient client, String text) {
        speaker(client, text, false, 1.0f, "mp3", "billy_meet_24k@warm", "tts_sample.mp3");
    }

    public static void speaker(
            TtsClient client,
            String text,
            boolean genSrt,
            float speed,
            String audioType,
            String speaker,
            String savePath) {

        TtsRequest request = TtsRequest.builder()
                .text(text)
                .speaker(speaker)
                .audio_type(audioType)
                .speed(speed)
                // 停顿调节需要对appkey授权后才可以使用，授权前传参无效。
                .symbol_sil("semi_250,exclamation_300,question_250,comma_200,stop_300,pause_150,colon_200")
                // 忽略1000字符长度限制，需要对appkey授权后才可以使用
                .ignore_limit(true)
                // 是否生成srt字幕文件，默认不开启。如果开启生成字幕，需要额外计费。生成好的srt文件地址将通过response header中的srt_address字段返回。
                .gen_srt(genSrt)
                .build();

        try {
            Response response = client.tts(request);
            String contentType = response.header("Content-Type");
            log.info("api contentType={}", contentType);
            if ("audio/mpeg".equals(contentType)) {
                // 保存音频文件
                File audioFile = new File(savePath);
                assert response.body() != null;

                byte[] audioBytes = response.body().bytes();

                FileUtils.writeByteArrayToFile(audioFile, audioBytes);
                log.info("api audioFile={}", audioFile.getPath());

                // 下载srt字幕文件
                String srtAddress = response.header("srt_address");
                if (StringUtils.isNotBlank(srtAddress)) {
                    log.info("api srt_address={}", srtAddress);
                    Response srtResponse = client.srt(srtAddress);
                    File srtFile = new File("tts_sample.srt");

                    assert srtResponse.body() != null;
                    FileUtils.writeByteArrayToFile(srtFile, srtResponse.body().bytes());
                    log.info("api srtFile={}", srtFile.getPath());
                }
            } else {
                // ContentType 为 null 或者为 "application/json"
                String result = (response.body() != null ? response.body().string() : null);
                log.error("api failed, request={} response={}", request, result);
                log.error("返回结果：{}", result);
            }
        } catch (Exception e) {
            log.error("api Exception: ", e);
        }
    }
}
