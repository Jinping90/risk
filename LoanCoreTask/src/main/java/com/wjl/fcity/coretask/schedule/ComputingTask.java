package com.wjl.fcity.coretask.schedule;

import com.wjl.fcity.coretask.common.util.DateUtil;
import com.wjl.fcity.coretask.service.WealthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 计算生成水滴定时任务
 *
 * @author czl
 */
@Slf4j
@Component
public class ComputingTask {

    @Resource
    private WealthService wealthService;

    @Scheduled(cron = "${cron.wealthCompute}")
    public void execute() {
        wealthCompute();
    }

    private void wealthCompute() {
        log.info("定时任务-计算每日qtb开始:" + DateUtil.dateStr4(new Date()));
        try {
            wealthService.wealthComputeEveryDay();
        } catch (Exception e) {
            log.error("定时任务-计算每日qtb出错:", e);
        }

        log.info("定时任务-计算每日qtb结束:" + DateUtil.dateStr4(new Date()));

    }
}
