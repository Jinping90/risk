package com.wjl.fcity.cms.entity.dto;

import lombok.Data;

import java.util.Comparator;

/**
 * @author czl
 */
@Data
public class AdminRoleAuthDto {
    private long roleId;
    private long authId;
    private String authName;
    private int type;
    private int parentId;
    private String url;
    private int sort;

    public static class AuthComparator implements Comparator<AdminRoleAuthDto> {
        @Override
        public int compare(AdminRoleAuthDto data1, AdminRoleAuthDto data2) {
            if (data1.getSort() < data2.getSort()) {
                return -1;
            } else if (data1.getSort() == data2.getSort()) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
