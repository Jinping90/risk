package com.wjl.fcity.park.mapper;

import com.wjl.fcity.park.model.Label;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨洋
 * @date 2018/3/27
 */
@Repository
public interface LabelMapper {
    /**
     * 查询数据库中配置的标签
     *
     * @return 标签list
     */
    @Select("SELECT * FROM label")
    List<Label> getLabels();
}
