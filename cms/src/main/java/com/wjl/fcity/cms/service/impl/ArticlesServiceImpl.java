package com.wjl.fcity.cms.service.impl;

import com.wjl.fcity.cms.common.util.SecurityUtil;
import com.wjl.fcity.cms.entity.dto.ArticlesDTO;
import com.wjl.fcity.cms.entity.model.ArticlesDO;
import com.wjl.fcity.cms.entity.request.ArticlesReq;
import com.wjl.fcity.cms.mapper.ArticlesMapper;
import com.wjl.fcity.cms.service.ArticlesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询信息
 *
 * @author czl
 */
@Service
public class ArticlesServiceImpl implements ArticlesService {

    @Resource
    private ArticlesMapper articlesMapper;

    @Override
    public List<ArticlesDTO> listArticles(ArticlesReq articlesReq) {
        return articlesMapper.listArticles(articlesReq);
    }

    @Override
    public Long countArticles(ArticlesReq articlesReq) {
        return articlesMapper.countArticles(articlesReq);
    }

    @Override
    public Integer deleteArticles(ArticlesReq articlesReq) {
        Long editor = SecurityUtil.getSecurityUser().getId();
        articlesReq.setEditor(SecurityUtil.getSecurityUser().getId());
        return articlesMapper.deleteArticles(articlesReq.getId(), editor);
    }

    @Override
    public ArticlesDO getById(ArticlesReq articlesReq) {
        return articlesMapper.getById(articlesReq.getId());
    }

    @Override
    public void insertArticlesDO(ArticlesReq articlesReq) {
        articlesReq.setEditor(SecurityUtil.getSecurityUser().getId());
        ArticlesDO articlesDO = getArticlesDO(articlesReq);

        articlesMapper.insertArticlesDO(articlesDO);
    }

    @Override
    public void updateById(ArticlesReq articlesReq) {
        articlesReq.setEditor(SecurityUtil.getSecurityUser().getId());
        ArticlesDO articlesDO = getArticlesDO(articlesReq);

        articlesMapper.updateById(articlesDO);
    }

    private ArticlesDO getArticlesDO(ArticlesReq articlesReq) {
        ArticlesDO articlesDO = new ArticlesDO();
        articlesDO.setId(articlesReq.getId());
        articlesDO.setTitle(articlesReq.getTitle());
        articlesDO.setContent(articlesReq.getContent());
        articlesDO.setEditor(articlesReq.getEditor());
        articlesDO.setType(articlesReq.getType());
        articlesDO.setNumberOfReaders(0);
        articlesDO.setImageUrl(articlesReq.getImageUrl());
        articlesDO.setShowInBanner(articlesReq.getShowInBanner());
        articlesDO.setShowUp(articlesReq.getShowUp());
        articlesDO.setSort(articlesReq.getSort());
        return articlesDO;
    }
}
