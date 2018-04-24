package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.UserAuthRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Fy
 * @date : 2018-03-30 10:13
 */
@Repository
public interface UserAuthRecordMapper {

    /**
     * 据用户的userId查询用户用户认证记录表
     *
     * @param userId       用户的userId
     * @param authCategory 认证类别[1: 居民登记中心, 2: 银行,
     *                     3: 手机营业厅, 4: 购物中心]
     * @param authItem     认证项目[1: 实名认证, 2: 身份证照片,
     *                     3: 人脸认证, 4: 信用卡认证,
     *                     5: 银行卡认证, 6: 信用卡邮箱,
     *                     7: 运营商认证, 8: 支付宝认证, 9: 淘宝认证]
     * @return UserAuthRecord
     */
    @Select("select * from user_auth_record where " +
            "   user_id=#{userId} and " +
            "   auth_category=#{authCategory} and " +
            "   auth_item=#{authItem} " +
            "order by " +
            "gmt_modified desc limit 1")
    UserAuthRecord findUserAuthRecordByUserId(@Param("userId") Long userId,
                                              @Param("authCategory") Integer authCategory,
                                              @Param("authItem") Integer authItem);


    /**
     * 更新用户认证记录表状态
     *
     * @param userAuthRecord 用户认证记录表对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update user_auth_record set " +
            "   status=#{status}, " +
            "   auth_num=#{authNum}+1, " +
            "   auth_detail=#{authDetail}, " +
            "   auth_score=#{authScore}, " +
            "   gmt_modified = CURRENT_TIMESTAMP " +
            " where " +
            "   id=#{id}")
    void updateUserAuthRecordStatus(UserAuthRecord userAuthRecord);


    /**
     * 将用户认证信息保存到用户认证记录表中
     *
     * @param userAuthRecord 用户认证信息对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Select("INSERT INTO user_auth_record ( " +
            "   user_id, " +
            "   auth_category, " +
            "   auth_item, " +
            "   auth_detail, " +
            "   auth_score, " +
            "   auth_num, " +
            "   status, " +
            "   gmt_created, " +
            "   gmt_modified )"  +
            " VALUE" +
            " (" +
            "   #{userId}, " +
            "   #{authCategory}, " +
            "   #{authItem}, " +
            "   #{authDetail}, " +
            "   #{authScore}, " +
            "   #{authNum}, " +
            "   #{status}, " +
            "   #{gmtCreated}, " +
            "   #{gmtModified} " +
            ") ")
    void insertUserAuthRecord(UserAuthRecord userAuthRecord);

    /**
     * 查询认证纪录
     *
     * @param userId   用户ID
     * @param authItem 认证项目
     * @return 认证纪录
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   user_auth_record " +
            "WHERE " +
            "   auth_item = #{authItem} " +
            "AND user_id = #{userId} " +
            "LIMIT 1;")
    UserAuthRecord findByUserIdAndItem(@Param("userId") Long userId,
                                         @Param("authItem") Integer authItem);

    /**
     * 新增认证纪录
     *
     * @param userAuthReport 新增的认证纪录对象
     */
    @Insert("INSERT INTO user_auth_record ( " +
            "   `user_id`, " +
            "   `auth_category`, " +
            "   `auth_item`, " +
            "   `auth_detail`, " +
            "   `auth_score`, " +
            "   `auth_num`, " +
            "   `status`, " +
            "   `gmt_created`, " +
            "   `gmt_modified` " +
            ") " +
            "VALUES " +
            "   (" +
            "       #{userId}, " +
            "       #{authCategory}, " +
            "       #{authItem}, " +
            "       #{authDetail}, " +
            "       #{authScore}, " +
            "       #{authNum}, " +
            "       #{status}, " +
            "       #{gmtCreated}, " +
            "       #{gmtModified} " +
            "   );")
    void insert(UserAuthRecord userAuthReport);

    /**
     * 修改认证纪录的结果信息，认证评分，认证次数，认证状态
     *
     * @param userAuthReport 修改认证纪录对象
     */
    @Update("UPDATE user_auth_record " +
            "SET auth_detail = #{authDetail}, " +
            " auth_score = #{authScore}, " +
            " auth_num = #{authNum}, " +
            " `status` = #{status}, " +
            " gmt_modified = #{gmtModified} " +
            "WHERE " +
            "   id = #{id};")
    void update(UserAuthRecord userAuthReport);
}
