package com.wjl.fcity.coretask.mapper;

import com.wjl.fcity.coretask.model.ConfigGenerateWealthDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 水滴结算生成信息配置Mapper
 *
 * @author czl
 */
@Repository
public interface ConfigGenerateWealthMapper {

    /**
     * 查询水滴结算生成信息配置
     * @param putYear 年份
     * @param putMonth 月份
     * @return 水滴结算生成信息配置
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   config_generate_wealth " +
            "WHERE " +
            "   put_year = #{putYear} " +
            "AND put_month = #{putMonth};")
    ConfigGenerateWealthDO getByPutYearAndPutMonth(@Param("putYear") Integer putYear, @Param("putMonth") Integer putMonth);
}
