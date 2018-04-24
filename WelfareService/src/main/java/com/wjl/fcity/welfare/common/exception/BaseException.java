package com.wjl.fcity.welfare.common.exception;

import com.wjl.fcity.welfare.common.enums.CodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Fy
 * @date : 2018-04-04 18:52
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

