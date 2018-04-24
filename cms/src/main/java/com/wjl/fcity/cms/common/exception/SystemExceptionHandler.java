package com.wjl.fcity.cms.common.exception;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author czl
 */
@ControllerAdvice
public class SystemExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response systemError(Exception e) {
        logger.info("系统错误：" + e.getMessage(), e);
        return new Response<>(CodeEnum.SYS_UNKNOWN, "");
    }

    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public Response baseException(BaseException e) {
        return new Response<>(e.getCodeEnum(), "");
    }

}
