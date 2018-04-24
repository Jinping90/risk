package com.wjl.fcity.user.controller;

import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.util.StringUtil;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.service.FeedBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 问题反馈接口
 *
 * @author 杨洋
 * @date 2018/4/3
 */
@Slf4j
@RestController
@RequestMapping("/feedback/auth")
public class FeedBackController {
    @Resource
    private FeedBackService feedBackService;

    /**
     * 添加用户反馈
     *
     * @param request request
     * @param params  questionType 问题类型
     *                questionContent 问题内容
     * @return response
     */
    @PostMapping("/saveFeedBack")
    public Response saveFeedBack(HttpServletRequest request, @RequestBody Map<String, String> params) {
        String userId = "userId";
        String questionType = "questionType";
        String questionContent = "questionContent";

        if (StringUtil.isBlank(request.getHeader(userId))) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "缺失userId或token");
        }
        if (StringUtil.isBlank(params.get(questionType))) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "请选择问题类型");
        }
        if (StringUtil.isBlank(params.get(questionContent))) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "请填写问题反馈");
        }

        try {
            feedBackService.saveFeedBack(Long.valueOf(request.getHeader("userId")), Integer.valueOf(params.get("questionType")), params.get("questionContent"));
        } catch (Exception e) {
            log.error("添加用户问题失败,原因:", e);
            return new Response<>(CodeEnum.SYS_UNKNOWN, "保存失败");
        }

        return new Response<>(CodeEnum.SUCCESS, "您所填写的问题已经提交成功");
    }
}
