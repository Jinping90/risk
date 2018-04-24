package com.wjl.fcity.user.service.impl;

import com.wjl.fcity.user.common.util.EmojiFilter;
import com.wjl.fcity.user.mapper.FeedBackMapper;
import com.wjl.fcity.user.service.FeedBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 杨洋
 * @date 2018/4/3
 */
@Slf4j
@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Resource
    private FeedBackMapper feedBackMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFeedBack(Long userId, Integer questionType, String questionContent) {
        String content = EmojiFilter.filterEmoji(questionContent);
        feedBackMapper.saveFeedBack(userId, questionType, content, new Date());
    }
}
