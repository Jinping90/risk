package com.wjl.fcity.bank.mapper;

import com.wjl.fcity.bank.entity.model.MoxieBankCardAuthRecordDO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * 魔蝎银行卡三四要素查询记录
 *
 * @author czl
 */
@Repository
public interface MoxieBankCardAuthRecordMapper {

    /**
     * 新增魔蝎校验纪录对象
     *
     * @param moxieBankCardAuthRecordDo 新增的魔蝎校验纪录对象
     */
    @Insert("INSERT INTO moxie_bank_card_auth_record ( " +
            "   `user_id`, " +
            "   `card_no`, " +
            "   `mobile`, " +
            "   `bank_name`, " +
            "   `card_type`, " +
            "   `code`, " +
            "   `describe`, " +
            "   `trans_id`, " +
            "   `trade_no`, " +
            "   `gmt_created`, " +
            "   `gmt_modified` " +
            ") " +
            "VALUES " +
            "   ( " +
            "       #{userId}, " +
            "       #{cardNo}, " +
            "       #{mobile}, " +
            "       #{bankName}, " +
            "       #{cardType}, " +
            "       #{code}, " +
            "       #{describe}, " +
            "       #{transId}, " +
            "       #{tradeNo}, " +
            "       NOW(), " +
            "       #{gmtModified} " +
            "   );")
    void insert(MoxieBankCardAuthRecordDO moxieBankCardAuthRecordDo);
}
