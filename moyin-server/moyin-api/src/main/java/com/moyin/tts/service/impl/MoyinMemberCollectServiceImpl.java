package com.moyin.tts.service.impl;

import com.moyin.tts.domain.MoyinMemberCollect;
import com.moyin.tts.mapper.MoyinMemberCollectMapper;
import com.moyin.tts.service.IMoyinMemberCollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员收藏配音员
 *
 * @author 31734
 */
@Service
public class MoyinMemberCollectServiceImpl implements IMoyinMemberCollectService {

    @Resource
    private MoyinMemberCollectMapper memberCollectMapper;

    @Override
    public List<MoyinMemberCollect> collectSpeakerList(MoyinMemberCollect collectSpeaker) {
        return memberCollectMapper.collectSpeakerList(collectSpeaker);
    }

    @Override
    public int collectSpeakerAdd(MoyinMemberCollect memberCollectSpeaker) {
        return memberCollectMapper.collectSpeakerAdd(memberCollectSpeaker);
    }

    @Override
    public int collectSpeakerRemove(Long speakerId, Long userId) {
        return memberCollectMapper.collectSpeakerRemoveBySpeakerId(speakerId, userId);
    }

    @Override
    public int collectSpeakerRemove(Long id) {
        return memberCollectMapper.collectSpeakerRemoveById(id);
    }

    @Override
    public int collectSpeakerEdit(MoyinMemberCollect memberCollectSpeaker) {
        return memberCollectMapper.collectSpeakerEdit(memberCollectSpeaker);
    }

    @Override
    public MoyinMemberCollect collectSpeakerDetail(Long id) {
        return memberCollectMapper.collectSpeakerDetail(id);
    }


}
