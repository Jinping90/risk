package com.wjl.fcity.user.common.enums;

import lombok.Getter;

/**
 * @author czl
 */
@Getter
public enum CodeEnum {
    /**
     * 全局异常捕获
     */
    SUCCESS("0", "处理成功"),

    AUTH_NO_REGISTER("0", "该手机号未注册"),
    AUTH_NO_SET("1", "已注册但未设置密码"),
    AUTH_MOBILE_PWD_EXIST("2", "手机号已经被注册并已设置密码"),

    AUTH_ILLEGAL("1001", "非法请求"),
    AUTH_UN_MATCH("1002", "用户名或密码不正确"),
    AUTH_NOT_LOGIN("1003", "用户未登录或长时间未操作，请重新登录"),
    AUTH_FORBID("1004", "用户无操作权限"),
    AUTH_LOCKED("1005", "用户已被锁定，请联系管理员"),
    AUTH_UN_KNOW_USER("1106", "无法找到当前用户"),
    PARAMETER_MISTAKE("1006", "参数错误"),
    AUTH_NO_MOBILE("1007", "手机号不能为空"),
    AUTH_MOBILE("1008", "请输入正确的手机号"),
    AUTH_LOGIN_COUNT("1012", "请尝试使用验证码登录或点击“忘记密码”"),
    AUTH_MOBILE_EXIST("1013", "该手机号已注册"),
    AUTH_NO_PWD("1014", "密码不能为空"),
    AUTH_NO_V_CODE("1015", "短信验证码不能为空"),
    AUTH_V_CODE_ERR("1016", "验证码错误，请输入正确的验证码"),
    AUTH_V_CODE_LOSE("1017", "验证码已失效，请重新获取"),
    AUTH_REGISTER_FAIL("1018", "注册失败,请检查是否已经注册"),
    AUTH_NO_USER("1019", "用户不存在"),
    AUTH_NO_NEW_PWD("1020", "新密码不能为空"),
    AUTH_V_CODE_STALE("1021", "短信验证码过期"),
    AUTH_RESET_PWD_FAIL("1022", "修改密码失败，请重试!"),
    AUTH_AGAIN("1023", "用户已经别的地方登录，请退出后重新尝试登录"),
    AUTH_NO_SET_PWD("1024", "手机号未设置密码"),
    AUTH_NULL("1025", "登录信息不能为空"),
    AUTH_PWD_ERR("1026", "请输入正确的密码"),
    AUTH_ILLEGAL_LOGIN("1027", "登录失败,请检查输入信息是否合法"),
    AUTH_LOGIN_ERR("1028", "登录失败,请检查设备是否正常"),
    AUTH_ID_CARD_NO_AUTH("1030", "身份证未认证"),
    NOTE_TOKEN_IS_NULL("1029", "短信验证token为空"),
    WRONG_CREDIT_CARD("1111", "请输入信用卡卡号"),
    WRONG_CREDIT_CARD_ID("1112", "信息不一致"),
    UN_KNOW_CREDIT_CARD("1113", "验证失败"),
    TOKEN_ERR("1114", "登录token有误"),
    FACE_RECOGNITION("1115", "人脸识别失败！"),

    /**
     * 二要素
     */
    SYSTEM_BUSY("1202", "系统繁忙，请稍后再试"),
    TWO_FACTOR_AUTH_IS_EXIST("1204", "您早前已入住，请切换原身份登录"),
    TWO_FACTOR_AUTH_NOT_IS_EXIST("1203", "未验证"),
    TWO_FACTOR_AUTH_FAILED("1205", "请求失败"),
    TWO_FACTOR_CARD_FORMAT_IS_WRONG("1209", "身份证信息格式有误"),
    TWO_FACTOR_AUTH_NOT_EXIST("1206", "认证信息不存在"),
    TWO_FACTOR_AUTH_OTHER_EXCEPTION("1207", "其他异常"),
    TWO_FACTOR_AUTH_PARAM_CONFIG_EXCEPTION("1208", "参数未配置"),

