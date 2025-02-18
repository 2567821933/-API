package com.yupi.project.scheduling;


import com.yupi.project.service.UserSignInfoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SchedulingApplication {

    @Resource
    UserSignInfoService userSignInfoService;

    // 每天 23:59 执行
    @Scheduled(cron = "0 59 23 * * ?") // Cron 表达式
    public void executeUpdateSign() {
        Boolean aBoolean = userSignInfoService.doUpdateSign();
        if(aBoolean) {
            System.out.println("数据更新成功");
        } else {
            System.out.println("数据更新失败");
        }
    }
}
