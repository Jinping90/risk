package com.wjl.fcity.coretask.mapper;

import com.wjl.fcity.coretask.model.WealthRecordDO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * 财富记录Mapper
 *
 * @author czl
 */
@Repository
public interface WealthRecordMapper {

    /**
     * 查入新的用户财富记录
     *
     * @param wealthRecordDO 财富记录
     */
    @Insert("INSERT INTO wealth_record ( " +
            "   `user_id`, " +
            "   `change_wealth`, " +
            "   `status`, " +
            "   `type`, " +
            "   `generate_credit_value`, " +
            "   `gmt_created` " +
            ") " +
            "VALUES " +
            "   ( " +
            "    #{userId}, " +
            "    #{changeWealth}, " +
            "    #{status}, " +
            "    #{type}, " +
            "    #{generateCreditValue}, " +
            "    NOW() " +
            "   );")
    void insert(WealthRecordDO wealthRecordDO);
}
