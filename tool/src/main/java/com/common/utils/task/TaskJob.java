package com.common.utils.task;

import com.common.utils.httpClient.WebUtilsOld;

import java.util.HashMap;

public class TaskJob {

    public void job() {
        System.out.println("task = = = = = start");
//        String url = "http://39.97.52.148/Molly/rest/molly/timingTask";
        String url = "http://localhost:8080/SafeGA/rest/safe/riskJudg";
        try {
            WebUtilsOld.doGet(url,new HashMap<String, String>(),20*1000,20*1000,new HashMap<String, String>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("task = = = = = end");
    }
}
