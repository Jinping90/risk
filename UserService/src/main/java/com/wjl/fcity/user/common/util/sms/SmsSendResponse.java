package com.wjl.fcity.user.common.util.sms;

import lombok.Data;

/**
 * 普通短信发送响应实体类
 *
 * @author tianyh
 * 2017年4月15日 下午3:41:59
 * SmsSingleResponse
 * SmsSingleResponse
 */
@Data
public class SmsSendResponse {
    /**
     * 响应时间
     */
    private String time;
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 状态码说明（成功返回空）
     */
    private String errorMsg;
    /**
     * 状态码（详细参考提交响应状态码）
     */
    private String code;

    @Override
    public String toString() {
        return "SmsSingleResponse [time=" + time + ", msgId=" + msgId + ", errorMsg=" + errorMsg + ", code=" + code
                + "]";
    }


}
