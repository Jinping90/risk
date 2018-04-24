package com.wjl.fcity.cms.common.exception;

import com.wjl.fcity.cms.common.enumeration.CodeEnum;
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
