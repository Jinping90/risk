package com.wjl.fcity.user.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.exception.BaseException;
import com.wjl.fcity.user.common.properties.AppProperties;
import com.wjl.fcity.user.common.util.AesUtils;
import com.wjl.fcity.user.common.util.IpUtil;
import com.wjl.fcity.user.common.util.PhoneUtil;
import com.wjl.fcity.user.common.util.StringUtil;
import com.wjl.fcity.user.model.PromotionChannelConfig;
import com.wjl.fcity.user.model.PromotionUser;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.model.SmsCodeData;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.request.UserReq;
import com.wjl.fcity.user.service.PromotionUserService;
import com.wjl.fcity.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 推广用户
 *
 * @author xuhaihong
 */
@Slf4j
@RestController
public class PromotionUserController {

    @Resource
    private PromotionUserService promotionUserService;
    @Resource
    private UserService userService;
    @Resource
    private AppProperties appProperties;

    @RequestMapping(value = "/promotion")
    public String click() {
        return "index";
    }

    /**
     * 生成分享链接 channel + uid (uid = userId+phone后四位) 拼接
     *
     * @param request 用户id
     * @return 分享链接
     */
    @PostMapping("/promote/promoteUrl")
    public Response promotionUrl(HttpServletRequest request) {
        Long userId;
        String userIdStr = request.getHeader("userId");
        if (!StringUtil.isBlank(userIdStr)) {
            userId = Long.valueOf(userIdStr);
        } else {
            log.error("获取用户id失败");
            return new Response<>(CodeEnum.AUTH_ILLEGAL, Maps.newHashMap());
        }
        UserPO userPO = userService.findOne(userId);
        String phone = userPO.getMobile();
        Map<String, String> map = new HashMap<>(1);
        map.put("promotionUrl", "?channel=1&uid=" + userId + phone.substring(phone.length() - 4, phone.length()));
        return new Response<>(CodeEnum.SUCCESS, map);

    }

    /**
     * 保存点击过链接相关消息
     *
     * @param request deviceType 设备类型  channelType 渠道
     * @return 返回当前信息的id
     */
    @PostMapping("/promotion/promotionId")
    public Response clickPost(HttpServletRequest request, @RequestBody Map maps) {
        //获取请求ip地址
        String ip = IpUtil.getIp(request);
        final String iphone = "iphone";
        final String mac = "Mac";
        final String android = "Android";
        //设备类型
        Long channelType = Long.valueOf(maps.get("channelType").toString());
        String deviceType = null;
        String userAgent = request.getHeader("user-agent");
        //判断设备类型
        if (userAgent.contains(iphone) || userAgent.contains(mac)) {
            //设备类型为iso
            deviceType = "IOS";
        } else if (userAgent.contains(android)) {
            deviceType = "Android";
        }
        PromotionUser promotionUser = new PromotionUser();
        if ("".equals(ip) || null == ip) {
            log.info("ip为空 保存失败");
        } else {
            promotionUser.setIp(ip);
            promotionUser.setDeviceType(deviceType);
            //检测downloadSource下载源是否为空  渠道代码
            if (channelType != null) {
                PromotionChannelConfig channelConfig = promotionUserService.getPromotionChannelConfig(channelType);
                if (null == channelConfig) {
                    channelType = 0L;
                } else {
                    channelType = channelConfig.getId();
                }
                try {
                    promotionUser.setChannelId(channelType);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    log.error("渠道代码非数字");
                }
            }
            promotionUser.setVisitDate(new Date());
            promotionUserService.insert(promotionUser);
            log.info("========================保存信息成功=======================");
            Map<String, String> map = new HashMap<>(1);
            map.put("id", promotionUser.getId().toString());
            return new Response<>(CodeEnum.SUCCESS, map);
        }
        Map<String, String> map = new HashMap<>(1);
        map.put("id", "");
        return new Response<>(CodeEnum.PARAMETER_MISTAKE, map);
    }


