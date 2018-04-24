package com.wjl.fcity.user.service;

import com.wjl.fcity.user.model.UserIdCard;

import java.util.List;

/**
 * @author : Fy
 * @implSpec : Created with IntelliJ IDEA.
 * @date : 2018-03-26 20:41
 */
public interface UserIdCardService {


    /**
     * 保存用户的身份信息
     *
     * @param userIdCard 用户身份证信息表对象
     */
    void insertUserIdCard(UserIdCard userIdCard);

    /**
     * 跟新用户身份证信息状态
     *
     * @param status 用户的状态
     * @param userId 用户身份证信息userId
     */
    void updateUserIdCard(Integer status, Long userId);

    /**
     * 根据用户身份证号去user_id_card表查找是否该身份证已经被其他用户所绑定
     *
     * @param idCardNo 用户的身份证号码
     * @return UserIdCard的集合
     */
    List<UserIdCard> findUserIdCardByIdCardNo(String idCardNo);

}
