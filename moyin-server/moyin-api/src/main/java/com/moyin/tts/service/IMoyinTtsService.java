package com.moyin.tts.service;

import com.moyin.tts.model.vo.MoyinTtsResultVo;
import com.moyin.tts.model.vo.MoyinTtsRequestVo;

/**
 * 魔音文本转语音服务
 *
 * @author 31734
 */
public interface IMoyinTtsService {

    /**
     * 返回多音字
     *
     * @param word 单字
     * @return 返回多音字
     */
    String getWords(String word);

    /**
     * 返回数值读法
     *
     * @param word 单字
     * @return 返回数值读法
     */
    String tnList(String word);

    /**
     * 简单的文本转语音
     *
     * @param text 需要生成语音的文字
     * @return 返回配音数据
     */
    MoyinTtsResultVo textToSpeech(String text);

    /**
     * ssml标记转语音
     *
     * @param ssml ssml标记
     * @return 配音数据
     */
    MoyinTtsResultVo ssmlToSpeech(String ssml);

    /**
     * 文本转语音
     *
     * @param requestVo 文本转语音
     * @return 语音
     */
    MoyinTtsResultVo textToSpeech(MoyinTtsRequestVo requestVo);

    /**
     * 临时保存数据
     *
     * @return 保存结果
     */
    int saveMoyinTts();

    int getLimitCount();
}