    /**
     * 推广用户注册
     *
     * @param map     map
     *                platform       平台 1：ios 2：Android 3：其他
     *                phone          手机号
     *                vcode          验证码
     *                token          token
     *                id             promotion_user表 id  用于更新成功注册用户
     * @param request request
     *                uid            邀请id
     * @return 结果信息
     */
    @PostMapping("/promotion/register")
    public Response register(@RequestBody Map map, HttpServletRequest request) {

        String phone = (String) map.get("phone");
        String vcode = (String) map.get("vcode");
        String noteToken = (String) map.get("noteToken");
        String id = (String) map.get("id");
        String uid = (String) map.get("uid");
        String regSource = (String) map.get("regSource");
        //获取plantform
        final String iphone = "iphone";
        final String mac = "Mac";
        final String android = "Android";
        int platform;
        String userAgent = request.getHeader("user-agent");
        //判断设备类型
        if (userAgent.contains(iphone) || userAgent.contains(mac)) {
            //设备类型为iso
            platform = 1;
        } else if (userAgent.contains(android)) {
            platform = 2;
        } else {
            platform = 3;
        }
        final int tokenFlagForRegister = 2;
        final String ip = IpUtil.getIp(request);
        Integer fiveMinutes = 5 * 60 * 1000;
        log.info(String.format("===>手机号%s开始注册,注册ip为:%s", phone, ip));
        if (StringUtil.isBlank(phone)) {
            log.error("输入的手机号为空,注册失败,注册结束");
            throw new BaseException(CodeEnum.PARAMETER_MISTAKE);
        }
        if (StringUtil.isBlank(vcode)) {
            log.error(String.format("注册手机号%s,短信验证码为空，注册失败，注册结束", phone));
            return new Response<>(CodeEnum.AUTH_NO_V_CODE, "请输入短信验证码");
        }
        //验证手机号是否合法
        if (!PhoneUtil.isMobileNO(phone)) {
            log.error(String.format("手机号%s，不符合手机号标注，注册失败, 注册结束", phone));
            return new Response<>(CodeEnum.AUTH_MOBILE, "请输入正确的手机号");
        }
        //检验手机号是否已经注册
        if (userService.checkPhoneExist(phone)) {
            log.error(String.format("手机号%s，已经被注册，注册失败，注册结束", phone));
            return new Response<>(CodeEnum.AUTH_MOBILE_EXIST, "该手机号已被注册");
        }

        //验证短信验证码是否正确
        try {
            //解密发送短信产生的token
            String params = AesUtils.aesDecryptHexString(noteToken, appProperties.getCodeKey());
            SmsCodeData smsCodeData = JSON.parseObject(params, SmsCodeData.class);
            if (!phone.equals(smsCodeData.getPhone())) {
                log.info(String.format("注册手机号和获取短信手机号不符,注册的手机号是:%s, 获取短信的手机号为:%s", phone, smsCodeData.getPhone()));
                return new Response<>(CodeEnum.AUTH_V_CODE_ERR, "注册手机号和获取短信手机号不符");
            }
            if (!vcode.equals(smsCodeData.getVCode())) {
                log.info(String.format("短信验证码不正确，请求的验证码为%s, 发送的验证码为：%s", vcode, smsCodeData.getVCode()));
                return new Response<>(CodeEnum.AUTH_V_CODE_ERR, "短信验证码不正确");
            }
            if (tokenFlagForRegister != smsCodeData.getFlag()) {
                log.error("短信验证token可能是非法获取的，请注意");
                return new Response<>(CodeEnum.AUTH_V_CODE_ERR, "短信验证token可能是非法获取的");
            }
            Long now = System.currentTimeMillis();
            //短信验证码大于五分钟失效
            if ((now - smsCodeData.getTime()) > fiveMinutes) {
                log.info(String.format("短信验证码失效，短信验证码生成时间为:%s, 当前时间为:%s", smsCodeData.getTime(), now));
                return new Response<>(CodeEnum.AUTH_V_CODE_STALE, "验证码失效，请重新获取");
            }
        } catch (Exception e) {
            log.error(String.format("手机号%s短信验证码验证异常，原因:", phone), e);
            return new Response<>(CodeEnum.AUTH_V_CODE_ERR, "短信验证码验证异常");
        }
        UserReq user = new UserReq();
        user.setMobile(phone);
        user.setVerifyCode(vcode);
        user.setNoteToken(noteToken);
        user.setRegSource(regSource);
        user.setPlatform(platform);
        user.setDeviceId("");
        Map maps = promotionUserService.register(user, ip, id, uid);

        return new Response<>(CodeEnum.SUCCESS, maps);
    }


}


