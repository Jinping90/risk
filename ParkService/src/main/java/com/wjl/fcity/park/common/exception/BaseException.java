package com.wjl.fcity.park.common.exception;

import com.wjl.fcity.park.common.enums.CodeEnum;
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
