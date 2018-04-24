package com.wjl.fcity.park.service.impl;

import com.wjl.fcity.park.dto.LabelDTO;
import com.wjl.fcity.park.dto.MyLabelDTO;
import com.wjl.fcity.park.mapper.LabelMapper;
import com.wjl.fcity.park.mapper.LabelRecordMapper;
import com.wjl.fcity.park.model.Label;
import com.wjl.fcity.park.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 杨洋
 * @date 2018/3/27
 */
@Slf4j
@Service
public class LabelServiceImpl implements LabelService {

    @Resource
    private LabelRecordMapper labelRecordMapper;
    @Resource
    private LabelMapper labelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addLabel(Long userId, String mobile, String nickName, List<String> labels) {
        Date gmtCreated = new Date();
        //labels.forEach(label -> labelRecordMapper.addLabel(userId, mobile, nickName, gmtCreated, label));
        labelRecordMapper.addLabelList(userId, mobile, nickName, gmtCreated, labels);
    }

    @Override
    public List<LabelDTO> getLabeledList(Long userId) {
        return labelRecordMapper.getAllLabel(userId);
    }

    @Override
    public Integer countMyLabels(String mobile) {
        return labelRecordMapper.countMyLabels(mobile);
    }

    @Override
    public List<MyLabelDTO> getMyLabels(String mobile) {
        return labelRecordMapper.getMyLabels(mobile);
    }

    @Override
    public List<Label> getLabels() {
        return labelMapper.getLabels();
    }
}
