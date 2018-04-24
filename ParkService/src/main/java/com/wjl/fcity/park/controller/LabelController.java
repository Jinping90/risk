package com.wjl.fcity.park.controller;

import com.wjl.fcity.park.common.enums.CodeEnum;
import com.wjl.fcity.park.common.util.StringUtil;
import com.wjl.fcity.park.dto.LabelDTO;
import com.wjl.fcity.park.dto.MyLabelDTO;
import com.wjl.fcity.park.dto.Response;
import com.wjl.fcity.park.model.Label;
import com.wjl.fcity.park.service.LabelService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 公园(标签页)Controller
 *
 * @author 杨洋
 * @date 2018/3/27
 */
@Slf4j
@RestController
@RequestMapping("/label/auth")
public class LabelController {
    @Resource
    private LabelService labelService;

    private String userIdStr = "userId";
    private String mobileStr = "mobile";


    /**
     * 获取标签列表接口
     *
     * @return Response
     */
    @PostMapping(value = "/getLabels")
    public Response getLabels() {
        List<Label> labels = labelService.getLabels();
        return new Response<>(CodeEnum.SUCCESS, labels);
    }

    /**
     * 新增标签接口
     *
     * @param request request
     * @param params: mobile   被标记人的号码
     *                nickName 被标记人的昵称
     *                labels   标签
     * @return Response
     */
    @PostMapping(value = "/addLabel")
    public Response addLabel(HttpServletRequest request, @RequestBody Map<String, String> params) {
        String nickNameStr = "nickName";
        String labels = "labels";

        Long userId = Long.valueOf(request.getHeader(userIdStr));
        if (StringUtil.isEmpty(params.get(mobileStr))) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "手机号不能为空");
        }
        if (StringUtil.isEmpty(params.get(nickNameStr))) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "昵称不能为空");
        }
        if (StringUtil.isEmpty(params.get(labels))) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "标签不能为空");
        }
        String[] labelsStr = params.get(labels).split(",");
        labelService.addLabel(userId, params.get(mobileStr), params.get(nickNameStr), Arrays.asList(labelsStr));
        return new Response<>(CodeEnum.SUCCESS, "");
    }

    /**
     * 获取用户已标好友列表
     *
     * @param request request
     * @return Response
     */
    @PostMapping(value = "/getLabeledList")
    public Response getLabeledList(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getHeader(userIdStr));

        List<LabelDTO> labelList = labelService.getLabeledList(userId);
        labelList.forEach(label -> {
            String labelStr = label.getLabels();
            label.setLabels(labelStr.substring(0, labelStr.length() - 1));
        });

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("list", labelList);
        return new Response<>(CodeEnum.SUCCESS, map);
    }

    /**
     * 查询我被打的标签
     *
     * @param params mobile 用户手机号
     * @return Response
     */
    @PostMapping(value = "/getMyLabels")
    public Response getMyLabels(@RequestBody Map<String, String> params) {
        HashMap<String, Object> result = Maps.newHashMap();
        String mobile = params.get(mobileStr);
        Integer countMyLabels = labelService.countMyLabels(mobile);
        result.put("total", countMyLabels);
        if (0 == countMyLabels) {
            result.put("list", null);
            return new Response<>(CodeEnum.SUCCESS, result);
        }
        List<MyLabelDTO> myLabels = labelService.getMyLabels(mobile);
        result.put("list", myLabels);
        return new Response<>(CodeEnum.SUCCESS, result);
    }
}
