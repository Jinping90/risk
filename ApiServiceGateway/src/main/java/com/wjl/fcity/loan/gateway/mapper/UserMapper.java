package com.wjl.fcity.loan.gateway.mapper;

import com.wjl.fcity.loan.gateway.model.dto.UserDTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author shengju
 */
@Repository
public interface UserMapper {

    /**
     * 根据用户id查找用户
     *
     * @param userId userId
     * @return user
     */
    @Select("SELECT " +
            "* " +
            "FROM " +
            "`user` " +
            "WHERE " +
            "id =#{userId}")
    UserDTO findUserByUserId(Long userId);
}
