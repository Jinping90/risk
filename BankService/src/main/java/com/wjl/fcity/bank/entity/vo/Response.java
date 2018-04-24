package com.wjl.fcity.bank.entity.vo;

import com.wjl.fcity.bank.common.enums.CodeEnum;
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
    /**
     * token10分钟过期后会生成新token，随response返回,默认值空字符串
     */
    private String newToken = "";

    public Response(CodeEnum code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

}