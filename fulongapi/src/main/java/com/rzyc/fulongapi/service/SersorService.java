package com.rzyc.fulongapi.service;

import com.alibaba.fastjson.JSONArray;
import com.common.utils.TypeConversion;
import com.common.utils.model.Pager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rzyc.fulongapi.mapper.SensorWtMapper;
import com.rzyc.fulongapi.mapper.SersorHisValMapper;
import com.rzyc.fulongapi.mapper.SersorMapper;
import com.rzyc.fulongapi.model.SensorWt;
import com.rzyc.fulongapi.model.Sersor;
import com.rzyc.fulongapi.websocket.SersorWs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dong
 * @date 2023-05-27 10:32
 * @Version V1.0
 */
@Service
public class SersorService {

    //传感器
    @Autowired
    protected SersorMapper sersorMapper;

    //传感器历史数据
    @Autowired
    protected SersorHisValMapper sersorHisValMapper;

    //水压传感器
    @Autowired
    protected SensorWtMapper sensorWtMapper;

    /**
     * 发送数据
     * @version v1.0
     * @author dong
     * @date 2023/5/27 12:14
     */
    public void sendSersor(String userId)throws Exception{



        Map<String,Object> mapData = new HashMap<>();

        String[] strs = userId.split(",");
        String sensorTypeStr = strs[0];
        String pageStr = strs[1];

        Pager<Sersor> pager = new Pager<>();
        PageHelper.startPage(TypeConversion.StringToInteger(pageStr), 10);
        Page<SensorWt> pages = (Page<SensorWt>) sensorWtMapper.findAll();
        getDatePage(pager,pages);
        mapData.put("dataType",sensorTypeStr);
        mapData.put("data",pager);
        SersorWs.sendMessage(JSONArray.toJSONString(mapData));

    }

    /**
     * 发送数据
     * @version v1.0
     * @author dong
     * @date 2023/5/27 14:01
     */
    public void sendData()throws Exception{
        Map<String, List<Session>> sessionMap = SersorWs.clientList;
        if(null != sessionMap && sessionMap.size() > 0){
            for (Map.Entry<String,List<Session>> entry : sessionMap.entrySet()){
                sendSersor(entry.getKey());
            }
        }

    }

    /**
     * 统一处理分页
     *
     * @param pager api返回的对象
     * @param page  分页插件对象
     * @throws Exception
     */
    protected void getDatePage(Pager pager, Page page) throws Exception {
        pager.setTotal(page.getTotal());
        pager.setPage(page.getPages());
        pager.setPageSize(page.getPageSize());
        pager.setRows(page.getResult());
    }


    /**
     * 发送数据
     * @version v1.0
     * @author dong
     * @date 2023/5/27 12:14
     */
    public void sendSersor(String userId,String smokeid){

        try {
            SensorWt sensorWt = sensorWtMapper.findBySmokeid(smokeid);
            if(null != sensorWt){
                SersorWs.sendMessage(JSONArray.toJSONString(sensorWt));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
