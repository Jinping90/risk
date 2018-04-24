package com.wjl.fcity.msg.dto;

import com.wjl.fcity.msg.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回参数
 *
 * @author czl
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
