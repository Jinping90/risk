package com.wjl.fcity.msg.controller;

import com.google.common.collect.Maps;
import com.wjl.fcity.msg.common.enums.CodeEnum;
import com.wjl.fcity.msg.dto.*;
import com.wjl.fcity.msg.model.MessageContent;
import com.wjl.fcity.msg.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuhaihong
 * 个人消息/公告controller
 */
@Slf4j
@RestController
@RequestMapping(value = "/auth/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 未读消息统计接口
     */
    @RequestMapping(value = "/countMessage", method = RequestMethod.POST)
    public Response countMessage(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getHeader("userId"));
        Integer totalMessage = messageService.countUnReadMessage(userId);
        if (totalMessage == null) {
            totalMessage = 0;
        }
        Map<String, Integer> map = Maps.newHashMap();
        map.put("total", totalMessage);
        return new Response<>(CodeEnum.SUCCESS, map);

    }

    /**
     * 查询个人消息接口
     *
     * @param params  参数
     * @param request 参数
     * @return 结果
     */
    @RequestMapping(value = "/findPrivateMessage", method = RequestMethod.POST)
    public Response findPrivateMessage(@RequestBody Map params, HttpServletRequest request) {

        Integer page;
        Integer size;
        try {
            page = (Integer) params.get("page");
            size = (Integer) params.get("size");
        } catch (Exception e) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        Integer pageSize;
        if (size == null) {
            pageSize = 20;
        } else {
            pageSize = size;
        }

        Long userId = Long.valueOf(request.getHeader("userId"));
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setPage(page);
        messageDTO.setSize(pageSize);
        messageDTO.setUserId(userId);
        //查询出个人消息
        List<MessageContent> list = messageService.listPrivateMessage(messageDTO);
        Integer total = messageService.countByUserId(userId);
        List<MessagePublicListDto> list1 = new ArrayList<>();
        if (list != null) {
            MessagePublicListDto mesagePublicListDto;
            for (MessageContent m : list) {
                mesagePublicListDto = new MessagePublicListDto();
                m.setStatus(m.getType().toString());
                mesagePublicListDto.setTital(m.getTitle());
                mesagePublicListDto.setContent(m.getContent());
                mesagePublicListDto.setStatus(m.getType().toString());
                mesagePublicListDto.setId(m.getId());
                mesagePublicListDto.setAddTime(m.getGmtCreated().getTime());
                list1.add(mesagePublicListDto);
            }

        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("list", list1);
        map.put("total", total);
        map.put("size", pageSize);
        map.put("page", page);

        log.info("查询个人消息列表方法执行" + page + size);
        return new Response<>(CodeEnum.SUCCESS, map);
    }

    /**
     * 查询公告接口
     *
     * @param params  参数
     * @param request 参数
     * @return 结果
     */
    @RequestMapping(value = "/findPublicMessage", method = RequestMethod.POST)
    public Response findPublicMessage(@RequestBody Map params, HttpServletRequest request) {

        Integer page;
        Integer size;
        try {
            page = (Integer) params.get("page");
            size = (Integer) params.get("size");
        } catch (Exception e) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        Integer pageSize;
        if (size == null) {
            pageSize = 5;
        } else {
            pageSize = size;

        }
        Long userId = Long.valueOf(request.getHeader("userId"));
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setPage(page);
        messageDTO.setSize(pageSize);
        messageDTO.setUserId(userId);
        //查询出公告消息
        List<MessageContent> list = messageService.listPublicMessage(messageDTO);
        Integer total = messageService.countPublicMessage(userId);
        List<MessagePublicListDto> list1 = new ArrayList<>();
        MessagePublicListDto mesagePublicListDto;
        if (list != null) {
            for (MessageContent m : list) {
                mesagePublicListDto = new MessagePublicListDto();
                mesagePublicListDto.setTital(m.getTitle());
                mesagePublicListDto.setContent(m.getContent());
                if (null == m.getType()) {
                    mesagePublicListDto.setStatus("0");
                } else {
                    mesagePublicListDto.setStatus(m.getType().toString());
                }
                mesagePublicListDto.setId(m.getId());
                mesagePublicListDto.setAddTime(m.getGmtCreated().getTime());
                list1.add(mesagePublicListDto);
            }
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("list", list1);
        map.put("total", total);
        map.put("size", pageSize);
        map.put("page", page);
        log.info("查询公告  消息列表方法执行" + page + size);
        return new Response<>(CodeEnum.SUCCESS, map);

    }

    /**
     * 公告详情接口
     *
     * @param map id
     * @return 返回结果
     */
    @RequestMapping(value = "/publicMessageDetail", method = RequestMethod.POST)
    public Response publicMessageDetail(@RequestBody Map map, HttpServletRequest request) {

        Long id;
        try {
            id = Long.valueOf((Integer) map.get("id"));
        } catch (Exception e) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        Long userId = Long.valueOf(request.getHeader("userId"));
        MessageContent messageContent = messageService.getMessageDetail(id);
        //更新当前公告为已读
        messageService.savePublicMessageStatus(id, userId);
        MessagePublicDto messagePublicDto = new MessagePublicDto();
        messagePublicDto.setTital(messageContent.getTitle());
        messagePublicDto.setContent(messageContent.getContent());
        messagePublicDto.setAddTime(messageContent.getGmtCreated().getTime());
        return new Response<>(CodeEnum.SUCCESS, messagePublicDto);

    }

    /**
     * 个人消息详情接口
     *
     * @param params  id
     * @param request 参数
     * @return 返回结果
     */
    @RequestMapping(value = "/privateMessageDetail", method = RequestMethod.POST)
    public Response privateMessageDetail(@RequestBody Map params, HttpServletRequest request) {

        Long id;
        try {
            id = Long.valueOf((Integer) params.get("id"));
        } catch (Exception e) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, null);
        }
        //查询个人消息详情
        Long userId = Long.valueOf(request.getHeader("userId"));
        MessageContent messageContent = messageService.getMessageDetail(id);
        //更新当前个人消息为已读
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setUserId(userId);
        messageDTO.setId(id);
        messageService.updatePrivateMessageStatus(messageDTO);

        MessagePrivateDto messagePrivateDto = new MessagePrivateDto();
        messagePrivateDto.setTital(messageContent.getTitle());
        messagePrivateDto.setContent(messageContent.getContent());
        messagePrivateDto.setAddTime(messageContent.getGmtCreated().getTime());
        return new Response<>(CodeEnum.SUCCESS, messagePrivateDto);

    }


}

