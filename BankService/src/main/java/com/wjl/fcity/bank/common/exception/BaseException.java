package com.wjl.fcity.bank.common.exception;

import com.wjl.fcity.bank.common.enums.CodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author czl
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
    private CodeEnum codeEnum;

    public BaseException(CodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.codeEnum = codeEnum;
    }
}
