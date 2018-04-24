package com.wjl.fcity.user.common.aop;

import com.wjl.fcity.user.common.enums.CodeEnum;
import com.wjl.fcity.user.common.exception.BaseException;
import com.wjl.fcity.user.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 对所有接口进行校验
 * 在header中取出newToken返回
 *
 * @author shengju
 */
@Slf4j
@Aspect
@Component
public class AuthUserIdAop {

    @Pointcut("execution(* com.wjl.fcity.user.controller.*.*(..))")
    private void allMethod() {
    }

    /**
     * 监控所有接口
     */
    @Around("allMethod()")
    public Object useTimeLog(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String newToken = request.getHeader("newToken");

        try {
            Object object = pjp.proceed();
            Response response = (Response) object;
            if (newToken == null) {
                newToken = "";
            }
            response.setNewToken(newToken);
            return response;
        } catch (BaseException be) {
            log.error("service异常：" + be.getMessage() + be);
            return new Response<>(be.getCodeEnum(), null);
        } catch (Exception e) {
            log.error("系统异常：" + e.getMessage(), e);
            return new Response<>(CodeEnum.SYS_UNKNOWN, null);
        }

    }
}