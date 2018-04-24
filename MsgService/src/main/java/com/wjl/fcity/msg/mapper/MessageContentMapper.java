package com.wjl.fcity.msg.mapper;

import com.wjl.fcity.msg.common.config.MybatisInsertLanguageDriver;
import com.wjl.fcity.msg.dto.MessageDTO;
import com.wjl.fcity.msg.mapper.provider.MessageContentProvider;
import com.wjl.fcity.msg.model.MessageContent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xuhaihong
 * @create 2018-03-27 11:31
 **/
@Repository
public interface MessageContentMapper {

    /**
     * 查询所有未读的个人信息总数
     *
     * @param userId 用户id
     * @return 个人信息总数
     */
    @Select("SELECT " +
            "   COUNT(*) " +
            "FROM " +
            "   message AS T1 " +
            "LEFT JOIN message_record AS T2 ON T2.message_id = T1.id " +
            "WHERE " +
            "   T2.user_id = #{userId} " +
            "AND T1.type = 2 " +
            "AND T2.`status` = 0;")
    Integer countUnReadPrivateMessage(Long userId);

    /**
     * 查询所有未读的公告信息总数
     *
     * @param userId 用户id
     * @return 公告信息总数
     */
    @Select("SELECT " +
            "   COUNT(*) " +
            "FROM " +
            "   message AS T1 " +
            "WHERE " +
            "   T1.id NOT IN ( " +
            "    SELECT " +
            "       T2.message_id " +
            "    FROM " +
            "       message_record AS T2 " +
            "    WHERE " +
            "       T2.user_id = #{userId} " +
            "    AND T2.`status` = 1 " +
            "   ) AND T1.type = 1;")
    Integer countUnReadPublicMessage(Long userId);

    /**
     * 查询当前用户 个人消息
     *
     * @param messageDTO 消息参数
     * @return 当前用户 个人消息
     */
    @SelectProvider(type = MessageContentProvider.class, method = "listPrivateMessage")
    List<MessageContent> listPrivateMessage(MessageDTO messageDTO);

    /**
     * 统计当前用户 个人消息
     *
     * @param userId 用户id
     * @return 当前用户 个人消息数
     */
    @SelectProvider(type = MessageContentProvider.class, method = "countByUserId")
    Integer countByUserId(Long userId);

    /**
     * 查询当前用户未读 公告
     *
     * @param messageDTO 消息参数
     * @return 用户未读 公告信息
     */
    @SelectProvider(type = MessageContentProvider.class, method = "listPublicMessage")
    List<MessageContent> listPublicMessage(MessageDTO messageDTO);

    /**
     * 查询当前用户未读 公告
     *
     * @param userId 消息参数
     * @return 未读 公告数
     */
    @SelectProvider(type = MessageContentProvider.class, method = "countPublicMessage")
    Integer countPublicMessage(Long userId);

    /**
     * 通过id查询message
     *
     * @param id id
     * @return message实体类
     */
    @SelectProvider(type = MessageContentProvider.class, method = "getMessageById")
    MessageContent getMessageById(Long id);

    /**
     * 保存消息
     *
     * @param messageContent 消息
     */
    @Insert("INSERT INTO message (#{messageContent})")
    @Lang(MybatisInsertLanguageDriver.class)
    void save(MessageContent messageContent);
}
