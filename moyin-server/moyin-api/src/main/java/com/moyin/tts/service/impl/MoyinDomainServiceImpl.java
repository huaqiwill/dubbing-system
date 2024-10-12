package com.moyin.tts.service.impl;

import com.moyin.common.utils.DateUtils;
import com.moyin.tts.domain.MoyinDomain;
import com.moyin.tts.mapper.MoyinDomainMapper;
import com.moyin.tts.service.IMoyinDomainService;
import com.moyin.tts.service.IMoyinEmotionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 配音员情绪Service业务层处理
 *
 * @author moyin
 * @date 2024-06-25
 */
@Service
public class MoyinDomainServiceImpl implements IMoyinDomainService {

    @Resource
    private MoyinDomainMapper domainMapper;

    /**
     * 查询配音员情绪
     *
     * @param id 配音员情绪主键
     * @return 配音员情绪
     */
    @Override
    public MoyinDomain selectById(Long id) {
        return domainMapper.selectById(id);
    }

    /**
     * 查询配音员情绪列表
     *
     * @param domain 配音员情绪
     * @return 配音员情绪
     */
    @Override
    public List<MoyinDomain> selectList(MoyinDomain domain) {
        return domainMapper.selectList(domain);
    }

    /**
     * 新增配音员情绪
     *
     * @param domain 配音员情绪
     * @return 结果
     */
    @Override
    public int insert(MoyinDomain domain) {
        domain.setCreateTime(DateUtils.getNowDate());
        return domainMapper.insert(domain);
    }

    /**
     * 修改配音员情绪
     *
     * @param domain 配音员情绪
     * @return 结果
     */
    @Override
    public int updateById(MoyinDomain domain) {
        domain.setUpdateTime(DateUtils.getNowDate());
        return domainMapper.update(domain);
    }

    /**
     * 批量删除配音员情绪
     *
     * @param ids 需要删除的配音员情绪主键
     * @return 结果
     */
    @Override
    public int deleteByIds(Long[] ids) {
        return domainMapper.deleteByIds(ids);
    }

    /**
     * 删除配音员情绪信息
     *
     * @param id 配音员情绪主键
     * @return 结果
     */
    @Override
    public int deleteById(Long id) {
        return domainMapper.deleteById(id);
    }
}
