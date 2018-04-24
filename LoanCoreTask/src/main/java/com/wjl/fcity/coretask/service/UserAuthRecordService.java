package com.wjl.fcity.coretask.service;

import com.wjl.fcity.coretask.model.UserAuthRecordDO;

import java.util.Date;
import java.util.List;

/**
 * 认证纪录业务
 *
 * @author czl
 */
public interface UserAuthRecordService {

    /**
     * 已过期的认证记录
     *
     * @param authItem    认证类型
     * @param overdueDate 当前时间 - 有效期天数
     * @return List<UserAuthRecordDO>
     */
    List<UserAuthRecordDO> listOverdueRecord(Integer authItem, Date overdueDate);

    /**
     * 修改已过期的认证记录
     *
     * @param authItem   认证类型
     * @param authStatus 认证状态
     * @param authDetail 结果信息
     * @param idList     id集合
     */
    void updateUserAuthRecordStatus(Integer authItem, Integer authStatus, String authDetail, List<Long> idList);
}
