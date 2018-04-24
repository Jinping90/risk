package com.wjl.fcity.user.po;

import lombok.Data;

/**
 * @author : Fy
 * @date : 2018-04-01 9:59
 * 三方请求状态码
 */
@Data
public class ResponseResult<T> {

    /**
     * 成功
     */
    public static int REQ_CODE_SUCCESS = 0;
    /**
     * 失败
     */
    public static int REQ_CODE_FAIL = 1;
    /**
     * 出错
     */
    public static int REQ_CODE_ERROR = -1;
    /**
     * 轮训时间过长失败
     */
    public static int REQ_KEEP_QUERY_FAIL = 2;

    private Integer code;

    private String msg = "success";

    private T data;

    public ResponseResult() {

    }

    public ResponseResult(Integer code) {
        this.code = code;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


}
