package com.wjl.fcity.msg.service;

import com.wjl.fcity.msg.dto.MessageDTO;
import com.wjl.fcity.msg.model.Articles;

import java.util.List;

/**
 * @author xuhaihong
 * @date 2018-03-29 14:31
 **/
public interface ArticlesService {

    /**
     *  更新阅读人数
     *  @param id 资讯id
     */
    void updateConsultingInfo(Long  id);

    /**
     * 查询资讯
     * @param messageDTO 参数
     * @return list
     */
    List<Articles> listConsultingInfo(MessageDTO messageDTO);

    /**
     * 统计资讯
     * @param messageDTO 参数
     * @return integer
     */
    Integer countConsultingInfo(MessageDTO messageDTO);

    /**
     * 查询资讯 详情
     * @param id  资讯id
     * @return 资讯
     */
    Articles getConsultingInfo(Long id);

}
