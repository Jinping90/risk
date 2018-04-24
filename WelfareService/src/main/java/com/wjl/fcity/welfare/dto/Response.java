package com.wjl.fcity.welfare.dto;

import com.wjl.fcity.welfare.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 公共返回参数
 *
 * @param <T>
 * @author czl
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response<T> {
    private String code;
    private String message;
    private T data;

    public Response(CodeEnum code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

}