package com.wjl.fcity.msg.mapper.provider;

import com.wjl.fcity.msg.dto.MessageDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * provider
 *
 * @author xuhaihong
 * @date 2018-03-29 14:02
 **/
public class ArticlesProvider {

    /**
     * 查询资讯list
     * @param messageDTO 参数
     * @return list
     */
    public String listConsultingInfo(MessageDTO messageDTO) {

        Integer page = messageDTO.getPage();
        Integer size = messageDTO.getSize();
        Integer type = messageDTO.getType();

        SQL sql = new SQL().SELECT("*")
                .FROM("articles")
                .WHERE("show_up = 0");
        if (type != null) {
            sql.AND().WHERE("show_in_banner =" + type);
        }
        String pageSql = sql.toString();
        pageSql += "ORDER BY sort ASC , gmt_created DESC ";
        if (page != null) {
            pageSql += " LIMIT " + (page - 1) * size  + "," +size ;
        }
        return pageSql;

    }

    /**
     * 统计资讯
     * @param messageDTO param
     * @return list
     */
    public String countConsultingInfo(MessageDTO messageDTO) {

        Integer type = messageDTO.getType();

        SQL sql = new SQL().SELECT("count(*)")
                .FROM("articles")
                .WHERE("show_up = 0");
        if (type != null) {
            sql.AND().WHERE("show_in_banner =" + type);
        }
        return sql.toString();

    }

    /**
     * 查询资讯
     * @param id 资讯id
     * @return 资讯
     */
    public String getConsultingInfo(Long  id) {

        SQL sql = new SQL().SELECT("*")
                .FROM("articles")
                .WHERE("show_up = 0 and id ="+id);
        return sql.toString();

    }
}
