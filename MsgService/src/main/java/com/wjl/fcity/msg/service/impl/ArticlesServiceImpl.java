package com.wjl.fcity.msg.service.impl;

import com.wjl.fcity.msg.dto.MessageDTO;
import com.wjl.fcity.msg.mapper.ArticlesMapper;
import com.wjl.fcity.msg.model.Articles;
import com.wjl.fcity.msg.service.ArticlesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuhaihong
 * @date 2018-03-29 14:33
 **/
@Slf4j
@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Resource
    private ArticlesMapper consultingInfoMapper;

    /**
     * 更新阅读人数
     *
     * @param id 资讯id
     */
    @Override
    public void updateConsultingInfo(Long id) {

        consultingInfoMapper.updateConsultingInfo(id);
    }

    /**
     * 查询资讯
     *
     * @param messageDTO 参数
     * @return list
     */
    @Override
    public List<Articles> listConsultingInfo(MessageDTO messageDTO) {

        return consultingInfoMapper.listConsultingInfo(messageDTO);
    }

    /**
     * 统计资讯
     *
     * @param messageDTO 参数
     * @return integer
     */
    @Override
    public Integer countConsultingInfo(MessageDTO messageDTO) {

        return consultingInfoMapper.countConsultingInfo(messageDTO);
    }

    /**
     * 查询资讯 详情
     * 每次查询详情 表示已读 更新已读次数  +1
     *
     * @param id 资讯id
     * @return 资讯
     */
    @Override
    public Articles getConsultingInfo(Long id) {
        consultingInfoMapper.updateConsultingInfo(id);
        return consultingInfoMapper.getConsultingInfo(id);
    }
}
