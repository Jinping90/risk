package com.wjl.fcity.msg.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 自定义参数
 *
 * @author czl
 */
@Data
@ConfigurationProperties(prefix = "appConfig")
public class AppProperties {
    private String appName;
    private String appNameCn;
    /**
     * 核心任务调用密钥
     */
    private String secretOfLoanCoreTask;
    /**
     * 核心服务调用密钥
     */
    private String secretOfCoreService;
    /**
     * 黑名单核心服务调用密钥
     */
    private String secretOfListService;
    /**
     * 加密秘钥
     */
    private String tokenKey;
    /**
     * 版本更新地址
     */
    private String updateUrl;
    /**
     * 短信模板
     */
    private Map<String, String> smsTemplate;
    /**
     * 短信模板
     */
    private Map<String, String> pmTemplate;
    /**
     * 短信加密秘钥
     */
    private String codeKey;
    /**
     * 支付网关地址
     */
    private String paymentGatewayUrl;
    /**
     * 支付网关应用 id （51速贷为0；趣借钱为1，其他待定）
     */
    private String appId;
    /**
     * 申请还款异步回调
     */
    private String repaymentNotifyUrl;

    @NotNull
    private CreditWeb creditWeb;
    @NotNull
    private MobileData mobileData;

    @Data
    public static class CreditWeb {
        private String rootPath;
    }

    @Data
    public static class Linkface{
        private String apiId;
        private String apiSecret;
        private String selfieIdnumberVerification;
        private String livenessSelfieVerification;
    }

    @Data
    public static class MobileData {
        private String partnerCode;
        private String partnerKey;
        private String createTaskUrl;
        private String queryTaskResultUrl;
        private String initTaskUrl;
        private String retryUrl;
    }

}

