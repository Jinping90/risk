package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.CreditValueRecord;
import com.wjl.fcity.user.model.CreditValueRecordDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户信用值记录表
 *
 * @author czl
 */
@Repository
public interface CreditValueRecordMapper {

    /**
     * 根据用户生的信用值插入到用户信用值记录表中
     *
     * @param creditValueRecord 用户信用值记录表对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Insert("insert into " +
            "   credit_value_record " +
            "(" +
            "   user_id," +
            "   change_credit_value," +
            "   type," +
            "   gmt_created," +
            "   gmt_modified" +
            ") " +
            "   values(" +
            "   #{userId}," +
            "   #{changeCreditValue}," +
            "   #{type}," +
            "   #{gmtCreated}," +
            "   #{gmtModified}) ")
    void insertCreditValueRecordToSign(CreditValueRecord creditValueRecord);

    /**
     * 查询新的用户信用值修改记录
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
     * 查询信用值增加记录
     * @param userId 用户ID
     * @param type 修改类型
     * @return List<CreditValueRecord>
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   credit_value_record " +
            "WHERE " +
            "   user_id = #{userId} " +
            "AND type = #{type};")
    List<CreditValueRecord> listByUserIdAndType(@Param("userId") Long userId, @Param("type") Integer type);
}
