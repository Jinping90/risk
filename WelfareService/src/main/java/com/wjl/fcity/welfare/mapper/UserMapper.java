package com.wjl.fcity.welfare.mapper;

import com.wjl.fcity.welfare.dto.RankingDto;
import com.wjl.fcity.welfare.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fy
 */
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
            "   welfare " +
            "WHERE " +
            "   mobile = #{mobile}")
    User findByMobile(String mobile);


    /**
     * 根据用户的userId，获取用户的信息
     *
     * @param userId 用户的userId
     * @return 用户表的信息
     */
    @Select("select * from user where id=#{userId}")
    User findOne(@Param("userId") Long userId);

    /**
     * 用户签到后，修改用户的的信用值
     *
     * @param user 用户对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update user " +
            "   set " +
            "   credit_value=#{creditValue}," +
            "   gmt_modified=#{gmtModified}," +
            "   sign_in_days=#{signInDays} " +
            "where " +
            "   id=#{id}")
    void updateUser(User user);

    /**
     * 货币收取后，增加用户总的财富值
     *
     * @param wealth      此次收取的财富值
     * @param gmtModified 此次用户修改的时间
     * @param userId      用户的userId
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update user as u" +
            "   set u.total_wealth=#{wealth} + u.total_wealth," +
            "       u.gmt_modified=#{gmtModified} " +
            "where" +
            "    u.id=#{userId}")
    void updateTotalWealth(@Param("wealth") BigDecimal wealth, @Param("gmtModified") Date gmtModified, @Param("userId") Long userId);


    /**
     * 获取全服排行的集合
     *
     * @return 所有用户根据用户拥有的算力降序的排行集合
     */
    @Select(" SELECT    " +
            " u.credit_Value AS creditValue,    " +
            " u.total_wealth AS totalWealth,    " +
            " u.id AS userId,    " +
            " t.name AS username    " +
            "FROM   " +
            "   two_elements_auth_record AS t      " +
            " LEFT JOIN  user AS u ON u.id = t.user_id  " +
            " group by t.user_id    " +
            " order by u.credit_value DESC,u.total_wealth DESC,u.gmt_modified ASC   ")
    List<RankingDto> getAllServiceOrOwnRankingDesc();
}
