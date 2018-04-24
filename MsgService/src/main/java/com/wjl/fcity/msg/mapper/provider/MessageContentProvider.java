package com.wjl.fcity.msg.mapper.provider;

import com.wjl.fcity.msg.dto.MessageDTO;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author xuhaihong
 * @date 2018-03-27 17:46
 **/
public class MessageContentProvider {

    public String countMessage(Long userId) {
        SQL sql = new SQL().SELECT("count(*) as id ")
                .FROM("message t")
                .WHERE(" t.id IN ( " +
                        "  SELECT " +
                        "   s.message_id " +
                        "  FROM " +
                        "   message_record s " +
                        "  WHERE " +
                        "   s. status = 0 " +
                        "  AND s.user_id = " + userId + " )");
        return sql.toString();
    }


    public String listPrivateMessage(MessageDTO messageDTO) {

        Integer page = messageDTO.getPage();
        Integer size = messageDTO.getSize();
        Long userId = messageDTO.getUserId();
        SQL sql = new SQL().SELECT(" e.id  ,  " +
                "  r.status  as type ,  " +
                "  e.title   ,  " +
                "  e.content  ,  " +
                "  e.gmt_created    ")
                .FROM("message  e ")
                .LEFT_OUTER_JOIN("message_record r on e.id = r.message_id ")
                .WHERE(" e.type = 2 and r.user_id = " + userId);
        String pageSql = sql.toString();
        pageSql += " ORDER BY  r.status ASC ,  r.gmt_created DESC ";
        if (page != null) {
            pageSql += " LIMIT " + ((page - 1) * size) + "," + size;
        }
        return pageSql;
    }

    public String countByUserId(Long userId) {

        SQL sql = new SQL().SELECT(" count(*) ")
                .FROM("message  e ")
                .LEFT_OUTER_JOIN("message_record r on e.id = r.message_id ")
                .WHERE(" e.type = 2 and r.user_id = " + userId);
        return sql.toString();
    }

    public String listPublicMessage(MessageDTO messageDTO) {
        Integer page = messageDTO.getPage();
        Integer size = messageDTO.getSize();
        Long userId = messageDTO.getUserId();
        SQL sql = new SQL().SELECT("   e.id  ,  " +
                "  r.status as type  ,  " +
                "  e.title   ,  " +
                "  e.content  ,  " +
                "  e.gmt_created ")
                .FROM(" message e ")
                .LEFT_OUTER_JOIN(" message_record r on e.id = r.message_id and r.user_id = " + userId)
                .WHERE("e.type = 1 ");
        String pageSql = sql.toString();
        pageSql += " ORDER BY r.status ASC , e.gmt_created DESC ";
        if (null != page) {
            pageSql += " LIMIT " + ((page - 1) * size) + "," + size;
        }
        return pageSql;
    }

    public String countPublicMessage(Long userId) {
        SQL sql = new SQL().SELECT(" count(*)")
                .FROM(" message e ")
                .LEFT_OUTER_JOIN(" message_record r on e.id = r.message_id and r.user_id = " + userId)
                .WHERE("e.type = 1 ");
        return sql.toString();
    }

    public String getMessageById(Long id) {

        SQL sql = new SQL().SELECT(" id ," +
                "title ," +
                "content," +
                "type," +
                "gmt_created," +
                "gmt_modified")
                .FROM("message")
                .WHERE("id = " + id);
        return sql.toString();
    }

}

