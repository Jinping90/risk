package com.wjl.fcity.cms.common.security;

import com.alibaba.fastjson.JSON;
import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.vo.Response;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * @author czl
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        if (exception.getClass() == InsufficientAuthenticationException.class ||
                exception.getCause() instanceof AccessDeniedException) {
            response.getWriter().println(JSON.toJSONString(new Response<>(CodeEnum.AUTH_NOT_LOGIN, "")));
        } else if (exception.getClass() == UserLockedException.class) {
            response.getWriter().println(JSON.toJSONString(new Response<>(CodeEnum.AUTH_LOCKED, "")));
        } else {
            response.getWriter().println(JSON.toJSONString(new Response<>(CodeEnum.AUTH_UN_MATCH, "")));
        }

        response.getWriter().flush();
    }
}
