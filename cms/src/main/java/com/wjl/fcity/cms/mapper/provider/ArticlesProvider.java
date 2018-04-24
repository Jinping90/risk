package com.wjl.fcity.cms.mapper.provider;

import com.wjl.fcity.cms.common.util.PageUtil;
import com.wjl.fcity.cms.entity.request.ArticlesReq;
import org.apache.commons.lang3.StringUtils;

/**
 * 咨询信息表
 *
 * @author czl
 */
public class ArticlesProvider {

    public String listArticles(ArticlesReq articlesReq) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	T1.id AS id, ");
        sql.append("	T1.image_url AS imageUrl, ");
        sql.append("	T1.title AS title, ");
        sql.append("	T1.type AS type, ");
        sql.append("	T1.show_in_banner AS showInBanner, ");
        sql.append("	T1.show_up AS showUp, ");
        sql.append("	T1.number_of_readers AS numberOfReaders, ");
        sql.append("	T2.real_name AS realName, ");
        sql.append("	T1.gmt_modified AS gmtModified ");
        sql.append("FROM ");
        sql.append("	articles AS T1 ");
        sql.append("LEFT JOIN admin_user AS T2 ON T2.id = T1.editor ");
        sql.append("WHERE ");
        sql.append("	1 = 1 ");

        if (StringUtils.isNotBlank(articlesReq.getTitle())) {
            sql.append("AND T1.title LIKE '%").append(articlesReq.getTitle()).append("%' ");
        }
        if (articlesReq.getType() != null) {
            sql.append("AND T1.type = #{type} ");
        }
        if (articlesReq.getShowInBanner() != null) {
            sql.append("AND T1.show_in_banner = #{showInBanner} ");
        }
        if (articlesReq.getShowUp() != null) {
            sql.append("AND T1.show_up = #{showUp} ");
        }

        sql.append("ORDER BY ");
        sql.append("    T1.gmt_modified DESC ");

        return PageUtil.page(articlesReq, sql.toString());
    }

    public String countArticles(ArticlesReq articlesReq) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("	COUNT(*) ");
        sql.append("FROM ");
        sql.append("	articles AS T1 ");
        sql.append("LEFT JOIN admin_user AS T2 ON T2.id = T1.editor ");
        sql.append("WHERE ");
        sql.append("	1 = 1 ");

        if (StringUtils.isNotBlank(articlesReq.getTitle())) {
            sql.append("AND T1.title LIKE '%${title}%' ");
        }
        if (articlesReq.getType() != null) {
            sql.append("AND T1.type = #{type} ");
        }
        if (articlesReq.getShowInBanner() != null) {
            sql.append("AND T1.show_in_banner = #{showInBanner} ");
        }
        if (articlesReq.getShowUp() != null) {
            sql.append("AND T1.show_up = #{showUp} ");
        }

        return sql.toString();
    }
}
