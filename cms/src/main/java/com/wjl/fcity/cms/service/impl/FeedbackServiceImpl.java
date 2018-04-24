package com.wjl.fcity.cms.service.impl;

import com.wjl.fcity.cms.entity.request.FeedbackArg;
import com.wjl.fcity.cms.entity.vo.FeedbackVo;
import com.wjl.fcity.cms.mapper.FeedbackMapper;
import com.wjl.fcity.cms.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Fy
 * @date : 2018-04-19 16:02
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    /**
     * 处理问题反馈
     *
     * @param feedbackArg 页面传递数据
     * @return Map
     */
    @Override
    public Map<String, Object> getFeedback(FeedbackArg feedbackArg) {

        List<FeedbackVo> feedbackVoList = feedbackMapper.getFeedback(feedbackArg);

        //查询总数
        int countFeedback = feedbackMapper.getCountFeedback(feedbackArg);

        Map<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("total", countFeedback);
        resultMap.put("size", feedbackArg.getSize());
        resultMap.put("page", feedbackArg.getPage());
        resultMap.put("data", feedbackVoList);
        return resultMap;

    }
}
