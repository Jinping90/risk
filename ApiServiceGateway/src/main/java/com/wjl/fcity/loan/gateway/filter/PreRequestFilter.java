package com.wjl.fcity.loan.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.wjl.fcity.loan.gateway.LocalCache;
import com.wjl.fcity.loan.gateway.constant.ImmutableConfig;
import com.wjl.fcity.loan.gateway.enums.CodeEnum;
import com.wjl.fcity.loan.gateway.model.RedisKeyConstant;
import com.wjl.fcity.loan.gateway.model.Response;
import com.wjl.fcity.loan.gateway.model.Token;
import com.wjl.fcity.loan.gateway.model.dto.UserDTO;
import com.wjl.fcity.loan.gateway.service.RedisService;
import com.wjl.fcity.loan.gateway.util.AesUtils;
import com.wjl.fcity.loan.gateway.mapper.UserMapper;
import com.wjl.fcity.loan.gateway.properties.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;

/**
 * 请求过滤器
 * <p>
 * 对APP请求
 * 1.以“/Api-App/”路由到APP的服务端App-Service，需要校验token
 * 2.包含“/auth/” 的请求是需要校验token的
 * 3.其他的请求为安全请求
 *
 * @author 秦瑞华.
 */
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {


    @Resource
    private AppProperties appProperties;
    @Resource
    private RedisService redisService;
    @Resource
    private UserMapper userMapper;


    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        String comma = ",";
        RequestContext ctx = RequestContext.getCurrentContext();
        //解决中文乱码问题
        ctx.getResponse().setContentType("text/html;charset=UTF-8");
        HttpServletRequest request = ctx.getRequest();

        long start = System.currentTimeMillis();

        String requestPath = request.getServletPath();


    	/*
         * App接口
    	 */
        if (isApp(requestPath)) {
            String ip = request.getHeader("x-forwarded-for");
            String unknown = "unknown";
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            } else if (ip.contains(comma)) {
                ip = ip.split(",")[0];
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                String head = "WL-Proxy-Client-IP";
                ip = request.getHeader(head);
            }
            if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            log.info(String.format("ip=%s, req=%s", ip, requestPath));
            if (!StringUtils.isEmpty(ip)) {
                //IP黑名单检测
                boolean hitBlackIP = LocalCache.hitBlackIP(ip);
                if (hitBlackIP) {
                    log.error(String.format("命中IP黑名单, ip=%s, req=%s", ip, requestPath));
                    blackResponseBody(ctx);
                    return null;
                }

                //短信请求
                caseVCode(requestPath, ip);
            }

    		/*
             * 1.安全请求检测
    		 */

            // 登录，发送验证码，注册和修改密码,回调等为安全请求
            boolean safeReq = (requestPath.toLowerCase().contains("login") && !(requestPath.contains("/loginOut")))
                    || requestPath.contains("sendVerifyCode")
                    || requestPath.contains("regist")
                    || requestPath.contains("/resetPassword")
                    || requestPath.contains("Callback")
                    || requestPath.contains("promotion")
                    || requestPath.contains("/checkAppVersion")
                    || requestPath.contains("/getScrollBarList")
                    || requestPath.contains("/getBuildingStatus")
                    || requestPath.contains("/articles")
                    || requestPath.contains("/deviceId");
            if (safeReq) {
                log.info("安全请求:" + requestPath);
                return null;
            }

            log.info("请求=" + requestPath);
            /*
    		 * 2.过滤重复请求
    		 * path+parameters+headers
    		 */
            StringBuilder parameters = new StringBuilder();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String pKey = parameterNames.nextElement();
                String pValue = request.getParameter(pKey);
                parameters.append(pKey).append("=").append(pValue).append(",");
            }
            StringBuilder headersStr = new StringBuilder();
            Enumeration<String> headers = request.getHeaderNames();
            while (headers.hasMoreElements()) {
                String headerName = headers.nextElement();
                if (Sets.newHashSet("cache-control").contains(headerName)) {
                    continue;
                }
                String headerValue = request.getHeader(headerName);
                headersStr.append(headerName).append("=").append(headerValue).append(",");
            }
            String req = requestPath + "|" + parameters + "|" + headersStr.toString();
