package com.wjl.fcity.bank.service.impl;

import com.alibaba.fastjson.JSON;
import com.wjl.fcity.bank.common.config.PaymentGatewayConfig;
import com.wjl.fcity.bank.common.enums.CodeEnum;
import com.wjl.fcity.bank.common.exception.BaseException;
import com.wjl.fcity.bank.entity.dto.BankCardBinDTO;
import com.wjl.fcity.bank.service.PaymentGatewayService;
import com.qurong.payment.PaymentClient;
import com.qurong.payment.response.JsonRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 趣融支付网关Service
 *
 * @author TJP
 * @date 2018/1/8 11:29
 **/
@Slf4j
@Service
public class PaymentGatewayServiceImpl implements PaymentGatewayService {
    /**
     * 支付网关返回成功码
     */
    private static final String PAYMENT_SUCCESS_RESULT = "0";
    /**
     * 储蓄卡
     */
    private static final String DEPOSIT_CARD = "2";
    /**
     * 信用卡
     */
    private static final String CREDIT_CARD = "3";

    @Resource
    private PaymentGatewayConfig paymentGatewayConfig;

    @Override
    public BankCardBinDTO bankCardBinQuery(final Long userId, String bankCardNo) {
        // 卡bin查询使用连连支付
        String ppId = "0";
        // 请求地址
        String url = paymentGatewayConfig.getPaymentGatewayUrl() + "/qurong-payment/common/bankCardBinQuery/"
                + userId + "/" + paymentGatewayConfig.getAppId() + "/" + ppId;
        // 请求参数
        Map<String, Object> params = new HashMap<>(16);
        params.put("cardNo", bankCardNo);
        params.put("signType", "RSA");

        log.info("卡bin查询,url : " + url + " , param = " + params);

        // 请求支付网关
        PaymentClient paymentClient = new PaymentClient(url, params);
        JsonRespVO jsonRespVO;
        try {
            jsonRespVO = paymentClient.doPost();

            log.info("查询卡bin结果:" + jsonRespVO);

            if (PAYMENT_SUCCESS_RESULT.equals(jsonRespVO.getCode())) {
                Map result = JSON.parseObject(JSON.toJSONString(jsonRespVO.getData()), Map.class);
                String bankCode = (String) result.get("bank_code");
                String bankName = (String) result.get("bank_name");
                String cardType = (String) result.get("card_type");

                BankCardBinDTO bankCardBinDto = new BankCardBinDTO();
                bankCardBinDto.setBankName(bankName);
                bankCardBinDto.setBankCode(bankCode);
                bankCardBinDto.setCardNo(bankCardNo);

                if (CREDIT_CARD.equals(cardType)) {
                    // 信用卡,默认返回支持状态
                    bankCardBinDto.setCardType(Integer.parseInt(CREDIT_CARD));
                    bankCardBinDto.setValidStatus(1);
                }
                if (DEPOSIT_CARD.equals(cardType)) {
                    // 储蓄卡，提示 输入信用卡卡号
                    bankCardBinDto.setCardType(Integer.parseInt(DEPOSIT_CARD));
                    bankCardBinDto.setValidStatus(0);
                }
                return bankCardBinDto;
            } else {
                log.info("查询银行卡bin失败");
                throw new BaseException(CodeEnum.BANK_CARD_BIN_FAIL);
            }
        } catch (Exception e) {
            log.error("查询银行卡bin失败", e);
            throw new BaseException(CodeEnum.BANK_CARD_BIN_FAIL);
        }
    }
}
