package com.wjl.fcity.msg.mapper;

import com.wjl.fcity.msg.common.config.MybatisInsertLanguageDriver;
import com.wjl.fcity.msg.model.MessageStatus;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author xuhaihong
 * @date 2018-03-28 20:23
 **/
@Repository
public interface MessageStatusMapper {

    /**
     * 保存
     * @param messageStatus 消息
     */
    @Lang(MybatisInsertLanguageDriver.class)
    @Insert("INSERT INTO message_record (#{messageStatus})")
    void save(MessageStatus messageStatus);

    /**
     * 更新
     * @param messageStatus 消息
     */
    @Lang(MybatisInsertLanguageDriver.class)
    @Update("update message_record  SET  `status` = 1 where user_id = #{userId} and message_id = #{messageId} ")
    void update(MessageStatus messageStatus);

    /**
     * 查询消息
     * @param messageStatus 消息
     * @return 结果
     */
    @Select("SELECT * FROM message_record r WHERE r.message_id = #{messageId} and r.user_id = #{userId}")
    MessageStatus findMessageStatus(MessageStatus messageStatus);
}
