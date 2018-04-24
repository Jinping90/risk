package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.po.LoginRecordPO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * 用户登录记录
 *
 * @author shengju
 */
@Repository
public interface LoginRecordMapper {
    /**
     * 添加用户登录记录
     *
     * @param loginRecordPO 登录记录
     */
    @Insert("INSERT INTO login_record ( " +
            "   user_id, " +
            "   device_id, " +
            "   login_time, " +
            "   ip, " +
            "   gmt_created, " +
            "   gmt_modified " +
            ") " +
            "VALUES " +
            "   (#{userId},#{deviceId},#{loginTime},#{ip},#{gmtCreated},#{gmtModified})")
    void addLoginRecord(LoginRecordPO loginRecordPO);
}
