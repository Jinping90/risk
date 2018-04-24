package com.wjl.fcity.cms.service.impl;

import com.wjl.fcity.cms.entity.request.UserReq;
import com.wjl.fcity.cms.mapper.UserMapper;
import com.wjl.fcity.cms.entity.vo.UserVo;
import com.wjl.fcity.cms.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shengju
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> getUserList(UserReq userReq) {
        List<UserVo> userVoList = userMapper.getUserList(userReq, true);
        for (UserVo userVo : userVoList) {
            if (StringUtils.isNotEmpty(userVo.getMobile()) && userVo.getMobile().length() > 10) {
                String mobile = userVo.getMobile();
                mobile = mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
                userVo.setMobile(mobile);
            }
            if (StringUtils.isNotEmpty(userVo.getIdCardNo()) && userVo.getIdCardNo().length() > 14) {
                String idCardNo = userVo.getIdCardNo();
                idCardNo = idCardNo.substring(0, 4) + "**********" + idCardNo.substring(14, 18);
                userVo.setIdCardNo(idCardNo);
            }
        }
        Map<String, Object> resultMap = new HashMap<>(3);
        List<UserVo> totalList = userMapper.getUserList(userReq, false);
        resultMap.put("total", totalList.size());
        resultMap.put("size", userReq.getSize());
        resultMap.put("page", userReq.getPage());
        resultMap.put("list", userVoList);
        return resultMap;
    }
}