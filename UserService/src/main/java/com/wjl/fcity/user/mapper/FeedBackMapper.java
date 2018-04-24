package com.wjl.fcity.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author 杨洋
 * @date 2018/4/3
 */
@Repository
public interface FeedBackMapper {

    /**
     * 保存用户反馈
     *
     * @param userId          用户id
     * @param questionType    反馈类型
     * @param questionContent 反馈内容
     * @param gmtCreated      创建时间
     */
    @Insert("INSERT INTO question_feedback (user_id,question_type,question_content,gmt_created) VALUES (#{userId},#{questionType},#{questionContent},#{gmtCreated})")
    void saveFeedBack(@Param("userId") Long userId, @Param("questionType") Integer questionType, @Param("questionContent") String questionContent, @Param("gmtCreated") Date gmtCreated);
}
