package com.wjl.fcity.cms.common.security;

import com.alibaba.fastjson.JSON;
import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.vo.Response;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author czl
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        response.getWriter().println(JSON.toJSONString(new Response(CodeEnum.AUTH_FORBID, "")));
        response.getWriter().flush();
    }
}
