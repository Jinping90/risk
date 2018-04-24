package com.wjl.fcity.cms.mapper;

import com.wjl.fcity.cms.entity.request.FeedbackArg;
import com.wjl.fcity.cms.entity.vo.FeedbackVo;
import com.wjl.fcity.cms.mapper.provider.FeedbackProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Fy
 * @date : 2018-04-19 16:11
 */
@Repository
public interface FeedbackMapper {
    /**
     * 获取问题反馈列表
     *
     * @param feedbackArg feedbackArg
     * @return FeedbackVo
     */
    @SelectProvider(type = FeedbackProvider.class, method = "getFeedback")
    List<FeedbackVo> getFeedback(FeedbackArg feedbackArg);

    /**
     * 查询总数
     *
     * @param feedbackArg feedbackArg
     * @return 总数
     */
    @SelectProvider(type = FeedbackProvider.class, method = "countFeedback")
    Integer getCountFeedback(FeedbackArg feedbackArg);
}
