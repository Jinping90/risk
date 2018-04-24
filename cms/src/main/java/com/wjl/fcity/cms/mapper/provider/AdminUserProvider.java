package com.wjl.fcity.cms.mapper.provider;

import com.wjl.fcity.cms.common.util.PageUtil;
import com.wjl.fcity.cms.entity.model.AdminUser;
import com.wjl.fcity.cms.entity.request.AdminUserReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author 黄骏毅
 */
public class AdminUserProvider {
    private final static String TABLE_NAME = "admin_user";

    public String list(AdminUserReq args) {
        SQL sql = new SQL();
        sql.SELECT("*").FROM(TABLE_NAME).WHERE("1=1");
        if (StringUtils.isNotBlank(args.getAdminName())) {
            sql.AND().WHERE("login_name like '%" + args.getAdminName() + "%'");
        }
        return PageUtil.page(args, sql.toString());
    }

    public String count(AdminUserReq args) {
        SQL sql = new SQL();
        sql.SELECT("count(1)").FROM(TABLE_NAME).WHERE("1=1");
        if (StringUtils.isNotBlank(args.getAdminName())) {
            sql.AND().WHERE("login_name like '%" + args.getAdminName() + "%'");
        }
        return sql.toString();
    }

    public String save() {
        SQL sql = new SQL();
        sql.INSERT_INTO(TABLE_NAME).INTO_COLUMNS("role_id", "login_name", "password", "real_name", "email", "mobile", "status", "gmt_created", "gmt_modified")
                .INTO_VALUES("#{roleId}", "#{loginName}", "#{password}", "#{realName}", "#{email}", "#{mobile}", "#{status}", "#{gmtCreated}", "#{gmtModified}");
        return sql.toString();
    }

    public String update(AdminUser user) {
        SQL sql = new SQL();
        sql.UPDATE(TABLE_NAME);
        if (user.getRoleId() != 1L) {
            sql.SET("role_id = #{roleId}");
        }
        if (StringUtils.isNotBlank(user.getLoginName())) {
            sql.SET("login_name = #{loginName}");
        }
        if (StringUtils.isNotBlank(user.getRealName())) {
            sql.SET("real_name = #{realName}");
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            sql.SET("email = #{email}");
        }
        if (StringUtils.isNotBlank(user.getMobile())) {
            sql.SET("mobile = #{mobile}");
        }
        sql.SET("status = #{status}");
        sql.SET("gmt_modified = #{gmtModified}");
        sql.WHERE("id =#{id}");
        return sql.toString();
    }
}
