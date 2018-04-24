package com.wjl.fcity.cms.service;

import com.wjl.fcity.cms.entity.request.FeedbackArg;

import java.util.Map;

/**
 * @author : Fy
 * @date : 2018-04-19 16:01
 */
public interface FeedbackService {
    /**
     * 处理问题反馈
     *
     * @param feedbackArg 页面传递数据
     * @return Map
     */
    Map<String, Object> getFeedback(FeedbackArg feedbackArg);
}
