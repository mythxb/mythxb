package com.rzyc.fulongapi.controller;

import com.common.utils.StringUtils;
import com.common.utils.jwt.JwtUtil;
import com.common.utils.model.SingleResult;
import com.rzyc.fulongapi.intercept.LoginAuth;
import com.rzyc.fulongapi.model.BuildingResident;
import com.rzyc.fulongapi.service.ImportDanger;
import com.rzyc.fulongapi.service.ImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台")
@RestController
@CrossOrigin("*")
@RequestMapping("web")
public class Webcollector {

    @Autowired
    private ImportDanger importDanger;

    @Autowired
    private ImportService importService;

    /**
     * 导入楼栋信息
     *
     * @return
     * @throrws Exception
     */
    @PostMapping("/importBuildingInfo")
    @ApiOperation("导入楼栋信息")
    public SingleResult importBuildingInfo(HttpServletRequest request) throws Exception {
        SingleResult singleResult = new SingleResult();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String token = request.getHeader("userToken");
        String userId = JwtUtil.getTokenMsg(token);
        resultMap = importService.importBuildingInfo(request, userId);
        singleResult.setData(resultMap);
        return singleResult;
    }

    /**
     * 导入隐患信息
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/importDangerInfo")
    @ApiOperation("导入隐患信息")
    public SingleResult importDangerInfo(HttpServletRequest request) throws Exception {
        SingleResult singleResult = new SingleResult();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String token = request.getHeader("userToken");
        String userId = JwtUtil.getTokenMsg(token);
        resultMap = importService.importDangerInfo(request, userId);
        singleResult.setData(resultMap);
        return singleResult;
    }

    /**
     * 导入燃气报警器住户反向生成无报警器住户隐患
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/importGasAlarm")
    @ApiOperation("导入燃气报警器住户反向生成无报警器住户隐患")
    public SingleResult importGasAlarm(HttpServletRequest request) throws Exception {
        SingleResult singleResult = new SingleResult();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String token = request.getHeader("userToken");
        String userId = JwtUtil.getTokenMsg(token);
        resultMap = importDanger.importDanger(request, userId);
        singleResult.setData(resultMap);
        return singleResult;
    }

    /**
     * 导入居住人员信息
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/importResident")
    @ApiOperation("导入居住人员信息")
    public SingleResult importResident(HttpServletRequest request) throws Exception {
        SingleResult singleResult = new SingleResult();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String token = request.getHeader("userToken");
        String userId = JwtUtil.getTokenMsg(token);
        resultMap = importService.importResident(request, userId);
        singleResult.setData(resultMap);
        return singleResult;
    }
}
