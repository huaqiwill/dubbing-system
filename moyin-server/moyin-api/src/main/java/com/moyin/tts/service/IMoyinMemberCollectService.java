package com.moyin.tts.service;

import com.moyin.tts.domain.MoyinMemberCollect;

import java.util.List;

/**
 * 会员收藏配音员
 *
 * @author 31734
 */
public interface IMoyinMemberCollectService {

    List<MoyinMemberCollect> collectSpeakerList(MoyinMemberCollect collectSpeaker);

    int collectSpeakerAdd(MoyinMemberCollect memberCollectSpeaker);

    int collectSpeakerRemove(Long speakerId, Long userId);

    int collectSpeakerRemove(Long id);

    int collectSpeakerEdit(MoyinMemberCollect memberCollectSpeaker);

    MoyinMemberCollect collectSpeakerDetail(Long id);

}
