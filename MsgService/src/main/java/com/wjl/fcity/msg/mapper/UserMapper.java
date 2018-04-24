package com.wjl.fcity.msg.mapper;

import com.wjl.fcity.msg.model.User;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据用户手机号，获取用户信息
     * @param mobile 手机号
     * @return User
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   msg " +
            "WHERE " +
            "   mobile = #{mobile}")
    User findByMobile(String mobile);
}
