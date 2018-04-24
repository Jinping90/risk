package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.mapper.UserIdCardMapper;
import com.wjl.fcity.user.model.UserIdCard;
import com.wjl.fcity.user.service.UserIdCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : Fy
 * @date : 2018-03-26 20:43
 */
@Slf4j
@Service
public class UserIdCardServiceImpl implements UserIdCardService {

    @Resource
    private UserIdCardMapper userIdCardMapper;

    /**
     * 保存用户身份信息
     *
     * @param userIdCard 用户身份证信息表对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserIdCard(UserIdCard userIdCard) {
        userIdCardMapper.insertUserIdCard(userIdCard);
    }

    /**
     * 跟新用户身份证信息状态
     *
     * @param status 用户的userId
     * @param userId 用户身份证信息userId
     */
    @Override
    public void updateUserIdCard(Integer status, Long userId) {
        userIdCardMapper.updateUserIdCard(status, userId);
    }

    /**
     * 根据用户身份证号去user_id_card表查找是否该身份证已经被其他用户所绑定
     *
     * @param idCardNo 用户的身份证号码
     * @return UserIdCard的集合
     */
    @Override
    public List<UserIdCard> findUserIdCardByIdCardNo(String idCardNo) {
        return userIdCardMapper.findUserIdCardByIdCardNo(idCardNo);
    }


}
