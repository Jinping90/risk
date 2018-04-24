package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.vo.TongDunBean;
import com.wjl.fcity.user.po.CityConfigPO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 公共
 *
 * @author shengju
 */
@Repository
public interface CommonMapper {
    /**
     * 获取用户信息
     *
     * @param userId userId
     * @return TongDunBean
     */
    @Select("SELECT " +
            "   u.mobile, " +
            "   t.`name`, " +
            "   t.id_card_no idCard " +
            "FROM " +
            "   `user` u " +
            "LEFT OUTER JOIN two_elements_auth_record t ON u.id = t.user_id " +
            "WHERE " +
            "   u.id = #{userId} " +
            "AND t.`code` IN (0, 3)")
    TongDunBean getUserInfo(@Param("userId") Long userId);

    /**
     * 保存第三方报告
     *
     * @param type     报告类型
     * @param userId   用户id
     * @param verifyId 报告id
     * @param message  信息
     * @param now      当前时间
     */
    @Insert("INSERT INTO user_third_report ( " +
            "   user_id, " +
            "   third_type, " +
            "   report_id, " +
            "   message, " +
            "   gmt_created " +
            ") values (#{userId},#{type},#{verifyId},#{message},#{now})")
    void addThirdReport(@Param("type") Integer type, @Param("userId") Long userId, @Param("verifyId") String verifyId, @Param("message") String message, @Param("now") Date now);

    /**
     * 获取激活的城市
     *
     * @return List<CityConfigPO>
     */
    @Select("SELECT " +
            "   city_code, " +
            "   city_name " +
            "FROM " +
            "   city_config " +
            "WHERE " +
            "   `status` IN (2, 4, 5)")
    List<CityConfigPO> getCityConfig();

}
