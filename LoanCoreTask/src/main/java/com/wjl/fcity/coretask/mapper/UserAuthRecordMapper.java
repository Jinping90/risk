package com.wjl.fcity.coretask.mapper;

import com.wjl.fcity.coretask.model.UserAuthRecordDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : Fy
 * @date : 2018-03-30 10:13
 */
@Repository
public interface UserAuthRecordMapper {

    /**
     * 已过期的认证记录
     *
     * @param authItem    认证类型
     * @param overdueDate 当前时间 - 有效期天数
     * @return List<UserAuthRecordDO>
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   user_auth_record " +
            "WHERE " +
            "  status = 2 " +
            "AND auth_item = #{authItem} " +
            "AND " +
            "IF ( " +
            "   gmt_modified IS NULL, " +
            "   gmt_created, " +
            "   gmt_modified " +
            ") < #{overdueDate};")
    List<UserAuthRecordDO> listOverdueRecord(@Param("authItem") Integer authItem,
                                             @Param("overdueDate") Date overdueDate);


    /**
     * 更新用户认证记录表状态
     *
     * @param authItem 认证类型
     * @param authStatus 状态
     * @param authDetail 结果信息
     * @param idList   需要修改的id集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("UPDATE user_auth_record " +
            "SET status = #{authStatus}, " +
            "   auth_detail = #{authDetail}, " +
            "   gmt_modified = NOW() " +
            "WHERE " +
            "   auth_item = #{authItem} " +
            "AND id IN (${idList});")
    void updateUserAuthRecordStatus(@Param("authItem") Integer authItem,
                                    @Param("authStatus") Integer authStatus,
                                    @Param("authDetail") String authDetail,
                                    @Param("idList") String idList);


    /**
     * 将用户认证信息保存到用户认证记录表中
     *
     * @param userAuthRecord 用户认证信息对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Insert("INSERT INTO user_auth_record ( user_id, auth_category, auth_item, auth_detail, auth_score, auth_num, status, gmt_created, gmt_modified )" +
            "VALUE" +
            "(#{userId}, #{authCategory}, #{authItem}, #{authDetail}, #{authScore}, #{authNum}, #{status}, #{gmtCreated}, #{gmtModified} ) ")
    void insertUserAuthRecord(UserAuthRecordDO userAuthRecord);
}
