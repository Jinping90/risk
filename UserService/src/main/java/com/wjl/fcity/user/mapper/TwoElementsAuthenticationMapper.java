package com.wjl.fcity.user.mapper;

import com.wjl.fcity.user.model.TwoElementAuth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author : Fy
 * @date : 2018-03-30 16:39
 */
@Repository
public interface TwoElementsAuthenticationMapper {

    /**
     * 根据用户的userId来查找该用户的二要素认证记录表记录
     *
     * @param userId 用户的userId
     * @return TwoElementAuth
     */
    @Select("select * from " +
            "   two_elements_auth_record " +
            "where " +
            "   user_id=#{userId} " +
            "order by " +
            "   gmt_created desc limit 1")
    TwoElementAuth findTwoElementAuthByUserId(@Param("userId") Long userId);
}
