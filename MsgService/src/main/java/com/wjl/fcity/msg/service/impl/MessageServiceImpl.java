package com.wjl.fcity.msg.service.impl;

import com.wjl.fcity.msg.dto.MessageDTO;
import com.wjl.fcity.msg.mapper.MessageContentMapper;
import com.wjl.fcity.msg.mapper.MessageStatusMapper;
import com.wjl.fcity.msg.model.MessageContent;
import com.wjl.fcity.msg.model.MessageStatus;
import com.wjl.fcity.msg.service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author xuhaihong
 * @date 2017-12-14 17:20
 **/

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageContentMapper messageContentMapper;

    @Resource
    private MessageStatusMapper messageStatusMapper;

    @Override
    public Integer countUnReadMessage(Long userId) {
        int count = 0;
        Integer privateMessage= messageContentMapper.countUnReadPrivateMessage(userId);
        Integer publicMessage= messageContentMapper.countUnReadPublicMessage(userId);
        if(privateMessage != null){
            count += privateMessage;
        }

        if(publicMessage !=null){
            count += publicMessage;
        }
        return count;
    }

    @Override
    public List<MessageContent> listPrivateMessage(MessageDTO messageDTO) {

        return messageContentMapper.listPrivateMessage(messageDTO);
    }

    @Override
    public Integer countByUserId(Long userId) {

        return messageContentMapper.countByUserId(userId);
    }

    @Override
    public List<MessageContent> listPublicMessage(MessageDTO messageDTO) {

        return messageContentMapper.listPublicMessage(messageDTO);
    }

    @Override
    public Integer countPublicMessage(Long userId) {

        return messageContentMapper.countPublicMessage(userId);
    }

    @Override
    public MessageContent getMessageDetail(Long id) {

        return messageContentMapper.getMessageById(id);
    }


    @Override
    public void savePublicMessageStatus(Long id, Long userId) {

        MessageStatus messageStatus = new MessageStatus();
        Integer hasRead = 1;
        Date date = new Date();
        messageStatus.setMessageId(id);
        messageStatus.setUserId(userId);
        messageStatus.setStatus(hasRead);
        messageStatus.setGmtCreated(date);
        messageStatus.setGmtModified(date);
        MessageStatus messageStatu = messageStatusMapper.findMessageStatus(messageStatus);
        if (messageStatu == null) {
            messageStatusMapper.save(messageStatus);
        }
    }

    @Override
    public void updatePrivateMessageStatus(MessageDTO messageDTO) {
        MessageStatus messageStatus = new MessageStatus();
        Integer hasRead = 1;
        Date date = new Date();
        messageStatus.setMessageId(messageDTO.getId());
        messageStatus.setUserId(messageDTO.getUserId());
        messageStatus.setStatus(hasRead);
        messageStatusMapper.update(messageStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addPrivateMessage(Long userId, MessageContent messageContent) {
        Integer personalMsg = 2;
        Integer unRead = 0;
        Date date = new Date();
        //添加一条消息
        if (messageContent.getContent() == null) {
            return "内容为空";
        }
        if (messageContent.getTitle() == null) {
            return "标题为空";
        }
        if (userId == null) {
            return "用户id为空";
        }
        messageContent.setGmtCreated(date);
        messageContent.setGmtModified(date);
        messageContent.setType(personalMsg);
        messageContentMapper.save(messageContent);
        //添加一条未读消息记录
        MessageStatus messageStatus = new MessageStatus();
        messageStatus.setMessageId(messageContent.getId());
        messageStatus.setUserId(userId);
        messageStatus.setStatus(unRead);
        messageStatus.setGmtCreated(date);
        messageStatus.setGmtModified(date);
        messageStatusMapper.save(messageStatus);
        return "";
    }

    @Override
    public String addPublicMessage(MessageContent messageContent) {
        //添加时间
        Date date = new Date();
        Integer publicMsg = 2;
        if (messageContent.getContent() == null) {
            return "内容为空";
        }
        if (messageContent.getTitle() == null) {
            return "标题为空";
        }
        messageContent.setGmtCreated(date);
        messageContent.setGmtCreated(date);
        messageContent.setType(publicMsg);
        messageContentMapper.save(messageContent);
        return "";
    }
}
