package com.wjl.fcity.msg.mapper;

import com.wjl.fcity.msg.dto.MessageDTO;
import com.wjl.fcity.msg.mapper.provider.ArticlesProvider;
import com.wjl.fcity.msg.model.Articles;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资讯
 * @author xuhaihong
 * @date 2018-03-29 13:18
 **/
@Repository
public interface ArticlesMapper {


    /**
     *  更新阅读人数
     *  @param id 资讯id
     */
    @Update("UPDATE articles d\n" +
            "SET d.number_of_readers = d.number_of_readers + 1\n" +
            "WHERE\n" +
            "\td.id = #{id}")
    void updateConsultingInfo(Long  id);

    /**
     * 查询资讯
     * @param messageDTO 参数
     * @return list
     */
    @SelectProvider(type = ArticlesProvider.class, method = "listConsultingInfo")
    List<Articles> listConsultingInfo(MessageDTO messageDTO);

    /**
     * 统计资讯
     * @param messageDTO 参数
     * @return integer
     */
    @SelectProvider(type = ArticlesProvider.class, method = "countConsultingInfo")
    Integer countConsultingInfo(MessageDTO messageDTO);

    /**
     * 查询资讯 详情
     * @param id  资讯id
     * @return 资讯
     */
    @SelectProvider(type = ArticlesProvider.class, method = "getConsultingInfo")
    Articles getConsultingInfo(Long id);


}
