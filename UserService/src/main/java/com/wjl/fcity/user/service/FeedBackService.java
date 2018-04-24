package com.wjl.fcity.user.service;

/**
 * @author 杨洋
 * @date 2018/4/3
 */
public interface FeedBackService {
    /**
     * 保存用户反馈
     *
     * @param userId          用户id
     * @param questionType    反馈类型
     * @param questionContent 反馈内容
     */
    void saveFeedBack(Long userId, Integer questionType, String questionContent);
}
