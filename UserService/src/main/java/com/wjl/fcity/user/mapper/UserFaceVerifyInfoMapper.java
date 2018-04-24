package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.UserFaceVerifyInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Fy
 * @date : 2018-03-31 16:00
 */
@Repository
public interface UserFaceVerifyInfoMapper {

    /**
     * 将人脸识别信息插入到用户人脸识别信息表中
     *
     * @param userFaceVerifyInfo 用户人脸识别信息对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Insert("insert into user_face_verify_info " +
            "( " +
            "   user_id, " +
            "   valid_status, " +
            "   valid_date, " +
            "   ip, " +
            "   match_degree, " +
            "   avatar_url1, " +
            "   avatar_url2, " +
            "   avatar_url3, " +
            "   avatar_url4, " +
            "   gmt_created, " +
            "   gmt_modified) " +
            "value" +
            "(" +
            "   #{userId}, " +
            "   #{validStatus} , " +
            "   #{validDate}, " +
            "   #{ip}, " +
            "   #{matchDegree}, " +
            "   #{avatarUrl1}, " +
            "   #{avatarUrl2}, " +
            "   #{avatarUrl3}, " +
            "   #{avatarUrl4}, " +
            "   #{gmtCreated}, " +
            "   #{gmtModified} " +
            ")")
    void insertUserFaceVerifyInfo(UserFaceVerifyInfo userFaceVerifyInfo);

    /**
     * 根据用户的userId去人脸识别user_face_verify_info表查找对象是否已经存在
     *
     * @param userId 用户的userId
     * @return UserFaceVerifyInfo
     */
    @Select("select id from " +
            "   user_face_verify_info " +
            "where " +
            "   user_id=#{userId}")
    Long findByUserId(@Param("userId") Long userId);


    /**
     * 根据用户的id来跟新用户的用户人脸识别信息信息
     *
     * @param userFaceVerifyInfo 用户人脸识别信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update " +
            "   user_face_verify_info " +
            "set " +
            "   valid_status=#{validStatus}," +
            "   valid_date=#{validDate}," +
            "   ip=#{ip}," +
            "   match_degree=#{matchDegree}," +
            "   avatar_url1=#{avatarUrl1}," +
            "   avatar_url2=#{avatarUrl2}," +
            "   avatar_url3=#{avatarUrl3}," +
            "   avatar_url4=#{avatarUrl4}," +
            "   gmt_modified=#{gmtModified} " +
            "where " +
            "   id=#{id} ")
    void updateUserFaceVerifyInfoById(UserFaceVerifyInfo userFaceVerifyInfo);

    /**
     * 根据用户的userId查找用户人脸识别信息对象
     *
     * @param userId 用户的userId
     * @return UserFaceVerifyInfo
     */
    @Select("select * from " +
            "   user_face_verify_info " +
            "where " +
            "   user_id=#{userId}")
    UserFaceVerifyInfo findUserFaceVerifyInfoByUserId(@Param("userId") Long userId);

    /**
     * 保存用户的人脸识别度和状态
     *
     * @param userId      用户的userId
     * @param matchDegree 人脸匹配度
     * @param validStatus 验证是否通过(0:通过,1:未通过)
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update " +
            "   user_face_verify_info " +
            "set " +
            "   match_degree=#{matchDegree}," +
            "   valid_status=#{validStatus}," +
            "   gmt_modified=CURRENT_TIMESTAMP " +
            "where " +
            "   user_id=#{userId}")
    void updateUserFaceVerifyInfoMatchDegreeByUserId(@Param("userId") Long userId,
                                                     @Param("matchDegree") Float matchDegree,
                                                     @Param("validStatus") Integer validStatus);

    /**
     * 修改用户人脸识别的状态
     *
     * @param status 验证是否通过(0:通过,1:未通过)
     * @param userId 用户的userId
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("update " +
            "   user_face_verify_info " +
            "set " +
            "   valid_status=#{status}," +
            "   gmt_modified=CURRENT_TIMESTAMP " +
            "where " +
            "   user_id=#{userId} " +
            "order by " +
            "   gmt_created limit 1")
    void updateUserFaceVerifyInfoStatusByUserId(@Param("status") Integer status,
                                                @Param("userId") Long userId);
}
