package com.wjl.fcity.welfare.common.aop;

import com.wjl.fcity.welfare.common.enums.CodeEnum;
import com.wjl.fcity.welfare.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 对传token的接口进行校验
 * 在header中是否存在userId，不存在返回未登录
 *
 * @author czl
 */
@Slf4j
@Aspect
@Component
public class AuthUserIdAop {

    /**
     * 监控所有需要token的接口
     */
    @Around("execution(* com.wjl.fcity.welfare.controller.*.*(..)) && args(..,request)")
    public Object useTimeLog(ProceedingJoinPoint pjp, HttpServletRequest request) throws Throwable {

        try {
            Object object = pjp.proceed();
            Response response = (Response) object;
            return response;
        } catch (Exception e) {
            log.error("系统异常：" + e.getMessage() + e);
        }
        return new Response<>(CodeEnum.SYS_UNKNOWN, null);

        // String userId = request.getHeader("userId");

//        if (null == userId) {
//            // 未登录
//            return new Response<>(CodeEnum.AUTH_UN_KNOW_USER, null);
//        }
//        String token = request.getHeader("token");
//        String tokenOk = AesUtils.aesDecryptHexString(token, "123456zgqb");
//        Token tk = JSONObject.parseObject(tokenOk, Token.class);
//        Long userId = tk.getId();
//        log.info("用户ID：" + userId);
//
//        // 登录校验
//        String value = redisService.get(
//                String.format(RedisKeyConstant.TOKEN_KEY, userId));
//        String valueOld = redisService.get(
//                String.format(RedisKeyConstant.OLD_TOKEN_KEY, userId));
//        if (value == null && valueOld == null) {
//            // 未登录
//            return new Response<>(CodeEnum.AUTH_UN_KNOW_USER, "");
//        }
//        String[] values;
//        boolean tokenErr = true;
//        // 如果redis不存在token，返回tokenErr
//        if (value != null) {
//            values = value.split("&");
//            if (values[0].equals(token)) {
//                tokenErr = false;
//            }
//        } else {
//            values = valueOld.split("&");
//            if (values[0].equals(token)) {
//                tokenErr = false;
//            }
//        }
//        if (tokenErr) {
//            return new Response<>(CodeEnum.TOKEN_ERR, "");
//        }
//
//
//        try {
//            // 请求处理
//            Object object = pjp.proceed();
//
//            // 如果token超时返回新token - 10分钟
//            Response resp = (Response) object;
//            Long timeStamp = System.currentTimeMillis();
//            Long tenMinute = 600000L;
//            UserDTO userDTO = userMapper.findUserByUserId(Long.valueOf(userId));
//            Token userToken = new Token();
//            userToken.setId(userDTO.getId());
//            userToken.setLoginTime(timeStamp);
//            userToken.setMobile(userDTO.getMobile());
//            userToken.setUsername(userDTO.getUsername());
//            if (timeStamp - Long.valueOf(values[1]) > tenMinute) {
//                // 生成新token存入redis，将老token放入redis-old
//                Long nineMinute = 1000 * 60 * 9L;
//                redisService.psetex(String.format(RedisKeyConstant.OLD_TOKEN_KEY, Long.valueOf(userId)), nineMinute, token);
//                String newToken = AesUtils.aesEncryptHexString(JSON.toJSONString(userToken), appProperties.getTokenKey());
//                redisService.set(String.format(RedisKeyConstant.TOKEN_KEY, Long.valueOf(userId)), newToken + "&" + timeStamp);
//                resp.setNewToken(newToken);
//            }
//            return resp;
//        } catch (Exception e) {
//            log.error("系统错误：" + e.getMessage(), e);
//            return new Response<>(CodeEnum.SYS_UNKNOWN,null);
//        }
    }
}