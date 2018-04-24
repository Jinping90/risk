package com.wjl.fcity.cms.controller;

import com.google.common.collect.Maps;
import com.wjl.fcity.cms.common.enumeration.CodeEnum;
import com.wjl.fcity.cms.entity.dto.ArticlesDTO;
import com.wjl.fcity.cms.entity.model.ArticlesDO;
import com.wjl.fcity.cms.entity.request.ArticlesReq;
import com.wjl.fcity.cms.entity.vo.Response;
import com.wjl.fcity.cms.service.ArticlesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 咨询信息
 *
 * @author czl
 */
@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @Resource
    private ArticlesService articlesService;

    /**
     * 查询咨询信息列表
     *
     * @param articlesReq 咨询信息请求体
     * @return Response
     */
    @PostMapping("/listArticles")
    public Response listArticles(@RequestBody ArticlesReq articlesReq) {
        //查询咨询信息列表
        List<ArticlesDTO> articlesDTOList = articlesService.listArticles(articlesReq);

        //查询咨询信息列表总数
        Long total = articlesService.countArticles(articlesReq);

        Map<String, Object> map = Maps.newHashMap();
        map.put("list", articlesDTOList);
        map.put("page", articlesReq.getPage());
        map.put("size", articlesReq.getSize());
        map.put("total", total);
        return new Response<>(CodeEnum.SUCCESS, map);
    }

    /**
     * 删除咨询信息
     *
     * @param articlesReq 咨询信息请求体
     * @return Response
     */
    @PostMapping("/deleteArticles")
    public Response deleteArticles(@RequestBody ArticlesReq articlesReq) {
        //删除咨询信息
        Integer index = articlesService.deleteArticles(articlesReq);

        return new Response<>(CodeEnum.SUCCESS, index);
    }

    /**
     * 查询咨询信息详情
     *
     * @param articlesReq 咨询信息请求体
     * @return Response
     */
    @PostMapping("/getArticlesDetail")
    public Response getArticlesDetail(@RequestBody ArticlesReq articlesReq) {
        //查询咨询信息详情
        ArticlesDO articlesDO = articlesService.getById(articlesReq);

        return new Response<>(CodeEnum.SUCCESS, articlesDO);
    }

    /**
     * 插入咨询信息
     *
     * @param articlesReq 咨询信息请求体
     * @return Response
     */
    @PostMapping("/insertArticles")
    public Response insertArticles(@RequestBody ArticlesReq articlesReq) {
        //插入咨询信息
        articlesService.insertArticlesDO(articlesReq);

        return new Response<>(CodeEnum.SUCCESS, "");
    }

    /**
     * 更新咨询信息
     *
     * @param articlesReq 咨询信息请求体
     * @return Response
     */
    @PostMapping("/updateArticles")
    public Response updateArticles(@RequestBody ArticlesReq articlesReq) {
        //更新咨询信息
        articlesService.updateById(articlesReq);

        return new Response<>(CodeEnum.SUCCESS, "");
    }

}
