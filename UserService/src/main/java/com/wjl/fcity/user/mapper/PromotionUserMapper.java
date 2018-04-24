package com.wjl.fcity.user.mapper;


import com.wjl.fcity.user.model.PromotionUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 推广用户
 * @author xuhaihong
 * @date 2018-04-02 17:41
 **/
@Repository
public interface PromotionUserMapper {

    /**
     * 保存
     * @param promotionUser 参数
     *  @return  id
     */
    @Insert("INSERT into promotion_user (ip,user_id,device_type,channel_id,visit_date) " +
            "VALUES (#{ip},#{userId},#{deviceType},#{channelId},#{visitDate})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    Integer  insert(PromotionUser promotionUser);

    /**
     * 更新
     * @param promotionUser 参数
     */
    @Update(" UPDATE promotion_user set user_id = #{userId} and regist_date = #{registDate} where id = #{id}")
    void update(PromotionUser promotionUser);

}
