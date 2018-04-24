package com.wjl.fcity.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.wjl.fcity.user.model.TwoElementAuth;

/**
 * 二要素
 * @author xuhaihong
 * @date 2018-03-30 11:56
 **/
@Repository
public interface TwoElementsAuthMapper {

    /**
     * 保存
     * @param twoElementsAuth 二要素
     */
    @Insert("INSERT INTO two_elements_auth_record ( " +
            " user_id, " +
            " NAME, " +
            " id_card_no, " +
            " CODE, " +
            " `DESCRIBE`, " +
            " trans_id, " +
            " trade_no, " +
            " fee, " +
            " gmt_created " +
            ") " +
            " VALUES " +
            " (#{userId},#{name},#{idCardNo},#{code},#{describe},#{transId},#{tradeNo},#{fee},#{gmtCreated})")
    void save(TwoElementAuth twoElementsAuth);

    /**
     * 查询
     * @param twoElementsAuth 身份证号  姓名
     * @return 个人认证信息
     */
    @Select("SELECT " +
            " id " +
            " FROM " +
            " two_elements_auth_record r " +
            " WHERE " +
            " r.id_card_no = #{idCardNo} " +
            " AND r. NAME = #{name} and (r.`code` = '0' OR r.`code` = '3') ")
    TwoElementAuth getTwoElementAuth(TwoElementAuth twoElementsAuth);

    /**
     * 查询
     * @param userId 用户id
     *  @return  result
     */
    @Select("SELECT * FROM two_elements_auth_record t WHERE t.user_id = #{userId}" +
            " and (t.`code` = '0' OR t.`code` = '3')")
    TwoElementAuth getIfTwoElementAuth(Long userId);


}
