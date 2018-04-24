package com.wjl.fcity.msg.common.exception;

import com.wjl.fcity.msg.common.enums.CodeEnum;
import lombok.Data;

/**
 * @author czl
 */
@Data
public class BaseException extends RuntimeException {
    private CodeEnum codeEnum;

    public BaseException(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.codeEnum = codeEnum;
    }
}