//            if (!DEV_IP.equals(ip)||!PROD_IP.equals(ip)){
            if (LocalCache.repeatRequest(req)) {
                log.error("重复请求,req=" + req);
                repeatResponseBody(ctx);
                return null;
            }
//            }


        	/*
        	 * 3.是否登录
        	 */

            String token = request.getParameter("token");

            if (null == token) {
                Map map = request.getParameterMap();
                token = (String) map.get("token");

                try {
                    BufferedReader bufferedReader = request.getReader();
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        builder.append(line);
                    }
                    String postData = builder.toString();
                    JSONObject json = JSONObject.parseObject(postData);
                    token = json.getString("token");

                    if (token == null) {
                        // 未登录
                        unLoginResponseBody(ctx);
                    }
                } catch (Exception e) {
                    log.info("获取token失败：" + e.getMessage(), e);
                }
            }

            log.info(String.format("============ token = %s ============", token));

            //解密token
            Token tokenOk = decryptToken(token);
            if (tokenOk == null || tokenOk.getId() == null || tokenOk.getId() < 0) {
                log.error("无效token");
                unLoginResponseBody(ctx);
                return null;
            }

            Long userId = tokenOk.getId();
            log.info("用户ID：" + userId);

            // 登录校验
            String value = redisService.get(
                    String.format(RedisKeyConstant.TOKEN_KEY, userId));
            String valueOld = redisService.get(
                    String.format(RedisKeyConstant.OLD_TOKEN_KEY, userId));
            if (value == null && valueOld == null) {
                // 未登录
                unLoginResponseBody(ctx);
                return null;
            }
            String[] values = null;
            boolean tokenErr = true;
            // 如果redis不存在token，返回tokenErr
            if (value != null) {
                values = value.split("&");
                if (values[0].equals(token)) {
                    tokenErr = false;
                }
            }

            if (valueOld != null) {
                if (valueOld.equals(token)) {
                    tokenErr = false;
                }
            }

            if (tokenErr) {
                unLoginResponseBody(ctx);
                return null;
            }

            String newToken = "";
            // 如果token超时返回新token - 10分钟
            Long timeStamp = System.currentTimeMillis();
            Long tenMinute = 600000L;
            UserDTO userDTO = userMapper.findUserByUserId(userId);
            if (userDTO == null) {
                // 未登录
                unLoginResponseBody(ctx);
                return null;
            }
            Token userToken = new Token();
            userToken.setId(userDTO.getId());
            userToken.setLoginTime(timeStamp);
            userToken.setMobile(userDTO.getMobile());
            userToken.setUsername(userDTO.getUsername());
            // 如果用的是newToken,values不为空
            if (values != null && timeStamp - Long.valueOf(values[1]) > tenMinute) {
                Integer tokenDueTime = appProperties.getTokenDueTime();
                // 生成新token存入redis，将老token放入redis-old

                Long nineMinute = 1000 * 60L * tokenDueTime;
                redisService.psetex(String.format(RedisKeyConstant.OLD_TOKEN_KEY, userId), nineMinute, token);
                try {
                    newToken = AesUtils.aesEncryptHexString(JSON.toJSONString(userToken), appProperties.getTokenKey());
                } catch (Exception e) {
                    log.error("生成新token失败，加密异常：" + e.getMessage() + e);
                }
                redisService.set(String.format(RedisKeyConstant.TOKEN_KEY, userId), newToken + "&" + timeStamp);
            }

    		
    		/*
    		 * 4.检测请求重放攻击
    		 * 	客户2个特殊请求间隔不能小于5秒钟
    		 */

            String custReq;
            boolean tooSoon = false;

            //客户请求
            custReq = tokenOk.getId() + "-" + requestPath;
            if (ImmutableConfig.SecondNotCanRepeatRequestUrlSet.contains(requestPath)) {
                tooSoon = LocalCache.custSecondRequestTooSoon(custReq);
            }
            if (tooSoon) {
                log.error(String.format("检测到请求重放攻击,req=%s", custReq));
                repeatResponseBody(ctx);
                return null;
            }



            ctx.addZuulRequestHeader("userId", userId.toString());

            // 在json参数中添加 userId
            try {
                InputStream in = ctx.getRequest().getInputStream();
                String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
                JSONObject json = JSONObject.parseObject(body);
                json.put("userId", userId);
                String newBody = json.toString();
                final byte[] reqBodyBytes = newBody.getBytes();
                ctx.setRequest(new HttpServletRequestWrapper(request) {
                    @Override
                    public ServletInputStream getInputStream() throws IOException {
                        return new ServletInputStreamWrapper(reqBodyBytes);
                    }

                    @Override
                    public int getContentLength() {
                        return reqBodyBytes.length;
                    }

                    @Override
                    public long getContentLengthLong() {
                        return reqBodyBytes.length;
                    }
                });
            } catch (IOException e) {
                log.error("系统异常：" + e.getMessage(), e);
            }


            // 加入新token
            ctx.addZuulRequestHeader("newToken", newToken + "");
            long end = System.currentTimeMillis();
            log.info("App路由检测完毕，使用时间=" + (end - start));
        }

        return null;
    }

    /**
     * 未登录responseBody
     */
    private void unLoginResponseBody(RequestContext ctx) {
        ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(JSON.toJSONString(new Response<>(CodeEnum.AUTH_NOT_LOGIN, "")));
            ctx.setSendZuulResponse(false);
        }
    }

    /**
     * 账号登录已过期responseBody
     */
    private void loginOverResponseBody(RequestContext ctx) {
        ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody("{\"success\":\"false\",\"errCode\":\"E003\",\"errMsg\":\"App Login Over\"}");
            ctx.setSendZuulResponse(false);
        }
    }

    /**
     * 账号已在其他设备登录responseBody
     */
    private void otherLoginResponseBody(RequestContext ctx) {
        ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody("{\"success\":\"false\",\"errCode\":\"E004\",\"errMsg\":\"App Other Login\"}");
            ctx.setSendZuulResponse(false);
        }
    }

    /**
     * 是APP请求
     *
     * @param pathInfo 请求路径
     * @return isApp
     */
    private boolean isApp(String pathInfo) {
        return pathInfo.indexOf("/Api-App/") == 0;
    }

    /**
     * 解密Token
     *
     * @param token token
     * @return userId
     */
    private Token decryptToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String tokenKey = appProperties.getTokenKey();
        try {
            String tokenOk = AesUtils.aesDecryptHexString(token, tokenKey);
            return JSONObject.parseObject(tokenOk, Token.class);
        } catch (Exception e) {
            log.error(String.format("token解密失败：token=%s", token));

            return null;
        }
    }


    /**
     * 短信请求
     *
     * @param requestPath 请求路径
     */
    private void caseVCode(String requestPath, String ip) {
        String sendVerifyCode = "/sendVerifyCode";
        if (!requestPath.endsWith(sendVerifyCode)) {
            return;
        }
        //IP地址6小时内发送短信超过10次，自动加入IP黑名单
        LocalCache.checkVCodeOfIp(ip);
    }


    /**
     * 重复请求responseBody
     */
    private void repeatResponseBody(RequestContext ctx) {
        ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody("{\"success\":\"false\",\"errCode\":\"E001\",\"errMsg\":\"操作频繁，请稍候再操作\"}");
            ctx.setResponseBody(JSON.toJSONString(new Response<>(CodeEnum.PHONE_ERROR, null)));
            ctx.setSendZuulResponse(false);
        }
    }


    /**
     * 黑名单请求responseBody
     */
    private void blackResponseBody(RequestContext ctx) {
//        try {
//            String bigFile = "http://download.springsource.com/release/STS/3.9.1.RELEASE/dist/e4.7/spring-tool-suite-3.9.1.RELEASE-e4.7.1a-win32-x86_64.zip";
            //ctx.getResponse().sendRedirect(bigFile);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }

        //返回非法请求
        ctx.setResponseStatusCode(HttpServletResponse.SC_OK);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(JSON.toJSONString(new Response<>(CodeEnum.AUTH_ILLEGAL, null)));
            ctx.setSendZuulResponse(false);
        }
    }
}
