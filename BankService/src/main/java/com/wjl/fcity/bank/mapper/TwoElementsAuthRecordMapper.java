package com.wjl.fcity.bank.mapper;

import com.wjl.fcity.bank.entity.model.TwoElementsAuthRecordDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 二要素认证记录
 *
 * @author czl
 */
@Repository
public interface TwoElementsAuthRecordMapper {

    /**
     * 查询认证成功的二要素记录
     * @param userId 用户ID
     * @return TwoElementsAuthRecordDO
     */
    @Select("SELECT " +
            "   * " +
            "FROM " +
            "   two_elements_auth_record " +
            "WHERE " +
            "   `code` IN ('0', '3') " +
            "AND user_id = #{userId};")
    TwoElementsAuthRecordDO getSuccessRecord(@Param("userId") Long userId);
}
