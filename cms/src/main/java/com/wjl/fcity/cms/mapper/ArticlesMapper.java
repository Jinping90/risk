package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.dto.ArticlesDTO;
import com.wjl.fcity.cms.entity.model.ArticlesDO;
import com.wjl.fcity.cms.entity.request.ArticlesReq;
import com.wjl.fcity.cms.mapper.provider.ArticlesProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 咨询信息表
 *
 * @author czl
 */
public interface ArticlesMapper {

    /**
     * 查询咨询信息列表
     *
     * @param articlesReq 咨询信息请求体
     * @return List<ArticlesDO>
     */
    @SelectProvider(type = ArticlesProvider.class, method = "listArticles")
    List<ArticlesDTO> listArticles(ArticlesReq articlesReq);

    /**
     * 查询咨询信息列表总数
     *
     * @param articlesReq 咨询信息请求体
     * @return Long
     */
    @SelectProvider(type = ArticlesProvider.class, method = "countArticles")
    Long countArticles(ArticlesReq articlesReq);

    /**
     * 删除咨询信息
     *
     * @param id id
     * @param editor editor
     * @return int
     */
    @Delete("UPDATE articles " +
            "SET `show_up` = 1, " +
            " `gmt_modified` = NOW(), " +
            " `editor` = #{editor} " +
            "WHERE  " +
            "   id = #{id};")
    Integer deleteArticles(@Param("id") Long id, @Param("editor") Long editor);

    /**
     * 查询咨询信息详情
     *
     * @param id id
     * @return ArticlesDO
     */
    @Select("SELECT" +
            "   * " +
            "FROM   " +
            "   articles " +
            "WHERE  " +
            "   id = #{id};")
    ArticlesDO getById(@Param("id") Long id);

    /**
     * 插入咨询信息
     *
     * @param articlesDO articlesDO
     */
    @Insert("INSERT INTO `articles` ( " +
            "   `title`, " +
            "   `content`, " +
            "   `editor`, " +
            "   `type`, " +
            "   `number_of_readers`, " +
            "   `image_url`, " +
            "   `show_in_banner`, " +
            "   `show_up`, " +
            "   `sort`, " +
            "   `gmt_created`, " +
            "   `gmt_modified` " +
            ") " +
            "VALUES " +
            "   ( " +
            "    #{title}, " +
            "    #{content}, " +
            "    #{editor}, " +
            "    #{type}, " +
            "    #{numberOfReaders}, " +
            "    #{imageUrl}, " +
            "    #{showInBanner}, " +
            "    #{showUp}, " +
            "    #{sort}, " +
            "    NOW(), " +
            "    NOW() " +
            "   );")
    void insertArticlesDO(ArticlesDO articlesDO);

    /**
     * 更新咨询信息
     *
     * @param articlesDO articlesDO
     */
    @Update("UPDATE articles " +
            "SET `title` = #{title}, " +
            " `content` = #{content}, " +
            " `editor` = #{editor}, " +
            " `type` = #{type}, " +
            " `number_of_readers` = #{numberOfReaders}, " +
            " `image_url` = #{imageUrl}, " +
            " `show_in_banner` = #{showInBanner}, " +
            " `show_up` = #{showUp}, " +
            " `sort` = #{sort}, " +
            " `gmt_modified` = NOW() " +
            "WHERE " +
            "   id = #{id};")
    void updateById(ArticlesDO articlesDO);
}
