package com.wjl.fcity.park.mapper.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 杨洋
 * @date 2018/4/3
 */
@Slf4j
public class LabelRecordProvider {
    public String addLabelList(@Param("userId") Long userId, @Param("mobile") String mobile, @Param("nickName") String nickName, @Param("gmtCreated") Date gmtCreated, List<String> labels) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO label_record (user_id,mobile,nick_name,gmt_created,label_id,`status`) ");
        sql.append("VALUES ");
        labels.forEach(label -> {
            sql.append("(");
            sql.append("#{userId},#{mobile},#{nickName},#{gmtCreated},");
            sql.append(label);
            sql.append(",0),");
        });
        sql.setLength(sql.length() - 1);
        String sqlStr = sql.toString();
        log.info("新增标签接口,SQL:" + sqlStr);
        return sqlStr;
    }
}
