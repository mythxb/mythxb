package com.rzyc.fulongapi.controller;

import com.alibaba.fastjson.JSONArray;
import com.common.utils.DateUtils;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.jwt.JwtUtil;
import com.common.utils.model.MultiResult;
import com.common.utils.model.SingleResult;
import com.rzyc.fulongapi.bean.sensor.SensorDataDto;
import com.rzyc.fulongapi.mapper.SersorAlertMapper;
import com.rzyc.fulongapi.model.SensorWt;
import com.rzyc.fulongapi.model.SensorWtHis;
import com.rzyc.fulongapi.model.Sersor;
import com.rzyc.fulongapi.model.SersorAlert;
import com.rzyc.fulongapi.service.SersorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author dong
 * @date 2023-05-27 10:24
 * @Version V1.0
 */
@Api(tags = "传感器接收数据")
@CrossOrigin("*")
@RequestMapping("sensor")
@RestController
@Validated
public class SensorController extends BaseController{

    @Autowired
    private SersorService sersorService;

    //报警信息
    @Autowired
    protected SersorAlertMapper sersorAlertMapper;


    /**
     * 模拟水压
     * @version v1.0
     * @author dong
     * @date 2023/5/27 13:43
     */
    private void changeSersonData()throws Exception{
        List<Sersor> sersors = sersorMapper.findAll();
        if(null != sersors && sersors.size() > 0){
            for (Sersor sersor : sersors){
                String value = "";
                Random random = new Random();
                int randomNumber = random.nextInt(100000) + 1;
                if(randomNumber < 5){
                    value = 0.0+"";
                }else if(randomNumber >= 5 && randomNumber < 30000){
                    value = 0.1+"";
                }else if(randomNumber >= 30000 && randomNumber < 80000){
                    value = 0.2+"";
                }else if(randomNumber >= 80000 && randomNumber < 90000){
                    value = 0.3+"";
                }else if(randomNumber >= 90000 && randomNumber < 99995){
                    value = 0.4+"";
                }else if(randomNumber >= 99995){
                    value = 0.5+"";
                }

                randomNumber = random.nextInt(10) + 1;
                value = value+randomNumber;

                Sersor ser = new Sersor();
                ser.setSersorId(sersor.getSersorId());
                ser.setCurrentVal(value);

                Integer alertState = 1;
                Double valueDou = TypeConversion.StringToDouble(value);
                if(valueDou < 0.1){
                    alertState = 2;
                    SersorAlert sersorAlert = new SersorAlert();
                    sersorAlert.setAlertId(RandomNumber.getUUid());
                    sersorAlert.setSersorId(sersor.getSersorId());
                    sersorAlert.setAlertTime(new Date());
                    sersorAlert.setAlertState(alertState);
                    sersorAlert.setCurrentVal(value);
                    sersorAlert.setState(1);
                    sersorAlert.setCreateBy("导入");
                    sersorAlert.setCreateTime(new Date());
                    sersorAlert.setModifyBy("");
                    sersorAlert.setModifyTime(new Date());
                    sersorAlertMapper.insert(sersorAlert);
                }else if(valueDou > 0.5){
                    alertState = 3;
                    SersorAlert sersorAlert = new SersorAlert();
                    sersorAlert.setAlertId(RandomNumber.getUUid());
                    sersorAlert.setSersorId(sersor.getSersorId());
                    sersorAlert.setAlertTime(new Date());
                    sersorAlert.setAlertState(alertState);
                    sersorAlert.setCurrentVal(value);
                    sersorAlert.setState(1);
                    sersorAlert.setCreateBy("导入");
                    sersorAlert.setCreateTime(new Date());
                    sersorAlert.setModifyBy("");
                    sersorAlert.setModifyTime(new Date());
                    sersorAlertMapper.insert(sersorAlert);
                }
                ser.setAlertState(alertState);
                ser.setUpdateTime(new Date());
                sersorMapper.updateById(ser);

                System.out.println("randomNumber --> "+randomNumber);
            }
        }
    }

    /**
     * 传感器报警信息
     * @version v1.0
     * @author dong
     * @date 2023/5/27 14:39
     */
    @ApiOperation(value = "传感器报警信息", notes = "传感器报警信息")
    @GetMapping(value = "/alertList")
    public MultiResult<SersorAlert> alertList()throws Exception{
        MultiResult<SersorAlert> result = new MultiResult<>();
        List<SersorAlert> sersorAlerts = sersorAlertMapper.alertList();
        result.setData(sersorAlerts);
        return result;
    }


    @ApiOperation(value = "传感器数据接收", notes = "传感器数据接收")
    @PostMapping("/sensorData")
    public Map<String,String> sensorData(@RequestBody SensorDataDto sensorDataDto){
        Map<String,String> result = new HashMap<>();
        try {
            System.out.println("sensorDataDto ---> "+ JSONArray.toJSONString(sensorDataDto));


            SensorWt sensorWt = new SensorWt();
            BeanUtils.copyProperties(sensorWt,sensorDataDto);

            if(StringUtils.isNotBlank(sensorDataDto.getCreatetime())){
                sensorWt.setCreatetime(DateUtils.timet(Long.valueOf(sensorDataDto.getCreatetime()),"yyyy-MM-dd HH:mm:ss"));
            }
            if(StringUtils.isNotBlank(sensorDataDto.getCollectiontime())){
                sensorWt.setCollectiontime(DateUtils.timet(Long.valueOf(sensorDataDto.getCollectiontime()),"yyyy-MM-dd HH:mm:ss"));
            }



            //实时数据记录
            SensorWt wt = sensorWtMapper.findBySmokeid(sensorWt.getSmokeid());
            if(null != wt){
                sensorWt.setSersorId(wt.getSersorId());
                sensorWt.setModifyTime(new Date());
                sensorWtMapper.updateById(sensorWt);
            }else{
                sensorWt.setSersorId(RandomNumber.getUUid());
                sensorWt.setModifyTime(new Date());
                sensorWtMapper.insert(sensorWt);
            }




            //历史数据
            SensorWtHis sensorWtHis = new SensorWtHis();
            BeanUtils.copyProperties(sensorWtHis,sensorDataDto);
            sensorWtHis.setSersorId(RandomNumber.getUUid());


            if(StringUtils.isNotBlank(sensorDataDto.getCreatetime())){
                sensorWtHis.setCreatetime(DateUtils.timet(Long.valueOf(sensorDataDto.getCreatetime()),"yyyy-MM-dd HH:mm:ss"));
            }
            if(StringUtils.isNotBlank(sensorDataDto.getCollectiontime())){
                sensorWtHis.setCollectiontime(DateUtils.timet(Long.valueOf(sensorDataDto.getCollectiontime()),"yyyy-MM-dd HH:mm:ss"));
            }



            sensorWtHis.setModifyTime(new Date());
            sensorWtHisMapper.insert(sensorWtHis);

            sersorService.sendData();
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("code","ok");
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(DateUtils.timet(1686554179471l,"yyyy-MM-dd HH:mm:ss"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
