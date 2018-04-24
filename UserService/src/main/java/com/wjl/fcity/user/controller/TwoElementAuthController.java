package com.wjl.fcity.user.controller;

import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.util.StringUtil;
import com.wjl.fcity.user.po.UserPO;
import com.wjl.fcity.user.model.Response;
import com.wjl.fcity.user.model.TwoElementAuth;
import com.wjl.fcity.user.service.TwoElementsAuthService;
import com.wjl.fcity.user.service.UserService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 二要素认证
 *
 * @author xuhaihong
 * @date 2018-03-30 17:27
 **/
@Slf4j
@RestController
@RequestMapping("/user/twoElementAuth")
public class TwoElementAuthController {

    @Resource
    private UserService userService;
    @Resource
    private TwoElementsAuthService twoElementsAuthService;

    /**
     * 二要素验证
     *
     * @param request request
     * @param map     请求参数
     * @return 验证结果
     */
    @RequestMapping(value = "/identify", method = RequestMethod.POST)
    public Response identify(HttpServletRequest request, @RequestBody Map map) {

        String idCardNo = (String) map.get("idCardNo");
        String realName = (String) map.get("realName");
        log.info(String.format("二要素验证开始 idCardNo = %s ,realName = %s", idCardNo, realName));
        if (StringUtil.isBlank(idCardNo) || StringUtil.isBlank(realName)) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        TwoElementAuth twoElementAuth = new TwoElementAuth();
        twoElementAuth.setIdCardNo(idCardNo);
        twoElementAuth.setName(realName);

        Long userId;
        String userIdStr = request.getHeader("userId");
        if (!StringUtil.isBlank(userIdStr)) {
            userId = Long.valueOf(userIdStr);
        } else {
            log.error("获取用户id失败");
            return new Response<>(CodeEnum.AUTH_ILLEGAL, Maps.newHashMap());
        }
        UserPO user = userService.findOne(userId);
        if (user == null) {
            log.error("未查询到用户信息");
            return new Response<>(CodeEnum.AUTH_UN_KNOW_USER, null);
        }
        if (twoElementsAuthService.checkTwoElementAuthRecord(twoElementAuth)) {
            return new Response<>(CodeEnum.TWO_FACTOR_AUTH_IS_EXIST, null);
        }

        Long inviteUserId = user.getInviteUserId() == null ? null : Long.valueOf(String.valueOf(user.getInviteUserId()));

        return twoElementsAuthService.identify(userId, realName, idCardNo, inviteUserId);

    }

    /**
     * 查询二要素认证状态
     *
     * @param request request
     * @return result
     */
    @RequestMapping(value = "/checkIdentify", method = RequestMethod.POST)
    public Response checkIdentify(HttpServletRequest request) {

        Long userId;
        String userIdStr = request.getHeader("userId");
        if (!StringUtil.isBlank(userIdStr)) {
            userId = Long.valueOf(userIdStr);
        } else {
            log.error("获取用户id失败");
            return new Response<>(CodeEnum.AUTH_ILLEGAL, Maps.newHashMap());
        }

        TwoElementAuth ifIdentified = twoElementsAuthService.checkTwoElementAuthRecordByUserId(userId);
        if (ifIdentified != null) {
            //已验证
            log.info("用户二要素已验证");
            Map map = new HashMap(2);
            map.put("idCardNo", ifIdentified.getIdCardNo());
            map.put("name", ifIdentified.getName());
            return new Response<>(CodeEnum.SUCCESS, map);
        }

        return new Response<>(CodeEnum.TWO_FACTOR_AUTH_NOT_IS_EXIST, Maps.newHashMap());
    }


}
