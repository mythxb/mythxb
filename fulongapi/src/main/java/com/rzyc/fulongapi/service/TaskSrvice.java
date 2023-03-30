package com.rzyc.fulongapi.service;

import com.rzyc.fulongapi.mapper.EpidemicHistoryMapper;
import com.rzyc.fulongapi.mapper.EpidemicMapper;
import com.rzyc.fulongapi.model.Epidemic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * 定时任务
 */
@Service
public class TaskSrvice {

    //疫情数据
    @Autowired
    private EpidemicMapper epidemicMapper;

    //疫情历史数据
    private EpidemicHistoryMapper epidemicHistoryMapper;

    /**
     * 处理疫情数据
     * @throws Exception
     */
    public void handleEpidemic()throws Exception{

        String a = "12312";
        Integer b = 0;
        Long c = 0L;
        //先复制数据到历史记录表
        epidemicMapper.copyEpidemic();

        //更新最新数据时间
        epidemicMapper.changeTime();

    }


}
