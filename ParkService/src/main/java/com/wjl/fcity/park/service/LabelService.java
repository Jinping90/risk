package com.wjl.fcity.park.service;

import com.wjl.fcity.park.dto.LabelDTO;
import com.wjl.fcity.park.dto.MyLabelDTO;
import com.wjl.fcity.park.model.Label;

import java.util.List;

/**
 * @author 杨洋
 * @date 2018/3/27
 */
public interface LabelService {
    /**
     * 添加标签
     *
     * @param userId   用户id
     * @param mobile   被标记人手机
     * @param nickName 被标记人姓名
     * @param labels   标签
     */
    void addLabel(Long userId, String mobile, String nickName, List<String> labels);

    /**
     * 获取用户已标好友列表
     *
     * @param userId 用户id
     * @return 好友列表
     */
    List<LabelDTO> getLabeledList(Long userId);

    /**
     * 查询用户被打了多少标签
     *
     * @param mobile 用户手机号
     * @return 数量
     */
    Integer countMyLabels(String mobile);

    /**
     * 查询用户被打的标签
     *
     * @param mobile 用户手机号
     * @return 标签list
     */
    List<MyLabelDTO> getMyLabels(String mobile);

    /**
     * 获取数据库中已配置的标签
     *
     * @return 标签list
     */
    List<Label> getLabels();
}
