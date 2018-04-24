package com.wjl.fcity.coretask.mapper;

import com.wjl.fcity.coretask.model.CreditValueRecordDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 用户信用值记录表
 *
 * @author czl
 */
@Repository
public interface CreditValueRecordMapper {

    /**
     * 查入新的用户信用值修改记录
     *
     * @param creditValueRecordDO 用户信用值修改记录
     */
    @Insert("INSERT INTO credit_value_record ( " +
            "   `user_id`, " +
            "   `change_credit_value`, " +
            "   `type`, " +
            "   `gmt_created` " +
            ") " +
            "VALUES " +
            "   ( " +
            "    #{userId}, " +
            "    #{changeCreditValue}, " +
            "    #{type}, " +
            "    #{gmtCreated} " +
            "   );")
    void insert(CreditValueRecordDO creditValueRecordDO);

    /**
     * 用户在某时间前的认证的总算力
     *
     * @param userId 用户ID
     * @param date   时间
     * @return 总算力
     */
    @Select("SELECT " +
            "   SUM(change_credit_value) " +
            "FROM " +
            "   credit_value_record " +
            "WHERE " +
            "   user_id = #{userId} " +
            "AND type IN (2, 3, 4, 5, 6, 7, 8, 11) " +
            "AND gmt_created < #{date};")
    Integer getAuthCreditValue(@Param("userId") Long userId, @Param("date") Date date);

    /**
     * 用户在某时间前的其他的总算力
     *
     * @param userId 用户ID
     * @param date   时间
     * @return 总算力
     */
    @Select("SELECT " +
            "   SUM(change_credit_value) " +
            "FROM " +
            "   credit_value_record " +
            "WHERE " +
            "   user_id = #{userId} " +
            "AND type IN (1, 9, 10) " +
            "AND gmt_created < #{date};")
    Integer getOtherCreditValue(@Param("userId") Long userId, @Param("date") Date date);
}
