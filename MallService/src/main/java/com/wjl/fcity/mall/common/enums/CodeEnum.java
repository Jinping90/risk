package com.wjl.fcity.mall.common.enums;

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

    AUTH_NO_REGISTER("0", "该手机号未注册"),
    AUTH_NO_SET("1", "已注册但未设置密码"),
    AUTH_MOBILE_PWD_EXIST("2", "手机号已经被注册并已设置密码"),

    AUTH_ILLEGAL("1001", "非法请求"),
    AUTH_UN_MATCH("1002", "用户名或密码不正确"),
    AUTH_NOT_LOGIN("1003", "用户未登录或长时间未操作，请重新登录"),
    AUTH_FORBID("1004", "用户无操作权限"),
    AUTH_LOCKED("1005", "用户已被锁定，请联系管理员"),
    AUTH_UN_KNOW_USER("1006", "无法找到当前用户"),
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
    NOTE_TOKEN_IS_NULL("1029", "短信验证token为空"),
    WRONG_CREDIT_CARD("1111", "请输入信用卡卡号"),
    WRONG_CREDIT_CARD_ID("1112", "信息不一致"),
    UN_KNOW_CREDIT_CARD("1113", "验证失败"),

    /**
     * 手机认证返回码
     */
    PHONE_CONFLICT("3555", "不可以填自己手机号"),
    PHONE_ORGANIZATION_ACCOUNT_NON_EXSIT("3001", "机构账号不存在"),
    IDENTIFY_ID_CARD_FAIL("3001", "实名认证失败"),
    PHONE_ORGANIZATION_ACCOUNT_FROZEN("3002", "机构账号已冻结"),
    PHONE_ORGANIZATION_ACCOUNT_EXPIRE("3003", "机构账号已过期"),
    PHONE_BASIC_INFO_EMPTY("3004", "基本信息不能为空"),
    PHONE_ILLEGAL_ID_CARD("3005", "身份证号码为空或身份证号码不合法"),
    PHONE_ILLEGAL_NUMBER("3006", "手机号码为空或手机号码不合法"),
    PHONE_ILLEGAL_NAME("3007", "姓名为空或姓名不合法"),
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
    PHONE_ILLEGAL_DATASOURCE_NAME("3040", "数据源名称不合法"),
    PHONE_NUMBER_NOT_SUPPORT("3041", "手机号码不支持认证"),
    PHONE_OPERATOR_MAINTAIN("3042", "手机号码所在运营商正在维护"),
    PHONE_TOKEN_FAIL("3043", "生成token失败"),
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
    ACCOUNT_FREEZE("4006", "您的账号已被冻结"),
    CERTIFICATION_OVERDUE("4007", "用户状态异常，请联系客服"),
    NEW_VERSION("4017", "请下载最新版本的app"),
    CARDS_NOT_BOUND("4024", "银行卡未绑定"),

    OPERATE_FALSE("5001", "错误操作"),
    NO_BANKCARD("5101", "未查找到用户的银行卡"),

    // 银行卡绑卡返回
    BANK_CARD_SIGN_FAIL("6001", "银行卡签约失败"),
    BANK_CARD_BIND_FAIL("6002", "银行卡绑定失败"),
    BANK_CARD_INFO_MISTAKE("6003", "您输入的卡号信息或者银行信息有误"),
    BANK_CARD_REPETITION_BIND("6004", "您已添加过该银行卡,请更换其他银行卡"),
    BANK_CARD_IS_FULL("6005", "您已添加过3张银行卡,不能再次添加"),
    BANK_CARD_BIN_FAIL("6006", "未查询到该卡号信息"),
    BANK_CARD_ADD_FAIL("6007", "您输入的信息不完整,添加银行卡失败"),
    NO_ID_CARD_INFO("6008", "请先完成身份证认证"),
    BANK_CARD_BIND_TIME_OUT("6009", "绑卡请求超时,请稍后重试"),
    BANK_CARD_BIND_ERROR("6010", "绑卡请求异常,请稍后重试"),

    SYS_UNKNOWN("9001", "系统异常"),
    SYS_MAINTAIN("9002", "系统正在维护中");


    private String code;
    private String message;

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
