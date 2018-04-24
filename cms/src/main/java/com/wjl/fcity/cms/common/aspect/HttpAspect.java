package com.wjl.fcity.cms.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author czl
 */
@Aspect
@Component
public class HttpAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.wjl.fcity.cms.controller..*.*(..))")
    private void controllerAspect() {
    }

    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("url:{}", request.getRequestURL());
        logger.info("method:{}", request.getMethod());
        logger.info("ip:{}", request.getRemoteAddr());
        logger.info("classMethod:{}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
        logger.info("args:{}", joinPoint.getArgs());
    }
}
