package com.wjl.fcity.cms.mapper.provider;

import com.wjl.fcity.cms.entity.request.UserReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author shengju
 */
@Slf4j
public class UserProvider {

    public String getUserList(UserReq userReq, boolean isPaging) {
        Integer page = userReq.getPage();
        Integer size = userReq.getSize();
        String name = userReq.getName();
        Long userId = userReq.getUserId();
        String idCardNo = userReq.getIdCardNo();
        String mobile = userReq.getMobile();
        SQL sql = new SQL();
        sql.SELECT("   u.id, " +
                "   t.`name`, " +
                "   u.mobile, " +
                "   t.id_card_no, " +
                "   u.credit_value, " +
                "   u.total_wealth waterDrop, " +
                "   u.gmt_created registerTime, " +
                "   COUNT(a.auth_category) authCount " +
                "FROM " +
                "   `user` u " +
                "LEFT JOIN two_elements_auth_record t ON t.user_id = u.id " +
                "LEFT JOIN ( " +
                "   SELECT " +
                "       * " +
                "   FROM " +
                "       user_auth_record " +
                "   WHERE " +
                "       `status` = 2 " +
                ") a ON a.user_id = u.id ");
        if (StringUtils.isNotEmpty(name)) {
            sql.WHERE("t.`name` like '%" + name + "%'");
        }
        if (StringUtils.isNotEmpty(mobile)) {
            sql.WHERE("u.`mobile` = '" + mobile + "'");
        }
        if (StringUtils.isNotEmpty(idCardNo)) {
            sql.WHERE("t.id_card_no = '" + idCardNo + "'");
        }
        if (userId != null) {
            sql.WHERE("u.id = " + userId);
        }
        sql.GROUP_BY("u.id");

        String pageSql = sql.toString();
        if (isPaging) {
            pageSql = pageSql + " limit " + (page - 1) * size + "," + size;
        }
        log.info("查询的sql语句为:" + pageSql);

        return pageSql;
    }

}
