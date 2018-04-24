package com.wjl.fcity.cms.controller;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.request.UserReq;
import com.wjl.fcity.cms.entity.vo.Response;
import com.wjl.fcity.cms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户
 * @author shengju
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/user/getUserList")
    public Response getUserList(@RequestBody UserReq userReq) {
        if (userReq.getPage() == null) {
            userReq.setPage(1);
        }
        if (userReq.getSize() == null) {
            userReq.setSize(20);
        }
        Map<String, Object> resultMap = userService.getUserList(userReq);
        return new Response<>(CodeEnum.SUCCESS, resultMap);
    }

}
