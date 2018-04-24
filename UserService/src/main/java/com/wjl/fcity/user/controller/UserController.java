package com.wjl.fcity.user.controller;

import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.exception.BaseException;
import com.wjl.fcity.user.common.util.IpUtil;
import com.wjl.fcity.user.common.util.PhoneUtil;
import com.wjl.fcity.user.common.util.StringUtil;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.request.UserReq;
import com.wjl.fcity.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author czl
 */
@Slf4j
@RestController
@PropertySource({"classpath:basepath.properties"})
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 验证码登陆
     *
     * @param userReq userVo
     * @return response
     */
    @PostMapping(value = "/user/verifyCodeLogin")
    public Response vCodeLogin(@RequestBody UserReq userReq, HttpServletRequest request) {
        if (StringUtil.isBlank(userReq.getNoteToken())) {
            log.info(String.format("noteToken为空，用户:%s", userReq.getMobile()));
            return new Response<>(CodeEnum.V_CODE_ERR, null);
        }
        final String ip = IpUtil.getIp(request);
        return new Response<>(CodeEnum.SUCCESS, userService.vCodeLogin(userReq, ip));
    }

    /**
     * 登录接口
     *
     * @param userReq 用户登陆信息 用户设备号
     * @return Response
     */
    @PostMapping(value = "/user/login")
    public Response login(@RequestBody UserReq userReq, HttpServletRequest request) {

        final String ip = IpUtil.getIp(request);

        if (StringUtils.isEmpty(userReq.getMobile())) {
            log.info("手机号不能为空");
            return new Response<>(CodeEnum.AUTH_NO_MOBILE, null);
        }
        if (!PhoneUtil.isMobileNO(userReq.getMobile())) {
            log.info(String.format("手机号:%s,格式不正确", userReq.getMobile()));
            return new Response<>(CodeEnum.AUTH_MOBILE, null);
        }
        if (StringUtils.isEmpty(userReq.getPassword())) {
            log.info("用户%s密码为空", userReq.getMobile());
        }
        log.info(String.format("%s开始登录", userReq.getMobile()));
        if (StringUtils.isEmpty(userReq.getPassword())) {
            log.info(String.format("用户%s登录密码为空，登录失败，登录结束", userReq.getMobile()));
            return new Response<>(CodeEnum.AUTH_NO_PWD, null);
        }

        return new Response<>(CodeEnum.SUCCESS, userService.login(userReq, ip));

    }

    /**
     * 退出登录
     *
     * @param userReq userReq
     * @return Response
     */
    @PostMapping("/user/loginOut")
    public Response loginOut(@RequestBody UserReq userReq) {
        //退出登录
        return new Response<>(userService.loginOut(userReq.getUserId()), null);
    }

    /**
     * 注册用户
     *
     * @param userReq 手机号   短信验证码  验证码token 设备号
     * @return Response
     */
    @PostMapping("/user/regist")
    public Response register(@RequestBody UserReq userReq, HttpServletRequest request) throws IOException {

        final String ip = IpUtil.getIp(request);

        if (StringUtil.isBlank(userReq.getMobile())) {
            log.error("输入的手机号为空,注册失败,注册结束");
            throw new BaseException(CodeEnum.AUTH_NO_MOBILE);
        }
        if (StringUtil.isBlank(userReq.getVerifyCode())) {
            log.error(String.format("注册手机号%s,短信验证码为空，注册失败，注册结束", userReq.getMobile()));
            throw new BaseException(CodeEnum.AUTH_NO_V_CODE);
        }
        if (StringUtil.isBlank(userReq.getNoteToken())) {
            log.info(String.format("noteToken为空，用户:%s",userReq.getMobile()));
            throw new BaseException(CodeEnum.V_CODE_ERR);
        }
        //验证手机号是否合法
        if (!PhoneUtil.isMobileNO(userReq.getMobile())) {
            log.error(String.format("手机号%s，不符合手机号标注，注册失败, 注册结束", userReq.getMobile()));
            throw new BaseException(CodeEnum.AUTH_MOBILE);
        }


        return new Response<>(CodeEnum.SUCCESS, userService.register(userReq, ip));

    }


    /**
     * 设置密码
     *
     * @param userReq 手机号,密码
     * @return response
     */

    @PostMapping(value = "/user/setPassword")
    public Response setPassword(@RequestBody UserReq userReq) {
        String beginPassword = userReq.getBeginPassword();
        log.info(String.format("用户%s设置密码", userReq.getMobile()));
        if (StringUtil.isBlank(userReq.getMobile())) {
            log.error("手机号不能为空");
            return new Response<>(CodeEnum.AUTH_NO_MOBILE, null);
        }
        if (StringUtil.isBlank(beginPassword)) {
            log.error(String.format("用户%s设置的密码不能为空", userReq.getMobile()));
            return new Response<>(CodeEnum.AUTH_NO_PWD, null);
        }

        return new Response<>(userService.serPassword(userReq), null);
    }


    /**
     * 忘记密码，修改密码
     *
     * @param userReq 手机号  新密码  短信验证码 验证码token
     * @return Response
     */
    @PostMapping(value = "/user/resetPassword")
    public Response resetPassword(@RequestBody UserReq userReq) {

        if (StringUtil.isBlank(userReq.getMobile())) {
            log.error("修改密码的手机号为空");
            return new Response<>(CodeEnum.AUTH_NO_MOBILE, null);
        }
        if (StringUtil.isBlank(userReq.getNewPassword())) {
            log.error(String.format("用户%s的新密码不能为空", userReq.getMobile()));
            return new Response<>(CodeEnum.AUTH_NO_NEW_PWD, null);
        }
        if (StringUtil.isBlank(userReq.getVerifyCode())) {
            log.error(String.format("用户%s的短信验证码为空", userReq.getMobile()));
            return new Response<>(CodeEnum.AUTH_NO_V_CODE, null);
        }
        if (StringUtil.isBlank(userReq.getNoteToken())) {
            log.info(String.format("noteToken为空，用户:%s", userReq.getMobile()));
            return new Response<>(CodeEnum.V_CODE_ERR, null);
        }
        return new Response<>(userService.resetPassword(userReq), null);
    }

    /**
     * 获取用户基本信息
     *
     * @return response
     */
    @PostMapping("/user/getUserInfo")
    public Response getUserInfo(@RequestBody UserReq userReq) {
        return new Response<>(CodeEnum.SUCCESS, userService.getUserInfo(userReq.getUserId()));
    }

    /**
     * 获取建筑状态
     *
     * @return response
     */
    @PostMapping("/user/getUserBuildingStatus")
    public Response getUserBuildingStatus(@RequestBody UserReq userReq) {
        return new Response<>(CodeEnum.SUCCESS, userService.getUserBuildingStatus(userReq.getUserId()));
    }

    /**
     * 获取建筑状态
     *
     * @return response
     */
    @PostMapping("/user/getBuildingStatus")
    public Response getBuildingStatus() {
        return new Response<>(CodeEnum.SUCCESS, userService.getBuildingStatus());
    }

    /**
     * 获取用户认证信息
     *
     * @param userReq userReq
     * @return Response
     */
    @PostMapping("/user/getUserAuthInfo")
    public Response getUserAuthInfo(@RequestBody UserReq userReq) {
        return new Response<>(CodeEnum.SUCCESS, userService.getUserAuthInfo(userReq.getUserId()));
    }

    /**
     * 保存用户的初次登陆的DeviceId
     *
     * @param userReq userReq
     *                deviceId 唯一设备id
     */
    @PostMapping("/user/deviceId")
    public Response saveDeviceId(@RequestBody UserReq userReq) {

        userService.insertDeviceId(userReq.getDeviceId());
        return new Response<>(CodeEnum.SUCCESS, null);
    }
}
