package com.wjl.fcity.cms.entity.request;

import lombok.Data;

/**
 * 分页体
 * @author czl
 */
@Data
public class PageReq {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 大小
     */
    private Integer size;
}
