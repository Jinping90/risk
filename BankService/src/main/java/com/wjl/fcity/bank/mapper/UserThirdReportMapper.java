package com.wjl.fcity.bank.mapper;

import com.wjl.fcity.bank.entity.model.UserThirdReportDO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * 用户第三方报告信息
 *
 * @author czl
 */
@Repository
public interface UserThirdReportMapper {

    /**
     * 新增第三方报告信息
     *
     * @param userThirdReport 新增的第三方报告信息对象
     */
    @Insert("INSERT INTO user_third_report ( " +
            "   `user_id`, " +
            "   `third_type`, " +
            "   `bill_id`, " +
            "   `report_id`, " +
            "   `message`, " +
            "   `gmt_created`, " +
            "   `gmt_modified` " +
            ") " +
            "VALUES " +
            "   (" +
            "       #{userId}, " +
            "       #{thirdType}, " +
            "       #{billId}, " +
            "       #{reportId}, " +
            "       #{message}, " +
            "       #{gmtCreated}, " +
            "       #{gmtModified} " +
            "   );")
    void insert(UserThirdReportDO userThirdReport);
}
