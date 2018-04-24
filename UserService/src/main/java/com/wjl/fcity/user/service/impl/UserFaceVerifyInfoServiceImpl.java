package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.mapper.UserFaceVerifyInfoMapper;
import com.wjl.fcity.user.model.UserFaceVerifyInfo;
import com.wjl.fcity.user.service.UserFaceVerifyInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : Fy
 * @date : 2018-03-31 15:55
 */
@Service
public class UserFaceVerifyInfoServiceImpl implements UserFaceVerifyInfoService {

    @Resource
    private UserFaceVerifyInfoMapper userFaceVerifyInfoMapper;

    /**
     * 将人脸识别信息插入或是更新到用户人脸识别信息表中
     *
     * @param userFaceVerifyInfo 用户人脸识别信息对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdateUserFaceVerifyInfo(UserFaceVerifyInfo userFaceVerifyInfo) {

        //查询用户是否已经进行人脸识别
        Long id = userFaceVerifyInfoMapper.findByUserId(userFaceVerifyInfo.getUserId());
        if (null != id) {
            userFaceVerifyInfo.setId(id);
            userFaceVerifyInfoMapper.updateUserFaceVerifyInfoById(userFaceVerifyInfo);
        } else {
            userFaceVerifyInfoMapper.insertUserFaceVerifyInfo(userFaceVerifyInfo);
        }
    }

    /**
     * 根据用户的userId查找用户人脸识别信息对象
     *
     * @param userId 用户的userId
     * @return UserFaceVerifyInfo
     */
    @Override
    public UserFaceVerifyInfo findUserFaceVerifyInfoByUserId(Long userId) {

        return userFaceVerifyInfoMapper.findUserFaceVerifyInfoByUserId(userId);
    }

    /**
     * 保存用户的人脸识别度和状态
     *
     * @param userId      用户的userId
     * @param matchDegree 人脸匹配度
     * @param validStatus 验证是否通过(0:通过,1:未通过)
     */
    @Override
    public void updateUserFaceVerifyInfoMatchDegreeByUserId(Long userId, Float matchDegree, Integer validStatus) {
        userFaceVerifyInfoMapper.updateUserFaceVerifyInfoMatchDegreeByUserId(userId, matchDegree, validStatus);
    }

    /**
     * 修改用户人脸识别的状态
     *
     * @param status 验证是否通过(0:通过,1:未通过)
     * @param userId 用户的userId
     */
    @Override
    public void updateUserFaceVerifyInfoStatusByUserId(Integer status, Long userId) {
        userFaceVerifyInfoMapper.updateUserFaceVerifyInfoStatusByUserId(status, userId);
    }
}
