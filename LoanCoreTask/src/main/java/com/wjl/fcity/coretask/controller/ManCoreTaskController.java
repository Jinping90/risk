package com.wjl.fcity.coretask.controller;

import com.wjl.fcity.coretask.schedule.UserAuthTask;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 手动触发核心任务Controller
 *
 * @author czl
 */
@Slf4j
@RestController
@RequestMapping(value = "/coreTask/man")
public class ManCoreTaskController {
    @Resource
    private UserAuthTask userAuthTask;

    @ApiOperation(value = "用户认证信息更新", notes = "用户认证信息更新")
    @PostMapping("/user/userAuthTask")
    public void userAuthTask() {
        log.info("手动触发用户认证信息更新定时任务");
        userAuthTask.updateUserAuthTask();
    }

}
