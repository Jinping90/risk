package com.wjl.fcity.cms.controller;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.request.FeedbackArg;
import com.wjl.fcity.cms.entity.vo.Response;
import com.wjl.fcity.cms.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 杨洋
 * @date 2018/4/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController {
    @Resource
    private FeedbackService feedbackService;

    /**
     * @param feedbackArg 参数
     * @return response
     */
    @PostMapping("/list")
    public Response feedbackList(@RequestBody FeedbackArg feedbackArg) {

        if (feedbackArg.getPage() == null) {
            feedbackArg.setPage(1);
        }

        if (feedbackArg.getSize() == null) {
            feedbackArg.setSize(20);
        }

        log.info("【问题建议反馈】feedbackArg={}",ReflectionToStringBuilder.toString(feedbackArg, ToStringStyle.MULTI_LINE_STYLE));

        Map<String, Object> feedbackMap = feedbackService.getFeedback(feedbackArg);

        return new Response<>(CodeEnum.SUCCESS, feedbackMap);
    }
}
