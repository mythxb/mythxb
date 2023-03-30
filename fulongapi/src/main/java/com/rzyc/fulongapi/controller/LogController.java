package com.rzyc.fulongapi.controller;


import com.common.utils.model.SingleResult;
import com.rzyc.fulongapi.mapper.LogMapper;
import com.rzyc.fulongapi.model.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(tags = "日志管理")
@CrossOrigin("*")
@RequestMapping("log")
@Controller
@Validated
public class LogController extends BaseController {


    @ApiOperation(value = "日志信息", notes = "日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "startTime", required = false, dataType = "string"),
            @ApiImplicitParam(name = "endTime", value = "endTime", required = false, dataType = "string"),
            @ApiImplicitParam(name = "behavior", value = "操作", required = false, dataType = "string"),
            @ApiImplicitParam(name = "param", value = "param", required = false, dataType = "string"),
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
    })
    @GetMapping("/logList")
    @ResponseBody
    public SingleResult logList(String startTime, String endTime, String behavior, String param, Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        List<Log> logs = logMapper.logList(startTime, endTime, behavior, param, page, pageSize);
        for (Log log : logs) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(log.getCreateTime());
            log.setCreateTimeToString(dateString);
        }
        long count = logMapper.logListCount(startTime, endTime, behavior, param);
        singleResult.setData(logs);
        singleResult.setCount(count);
        return singleResult;
    }


}
