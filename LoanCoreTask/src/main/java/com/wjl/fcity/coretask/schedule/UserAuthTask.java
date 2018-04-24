package com.wjl.fcity.coretask.schedule;

import com.wjl.fcity.coretask.common.enums.AuthItemEnum;
import com.wjl.fcity.coretask.common.enums.AuthStatusEnum;
import com.wjl.fcity.coretask.common.enums.ChangeCreditValueEnum;
import com.wjl.fcity.coretask.common.util.DateUtil;
import com.wjl.fcity.coretask.model.ConfigAuthParamDO;
import com.wjl.fcity.coretask.model.UserAuthRecordDO;
import com.wjl.fcity.coretask.service.ConfigAuthParamService;
import com.wjl.fcity.coretask.service.CreditValueRecordService;
import com.wjl.fcity.coretask.service.UserAuthRecordService;
import com.wjl.fcity.coretask.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户认证信息更新
 *
 * @author shengju
 */
@Slf4j
@Component
public class UserAuthTask {

    @Resource
    private ConfigAuthParamService configAuthParamService;
    @Resource
    private UserAuthRecordService userAuthRecordService;
    @Resource
    private CreditValueRecordService creditValueRecordService;
    @Resource
    private UserService userService;

    @Scheduled(cron = "30 00 00 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void updateUserAuthTask() {
        long start = System.currentTimeMillis();
        // 每天00:00:30
        log.info("----定时任务【执行认证是否过期校验】开始----");

        List<ConfigAuthParamDO> configAuthParamDOList = configAuthParamService.listHaveValidity();

        for (ConfigAuthParamDO configAuthParamDO : configAuthParamDOList) {

            //查询已过期的认证记录
            List<UserAuthRecordDO> userAuthRecordDOList = userAuthRecordService.listOverdueRecord(
                    configAuthParamDO.getAuthItem(), DateUtil.getFewDaysAgoDate(configAuthParamDO.getValidityPeriod()));

            if (!userAuthRecordDOList.isEmpty()) {
                //提取出认证记录的ID集合
                List<Long> idList = userAuthRecordDOList.stream().map(UserAuthRecordDO::getId).collect(Collectors.toList());

                //更新用户相应的认证记录的状态为已过期
                userAuthRecordService.updateUserAuthRecordStatus(configAuthParamDO.getAuthItem(), AuthStatusEnum.PAST_DUE.getCode(), "已过期", idList);
                log.info("有{}用户的{}记录已修改为过期状态", idList.size(), Objects.requireNonNull(AuthItemEnum.getEnumByCode(configAuthParamDO.getAuthItem())).getName());

                //提取出用户ID的集合
                List<Long> userIdList = userAuthRecordDOList.stream().map(UserAuthRecordDO::getUserId).collect(Collectors.toList());

                //更新用户信用值
                userService.updateUserAuthRecordStatus(configAuthParamDO.getExpiredMinusCreditValue(), userIdList);

                for (UserAuthRecordDO userAuthRecordDO : userAuthRecordDOList) {
                    AuthItemEnum authItemEnum = AuthItemEnum.getEnumByCode(userAuthRecordDO.getAuthItem());
                    if (authItemEnum != null) {
                        ChangeCreditValueEnum changeCreditValueEnum = authItemEnum.getChangeCreditValueEnum();
                        if (changeCreditValueEnum != null) {

                            //插入用户减少信用值的记录
                            creditValueRecordService.insertCreditValueRecord(
                                    userAuthRecordDO.getUserId(),
                                    -configAuthParamDO.getExpiredMinusCreditValue(),
                                    authItemEnum.getChangeCreditValueEnum().getCode());
                        }
                    }
                }
            }
        }

        long end = System.currentTimeMillis();
        log.info(String.format("----定时任务【执行认证是否过期校验】结束，耗时%s毫秒----", end - start));
    }
}