    /**
     * 手机认证返回码
     */
    PHONE_ILLEGAL_NUMBER("3006", "手机号码为空或手机号码不合法"),
    PHONE_ILLEGAL_CONTACTS_NAME("3008", "联系人姓名不合法"),
    PHONE_ILLEGAL_CONTACTS_NUMBER("3009", "联系人手机号码不合法"),
    PHONE_ILLEGAL_CONTACTS_TYPE("3010", "联系人类型不合法"),
    PHONE_REIN_PUT_CODE("3011", "再次输入验证码"),
    PHONE_INPUT_CODE("3012", "请输入验证码"),
    PHONE_WRONG_CODE("3013", "验证码错误"),
    PHONE_INVALID_CODE("3014", "验证码失效"),
    PHONE_INPUT_QUERY_PASSWORD("3015", "请输入查询密码"),
    PHONE_WRONG_QUERY_PASSWORD("3016", "查询密码错误"),
    PHONE_WRONG_SERVICE_PASSWORD("3017", "服务密码错误"),
    PHONE_SIMPLE_SERVICE_PASSWORD("3018", "简单密码或初始密码无法登录"),
    PHONE_NETWORK_ANOMALY("3019", "网络异常，请稍后重试"),
    AUTH_USER_NOT_FOUND("3020", "未找到当前用户"),
    AUTH_NO_NAME("3021", "请填写姓名"),
    AUTH_NO_ID_CARD("3022", "请填写身份证号码"),
    AUTH_UNVERIFIED("3023", "信息识别失败,请重新拍摄身份证"),
    AUTH_NO_FRONT_IMG("3024", "身份证正面照不能为空"),
    AUTH_NO_BACK_IMG("3025", "身份证反面照不能为空"),
    AUTH_NO_HEAD_IMG("3026", "身份证头像照不能为空"),
    AUTH_ID_CARD_ERROR("3027", "身份证格式错误,请重新拍摄"),
    AUTH_UNDERAGE("3028", "您未满18周岁，根据国家相关要求不能申请贷款"),
    AUTH_ID_CARD_BINDING("3029", "您的身份证号已经与其他账号绑定"),
    AUTH_ID_CARD_VALIDITY_ERROR("3031", "身份证有效期错误,请重新拍摄"),
    AUTH_FAILED("3033", "认证失败"),
    ID_CARD_TWO_FACTOR_AUTH_DIFF("3034", "与二要素认证信息不一致"),
    PHONE_ILLEGAL_DATASOURCE_NAME("3040", "数据源名称不合法"),
    PHONE_OPERATOR_MAINTAIN("3042", "手机号码所在运营商正在维护"),
    PHONE_SEND_V_CODE_FAIL("3044", "发送验证码失败"),

    APP_TO_OLD("3080", "请下载最新版本的app"),
    BLACK_BOX_TO_LONG("3081", "申请失败，请重新申请"),
    AUTH_PROCESS_ERROR("3083", "存在部分认证项目未认证或已过期，请确认"),
    PHONE_NUM_INFO_AUTH("3084", "手机运营商认证中，请稍后再试"),
    ONLINE_BANKING_AUTH("3085", "网银认证中，请稍后再试"),

    USER_INFO_SUBMIT_FAILED("3090", "用户基本信息提交失败"),
    USER_INFO_SUBMIT_SUCCESS("3091", "用户基本信息提交成功"),
    CONTACTS_LIST_INSERT_FAILED("3092", "插入通讯录失败"),
    CONTACT_LIST_IS_NULL("3093", "未查询到通讯录信息,请检查您的通讯录"),

    PHONE_TIMEOUT("3099", "请求超时"),
    PHONE_ERROR("3999", "请求失败，请稍后重试"),

    V_CODE_ERR("4001", "短信验证码不正确"),
    INVALID_ERR("4002", "短信验证码失效，请重新获取"),
    BUSY_ERR("4003", "系统繁忙,请稍后重试!"),
    LOW_VERSION("4004", "版本过低,请更新到最新版本!"),


    SYS_UNKNOWN("9001", "系统异常"),
    SYS_MAINTAIN("9002", "系统正在维护中");


    private String code;
    private String message;

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
