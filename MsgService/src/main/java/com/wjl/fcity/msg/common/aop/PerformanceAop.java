package com.wjl.fcity.msg.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author czl
 */
@Slf4j
@Aspect
@Component
public class PerformanceAop {

    /**
     * 监控所有接口的耗时
     */
    @Around("execution(* com.wjl.fcity.msg.controller.*.*(..)) " +
            "&& @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object useTimeLog(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String method = methodSignature.getName();
        String className = pjp.getTarget().getClass().getName();
        RequestMapping classMapping = pjp.getTarget().getClass().getAnnotation(RequestMapping.class);
        String classUrl = "";
        if (null != classMapping) {
            String[] classUrls = classMapping.value();
            if (classUrls.length > 0) {
                classUrl = classUrls[0];
            }
        }

        String methodUrl = "";
        RequestMapping methodMapping = methodSignature.getMethod().getAnnotation(RequestMapping.class);
        if (null != methodMapping) {
            String[] methodUrls = methodMapping.value();
            if (methodUrls.length > 0) {
                methodUrl = methodUrls[0];
            }
        }
        String url;
        char slash = '/';
        if (classUrl.length() >= 1 && slash == (classUrl.charAt(classUrl.length() - 1))) {
            url = classUrl + methodUrl;
        } else {
            url = classUrl + methodUrl;
        }
        Object ret = pjp.proceed();
        log.info(String.format("............ 耗时usetime=%s毫秒 ............", System.currentTimeMillis() - begin));
        log.info(String.format("............ 接口url=%s ............", url));
        log.info(String.format("............ className=%s ............", className));
        log.info(String.format("............ method=%s ............", method));
        return ret;
    }
}
