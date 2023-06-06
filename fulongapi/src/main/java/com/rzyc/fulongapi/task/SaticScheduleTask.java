package com.rzyc.fulongapi.task;

import com.common.utils.httpClient.WebUtils;
import com.rzyc.fulongapi.service.TaskSrvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;

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


    /**
     *
     * @version v1.0
     * @author dong
     * @date 2023/5/27 14:06
     */
//    @Scheduled(fixedRate=10000)
    private void sendSersonData(){
        try {
            System.out.println("----------------定时任务sendSersonDatastart----------------");
            String url = "http://127.0.0.1:5668/sensor/sensorData";
            WebUtils.doPost(url,new HashMap<>(),new HashMap<>());
            System.out.println("----------------定时任务sendSersonDataend----------------");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
