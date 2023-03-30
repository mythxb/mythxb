package com.rzyc.fulongapi.task;

import com.rzyc.fulongapi.service.TaskSrvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class SaticScheduleTask {

    //定时任务service
    @Autowired
    private TaskSrvice taskSrvice;

    @Scheduled(cron = "0 01 02 * * ?")
    //或直接指定时间间隔，例如：5秒
//    @Scheduled(fixedRate=100000)
    private void configureTasks(){
        try {
            System.out.println("----------------定时任务start----------------");
            taskSrvice.handleEpidemic();
            System.out.println("----------------定时任务end----------------");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
