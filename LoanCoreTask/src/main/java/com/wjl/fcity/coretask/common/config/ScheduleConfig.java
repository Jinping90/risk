package com.wjl.fcity.coretask.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.*;


/**
 * 定时任务配置
 * <p>
 * 配置线程池：5个线程
 *
 * @author 秦瑞华
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean
    public Executor taskExecutor() {
        //线程池核心池的大小
        int corePoolSize = 5;
        //线程池的最大线程数
        int maximumPoolSize = 5;
        //当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
        long keepAliveTime = 0L;
        //keepAliveTime 的时间单位
        TimeUnit unit = TimeUnit.MILLISECONDS;
        //用来储存等待执行任务的队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        //线程工厂
        ThreadFactory threadFactory = new CustomizableThreadFactory();

        return new ScheduledThreadPoolExecutor(
                corePoolSize, threadFactory);
    }
}
