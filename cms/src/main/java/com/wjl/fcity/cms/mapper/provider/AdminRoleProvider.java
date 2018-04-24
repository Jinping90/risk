package com.wjl.fcity.cms.mapper.provider;

import com.wjl.fcity.cms.common.util.PageUtil;
import com.wjl.fcity.cms.entity.model.AdminRole;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 黄骏毅
 */
public class AdminRoleProvider {
    private final static String TABLE_NAME = "admin_role";

    public String count(AdminUserReq args) {
        SQL sql = new SQL().SELECT("count(1)").FROM(TABLE_NAME).WHERE("1=1");
        if (StringUtils.isNotBlank(args.getAdminName())) {
            sql.AND().WHERE("name like '% " + args.getAdminName() + " %'");
        }
        return sql.toString();

    }


    public String list(AdminUserReq args) {
        SQL sql = new SQL().SELECT("*").FROM(TABLE_NAME).WHERE("1=1");
        if (StringUtils.isNotBlank(args.getAdminName())) {
            sql.AND().WHERE("name like '%" + args.getAdminName() + "%'");
        }
        return PageUtil.page(args, sql.toString());
    }

    public String save() {
        SQL sql = new SQL();
        sql.INSERT_INTO(TABLE_NAME).INTO_COLUMNS("name", "gmt_created", "gmt_modified").INTO_VALUES("#{name}", "#{gmtCreated}", "#{gmtModified}");
        return sql.toString();
    }

    public String update(AdminRole role) {
        SQL sql = new SQL();
        sql.UPDATE(TABLE_NAME);
        if (StringUtils.isNotBlank(role.getName())) {
            sql.SET("name = #{name}");
        }
        sql.SET("gmt_modified = #{gmtModified}");
        sql.WHERE("id =#{id}");
        return sql.toString();
    }
}
