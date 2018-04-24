package com.wjl.fcity.bank.service;

import com.wjl.fcity.bank.common.enums.CodeEnum;
import com.wjl.fcity.bank.entity.model.TwoElementsAuthRecordDO;

/**
 * 魔蝎银行卡三四要素查询记录业务处理
 *
 * @author czl
 */
public interface MoxieBankCardAuthRecordService {

    /**
     * 校验银行卡信息是否一致
     * @param cardNo 银行卡号
     * @param phoneNum 手机号
     * @param userId 用户ID
     * @param twoElementsAuthRecordDO 二要素认证记录
     * @return Response
     */
    CodeEnum checkBankCardInfo(String cardNo, String phoneNum, Long userId, TwoElementsAuthRecordDO twoElementsAuthRecordDO);
}
