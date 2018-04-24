package com.wjl.fcity.user.common.util.sms;

import com.alibaba.fastjson.JSON;
import com.wjl.fcity.user.common.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author shengju
 */
@Slf4j
public class SendSmsUtil {
    /**
     * 用户平台API账号(非登录账号,示例:N1234567)
     * 熊猫账号 N8201321  hdwm9vsBn
     */

    public static boolean send(String phone, String msg) {
        String account = "N8201321";

        String pwd = "hdwm9vsBn";

        SmsSendResponse smsSingleResponse = null;
        String smsSingleRequestServerUrl = "http://smssh1.253.com/msg/send/json";
        // 短信内容

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pwd, msg, phone);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        String response = null;
        try {
            response = HttpUtils.sendPost(smsSingleRequestServerUrl, requestJson);
        } catch (IOException e) {
            log.error("系统异常-----------短信发送失败");
            e.printStackTrace();
        }
        if (!"".equals(response)) {
            //异常处理
            smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
        }

        String zero = "0";
        if (smsSingleResponse != null && zero.equals(smsSingleResponse.getCode())) {
            return true;
        }
        log.error(String.format("短信发送失败：phone=%s, msg=%s, ret=%s", phone, msg, response));
        return false;
    }

}
