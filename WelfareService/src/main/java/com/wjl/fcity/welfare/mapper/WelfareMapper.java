package com.wjl.fcity.welfare.mapper;

import com.wjl.fcity.welfare.model.CreditValueRecord;
import com.wjl.fcity.welfare.model.SignIn;
import com.wjl.fcity.welfare.model.WealthRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Fy
 * @implSpec : 签到表数据层
 * @date : 2018-03-27 11:32
 */
@Repository
public interface WelfareMapper {

    /**
     * 添加签到记录
     *
     * @param signIn 签到表对象
     */
    @Insert("insert into `sign_in` ( " +
            "   `user_id`," +
            "   `gmt_created`," +
            "   `gmt_modified`) " +
            "values (" +
            "   #{userId}, " +
            "   #{gmtCreated}, " +
            "   #{gmtModified} )")
    @Transactional(rollbackFor = Exception.class)
    void insertSignIn(SignIn signIn);

    /**
     * 获取签到次数，判断当天是否已经签到
     *
     * @param userId    用户的userId
     * @param startTime 该天开始的毫秒数
     * @param endTime   改天结束的毫秒数
     * @return 当天的签到次数(默认只能签到为1)
     */
    @Select("select count(*) " +
            "from `sign_in` " +
            "WHERE " +
            "   user_id = #{userId} " +
            "   AND UNIX_TIMESTAMP(gmt_created)>=#{startTime} " +
            "   AND UNIX_TIMESTAMP(gmt_created)<=#{endTime}")
    int countByTime(@Param("userId") Long userId,
                    @Param("startTime") Long startTime,
                    @Param("endTime") Long endTime);

    /**
     * 获取未收取的财富值
     *
     * @param wealthRecord 财富记录表对象
     * @return WealthRecord
     */
    @Select("select " +
            "   * " +
            "from " +
            "   wealth_record " +
            "where " +
            "   id=#{id} " +
            "   and status=#{status} " +
            "   AND user_id=#{userId}")
    WealthRecord notGetGather(WealthRecord wealthRecord);


    /**
     * 货币收取后更改货币记录的的状态状态[0: 未收取, 1: 已收取, 2: 已过期]
     *
     * @param oldWealthRecord 原来未收取的财富的记录对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update " +
            "   wealth_record " +
            "set " +
            "   status=#{status}," +
            "   gmt_modified=#{gmtModified} " +
            "where " +
            "   id=#{id}")
    void updateOldWealthRecord(WealthRecord oldWealthRecord);


    /**
     * 更新用户72小时内未收取的财富值状态
     *
     * @param userId    用户的userId
     * @param status    财富的状态[0: 未收取, 1: 已收取, 2: 已过期]
     * @param startTime 该天开始的毫秒数
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update " +
            "   wealth_record " +
            "set " +
            "   status=#{status}," +
            "   gmt_modified = CURRENT_TIMESTAMP " +
            "where " +
            "   user_id=#{userId} " +
            "   AND UNIX_TIMESTAMP(gmt_created)<=#{startTime} " +
            "   AND STATUS = 0")
    void updateOverdueWealth(@Param("userId") Long userId,
                             @Param("status") Integer status,
                             @Param("startTime") Long startTime);

    /**
     * 获取该用户未收取的财富对象的集合
     *
     * @param userId userId 该用户的userId
     * @param status 财富的状态[0: 未收取, 1: 已收取, 2: 已过期]
     * @param page   当前页数
     * @param size   每页容纳数
     * @return 财富对象的集合
     */
    @Select("select " +
            "   * " +
            "from " +
            "   wealth_record " +
            "where " +
            "   status=#{status} " +
            "   and user_id=#{userId} " +
            "   order by gmt_modified desc " +
            "   limit #{page},#{size} ")
    List<WealthRecord> getAllNotGatherWealth(@Param("userId") Long userId,
                                             @Param("status") Integer status,
                                             @Param("page") Integer page,
                                             @Param("size") Integer size);

    /**
     * 查询该用户在该状态status的总数
     *
     * @param userId 该用户的userId
     * @param status 财富的状态[0: 未收取, 1: 已收取, 2: 已过期]
     * @return 该状态下的总数
     */
    @Select("select " +
            "   count(*) " +
            "from " +
            "   wealth_record " +
            "where " +
            "   status=#{status} " +
            "   and user_id=#{userId} ")
    int getAllGatherWealthNum(@Param("userId") Long userId,
                              @Param("status") Integer status);

    /**
     * 根据用户签到产生的信用值插入到用户信用值记录表中
     *
     * @param creditValueRecord 用户信用值记录表对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Insert("insert into credit_value_record " +
            "   (user_id," +
            "   change_credit_value," +
            "   type,gmt_created," +
            "   gmt_modified) " +
            "values" +
            "   (#{userId}," +
            "   #{changeCreditValue}," +
            "   #{type}," +
            "   #{gmtCreated}," +
            "   #{gmtModified}) ")
    void insertCreditValueRecordToSign(CreditValueRecord creditValueRecord);

    /**
     * 根据用户的类型获取签到奖励的概率
     *
     * @param creditValueUser 用户类型
     * @return 奖励概率
     */
    @Select("select param_value from config_sys_param where param_key=#{creditValueUser}")
    String getUserGradeAward(@Param("creditValueUser") String creditValueUser);


}
