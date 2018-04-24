package com.wjl.fcity.cms.common.util;

import com.wjl.fcity.cms.entity.request.PageReq;

/**
 * 分页工具
 *
 * @author 黄骏毅
 */
public class PageUtil {
    public static String page(PageReq pageReq, String sql) {
        if (pageReq != null && pageReq.getPage() != null && pageReq.getSize() != null) {
            Integer page = pageReq.getPage() - 1;
            Integer size = pageReq.getSize();
            sql = sql + " limit " + page * size + "," + size;
            return sql;
        } else {
            return sql;
        }
    }
}
