package com.moyin.tts.mapper;

import com.moyin.tts.domain.MoyinMemberCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员收藏配音员
 *
 * @author 31734
 */
public interface MoyinMemberCollectMapper {

    int collectSpeakerEdit(MoyinMemberCollect memberCollectSpeaker);

    int collectSpeakerRemoveById(Long id);

    int collectSpeakerRemoveBySpeakerId(@Param("speakerId") Long speakerId, @Param("userId") Long userId);

    int collectSpeakerAdd(MoyinMemberCollect memberCollectSpeaker);

    List<MoyinMemberCollect> collectSpeakerList(MoyinMemberCollect collectSpeaker);

    MoyinMemberCollect collectSpeakerDetail(Long id);
}
