package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.request.UserReq;
import com.wjl.fcity.cms.mapper.provider.UserProvider;
import com.wjl.fcity.cms.entity.vo.UserVo;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shengju
 */
@Repository
public interface UserMapper {
    /**
     * 获取用户列表
     * @param userReq userReq
     * @param isPaging 是否分页
     * @return List<UserVo>
     */
    @SelectProvider(type = UserProvider.class,method = "getUserList")
    List<UserVo> getUserList(UserReq userReq, boolean isPaging);
}
