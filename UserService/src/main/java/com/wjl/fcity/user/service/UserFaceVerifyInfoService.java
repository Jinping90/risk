package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.UserFaceVerifyInfo;

/**
 * @author : Fy
 * @date : 2018-03-31 15:51
 */
public interface UserFaceVerifyInfoService {

    /**
     * 将人脸识别信息插入或是更新到用户人脸识别信息表中
     *
     * @param userFaceVerifyInfo 用户人脸识别信息对象
     */
    void insertOrUpdateUserFaceVerifyInfo(UserFaceVerifyInfo userFaceVerifyInfo);

    /**
     * 根据用户的userId查找用户人脸识别信息对象
     *
     * @param userId 用户的userId
     * @return UserFaceVerifyInfo
     */
    UserFaceVerifyInfo findUserFaceVerifyInfoByUserId(Long userId);

    /**
     * 保存用户的人脸识别度和状态
     *
     * @param userId      用户的userId
     * @param matchDegree 人脸匹配度
     * @param validStatus 验证是否通过(0:通过,1:未通过)
     */
    void updateUserFaceVerifyInfoMatchDegreeByUserId(Long userId, Float matchDegree, Integer validStatus);

    /**
     * 修改用户人脸识别的状态
     *
     * @param status 验证是否通过(0:通过,1:未通过)
     * @param userId 用户的userId
     */
    void updateUserFaceVerifyInfoStatusByUserId(Integer status, Long userId);
}
