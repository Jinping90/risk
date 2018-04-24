package com.wjl.fcity.cms.mapper.provider;

import com.wjl.fcity.cms.entity.request.FeedbackArg;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

/**
 * @author : Fy
 * @date : 2018-04-19 16:13
 */
@Slf4j
public class FeedbackProvider {

    /**
     * 查询问题反馈的列表
     *
     * @param feedbackArg 页面传参数
     * @return pageSql
     */
    public String getFeedback(FeedbackArg feedbackArg) {

        Integer page = feedbackArg.getPage();
        Integer size = feedbackArg.getSize();
        String username = feedbackArg.getUsername();
        String mobile = feedbackArg.getMobile();
        Long startTime = feedbackArg.getStartTime();
        Long endTime = feedbackArg.getEndTime();
        Integer questionType = feedbackArg.getQuestionType();
        String keyWord = feedbackArg.getKeyWord();

        SQL sql = new SQL();
        sql.SELECT("   u.username, " +
                "   u.id as userId, " +
                "   u.mobile, " +
                "   u.gender, " +
                "   q.question_type as questionType, " +
                "   q.question_content as questionContent , " +
                "   q.gmt_created as gmtCreated  " +
                "FROM " +
                "   question_feedback q " +
                "   LEFT JOIN user u ON q.user_id = u.id "
        );

        if (!StringUtils.isEmpty(username)) {
            sql.WHERE("u.`username` = '" + username + "'");
        }

        if (!StringUtils.isEmpty(mobile)) {
            sql.WHERE("u.`mobile` = '" + mobile + "'");
        }

        if (!StringUtils.isEmpty(questionType)) {
            sql.WHERE("q.`question_type` = '" + questionType + "'");
        }

        if (!StringUtils.isEmpty(keyWord)) {
            sql.WHERE(" q.question_content like '%" + keyWord + "%'");
        }

        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            sql.WHERE(" UNIX_TIMESTAMP(q.gmt_created) >=" + startTime + " and " + " UNIX_TIMESTAMP(q.gmt_created) <=" + endTime);
        }

        if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            sql.WHERE(" UNIX_TIMESTAMP(q.gmt_created) >=" + startTime);
        }

        if (StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            sql.WHERE(" UNIX_TIMESTAMP(q.gmt_created) <=" + endTime);
        }

        sql.ORDER_BY("q.gmt_created desc");

        String pageSql = sql.toString();

        pageSql = pageSql + " limit " + (page - 1) * size + "," + size;


        log.info("查询的sql语句为:" + pageSql);

        return pageSql;
    }


    /**
     * 根据条件得出问题的总数
     *
     * @param feedbackArg 页面传参数
     * @return 总数
     */
    public String countFeedback(FeedbackArg feedbackArg) {
        String username = feedbackArg.getUsername();
        String mobile = feedbackArg.getMobile();
        Long startTime = feedbackArg.getStartTime();
        Long endTime = feedbackArg.getEndTime();
        Integer questionType = feedbackArg.getQuestionType();
        String keyWord = feedbackArg.getKeyWord();

        SQL sql = new SQL();
        sql.SELECT(" count(*) " +
                "FROM " +
                "   question_feedback q " +
                "   LEFT JOIN user u ON q.user_id = u.id "
        );

        if (!StringUtils.isEmpty(username)) {
            sql.WHERE("u.`username` = '" + username + "'");
        }

        if (!StringUtils.isEmpty(mobile)) {
            sql.WHERE("u.`mobile` = '" + mobile + "'");
        }

        if (!StringUtils.isEmpty(questionType)) {
            sql.WHERE("q.`question_type` = '" + questionType + "'");
        }

        if (!StringUtils.isEmpty(keyWord)) {
            sql.WHERE(" q.question_content like '%" + keyWord + "%'");
        }

        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            sql.WHERE(" UNIX_TIMESTAMP(q.gmt_created) >=" + startTime + " and " + " UNIX_TIMESTAMP(q.gmt_created) <=" + endTime);
        }

        if (!StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            sql.WHERE(" UNIX_TIMESTAMP(q.gmt_created) >=" + startTime);
        }

        if (StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            sql.WHERE(" UNIX_TIMESTAMP(q.gmt_created) <=" + endTime);
        }

        return sql.toString();
    }
}
