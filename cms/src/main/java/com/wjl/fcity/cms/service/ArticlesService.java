package com.wjl.fcity.cms.service;

import com.wjl.fcity.cms.entity.dto.ArticlesDTO;
import com.wjl.fcity.cms.entity.model.ArticlesDO;
import com.wjl.fcity.cms.entity.request.ArticlesReq;

import java.util.List;

/**
 * 咨询信息
 *
 * @author czl
 */
public interface ArticlesService {

    /**
     * 查询咨询信息列表
     *
     * @param articlesReq 咨询信息请求体
     * @return List<ArticlesDO>
     */
    List<ArticlesDTO> listArticles(ArticlesReq articlesReq);

    /**
     * 查询咨询信息列表总数
     *
     * @param articlesReq 咨询信息请求体
     * @return Long
     */
    Long countArticles(ArticlesReq articlesReq);

    /**
     * 删除咨询信息
     *
     * @param articlesReq 咨询信息请求体
     * @return int
     */
    Integer deleteArticles(ArticlesReq articlesReq);


    /**
     * 查询咨询信息详情
     *
     * @param articlesReq 咨询信息请求体
     * @return ArticlesDO
     */
    ArticlesDO getById(ArticlesReq articlesReq);

    /**
     * 插入咨询信息
     *
     * @param articlesReq articlesReq
     */
    void insertArticlesDO(ArticlesReq articlesReq);

    /**
     * 更新咨询信息
     *
     * @param articlesReq articlesReq
     */
    void updateById(ArticlesReq articlesReq);
}
