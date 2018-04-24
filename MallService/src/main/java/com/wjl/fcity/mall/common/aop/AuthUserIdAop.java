package com.wjl.fcity.mall.common.aop;

import com.wjl.fcity.mall.common.enums.CodeEnum;
import com.wjl.fcity.mall.common.exception.BaseException;
import com.wjl.fcity.mall.entity.vo.Response;
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
 * 对传token的接口进行校验
 * 在header中是否存在userId，不存在返回未登录
 *
 * @author shengju
 */
@Slf4j
@Aspect
@Component
public class AuthUserIdAop {

    @Pointcut("execution(* com.fcity.mall.controller.*.*(..))")
    private void allMethod() {
    }

    /**
     * 监控所有需要token的接口
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
        }catch (BaseException be) {
            log.error("service异常：" + be.getMessage(), be);
            return new Response<>(be.getCodeEnum(),null);
        }catch (Exception e) {
            log.error("系统异常：" + e.getMessage(), e);
            return new Response<>(CodeEnum.SYS_UNKNOWN,null);
        }

    }
}