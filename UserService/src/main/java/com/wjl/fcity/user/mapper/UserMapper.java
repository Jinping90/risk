package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.dto.UserAuthReportDTO;
import com.wjl.fcity.user.model.vo.UserInfoVO;
import com.wjl.fcity.user.po.CityConfigPO;
import com.wjl.fcity.user.po.UserPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author czl
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据用户手机号，获取用户信息
     *
     * @param mobile 手机号
     * @return User
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   user " +
            "WHERE " +
            "   mobile = #{mobile}")
    UserPO findUserByMobile(String mobile);

    /**
     * 根据用户的userId，获取用户的信息
     *
     * @param userId 用户的userId
     * @return 用户表的信息
     */
    @Select("select * from user where id=#{userId}")
    UserPO findOne(@Param("userId") Long userId);


    /**
     * 修改密码
     *
     * @param id       用户id
     * @param password 密码
     * @param now      当前时间
     * @return result
     */
    @Update("UPDATE `user` " +
            "SET `password` = #{password},gmt_modified = #{now} " +
            "WHERE " +
            "   id =#{id}")
    Integer updatePassword(@Param("id") Long id, @Param("password") String password, @Param("now") Date now);

    /**
     * 添加用户
     *
     * @param userPO user
     */
    @Insert("INSERT INTO `user` ( " +
            "   mobile, " +
            "   username, " +
            "   password, " +
            "   sign_in_days, " +
            "   `status`, " +
            "   credit_level, " +
            "   invite_user_id, " +
            "   reg_source, " +
            "   profile_photo, " +
            "   gmt_created, " +
            "   platform, " +
            "   device_id " +
            ") " +
            "VALUES " +
            "   (#{mobile},#{username},#{password},#{signInDays},#{status},#{creditLevel},#{inviteUserId},#{regSource},#{profilePhoto},#{gmtCreated},#{platform},#{deviceId})")
    void addUser(UserPO userPO);


    /**
     * 获取用户基本信息
     *
     * @param userId userId
     * @return 用户基本信息
     */
    @Select("SELECT " +
            "   u.mobile, " +
            "   u.gender, " +
            "   u.total_wealth wealthValue, " +
            "   u.credit_value, " +
            "   ( " +
            "       CASE " +
            "       WHEN t.`code` = '0' " +
            "       OR t.`code` = '3' THEN " +
            "           t.`name` " +
            "       ELSE " +
            "           NULL " +
            "       END " +
            "   ) realname, " +
            "   u.`password` " +
            "FROM " +
            "   `user` u " +
            "LEFT OUTER JOIN two_elements_auth_record t ON u.id = t.user_id " +
            "WHERE " +
            "   u.id = #{userId} " +
            "   ORDER BY " +
            "       t.gmt_created DESC " +
            "LIMIT 1")
    UserInfoVO getUserInfo(Long userId);

    /**
     * 获取建筑状态
     *
     * @return 城市信息
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   city_config")
    List<CityConfigPO> getBuildingStatus();

    /**
     * 获取用户认证状态
     *
     * @param userId userId
     * @return result
     */
    @Select("SELECT " +
            "   T1.auth_item AS authItem, " +
            "   T1.`status` AS `status`, " +
            " " +
            "IF ( " +
            "   T1.gmt_modified IS NULL, " +
            "   T1.gmt_created, " +
            "   T1.gmt_modified " +
            ") AS authTime, " +
            " T1.auth_num AS authNum, " +
            " T2.validity_period AS validityPeriod, " +
            " T2.first_add_credit_value AS firstAddCreditValue, " +
            " T2.again_add_credit_value AS againAddCreditValue " +
            "FROM " +
            "   user_auth_record AS T1 " +
            "LEFT JOIN config_auth_param AS T2 ON T2.auth_item = T1.auth_item " +
            "WHERE " +
            "   T1.user_id = #{userId};")
    List<UserAuthReportDTO> getUserAuthInfo(Long userId);

    /**
     * 获取用户二要素
     *
     * @param id userId
     * @return 0存在，1不存在
     */
    @Select("SELECT " +
            "   COUNT(*) " +
            "FROM " +
            "   two_elements_auth_record " +
            "WHERE " +
            "   user_id = #{id} and (code='0' or code='3')")
    Integer getTwoAuthRecord(Long id);


    /**
     * 根据用户的 userId，更新用户的信用值
     *
     * @param userId      用户的userId
     * @param creditValue 信用值
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update user" +
            "   set " +
            "   credit_value=#{creditValue}+credit_value," +
            "   gmt_modified= now() " +
            "where " +
            "   id=#{userId}")
    void updateUserCreditValueById(@Param("userId") Long userId, @Param("creditValue") Integer creditValue);

    /**
     * 查询用户的信用值
     *
     * @param userId 用户ID
     * @return 用户的信用值
     */
    @Select("SELECT " +
            "   credit_value " +
            "FROM " +
            "   `user` " +
            "WHERE " +
            "   id = #{userId};")
    Integer getCreditValue(@Param("userId") Long userId);

    /**
     * 修改用户信用值
     *
     * @param userId      用户ID
     * @param creditValue 信用值
     */
    @Update("UPDATE `user` " +
            "SET credit_value = #{creditValue}, " +
            " gmt_modified = NOW() " +
            "WHERE " +
            "   id = #{userId};")
    void updateCreditValue(@Param("userId") Long userId, @Param("creditValue") Integer creditValue);

    /**
     * 二要素认证通过后  更新用户姓名
     *
     * @param userId 手机号
     * @param name   名称
     * @param gender 性别
     */
    @Update("UPDATE `user` u " +
            " SET u.username = #{name}, " +
            "  u.gender = #{gender} " +
            " WHERE " +
            " u.id = #{userId} ")
    void updateUserName(@Param("userId") Long userId, @Param("name") String name, @Param("gender") Integer gender);

    /**
     * 更新推荐人
     *
     * @param userId    推荐人id
     * @param mobile    手机号
     */
    @Update("UPDATE `user` u " +
            "SET u.invite_user_id = #{userId} " +
            "WHERE " +
            "u.mobile = #{mobile}")
    void updateInviteUserId(@Param("userId") Long userId, @Param("mobile") String mobile);

    /**
     * 查询是否今天签过到，0未签到，1签过到
     *
     * @param userId userId
     * @return 条数
     */
    @Select("SELECT " +
            "   COUNT(*) " +
            "FROM " +
            "   sign_in " +
            "WHERE " +
            "   user_id = #{userId} " +
            "AND TO_DAYS(gmt_created) = TO_DAYS(NOW())")
    Integer isSignInToday(Long userId);


    /**
     * 查询已邀请用户个数
     *
     * @param userId 邀请用户id
     * @return 总数
     */
    @Select("SELECT id from user where invite_user_id = #{userId}")
    List<Integer> countInvites(Long userId);

    /**
     * 今日签到获取的信用值
     *
     * @param userId userId
     * @return 信用值
     */
    @Select("SELECT " +
            "   change_credit_value " +
            "FROM " +
            "   credit_value_record " +
            "WHERE " +
            "   user_id = #{userId} " +
            "AND type = 1 " +
            "AND TO_DAYS(gmt_created) = TO_DAYS(NOW()) " +
            "LIMIT 1")
    Integer getSignInCreditValue(Long userId);

    /**
     * 保存用户的唯一设备id
     *
     * @param deviceId   唯一设备id
     * @param gmtCreated 创建时间
     */
    @Transactional(rollbackFor = Exception.class)
    @Insert(" insert into user_analysis_record (device_id,gmt_created) value (#{deviceId},#{gmtCreated})   ")
    void insertDeviceId(@Param("deviceId") String deviceId, @Param("gmtCreated") Date gmtCreated);
}
