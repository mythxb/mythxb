package com.rzyc.fulongapi.controller;

import com.alibaba.fastjson.JSONArray;
import com.common.utils.DateUtils;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.encryption.AesEncryptUtil;
import com.common.utils.excel.ExcelUtils;
import com.common.utils.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rzyc.fulongapi.bean.check.ChecksList;
import com.rzyc.fulongapi.bean.check.DangerData;
import com.rzyc.fulongapi.bean.check.DangerList;
import com.rzyc.fulongapi.bean.check.dto.*;
import com.rzyc.fulongapi.enums.*;
import com.rzyc.fulongapi.mapper.CheckDangerMapper;
import com.rzyc.fulongapi.mapper.ChecksMapper;
import com.rzyc.fulongapi.model.*;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Signature;
import java.sql.Timestamp;
import java.util.*;

@Api(tags = "检查系统")
@CrossOrigin("*")
@RequestMapping("check")
@Controller
@Validated
public class CheckController extends BaseController {


    @ApiOperation(value = "隐患列表", notes = "隐患列表")
    @GetMapping("/dangerPage")
    @ResponseBody
    public SingleResult<Pager<DangerList>> dangerPage(@Valid DangerPageDto dangerPageDto) throws Exception {
        SingleResult<Pager<DangerList>> result = new SingleResult<>();

        List<String> buildIds = new ArrayList<>();
        List<String> unitIds = new ArrayList<>();

        SysUser sysUser = sysUserMapper.selectById(dangerPageDto.getUserId());


        //判断企业类型
        if (StringUtils.isNotBlank(dangerPageDto.getUserId())) {

            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    unitIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                }
            }
        }

        String startTime = DateUtils.getHandleTime(dangerPageDto.getStartTime(), 1);
        String endTime = DateUtils.getHandleTime(dangerPageDto.getEndTime(), 2);

        String condition = TypeConversion.getCondition(dangerPageDto.getCondition());
        Pager<DangerList> pager = new Pager<>();
        PageHelper.startPage(dangerPageDto.getPage(), dangerPageDto.getPageSize());

        Page<DangerList> page = new Page<>();

        if (UserType.ENT.getType() == sysUser.getUserType()) {
            page = (Page<DangerList>) checkDangerMapper.entDangerList(condition, sysUser.getEnterpriseId(), startTime, endTime, dangerPageDto.getState(), dangerPageDto.getRiskLevel());
        } else {
            page = (Page<DangerList>) checkDangerMapper.dangerList(condition, dangerPageDto.getTargetId(), startTime, endTime, dangerPageDto.getState(), dangerPageDto.getRiskLevel(), buildIds, unitIds);
        }

        getDatePage(pager, page);

        //处理隐患信息
        handleDnagerInfo(pager.getRows());

        result.setData(pager);
        return result;
    }

    @ApiOperation(value = "隐患详情", notes = "隐患详情")
    @ApiImplicitParam(name = "id", value = "隐患id", required = true)
    @GetMapping("/dangerDetail")
    @ResponseBody
    public SingleResult<DangerList> dangerDetail(String id) throws Exception {
        SingleResult<DangerList> result = new SingleResult<>();
        DangerList dangerList = checkDangerMapper.dangerDetail(id);
        result.setData(dangerList);
        return result;
    }

    @ApiOperation(value = "隐患统计数据", notes = "隐患统计数据")
    @GetMapping("/dangerData")
    @ResponseBody
    public SingleResult<DangerData> dangerData(@Valid DangerDataDto dangerDataDto) throws Exception {
        SingleResult<DangerData> result = new SingleResult<>();
        DangerData dangerData = new DangerData();

        String condition = TypeConversion.getCondition(dangerDataDto.getCondition());

        //隐患总数
        Integer totalNum = checkDangerMapper.dangerData(null, condition);
        dangerData.setTotalNum(totalNum);

        //已整改
        Integer rectifyNum = checkDangerMapper.dangerData(DangerState.RECTIFYED.getState(), condition);
        dangerData.setRectifyNum(rectifyNum);

        //整改中数量
        Integer rectifyingNum = checkDangerMapper.dangerData(DangerState.RECTIFYING.getState(), condition);
        dangerData.setRectifyingNum(rectifyingNum);

        //未整改数量
        Integer notRectifyNum = checkDangerMapper.dangerData(DangerState.NOT_RECTIFY.getState(), condition);
        dangerData.setNotRectifyNum(notRectifyNum);

        //不能整改隐患
        Integer unableRectify = checkDangerMapper.dangerData(DangerState.UNABLE_RECTIFY.getState(), condition);
        dangerData.setUnableRectify(unableRectify);

        result.setData(dangerData);
        return result;
    }

    @ApiOperation(value = "修改隐患状态", notes = "修改隐患状态")
    @PostMapping("/dangerChangeState")
    @ResponseBody
    public SingleResult<String> dangerChangeState(@Valid @RequestBody ChangeDangerDto changeDangerDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        CheckDanger checkDanger = new CheckDanger();
        BeanUtils.copyProperties(checkDanger, changeDangerDto);


        List<Integer> dangerState = new ArrayList<>();
        dangerState.add(DangerState.NOT_RECTIFY.getState());
        dangerState.add(DangerState.RECTIFYED.getState());
        dangerState.add(DangerState.UNABLE_RECTIFY.getState());

        if (dangerState.contains(changeDangerDto.getRectificationState())) {
            checkDangerMapper.changeState(checkDanger);
        } else {
            checkDangerMapper.changeRectifyInfo(checkDanger);
        }
        return result;
    }

    @ApiOperation(value = "单元列表", notes = "单元列表")
    @GetMapping("/unitPage")
    @ResponseBody
    public SingleResult<Pager<BuildUnit>> unitPage(@Valid UnitPageDto unitPageDto) throws Exception {
        SingleResult<Pager<BuildUnit>> result = new SingleResult<>();
        String condition = TypeConversion.getCondition(unitPageDto.getCondition());

        List<String> buildIds = new ArrayList<>();
        List<String> unitIds = new ArrayList<>();

        //判断企业类型
        if (StringUtils.isNotBlank(unitPageDto.getUserId())) {
            SysUser sysUser = sysUserMapper.selectById(unitPageDto.getUserId());
            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    unitIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                }
            }
        }

        Pager<BuildUnit> pager = new Pager<>();
        PageHelper.startPage(unitPageDto.getPage(), unitPageDto.getPageSize());
        Page<BuildUnit> page = (Page<BuildUnit>) buildUnitMapper.unitListPage(condition, unitPageDto.getBuildingId(), unitPageDto.getUnitId(), buildIds, unitIds);

        for (BuildUnit buildUnit : page) {
            //将号码中4位隐藏
            if (buildUnit.getUnitManagerContact() != null && buildUnit.getUnitManagerContact().length() > 3 && buildUnit.getUnitManagerContact().length() <= 11) {
                buildUnit.setUnitManagerContact(new StringBuilder(buildUnit.getUnitManagerContact()).replace(3, 7, "****").toString());
            }
        }

        getDatePage(pager, page);
        result.setData(pager);
        return result;
    }

    @ApiOperation(value = "单元下楼层", notes = "单元下楼层")
    @ApiImplicitParam(name = "unitId", value = "单元id", required = true)
    @GetMapping("/unitFloor")
    @ResponseBody
    public MultiResult<BuildFloor> unitFloor(String unitId) throws Exception {
        MultiResult<BuildFloor> result = new MultiResult<>();
        List<BuildFloor> floors = buildFloorMapper.findByUnitId(unitId);
        result.setData(floors);
        return result;
    }

    /**
     * 检查项列表
     *
     * @param checkType
     * @param enterpriseId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "检查项列表", notes = "检查项列表")
    @ApiImplicitParam(name = "checkType", value = "检查类型：1、入户检查 2、公共区域检查 3、企业检查 4.特殊建筑体", required = true)
    @GetMapping("/checkItem")
    @ResponseBody
    public MultiResult<CheckItem> checkItem(Integer checkType, String enterpriseId) throws Exception {
        MultiResult<CheckItem> result = new MultiResult<>();

        //默认为入户检查
        if (null == checkType) {
            checkType = CheckType.HOUSE.getType();
        }

        String industryId = "";
        if (StringUtils.isNotBlank(enterpriseId)) {
            Enterprise enterprise = enterpriseMapper.selectById(enterpriseId);
            if (null != enterprise) {
                industryId = enterprise.getIndustryId();
            }
        }


        CheckList checkList = checkListMapper.findByListType(checkType);
        if (null != checkList) {
            List<CheckItem> checkItems = checkItemMapper.findByClassId(checkList.getCheckListId(), null, null);

            //加入行业清单
            if (StringUtils.isNotBlank(industryId)) {
                List<CheckItem> industryCheck = checkItemMapper.findByIndustryId(industryId);
                if (null != industryCheck && industryCheck.size() > 0) {
                    checkItems.addAll(industryCheck);
                }
            }

            result.setData(checkItems);
        }
        return result;
    }

    @ApiOperation(value = "新增检查", notes = "新增检查")
    @PostMapping("/checkAdd")
    @ResponseBody
    @Transactional
    public SingleResult<String> checkAdd(@Valid @RequestBody CheckAddDto checkAddDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        System.out.println("checkAddDto -> " + JSONArray.toJSONString(checkAddDto));

        //楼栋id
        String buildingId = "";
        //单元id
        String unitId = "";
        //楼层id
        String floorId = "";
        //楼层数
        Integer floorNumber = null;

        String industryId = "";

        if (checkAddDto.getCheckType() == CheckType.HOUSE.getType()) {
            //楼层信息
            BuildFloor floor = buildFloorMapper.selectById(checkAddDto.getFloorId());
            if (null != floor) {
                buildingId = floor.getBuildingId();
                unitId = floor.getUnitId();
                floorId = floor.getFloorId();
                floorNumber = floor.getFloorNumber();
            }
        } else if (checkAddDto.getCheckType() == CheckType.COMMON.getType()) {
            //楼栋
            Building building = buildingMapper.selectById(checkAddDto.getFloorId());
            if (null != building) {
                buildingId = building.getBuildId();
            }
        } else if (checkAddDto.getCheckType() == CheckType.ENT.getType()) {
            //企业
            Enterprise enterprise = enterpriseMapper.selectById(checkAddDto.getFloorId());
            if (null != enterprise) {
                buildingId = enterprise.getBuildingId();
                unitId = enterprise.getBuildingUnitId();
                floorId = enterprise.getEnterpriseId();
                industryId = enterprise.getIndustryId();
            }
        } else if (checkAddDto.getCheckType() == CheckType.SPECIAL_BUILD.getType()) {
            floorId = checkAddDto.getFloorId();
        }


        //获取检查项信息
        String classId = "a2a2c0ea-92f3-11ec-b82b-00163e0c1c62";
        CheckList checkList = checkListMapper.findByListType(checkAddDto.getCheckType());
        if (null != checkList) {
            classId = checkList.getCheckListId();
        }

        List<CheckItem> checkItems = checkItemMapper.findByClassId(classId, null, null);

        //加入行业清单
        if (StringUtils.isNotBlank(industryId)) {
            List<CheckItem> industryCheck = checkItemMapper.findByIndustryId(industryId);
            if (null != industryCheck && industryCheck.size() > 0) {
                checkItems.addAll(industryCheck);
            }
        }

        Map<String, CheckItem> itemMap = new HashMap<>();
        if (null != checkItems && checkItems.size() > 0) {
            for (CheckItem checkItem : checkItems) {
                itemMap.put(checkItem.getItemId(), checkItem);
            }
        }

        //当前时间
        Date nowTime = new Date();

        //检查信息
        Checks checks = new Checks();
        checks.setCheckId(checkAddDto.getCheckId());
        checks.setBuildingId(buildingId);
        checks.setBuildingUnitId(unitId);
        checks.setListId(classId);
        checks.setFloorFkey(floorId);
        checks.setAtFloor(floorNumber);
        checks.setCheckUserId(checkAddDto.getUserId());
        checks.setCheckTime(nowTime);
        checks.setRemark(checkAddDto.getRemarks());
        checks.setCheckType(checkAddDto.getCheckType());
        checks.setCreateTime(nowTime);
        checks.setModifyTime(nowTime);
        checks.setCreateBy(checkAddDto.getUserId());
        checks.setModifyBy(checkAddDto.getUserId());


        //检查项
        List<CheckDesc> checkDescs = new ArrayList<>();

        //隐患项
        List<CheckDanger> checkDangers = new ArrayList<>();

        //获取检查项和隐患项
        getCHeckInfo(checkAddDto, itemMap, checks, nowTime, checkDescs, checkDangers, buildingId, unitId, floorId);

        //插入检查记录
        checksMapper.insert(checks);

        //检查检查项
        if (null != checkDescs && checkDescs.size() > 0) {
            checkDescMapper.insertList(checkDescs);
        }

        //隐患项
        if (null != checkDangers && checkDangers.size() > 0) {
            checkDangerMapper.insertList(checkDangers);
        }

        //处理风险等级
        handleRiskLevel(checkAddDto.getCheckType(), floorId, unitId, buildingId);


        return result;
    }


    /**
     * 获取检查项和隐患项
     *
     * @param checkAddDto
     * @param itemMap
     * @param checks
     * @param nowTime
     * @param checkDescs
     * @param checkDangers
     * @param buildingId
     * @param unitId
     * @param floorId
     * @throws Exception
     */
    private void getCHeckInfo(CheckAddDto checkAddDto, Map<String, CheckItem> itemMap, Checks checks, Date nowTime, List<CheckDesc> checkDescs, List<CheckDanger> checkDangers, String buildingId, String unitId, String floorId) throws Exception {
        //检查项
        List<CheckInfoDto> checkInfos = checkAddDto.getCheckInfos();
        if (null != checkInfos && checkInfos.size() > 0) {

            for (CheckInfoDto checkInfoDto : checkInfos) {
                //获取检查项
                CheckItem checkItem = itemMap.get(checkInfoDto.getItemId());

                CheckDesc checkDesc = new CheckDesc();
                checkDesc.setId(RandomNumber.getUUid());
                checkDesc.setCheckId(checks.getCheckId());
                checkDesc.setCheckItemId(checkInfoDto.getItemId());

                //检查项名称
                if (null != checkItem) {
                    checkDesc.setCheckItemName(checkItem.getCheckItemName());
                }

                checkDesc.setCheckResult(checkInfoDto.getCheckResult());
                checkDesc.setCreateTime(nowTime);
                checkDesc.setModifyTime(nowTime);
                checkDesc.setCreateBy(checkAddDto.getUserId());
                checkDesc.setModifyBy(checkAddDto.getUserId());
                checkDescs.add(checkDesc);

                //不合格 处理隐患项
                System.out.println("CheckResult --------------------------------> " + checkInfoDto.getCheckResult());
                if (CheckResult.UNQUALIFIED.getResult() == checkInfoDto.getCheckResult() && null != checkInfoDto.getDangerInfos() && checkInfoDto.getDangerInfos().size() > 0) {
                    for (DangerInfoDto dangerInfoDto : checkInfoDto.getDangerInfos()) {
                        CheckDanger danger = new CheckDanger();
                        danger.setId(dangerInfoDto.getDangerId());
                        danger.setCheckId(checks.getCheckId());
                        danger.setCheckDescId(checkDesc.getId());
                        danger.setCheckItem(dangerInfoDto.getDangerDesc());
                        danger.setNormalorimportant(dangerInfoDto.getNormalorimportant());
                        danger.setRectificationState(DangerState.RECTIFYING.getState());


                        if (StringUtils.isNotBlank(checkAddDto.getRectifyTerm())) {
                            danger.setRectifyTerm(DateUtils.parseString2Date(checkAddDto.getRectifyTerm(), "yyyy-MM-dd"));
                        }

                        //隐患检查项id
                        if (null != checkItem) {
                            danger.setDangerTypeId(checkItem.getClassId());
                        }
                        danger.setBuildingId(buildingId);
                        danger.setBuildingUnitId(unitId);
                        danger.setFloorFkey(floorId);
                        danger.setCheckType(checkAddDto.getCheckType());
                        danger.setCreateTime(nowTime);
                        danger.setModifyTime(nowTime);
                        danger.setCreateBy(checkAddDto.getUserId());
                        danger.setModifyBy(checkAddDto.getUserId());
                        checkDangers.add(danger);

                    }
                }
            }
        }
    }


    /**
     * 处理风险等级
     *
     * @param checkType
     * @param floorId
     * @param unitId
     * @param buildingId
     * @throws Exception
     */
    private void handleRiskLevel(Integer checkType, String floorId, String unitId, String buildingId) throws Exception {
        //楼层检查出来风险等级
        if (checkType == CheckType.HOUSE.getType()) {
            //楼层风险等级
            floorRiskLevel(floorId);

            //单元风险等级
            unitRiskLevel(unitId);

            //楼栋风险等级
            bulidRiskLevel(buildingId);
        }

        //企业检查处理风险等级
        if (checkType == CheckType.ENT.getType()) {
            //楼层风险等级
            entRiskLevel(floorId);
        }
    }

    @ApiOperation(value = "检查列表", notes = "检查列表")
    @GetMapping("/checkPage")
    @ResponseBody
    public SingleResult<Pager<ChecksList>> checkPage(@Valid CheckPageDto checkPageDto) throws Exception {
        SingleResult<Pager<ChecksList>> result = new SingleResult<>();
        String condition = TypeConversion.getCondition(checkPageDto.getCondition());

        List<String> buildIds = new ArrayList<>();
        List<String> unitIds = new ArrayList<>();

        String startTime = DateUtils.getHandleTime(checkPageDto.getStartTime(), 1);
        String endTime = DateUtils.getHandleTime(checkPageDto.getEndTime(), 2);


        SysUser sysUser = sysUserMapper.selectById(checkPageDto.getUserId());

        //判断企业类型
        if (StringUtils.isNotBlank(checkPageDto.getUserId())) {

            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    unitIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                }
            }
        }


        Pager<ChecksList> pager = new Pager<>();
        PageHelper.startPage(checkPageDto.getPage(), checkPageDto.getPageSize());
        Page<ChecksList> page = new Page<>();

        if (UserType.ENT.getType() == sysUser.getUserType()) {
            //企业检查记录
            page = (Page<ChecksList>) checksMapper.encCheckList(condition, sysUser.getEnterpriseId(), startTime, endTime);
        } else {
            //所有检查记录
            page = (Page<ChecksList>) checksMapper.checkList(condition, buildIds, unitIds, startTime, endTime);
        }

        getDatePage(pager, page);
        result.setData(pager);
        return result;
    }

    @ApiOperation(value = "检查详情", notes = "检查详情")
    @ApiImplicitParam(name = "checkId", value = "检查id", required = true)
    @GetMapping("/checkDetail")
    @ResponseBody
    public SingleResult<ChecksList> checkDetail(String checkId) throws Exception {
        SingleResult<ChecksList> result = new SingleResult<>();
        ChecksList checksList = checksMapper.checkDetail(checkId);
        if (null != checksList) {

            //检查项
            List<CheckDesc> checkDescs = checkDescMapper.checkDescList(checksList.getCheckId());
            if (null != checkDescs && checkDescs.size() > 0) {
                checksList.setCheckDescs(checkDescs);
            }
            result.setData(checksList);
        }
        return result;
    }


    @ApiOperation(value = "企业列表", notes = "企业列表")
    @GetMapping("/entPage")
    @ResponseBody
    public SingleResult<Pager<Enterprise>> entPage(@Valid EntPageDto entPageDto) throws Exception {
        SingleResult<Pager<Enterprise>> result = new SingleResult<>();
        String condition = TypeConversion.getCondition(entPageDto.getCondition());


        List<String> buildIds = new ArrayList<>();
        List<String> unitIds = new ArrayList<>();

        //判断企业类型
        if (StringUtils.isNotBlank(entPageDto.getUserId())) {
            SysUser sysUser = sysUserMapper.selectById(entPageDto.getUserId());
            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    unitIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                }
            }
        }

        if (StringUtils.isNotBlank(entPageDto.getBuildingId())) {
            buildIds = new ArrayList<>();
            buildIds.add(entPageDto.getBuildingId());
        }

        Pager<Enterprise> pager = new Pager<>();
        PageHelper.startPage(entPageDto.getPage(), entPageDto.getPageSize());
        Page<Enterprise> page = (Page<Enterprise>) enterpriseMapper.entList(condition, entPageDto.getEnterpriseId(), entPageDto.getIndustryType(), buildIds, unitIds);

        for (Enterprise enterprise : page) {
            //将号码中4位隐藏
            if (enterprise.getContactMobile() != null && enterprise.getContactMobile().length() > 3 && enterprise.getContactMobile().length() <= 11) {
                enterprise.setContactMobile(new StringBuilder(enterprise.getContactMobile()).replace(3, 7, "****").toString());
            }
        }
        getDatePage(pager, page);
        result.setData(pager);
        return result;
    }

    @ApiOperation(value = "楼栋详情", notes = "楼栋详情")
    @ApiModelProperty(name = "buildId", value = "楼栋id", required = true)
    @GetMapping("/buildingDetail")
    @ResponseBody
    public SingleResult<Building> buildingDetail(String buildId) throws Exception {
        SingleResult<Building> result = new SingleResult<>();
        Building building = buildingMapper.selectById(buildId);
        result.setData(building);
        return result;
    }


    @ApiOperation(value = "小程序(新增/修改住户信息)", notes = "小程序(新增/修改住户信息)")
    @PostMapping("/addOrEditResident")
    @ResponseBody
    public SingleResult<BuildingResident> addOrEditResident(@RequestBody HashMap<String, String> map) throws Exception {
        SingleResult<BuildingResident> singleResult = new SingleResult<>();
        String userId = getUserId();
        int result = 0;
        List<BuildingResident> buildingResident = JSONArray.parseArray(map.get("data"), BuildingResident.class);
        if (null != buildingResident && buildingResident.size() > 0) {
            buildingResidentMapper.deleteByUnitOrFloor(buildingResident.get(0).getBuildingUnitId(), buildingResident.get(0).getFloorId());
            for (BuildingResident br : buildingResident) {
                if (StringUtils.isBlank(br.getResidentId())) {
                    br.setCreateBy(userId);
                    br.setCreateTime(new Date());
                }

                //判断是否加密
                try {
                    AesEncryptUtil.desEncrypt(br.getResidentIdentityCard());
                } catch (Exception e) {
                    br.setResidentIdentityCard(AesEncryptUtil.encrypt(br.getResidentIdentityCard()));
                }

                br.setResidentId(RandomNumber.getUUid());
                br.setModifyBy(userId);
                br.setModifyTime(new Date());
                result = buildingResidentMapper.insert(br);
            }
        }
        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    @ApiOperation(value = "小程序(住户信息列表)", notes = "小程序(住户信息列表)")
    @GetMapping("/residentList")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"), @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"), @ApiImplicitParam(name = "单元ID", value = "unitId", required = true, dataType = "string"), @ApiImplicitParam(name = "楼层ID", value = "floorId", required = true, dataType = "string"), @ApiImplicitParam(name = "房屋使用性质", value = "residentType", required = false, dataType = "int"), @ApiImplicitParam(name = "居民名称", value = "residentName", required = false, dataType = "string"), @ApiImplicitParam(name = "condition", value = "condition", dataType = "string")})
    @ResponseBody
    public SingleResult<List<BuildingResident>> residentList(Integer page, Integer pageSize, String unitId, String floorId, Integer residentType, String residentName, String condition) throws Exception {
        SingleResult<List<BuildingResident>> singleResult = new SingleResult<>();
        page = pageSize * (page - 1);
        String conditions = TypeConversion.getCondition(condition);
        List<BuildingResident> residents = buildingResidentMapper.selectResidentList(page, pageSize, unitId, floorId, residentType, residentName, conditions);
        long count = buildingResidentMapper.selectResidentCount(unitId, floorId, residentType, residentName, conditions);
        singleResult.setData(residents);
        singleResult.setCount(count);
        return singleResult;
    }


    @ApiOperation(value = "小程序(新增/修改设备信息)", notes = "小程序(新增/修改设备信息)")
    @PostMapping("/addOrEditGasEquipment")
    @ResponseBody
    public SingleResult<GasEquipment> addOrEditGasEquipment(@RequestBody HashMap<String, String> map) throws Exception {
        SingleResult<GasEquipment> singleResult = new SingleResult<>();
        int result = 0;
        String userId = getUserId();
        List<GasEquipment> gasEquipment = JSONArray.parseArray(map.get("data"), GasEquipment.class);
        if (null != gasEquipment && gasEquipment.size() > 0) {
            gasEquipmentMapper.deleteByUnitOrFloor(gasEquipment.get(0).getUnitId(), gasEquipment.get(0).getFloorId());
            for (GasEquipment ge : gasEquipment) {
                if (StringUtils.isBlank(ge.getGeId())) {
                    ge.setCreateBy(userId);
                    ge.setCreateTime(new Date());
                }
                ge.setGeId(RandomNumber.getUUid());
                ge.setModifyBy(userId);
                ge.setModifyTime(new Date());
                result = gasEquipmentMapper.insert(ge);
            }
        }
        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    @ApiOperation(value = "小程序(燃气设备信息列表)", notes = "小程序(燃气设备信息列表)")
    @GetMapping("/gasEquipmentList")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"), @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"), @ApiImplicitParam(name = "楼层ID", value = "floorId", required = true, dataType = "string"), @ApiImplicitParam(name = "单元ID", value = "unitId", required = true, dataType = "string"),})
    @ResponseBody
    public SingleResult<List<GasEquipment>> gasEquipmentList(Integer page, Integer pageSize, String floorId, String unitId) throws Exception {
        SingleResult<List<GasEquipment>> singleResult = new SingleResult<>();
        page = pageSize * (page - 1);
        List<GasEquipment> gasEquipments = gasEquipmentMapper.selectGasEquipmentList(page, pageSize, floorId, unitId);
        long count = gasEquipmentMapper.selectGasEquipmentCount(floorId, unitId);
        singleResult.setData(gasEquipments);
        singleResult.setCount(count);
        return singleResult;
    }


    /**
     * 新增/修改单元附属户主(管理员)
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/addOrEditSubsidiaryManager")
    @ApiOperation("新增/修改单元附属户主(管理员)")
    @ResponseBody
    public SingleResult<BuildUnitHouseholder> addOrEditSubsidiaryManager(@RequestBody HashMap<String, String> map) throws Exception {
        SingleResult singleResult = new SingleResult();
        int result = 0;
        int index = 0;
        List<BuildUnitHouseholder> buildUnitHouseholder = JSONArray.parseArray(map.get("data"), BuildUnitHouseholder.class);
        if (null != buildUnitHouseholder && buildUnitHouseholder.size() > 0) {
            buildUnitHouseholderMapper.delByUnitId(buildUnitHouseholder.get(0).getBuildingUnitId());
            for (BuildUnitHouseholder bd : buildUnitHouseholder) {
                index++;
                if (index - 1 == 0) {
                    result += buildUnitMapper.updateManager(bd);
                    continue;
                }
                bd.setCreateTime(new Date());
                if (StringUtils.isBlank(bd.getHouseholderId())) {
                    bd.setHouseholderId(RandomNumber.getUUid());
                }
                result += buildUnitHouseholderMapper.insert(bd);
            }
        }
        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    /**
     * 小程序问题上报
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/submitQuestion")
    @ApiOperation("小程序问题上报/修改")
    @ResponseBody
    public SingleResult<QuestionSubmit> submitQuestion(@RequestBody QuestionSubmit questionSubmit) throws Exception {
        SingleResult singleResult = new SingleResult();
        String userId = getUserId();
        if (StringUtils.isBlank(questionSubmit.getQsSubId())) {
            questionSubmit.setQsSubId(RandomNumber.getUUid());
            questionSubmit.setCreateBy(userId);
            questionSubmit.setCreateTime(new Date());
            questionSubmit.setQsState(1);//默认未解决
            questionSubmitMapper.insert(questionSubmit);
        } else {
            questionSubmit.setModifyBy(userId);
            questionSubmit.setModifyTime(new Date());
            questionSubmitMapper.updateById(questionSubmit);
        }
        return singleResult;
    }

    /**
     * 小程序问题查看
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/submitQuestionList")
    @ApiOperation("小程序问题查看")
    @ApiImplicitParams({@ApiImplicitParam(name = "condition", value = "condition", required = false, dataType = "string"),
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
    })
    @ResponseBody
    public SingleResult<List<QuestionSubmit>> submitQuestionList(String condition, Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        String conditions = TypeConversion.getCondition(condition);
        List<QuestionSubmit> questionSubmits = questionSubmitMapper.selectSubmitQuestionList(conditions, page, pageSize);
        long count = questionSubmitMapper.selectSubmitQuestionListCount(conditions);
        singleResult.setData(questionSubmits);
        singleResult.setCount(count);
        return singleResult;
    }

    /**
     * 所有隐患类型
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/allDangerTypes")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "1.普通 2.公共", required = false, dataType = "string"),})
    @ApiOperation("所有隐患类型")
    @ResponseBody
    public SingleResult allDangerTypes(Integer type) throws Exception {
        SingleResult singleResult = new SingleResult();
        List<DangerType> dangerTypes = dangerTypeMapper.allDangerTypes(type);
        singleResult.setData(dangerTypes);
        return singleResult;
    }

    @GetMapping("/getDangerTypeById")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "隐患类型id", required = false, dataType = "String"),})
    @ApiOperation("通过隐患类型id查询")
    @ResponseBody
    public SingleResult getDangerTypeById(String id) throws Exception {
        SingleResult singleResult = new SingleResult();
        DangerType dangerTypes = dangerTypeMapper.selectById(id);
        System.out.println(dangerTypes);
        singleResult.setData(dangerTypes);
        return singleResult;
    }

    @ApiOperation(value = "检查项列表", notes = "检查项列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "检查表ID", value = "classId", defaultValue = "1", required = false, dataType = "string"), @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"), @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),})
    @GetMapping("/checkItemList")
    @ResponseBody
    public SingleResult<CheckItem> checkItemList(String classId, String condition, Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        condition = TypeConversion.getCondition(condition);
        page = pageSize * (page - 1);
        List<CheckItem> checkItems = checkItemMapper.findByClassIdForWeb(classId, condition, page, pageSize);


        for (int i = 0; i < checkItems.size(); i++) {
            if (StringUtils.isNotBlank(checkItems.get(i).getIndustryId())){
                List<String> strToList = StringUtils.strToList(checkItems.get(i).getIndustryId(), ",");
                List<StoreType> storeTypes = storeTypeMapper.selectBatchIds(strToList);

                StringBuilder str = new StringBuilder();
                for (int j = 0; j < storeTypes.size(); j++) {
                    str.append(storeTypes.get(j).getTypeName()).append(",");
                }

                checkItems.get(i).setIndustryName(StringUtils.customTrim(str.toString(), ','));
            }
        }

        long checkItemsCount = checkItemMapper.findByClassIdCount(classId, condition);
        singleResult.setData(checkItems);
        singleResult.setCount(checkItemsCount);
        return singleResult;
    }

    @ApiOperation(value = "新增/修改检查项", notes = "新增/修改检查项")
    @PostMapping("/addOrEditCheckItem")
    @ResponseBody
    public SingleResult<CheckItem> addOrEditCheckItem(@RequestBody CheckItem checkItem) throws Exception {
        SingleResult singleResult = new SingleResult();

        String userId = getUserId();
        int result = 0;
        if (StringUtils.isBlank(checkItem.getItemId())) {
            checkItem.setItemId(RandomNumber.getUUid());
            checkItem.setCreateBy(userId);
            checkItem.setCreateTime(new Date());
            result = checkItemMapper.insert(checkItem);
        } else {
            result = checkItemMapper.updateById(checkItem);
        }
        if (result < 1) {
            singleResult.setMessage(Message.ERROR);
            singleResult.setCode(Code.ERROR.getCode());
        }
        return singleResult;
    }

    @ApiOperation(value = "导入整治工作推进表,修改整改状态", notes = "导入整治工作推进表,修改整改状态")
    @RequestMapping(value = "/importEnt", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> importEnt(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();// getPhysicalNumberOfRows();
            System.out.println("总行数" + totalRowNum);

            if (totalRowNum > 0) {
                DataFormatter dataFormatter = new DataFormatter();
                Integer index = 1;

                //获得所有数据
                for (int i = 1; i <= totalRowNum; i++) {
                    //获得第i行对象
                    Row row = sheet.getRow(i);
                    if (null == row) {
                        break;
                    }

                    System.out.println("index --> " + index);
                    System.out.println("i --> " + i);

                    //内容
                    String entName = "";

                    // 获取这一行的第一列单元格的信息
                    Cell cell = row.getCell(0);
                    if (null != cell) {
                        // 获取这个单元格的内容
                        entName = dataFormatter.formatCellValue(cell);
                    }

                    if (StringUtils.isNotBlank(entName)) {
                        //2、东街 1、西街
                        Integer direction = 2;
                        //号
                        String no = "";
                        //栋数
                        String buildNum = "";
                        //单元
                        String unitNum = "";

                        if (entName.contains("西")) {
                            direction = 1;
                            String[] haoStrs = entName.split("号");
                            no = haoStrs[0].substring(haoStrs[0].length() - 1);
                            buildNum = haoStrs[1].substring(0, haoStrs[1].indexOf("栋"));
                            String[] buildStrs = entName.split("栋");
                            unitNum = buildStrs[1].substring(0, buildStrs[1].indexOf("单"));
                        } else if (entName.contains("东")) {
                            direction = 2;
                            String[] haoStrs = entName.split("号");
                            no = haoStrs[0].substring(haoStrs[0].length() - 1);
                            buildNum = haoStrs[1].substring(0, haoStrs[1].indexOf("栋"));
                            String[] buildStrs = entName.split("栋");
                            unitNum = buildStrs[1].substring(0, buildStrs[1].indexOf("单"));
                        }

                        String findBuildId = checkDangerMapper.getBuildId(direction, Integer.valueOf(buildNum));
                        String findUnitId = checkDangerMapper.getUnitId(findBuildId, Integer.valueOf(unitNum));
                        checkDangerMapper.updateRectificationState(findBuildId, findUnitId);

                        index++;
                    }
                }
                System.out.println("数据整改完毕");
            }
        }
        return result;
    }

    /**
     * 隐患列表
     *
     * @param dangerPageDto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "隐患列表", notes = "隐患列表")
    @GetMapping("/fireDangerPage")
    @ResponseBody
    public SingleResult<Pager<DangerList>> fireDangerPage(@Valid DangerPageDto dangerPageDto) throws Exception {
        SingleResult<Pager<DangerList>> result = new SingleResult<>();

        List<String> buildIds = new ArrayList<>();
        List<String> unitIds = new ArrayList<>();

        List<String> typeIds = new ArrayList<>();
        typeIds.add("b9cc10fd-22d6-4880-a8ee-143654471e03");
        typeIds.add("s769898e-eac2-44a9-b53c-8141e2173231");

        //单元id
        if (StringUtils.isNotBlank(dangerPageDto.getUnitId())) {
            unitIds.add(dangerPageDto.getUnitId());
        }

        //判断企业类型
        if (StringUtils.isNotBlank(dangerPageDto.getUserId())) {
            SysUser sysUser = sysUserMapper.selectById(dangerPageDto.getUserId());
            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    unitIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                }
            }
        }

        String startTime = DateUtils.getHandleTime(dangerPageDto.getStartTime(), 1);
        String endTime = DateUtils.getHandleTime(dangerPageDto.getEndTime(), 2);

        String condition = TypeConversion.getCondition(dangerPageDto.getCondition());
        Pager<DangerList> pager = new Pager<>();
        PageHelper.startPage(dangerPageDto.getPage(), dangerPageDto.getPageSize());
        Page<DangerList> page = (Page<DangerList>) checkDangerMapper.fireDangerList(condition, dangerPageDto.getTargetId(), startTime, endTime, dangerPageDto.getState(), dangerPageDto.getRiskLevel(), buildIds, unitIds, typeIds,dangerPageDto.getDangerType());
        getDatePage(pager, page);
        result.setData(pager);
        return result;
    }

    /**
     * 未处理问题上报
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "未处理问题上报", notes = "未处理问题上报")
    @GetMapping("/questionList")
    @ResponseBody
    public MultiResult<QuestionSubmit> questionList() throws Exception {
        MultiResult<QuestionSubmit> result = new MultiResult<>();
        List<QuestionSubmit> questionSubmits = questionSubmitMapper.findByQsState();
        result.setData(questionSubmits);
        return result;
    }

    /**
     * 企业列表导出
     *
     * @param entExportDto
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "企业列表导出", notes = "企业列表导出")
    @GetMapping("/entExport")
    @ResponseBody
    public void entExport(@Valid EntExportDto entExportDto, HttpServletResponse response) throws Exception {
        String condition = TypeConversion.getCondition(entExportDto.getCondition());


        List<String> buildIds = new ArrayList<>();
        List<String> unitIds = new ArrayList<>();

        //判断企业类型
        if (StringUtils.isNotBlank(entExportDto.getUserId())) {
            SysUser sysUser = sysUserMapper.selectById(entExportDto.getUserId());
            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    unitIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                }
            }
        }

        if (StringUtils.isNotBlank(entExportDto.getBuildingId())) {
            buildIds = new ArrayList<>();
            buildIds.add(entExportDto.getBuildingId());
        }

        List<Enterprise> enterprises = enterpriseMapper.entList(condition, entExportDto.getEnterpriseId(), entExportDto.getIndustryType(), buildIds, unitIds);
        String filename = "商户列表.xlsx";
        ExcelUtils.writeExcel(response, filename, enterprises, Enterprise.class);

    }

}
