package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.UserIdCard;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Fy
 * @implSpec : 用户身份证信息dao数据库处理层
 * @date : 2018-03-29 18:05
 */
@Repository
public interface UserIdCardMapper {

    /**
     * 保存用户身份证信息
     *
     * @param userIdCard 用户信息表对象
     */
    @Insert("insert into user_id_card ( " +
            "   user_id, " +
            "   name, " +
            "   birthday, " +
            "   id_card_no, " +
            "   nation, " +
            "   gender, " +
            "   address, " +
            "   agency, " +
            "   valid_date_begin, " +
            "   valid_date_end, " +
            "   front_img_url, " +
            "   back_img_url, " +
            "   db_img_url, " +
            "   status, " +
            "   gmt_created, " +
            "   gmt_modified " +
            ") " +
            "value " +
            "   (#{userId}, " +
            "   #{name}, " +
            "   #{birthday}, " +
            "   #{idCardNo}, " +
            "   #{nation}, " +
            "   #{gender}, " +
            "   #{address}, " +
            "   #{agency}," +
            "   #{validDateBegin}, " +
            "   #{validDateEnd}, " +
            "   #{frontImgUrl}, " +
            "   #{backImgUrl}, " +
            "   #{dbImgUrl}, " +
            "   #{status}, " +
            "   #{gmtCreated}, " +
            "   #{gmtModified} )")
    @Transactional(rollbackFor = Exception.class)
    void insertUserIdCard(UserIdCard userIdCard);


    /**
     * 更新用户身份信息
     *
     * @param status 用户身份证信息认证状态
     * @param userId 用户身份证信息表userId
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update user_id_card set " +
            "   status=#{status} " +
            "where " +
            "   user_Id=#{userId} " +
            "   order by gmt_modified desc limit 1")
    void updateUserIdCard(@Param("status") Integer status,
                          @Param("userId") Long userId);

    /**
     * 根据用户身份证号去user_id_card表查找是否该身份证已经被其他用户所绑定
     *
     * @param idCardNo 用户的身份证号码
     * @return UserIdCard的集合
     */
    @Select("select * from user_id_card " +
            "where " +
            "   id_card_no =#{idCardNo}")
    List<UserIdCard> findUserIdCardByIdCardNo(@Param("idCardNo") String idCardNo);
}
