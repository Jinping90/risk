package com.wjl.fcity.coretask.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 用户信息Mapper
 *
 * @author shengju
 */
@Repository
public interface UserMapper {

    /**
     * 更新用户信用值
     *
     * @param creditValue 信用值
     * @param userIdList  需要修改的用户id集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Update("UPDATE `user` " +
            "SET credit_value = credit_value - #{creditValue}, " +
            "   gmt_modified = NOW() " +
            "WHERE " +
            "   id IN (${userIdList});")
    void updateUserAuthRecordStatus(@Param("creditValue") Integer creditValue,
                                    @Param("userIdList") String userIdList);

    /**
     * 查询注册时间小于传入时间的用户,信用值大于0
     * @param date 时间
     * @return 用户ID列表
     */
    @Select("SELECT " +
            "   id " +
            "FROM " +
            "   `user` " +
            "WHERE " +
            "   credit_value > 0 " +
            "AND gmt_created < #{date};")
    List<Long> listLessThanDate(@Param("date") Date date);
}
