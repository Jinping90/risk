package com.wjl.fcity.coretask.service.impl;


import com.wjl.fcity.coretask.common.util.StringUtil;
import com.wjl.fcity.coretask.mapper.UserAuthRecordMapper;
import com.wjl.fcity.coretask.model.UserAuthRecordDO;
import com.wjl.fcity.coretask.service.UserAuthRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 认证纪录业务实现
 *
 * @author czl
 */
@Slf4j
@Service
public class UserAuthRecordServiceImpl implements UserAuthRecordService {

    @Resource
    private UserAuthRecordMapper userAuthRecordMapper;

    /**
     * 已过期的认证记录
     *
     * @param authItem    认证类型
     * @param overdueDate 当前时间 - 有效期天数
     * @return List<UserAuthRecordDO>
     */
    @Override
    public List<UserAuthRecordDO> listOverdueRecord(Integer authItem, Date overdueDate) {

        return userAuthRecordMapper.listOverdueRecord(authItem, overdueDate);
    }

    /**
     * 更新用户认证记录表状态
     *
     * @param authItem   认证类型
     * @param authStatus 状态
     * @param authDetail 结果信息
     * @param idList     id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserAuthRecordStatus(Integer authItem, Integer authStatus, String authDetail, List<Long> idList) {

        String idListStr = StringUtil.listToString(idList);

        userAuthRecordMapper.updateUserAuthRecordStatus(authItem, authStatus, authDetail, idListStr);
    }
}
