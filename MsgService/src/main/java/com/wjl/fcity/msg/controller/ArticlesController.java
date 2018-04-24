package com.wjl.fcity.msg.controller;

import com.google.common.collect.Maps;
import com.wjl.fcity.msg.common.enums.CodeEnum;
import com.wjl.fcity.msg.dto.MessageDTO;
import com.wjl.fcity.msg.dto.Response;
import com.wjl.fcity.msg.model.Articles;
import com.wjl.fcity.msg.model.ArticlesVO;
import com.wjl.fcity.msg.service.ArticlesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 资讯
 *
 * @author xuhaihong
 * @date 2018-03-29 14:38
 **/
@RestController
@RequestMapping(value = "/articles")
@CrossOrigin
public class ArticlesController {

    @Resource
    private ArticlesService consultingInfoService;

    /**
     * 查询资讯 list
     *
     * @param messageDTO 参数
     * @return list
     */
    @RequestMapping(value = "/listConsultingInfo", method = RequestMethod.POST)
    public Response listConsultingInfo(@RequestBody MessageDTO messageDTO) {

        List<Articles> list = consultingInfoService.listConsultingInfo(messageDTO);
        if (list.size() < 1) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", list);
            map.put("total", 0);
            map.put("size", messageDTO.getPage());
            map.put("page", messageDTO.getSize());

            return new Response<>(CodeEnum.SUCCESS, map);
        }
        Integer total = consultingInfoService.countConsultingInfo(messageDTO);

        Map<String, Object> map = Maps.newHashMap();
        map.put("list", list);
        map.put("total", total);
        map.put("size", messageDTO.getPage());
        map.put("page", messageDTO.getSize());

        return new Response<>(CodeEnum.SUCCESS, map);
    }

    /**
     * 资讯详情
     *
     * @param params id 资讯id
     * @return 资讯
     */
    @RequestMapping(value = "/getConsultingInfo", method = RequestMethod.POST)
    public Response getConsultingInfo(@RequestBody Map<String, String> params) {

        Long id;
        try {
            id = Long.valueOf(params.get("id"));
        } catch (Exception e) {
            return new Response<>(CodeEnum.PARAMETER_MISTAKE, "没有资讯id");
        }
        Articles consultingInfo = consultingInfoService.getConsultingInfo(id);
        ArticlesVO articlesVO = new ArticlesVO();
        articlesVO.setContentType(consultingInfo.getShowInBanner());
        articlesVO.setId(consultingInfo.getId());
        articlesVO.setTitle(consultingInfo.getTitle());
        articlesVO.setContent(consultingInfo.getContent());
        articlesVO.setType(consultingInfo.getType());
        articlesVO.setNumberOfReaders(consultingInfo.getNumberOfReaders());
        articlesVO.setGmtCreated(consultingInfo.getGmtCreated());
        Map<String, Object> map = Maps.newHashMap();
        map.put("consultingInfo", articlesVO);

        return new Response<>(CodeEnum.SUCCESS, map);
    }
}
