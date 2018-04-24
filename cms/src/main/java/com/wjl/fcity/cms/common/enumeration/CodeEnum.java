package com.wjl.fcity.cms.common.enumeration;

import lombok.Getter;

/**
 * @author czl
 */
@Getter
public enum CodeEnum {
    /**
     * 系统错误码
     */
    SUCCESS("0", "处理成功"),

    AUTH_ILLEGAL("1001", "非法请求"),
    AUTH_UN_MATCH("1002", "用户名或密码不正确"),
    AUTH_NOT_LOGIN("1003", "用户未登录或长时间未操作，请重新登录"),
    AUTH_FORBID("1004", "用户无操作权限"),
    AUTH_LOCKED("1005", "用户已被锁定，请联系管理员"),
    PARAMETER_MISTAKE("1006", "参数错误"),

    USER_IS_NOT_EXISTS("2001", "该用户不存在"),
    APPLY_NOT_EXISTS("2002", "提现请求不存在"),

    NO_APPLY("3001", "用户没有历史提现记录"),

    NID_EMPTY("4001", "上传标识为空"),
    NID_WRONG("4002", "上传标识错误"),
    SAVE_IMG_FAIL("4003", "保存图片失败"),

    CHOSE_PAY_TIME("5001", "请选择还款时间"),

    LIAN_FALSE("8001", "连连请求错误"),
    YBAO_FALSE("8001", "易宝请求错误"),

    SYS_UNKNOWN("9001", "系统异常"),
    SYS_NOT_ALLOW("9002", "当前环境下禁止使用该功能"),
    CARD_UNBIND("9003", "当前卡已经解绑"),
    UNBIND_NOT_ALLOW("9004", "当前有未还账单,禁止解绑银行卡");

    private String code;
    private String message;

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
