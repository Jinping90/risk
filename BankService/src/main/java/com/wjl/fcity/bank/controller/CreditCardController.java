package com.wjl.fcity.bank.controller;

import com.wjl.fcity.bank.common.enums.CodeEnum;
import com.wjl.fcity.bank.entity.dto.BankCardBinDTO;
import com.wjl.fcity.bank.entity.model.TwoElementsAuthRecordDO;
import com.wjl.fcity.bank.entity.request.CreditCardReq;
import com.wjl.fcity.bank.entity.vo.Response;
import com.wjl.fcity.bank.mapper.TwoElementsAuthRecordMapper;
import com.wjl.fcity.bank.service.MoxieBankCardAuthRecordService;
import com.wjl.fcity.bank.service.PaymentGatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 信用卡认证
 *
 * @author czl
 **/
@Slf4j
@RestController
@RequestMapping(value = "/bank/auth")
public class CreditCardController {

    @Resource
    private PaymentGatewayService paymentGatewayService;
    @Resource
    private MoxieBankCardAuthRecordService moxieBankCardAuthRecordService;
    @Resource
    private TwoElementsAuthRecordMapper twoElementsAuthRecordMapper;

    /**
     * 银行卡卡bin信息查询
     */
    @PostMapping(value = "/checkCreditCard")
    public Response checkCreditCard(HttpServletRequest request, @RequestBody CreditCardReq creditCard) {
        final Long userId = Long.valueOf(request.getHeader("userId"));

        BankCardBinDTO bankCardBinDTO = paymentGatewayService.bankCardBinQuery(userId, creditCard.getCardNo());

        return new Response<>(CodeEnum.SUCCESS, bankCardBinDTO);
    }

    /**
     * 信用卡四要素验证
     * 信用卡绑定
     */
    @RequestMapping(value = "/validationCreditCard", method = RequestMethod.POST)
    public Response validationCreditCard(HttpServletRequest request, @RequestBody CreditCardReq creditCard) {
        //查询身份证号
        Long userId = Long.valueOf(request.getHeader("userId"));
        TwoElementsAuthRecordDO twoElementsAuthRecordDO = twoElementsAuthRecordMapper.getSuccessRecord(userId);
        if (twoElementsAuthRecordDO == null) {
            return new Response<>(CodeEnum.AUTH_ILLEGAL, "");
        }

        //校验银行卡信息是否一致
        CodeEnum codeEnum = moxieBankCardAuthRecordService.checkBankCardInfo(creditCard.getCardNo(), creditCard.getPhoneNum(), userId, twoElementsAuthRecordDO);
        return new Response<>(codeEnum, "");
    }
}
