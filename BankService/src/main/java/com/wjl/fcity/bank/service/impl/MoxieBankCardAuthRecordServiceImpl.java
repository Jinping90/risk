package com.wjl.fcity.bank.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjl.fcity.bank.common.constant.Constant;
import com.wjl.fcity.bank.common.enums.*;
import com.wjl.fcity.bank.common.util.MoXieHttpUtil;
import com.wjl.fcity.bank.entity.model.MoxieBankCardAuthRecordDO;
import com.wjl.fcity.bank.entity.model.TwoElementsAuthRecordDO;
import com.wjl.fcity.bank.entity.request.AuthRecordReq;
import com.wjl.fcity.bank.entity.request.CreditValueReq;
import com.wjl.fcity.bank.mapper.MoxieBankCardAuthRecordMapper;
import com.wjl.fcity.bank.service.MoxieBankCardAuthRecordService;
import com.wjl.fcity.bank.service.micro.service.UserMicroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 魔蝎银行卡三四要素查询记录业务处理实现
 *
 * @author czl
 */
@Slf4j
@Service
public class MoxieBankCardAuthRecordServiceImpl implements MoxieBankCardAuthRecordService {

    @Resource
    private MoxieBankCardAuthRecordMapper moxieBankCardAuthRecordMapper;
    @Resource
    private UserMicroService userMicroService;

    @Override
    public CodeEnum checkBankCardInfo(String cardNo, String phoneNum, Long userId, TwoElementsAuthRecordDO twoElementsAuthRecordDO) {
        String idCardNo = twoElementsAuthRecordDO.getIdCardNo();
        String name1 = twoElementsAuthRecordDO.getName();
        String param = "name=" + name1 + "&idCard=" + idCardNo + "&acctNo=" + cardNo + "&mobile=" + phoneNum + "&type=" + Constant.MOXIE_TYPE;
        log.error("魔蝎信用卡四要素认证信息 ： " + param);

        //校验银行卡号信息是否一致
        String result = MoXieHttpUtil.sendGet(Constant.MOXIE_URL, param);
        JSONObject jsonReport = JSONObject.parseObject(result);
        log.info("魔蝎查询信用卡信息结果：" + jsonReport.toJSONString());

        MoxieBankCardAuthRecordDO moxieBankCardAuthRecordDo = new MoxieBankCardAuthRecordDO();
        moxieBankCardAuthRecordDo.setUserId(userId);
        moxieBankCardAuthRecordDo.setMobile(phoneNum);
        moxieBankCardAuthRecordDo.setCardNo(cardNo);

        boolean success = (boolean) jsonReport.get("success");
        if (success) {
            //保存魔蝎查询返回结果
            JSONObject resultData = jsonReport.getJSONObject("data");
            moxieBankCardAuthRecordDo.setCode(resultData.get("code").toString());
            moxieBankCardAuthRecordDo.setDescribe(resultData.get("desc").toString());
            moxieBankCardAuthRecordDo.setTransId(resultData.get("trans_id").toString());
            moxieBankCardAuthRecordDo.setTradeNo(resultData.get("trade_no").toString());
            moxieBankCardAuthRecordDo.setBankName(resultData.get("bank_name") == null ? null : resultData.get("bank_name").toString());
            moxieBankCardAuthRecordDo.setCardType(resultData.get("card_type") == null ? null : resultData.get("card_type").toString());
            moxieBankCardAuthRecordMapper.insert(moxieBankCardAuthRecordDo);

            String code = (String) resultData.get("code");
            String authFail = "1";
            String authSuccess = "0";
            if (authFail.equals(code)) {

                log.info(String.format("用户userId=%s，信用卡认证失败  信息不一致", userId));
                return CodeEnum.WRONG_CREDIT_CARD_ID;
            } else if (authSuccess.equals(code)) {

                //保存认证信息
                log.info(String.format("用户userId=%s，信用卡认证", userId));
                AuthRecordReq authRecordReq = new AuthRecordReq(userId, AuthCategoryEnum.BANK_AUTH, AuthItemEnum.CREDIT_CARD_AUTH, AuthStatusEnum.AUTH_PASS);
                userMicroService.saveOrUpdateAuthRecord(authRecordReq);

                //修改用户的信用值
                userMicroService.updateCreditValue(new CreditValueReq(userId, AuthItemEnum.CREDIT_CARD_AUTH, ChangeCreditValueEnum.CREDIT_CARD_AUTH));
                return CodeEnum.SUCCESS;
            }

            log.error("魔蝎查询信用卡信息失败！");
            return CodeEnum.UN_KNOW_CREDIT_CARD;
        } else {
            //保存魔蝎查询返回结果
            moxieBankCardAuthRecordDo.setCode(jsonReport.get("errorCode") == null ? null : jsonReport.get("errorCode").toString());
            moxieBankCardAuthRecordDo.setDescribe(jsonReport.get("errorMsg") == null ? null : jsonReport.get("errorMsg").toString());
            moxieBankCardAuthRecordMapper.insert(moxieBankCardAuthRecordDo);

            log.error("魔蝎查询信用卡信息失败！");
            return CodeEnum.UN_KNOW_CREDIT_CARD;
        }
    }
}
