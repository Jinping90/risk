package com.wjl.fcity.msg.service;

import com.wjl.fcity.msg.dto.MessageDTO;
import com.wjl.fcity.msg.model.MessageContent;

import java.util.List;

/**
 * @author xuhaihong
 * @date 2017-12-14 17:19
 **/
public interface MessageService {


    /**
     * count Messages
     * 统计所有状态为null 和0 的消息、公告
     *
     * @param userId 用户id
     * @return 返回总数
     */
    Integer countUnReadMessage(Long userId);


    /**
     * 查询用户个人信息
     *
     * @param messageDTO 消息参数
     * @return 个人信息集合
     */
    List<MessageContent> listPrivateMessage(MessageDTO messageDTO);

    /**
     * 统计个人消息
     *
     * @param userId 用户ID
     * @return 总数
     */
    Integer countByUserId(Long userId);

    /**
     * 查询公告消息
     *
     * @param messageDTO 消息参数
     * @return 公告消息
     */
    List<MessageContent> listPublicMessage(MessageDTO messageDTO);

    /**
     * 统计公告消息
     *
     * @param userId 消息参数
     * @return 公告消息数量
     */
    Integer countPublicMessage(Long userId);

    /**
     * 查询详情
     *
     * @param id 消息id
     * @return 详情
     */
    MessageContent getMessageDetail(Long id);


    /**
     * 保存一条已读公告记录
     *
     * @param id     消息id
     * @param userId 用户id
     */
    void savePublicMessageStatus(Long id, Long userId);

    /**
     * 更新个人消息为已读
     *
     * @param messageDTO 消息
     */
    void updatePrivateMessageStatus(MessageDTO messageDTO);

    /**
     * * 添加新的个人消息接口
     *
     * @param userId         接受用户id
     * @param messageContent 设置 标题、消息内容
     * @return 返回结果  成功为空字符串“”
     */
    String addPrivateMessage(Long userId, MessageContent messageContent);

    /**
     * 添加新的公共消息接口
     *
     * @param messageContent 设置 标题、消息内容、图片路径（没有null）
     * @return 返回结果  成功为空字符串“”
     */
    String addPublicMessage(MessageContent messageContent);
}
