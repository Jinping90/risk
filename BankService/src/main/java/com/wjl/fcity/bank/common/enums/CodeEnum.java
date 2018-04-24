package com.wjl.fcity.bank.common.enums;

import lombok.Getter;

/**
 * @author czl
 */
@Getter
public enum CodeEnum {
    /**
     * 系统信息码
     */
    SUCCESS("0", "处理成功"),

    AUTH_NOT_LOGIN("1003", "用户未登录或长时间未操作，请重新登录"),
    AUTH_ILLEGAL("1027", "登录失败,请检查输入信息是否合法"),
    UN_KNOW_CREDIT_CARD("1113", "验证失败"),
    WRONG_CREDIT_CARD_ID("1112", "信息不一致"),

    // 银行卡返回
    BANK_CARD_BIN_FAIL("6006", "未查询到该卡号信息"),
    IS_NOT_CREDIT_CARD("6011", "请输入信用卡卡号"),

    SYS_UNKNOWN("9001", "系统异常"),
    SYS_MAINTAIN("9002", "系统正在维护中");

    private String code;
    private String message;

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
