package com.rzyc.fulongapi.controller;


import com.common.utils.*;
import com.common.utils.encryption.AesEncryptUtil;
import com.common.utils.Arith;
import com.common.utils.DateUtils;
import com.common.utils.TypeConversion;
import com.common.utils.httpClient.WebUtils;
import com.common.utils.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rzyc.fulongapi.bean.check.dto.DangerNumDto;
import com.rzyc.fulongapi.bean.fire.vo.FireControlTypeNum;
import com.rzyc.fulongapi.bean.map.ResidentDetailVo;
import com.rzyc.fulongapi.bean.map.ResidentTypeTotalVo;
import com.rzyc.fulongapi.bean.build.FloorNumInfo;
import com.rzyc.fulongapi.bean.check.DangerData;
import com.rzyc.fulongapi.bean.fire.dto.AlarmAddOrUpdateDto;
import com.rzyc.fulongapi.bean.fire.dto.AlarmRecordDto;
import com.rzyc.fulongapi.bean.fire.dto.DeleteAlarmDto;
import com.rzyc.fulongapi.bean.fire.vo.AlarmRecordVo;
import com.rzyc.fulongapi.bean.map.dto.ResidentDetailDto;
import com.rzyc.fulongapi.bean.map.dto.UnitDnagerNumDto;
import com.rzyc.fulongapi.bean.user.EpidemicInfo;
import com.rzyc.fulongapi.bean.user.PopulationInfo;
import com.rzyc.fulongapi.bean.user.PopulationType;
import com.rzyc.fulongapi.bean.user.PopulationTypeV1;
import com.rzyc.fulongapi.bean.user.dto.EpidemicPageDto;
import com.rzyc.fulongapi.bean.user.dto.ResidentPageDto;
import com.rzyc.fulongapi.enums.DangerState;
import com.rzyc.fulongapi.enums.DangerTypeEnums;
import com.rzyc.fulongapi.model.*;
import com.rzyc.fulongapi.model.statistic.DangerStatistic;

import io.swagger.annotations.*;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

@Api(tags = "大屏")
@RestController
@CrossOrigin("*")
@RequestMapping("map")
public class BigMapController extends BaseController {

    /**
     * 大屏隐患统计
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/mapDangersStatistic")
    @ApiOperation("大屏隐患统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "隐患类型", value = "dangerType", required = false, dataType = "string"),
            @ApiImplicitParam(name = "单元ID", value = "unitId", required = false, dataType = "string"),
    })
    public SingleResult<DangerStatistic> mapDangersStatistic(String dangerType, String unitId) throws Exception {
        SingleResult singleResult = new SingleResult();
        DangerStatistic dangerStatistic = checkDangerMapper.mapDangersStatistic(dangerType, unitId);
        if (null != dangerStatistic.getDangerTotal() && 0 != dangerStatistic.getDangerTotal()) {
            dangerStatistic.setRectifiedPercent(Arith.div(dangerStatistic.getRectifiedTotal(), dangerStatistic.getDangerTotal()) * 100);
            dangerStatistic.setNotRectifiedPercent(Arith.div(dangerStatistic.getNotRectifiedTotal(), dangerStatistic.getDangerTotal()) * 100);
            dangerStatistic.setRectifyingPercent(Arith.div(dangerStatistic.getRectifyingTotal(), dangerStatistic.getDangerTotal()) * 100);

            //使用Decimal处理小数问题
            BigDecimal a = new BigDecimal(dangerStatistic.getRectifiedPercent());
            dangerStatistic.setRectifiedPercent(a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            BigDecimal b = new BigDecimal(dangerStatistic.getNotRectifiedPercent());
            dangerStatistic.setNotRectifiedPercent(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

            BigDecimal c = new BigDecimal(dangerStatistic.getRectifyingPercent());
            dangerStatistic.setRectifyingPercent(c.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }


        singleResult.setData(dangerStatistic);
        return singleResult;
    }

    /**
     * 隐患类型
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/dangerTypes")
    @ApiOperation("隐患类型")
    public SingleResult dangerTypes(String unitId) throws Exception {
        SingleResult singleResult = new SingleResult();
        List<DangerType> dangerTypes = dangerTypeMapper.selectDangerTypes(unitId);
        singleResult.setData(dangerTypes);
        return singleResult;
    }

    /**
     * 根据隐患类型查询对应隐患
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/selectDanger")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "起始时间", value = "startTime", required = false, dataType = "string"),
            @ApiImplicitParam(name = "结束时间", value = "endTime", required = false, dataType = "string"),
            @ApiImplicitParam(name = "隐患类型", value = "dangerType", required = false, dataType = "string"),
            @ApiImplicitParam(name = "隐患等级", value = "normalOrImportant", required = false, dataType = "string"),
            @ApiImplicitParam(name = "建筑体ID", value = "buildingId", required = false, dataType = "string"),
            @ApiImplicitParam(name = "单元ID", value = "unitId", required = false, dataType = "string"),
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
    })
    @ApiOperation("根据隐患类型查询对应隐患")
    public SingleResult selectDanger(String startTime, String endTime, String dangerType,
                                     String normalOrImportant, String buildingId, String unitId,
                                     Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);

        List<CheckDanger> checkDangers = checkDangerMapper.selectDanger(startTime, endTime, dangerType, normalOrImportant, buildingId, unitId, page, pageSize);
        long count = checkDangerMapper.selectDangerTotal(startTime, endTime, dangerType, normalOrImportant, buildingId, unitId);
        singleResult.setData(checkDangers);
        singleResult.setCount(count);
        return singleResult;
    }


    /**
     * 分组栋查询所有隐患并且绑定建筑信息(地图上建筑)
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/selectDangerAndBuilding")
    @ApiOperation("分组栋查询所有隐患并且绑定建筑信息")
    public SingleResult selectDangerAndBuilding() throws Exception {
        SingleResult singleResult = new SingleResult();
        List<Building> buildings = buildingMapper.selectDangerAndBuilding(DangerTypeEnums.FIRE.getType());
        singleResult.setData(buildings);
        return singleResult;
    }


    /**
     * 查询楼栋信息
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/selectBuildingInfo")
    @ApiOperation("查询楼栋信息")
    public SingleResult<Building> selectBuildingInfo(String buildingId) throws Exception {
        SingleResult<Building> singleResult = new SingleResult<>();
        Building building = buildingMapper.selectBuildingInfo(buildingId);
        Integer household = buildFloorMapper.selectHouseHold(buildingId);
        building.setHouseholdSize(household);
        singleResult.setData(building);
        return singleResult;
    }


    /**
     * 查询楼栋单元信息列表
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/selectBuildingUnitsInfo")
    @ApiOperation("查询楼栋单元信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "楼栋ID", value = "buildingId", required = true, dataType = "string"),
    })
    public SingleResult selectBuildingUnitsInfo(String buildingId, Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        List<BuildUnit> buildUnits = buildUnitMapper.selectBuildingUnitsInfo(buildingId, page, pageSize);
        long total = buildUnitMapper.selectBuildingUnitsInfoTotal(buildingId);
        singleResult.setData(buildUnits);
        singleResult.setCount(total);
        return singleResult;
    }

    /**
     * 查询楼栋单元详细
     *
     * @return
     * @throrws Exception
     */
    @GetMapping("/selectBuildingUnitDetail")
    @ApiOperation("查询楼栋单元详细")
    public SingleResult selectBuildingUnitDetail(String unitId) throws Exception {
        SingleResult singleResult = new SingleResult();
        BuildUnit buildUnit = buildUnitMapper.selectBuildingUnitDetail(unitId);
        singleResult.setData(buildUnit);
        return singleResult;
    }


    /**
     * 查询楼栋单元楼层信息
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/selectBuildingUnitFloorTable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "单元ID", value = "unitId", required = true, dataType = "string"),
    })
    @ApiOperation("查询楼栋单元楼层信息")
    public SingleResult selectBuildingUnitFloorTable(String unitId, Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        List<BuildFloor> floors = buildFloorMapper.selectBuildingUnitFloorTable(unitId, page, pageSize);
        long count = buildFloorMapper.selectBuildingUnitFloorTableCount(unitId);
        singleResult.setData(floors);
        singleResult.setCount(count);
        return singleResult;
    }


    /**
     * 消防站
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/fireStationList")
    @ApiOperation("消防站信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "condition", dataType = "string")
    })
    public SingleResult fireStationList(String condition) throws Exception {
        SingleResult singleResult = new SingleResult();
        String conditions = TypeConversion.getCondition(condition);
        List<FireStation> fireStations = fireStationMapper.fireStations(null, null, conditions);

        singleResult.setData(fireStations);

        return singleResult;
    }


    /**
     * 消防应急器材分类
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/equipmentCategory")
    @ApiOperation("器材分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
    })
    public SingleResult equipmentCategory(Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        List<FireEquipmentCategory> fireEquipmentCategories = fireEquipmentCategoryMapper.equipmentCategory(page, pageSize);
        Integer count = fireEquipmentCategoryMapper.equipmentCategoryCount();
        singleResult.setData(fireEquipmentCategories);
        singleResult.setCount(count);
        return singleResult;
    }


    /**
     * 消防应急器材信息
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/equipmentTable")
    @ApiOperation("消防应急器材信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "器材分类", value = "feCategoryId", required = true, dataType = "string"),
    })
    public SingleResult equipmentTable(String feCategoryId, Integer page, Integer pageSize) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        List<FireEquipment> fireEquipments = fireEquipmentMapper.equipmentTable(feCategoryId, page, pageSize);
        Integer count = fireEquipmentMapper.equipmentTableCount(feCategoryId);
        singleResult.setData(fireEquipments);
        singleResult.setCount(count);
        return singleResult;
    }


    /**
     * 修改大屏单元隐藏状态
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/updateUnitHiddenState")
    @ApiOperation("修改大屏单元隐藏状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buildingId", value = "楼栋ID", required = true, dataType = "string"),
            @ApiImplicitParam(name = "state", value = "1.显示 2.不显示", required = true, dataType = "int"),
            @ApiImplicitParam(name = "type", value = "1.单元显示 2.消防隐患显示", required = true, dataType = "int"),
    })
    public SingleResult updateUnitHiddenState(String buildingId, Integer state,Integer type) throws Exception {
        SingleResult singleResult = new SingleResult();
        if(1 == type){
            buildingMapper.updateUnitHiddenState(buildingId, state);
        }else{
            buildingMapper.updateFireState(buildingId, state);
        }
        return singleResult;
    }


    /**
     * 特殊建筑体列表
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/specialBuildingList")
    @ApiOperation("特殊建筑体列表")
    public SingleResult<SpecialBuilding> specialBuildingList() throws Exception {
        SingleResult singleResult = new SingleResult();
        List<SpecialBuilding> list = specialBuildingMapper.specialBuildingList();
        singleResult.setData(list);
        return singleResult;
    }

    /**
     * 特殊建筑体详细
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/specialBuildingDetail")
    @ApiOperation("特殊建筑体详细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "特殊建筑体ID", value = "sbId", required = true, dataType = "string"),
    })
    public SingleResult<SpecialBuilding> specialBuildingDetail(String sbId) throws Exception {
        SingleResult singleResult = new SingleResult();
        SpecialBuilding specialBuilding = specialBuildingMapper.specialBuildingDetail(sbId);
        singleResult.setData(specialBuilding);
        return singleResult;
    }

    /**
     * 单元附属户主
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/subsidiaryManager")
    @ApiOperation("单元附属户主")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "单元ID", value = "unitId", required = true, dataType = "string"),
    })
    @ResponseBody
    public SingleResult<BuildUnitHouseholder> subsidiaryManager(String unitId) throws Exception {
        SingleResult singleResult = new SingleResult();
        List<BuildUnitHouseholder> buildUnitHouseholder = buildUnitHouseholderMapper.selectByUnitId(unitId);
        singleResult.setData(buildUnitHouseholder);
        return singleResult;
    }

    /**
     * 燃气点数和已整改户数
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/floorNumInfo")
    @ApiOperation("燃气点数和已整改户数")
    public SingleResult<FloorNumInfo> floorNumInfo() throws Exception {
        SingleResult<FloorNumInfo> result = new SingleResult<>();
        FloorNumInfo floorNumInfo = new FloorNumInfo();
        /*已整改户数*/
        Integer houseNum = buildFloorMapper.sumByHouseholdSize();
        /*燃气点数*/
        Integer usertableNum = buildFloorMapper.sumByUsertableNum();

        floorNumInfo.setHouseNum(houseNum);
        floorNumInfo.setUsertableNum(usertableNum);
        result.setData(floorNumInfo);
        return result;
    }

    /**
     * 疫情数据
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/epidemicInfo")
    @ApiOperation("疫情数据")
    public SingleResult<EpidemicInfo> epidemicInfo() throws Exception {
        SingleResult<EpidemicInfo> result = new SingleResult<>();
        EpidemicInfo epidemicInfo = new EpidemicInfo();

        //最新上传时间
        Epidemic epidemic = epidemicMapper.newestEpidemic();
        if (null != epidemic) {
            epidemicInfo.setChangeTime(DateUtils.parseDate2String(epidemic.getModifyTime(), "yyyy-MM-dd HH:mm:ss"));
        }

        //确诊数
        Integer diagnosisNum = epidemicMapper.countByIsolateState("确诊");
        epidemicInfo.setDiagnosisNum(diagnosisNum);

        //隔离监测数
        Integer quarantineNum = epidemicMapper.countAll();
        epidemicInfo.setQuarantineNum(quarantineNum);

        //隔离完成数
        Integer quarantinedNum = 0;
        //历史隔离人员身份证号
        List<String> historyCardIds = epidemicMapper.historyCardId();
        //当前隔离人员身份证号
        List<String> todayCardIds = epidemicMapper.todayCardId();
        historyCardIds.removeAll(todayCardIds);
        quarantinedNum = historyCardIds.size();
        epidemicInfo.setQuarantinedNum(quarantinedNum);

        //前一天确诊数
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DAY_OF_MONTH, -1);
        String time = DateUtils.parseCalendar2String(nowTime, "yyyy-MM-dd");
        String startTime = time + " 00:00:00";
        String endTime = time + " 23:59:59";
        List<String> yestodayCardIds = epidemicMapper.yestodayCardId(startTime, endTime);

        //隔离变化数
        Integer isolationNum = quarantineNum - yestodayCardIds.size();
        if(isolationNum > 0){
            epidemicInfo.setIsolationNum("+"+isolationNum);
        }else{
            epidemicInfo.setIsolationNum(""+isolationNum);
        }


        //隔离变化数
        Integer isolationedNum = yestodayCardIds.size() - quarantineNum;
        if(isolationedNum < 0){
            isolationedNum = 0;
        }
        epidemicInfo.setIsolationedNum("+"+isolationedNum);

        result.setData(epidemicInfo);
        return result;
    }

    /**
     * 疫情数据分页
     *
     * @param epidemicPageDto
     * @return
     * @throws Exception
     */
    @GetMapping("/epidemicPage")
    @ApiOperation("疫情数据分页")
    @ResponseBody
    public SingleResult<Pager<Epidemic>> epidemicPage(@Valid EpidemicPageDto epidemicPageDto) throws Exception {
        SingleResult<Pager<Epidemic>> result = new SingleResult<>();
        String condition = TypeConversion.getCondition(epidemicPageDto.getCondition());
        Pager<Epidemic> pager = new Pager<>();
        PageHelper.startPage(epidemicPageDto.getPage(), epidemicPageDto.getPageSize());
        Page<Epidemic> page = (Page<Epidemic>) epidemicMapper.epidemicList(condition);
        getDatePage(pager, page);
        handleEpidemic(pager.getRows());
        result.setData(pager);
        return result;
    }


    /**
     * 隐患统计
     *
     * @param dangerType
     * @return
     * @throws Exception
     */
    @GetMapping("/dangerInfo")
    @ApiOperation("隐患统计")
    @ApiImplicitParam(name = "dangerType", value = "隐患类型")
    public SingleResult<DangerData> dangerInfo(String dangerType,String unitId) throws Exception {
        SingleResult<DangerData> result = new SingleResult<>();

        DangerData dangerData = new DangerData();

        List<String> dangers = new ArrayList<>();
        if(StringUtils.isNotBlank(dangerType)){
            dangers.add(dangerType);
        }else{
            dangers.add("b5b7da2a-1f2e-4fe9-9828-3813956dc81c");//链接软管
            dangers.add("a769898e-eac2-44a9-b53c-8141e217383e");//链接软管
            dangers.add("95c924f2-995e-4827-9aa2-b0d684ae8333");//燃气燃烧灶具
        }

        //总数
        Integer totalNum = checkDangerMapper.countByTypeId(dangers, null,unitId);
        //已整改
        Integer rectifyNum = checkDangerMapper.countByTypeId(dangers, DangerState.RECTIFYED.getState(),unitId);
        //未整改
        Integer notRectifyNum = checkDangerMapper.countByTypeId(dangers, DangerState.NOT_RECTIFY.getState(),unitId);
        //整改中
        Integer rectifyingNum = totalNum - rectifyNum - notRectifyNum;

        //整改完成率
        Integer rectifyRate = 0;
        if (totalNum > 0) {
            Double rate = Arith.div(rectifyNum, totalNum);
            rate = rate * 100;
            rectifyRate = rate.intValue();
        }

        dangerData.setTotalNum(totalNum);
        dangerData.setRectifyNum(rectifyNum);
        dangerData.setNotRectifyNum(notRectifyNum);
        dangerData.setRectifyingNum(rectifyingNum);
        dangerData.setRectifyRate(rectifyRate);
        ;

        //其他三类隐患
        List<DangerType> dangerTypes = dangerTypeMapper.dangerNumInfo(unitId);
        if (null != dangerTypes && dangerTypes.size() > 0) {
            for (DangerType danger : dangerTypes) {
                danger.setNotRectifyNum(danger.getTotalNum() - danger.getRectifyNum() - danger.getRectifyingNum());
            }
        }
        dangerData.setDangerNumInfo(dangerTypes);

        result.setData(dangerData);

        return result;
    }


    /**
     * 小区住房信息
     *
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("/housingInformation")
    @ApiOperation("小区住房信息")
    public SingleResult<List<ResidentTypeTotalVo>> housingInformation() throws Exception {
        SingleResult<List<ResidentTypeTotalVo>> result = new SingleResult<>();
        List<ResidentTypeTotalVo> residentTypeTotalVos = buildingResidentMapper.selectResidentTypeTotal();
        long count = buildingResidentMapper.selectResidentTotal();

        result.setData(residentTypeTotalVos);
        result.setCount(count);

        return result;
    }


    /**
     * 小区住房信息分页列表
     *
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("/housingInfoList")
    @ApiOperation("小区住房信息分页列表")
    public SingleResult<Pager<ResidentDetailVo>> housingInfoList(@Valid ResidentDetailDto residentDetailDto) throws Exception {
        SingleResult<Pager<ResidentDetailVo>> result = new SingleResult<>();
        String condition = TypeConversion.getCondition(residentDetailDto.getCondition());
        Pager<ResidentDetailVo> pager = new Pager<>();
        PageHelper.startPage(residentDetailDto.getPage(), residentDetailDto.getPageSize());
        Page<ResidentDetailVo> page = (Page<ResidentDetailVo>) buildingResidentMapper.selectHousingInfoList(condition);
        getDatePage(pager, page);
        result.setData(pager);
        return result;
    }

    /**
     * alarmRecord
     * @param alarmRecordDto
     * @return
     */
    @GetMapping("/alarmRecord")
    @ApiOperation("消防出警信息分页")
    public SingleResult<Pager<AlarmRecordVo>> alarmRecord(AlarmRecordDto alarmRecordDto){
        PageHelper.startPage(alarmRecordDto.getPage(), alarmRecordDto.getPageSize());
        Page<AlarmRecordVo> alarmRecordVos = (Page<AlarmRecordVo>) fireRecordMapper.alarmRecord(alarmRecordDto);
        Pager<AlarmRecordVo> pager = new Pager<>();
        pager.setRows(alarmRecordVos.getResult());
        pager.setTotal(alarmRecordVos.getTotal());
        SingleResult<Pager<AlarmRecordVo>> singleResult = new SingleResult<>();
        singleResult.setData(pager);
        return singleResult;
    }

    /**
     * alarmAddOrUpdate
     * @param alarmAddOrUpdateDto
     * @return
     * @throws Exception
     */
    @PostMapping("/alarmAddOrUpdate")
    @ApiOperation("出警信息新增或修改")
    public SingleResult<String> alarmAddOrUpdate(@RequestBody AlarmAddOrUpdateDto alarmAddOrUpdateDto) throws Exception {

        FireRecord  fireRecord = fireRecordMapper.selectById(alarmAddOrUpdateDto.getRecordId());

        DateConverter dateConverter = new DateConverter();
        //设置日期格式
        dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"});
        //注册格式
        ConvertUtils.register(dateConverter, Date.class);

        if (fireRecord==null){
                fireRecord = new FireRecord();
                BeanUtils.copyProperties(alarmAddOrUpdateDto,fireRecord);
                fireRecord.setAlarmTime(DateUtils.parseString2Date(alarmAddOrUpdateDto.getAlarmTime(),"yyyy-MM-dd HH:mm:ss"));
                fireRecord.setEndTime(DateUtils.parseString2Date(alarmAddOrUpdateDto.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
                fireRecord.setCreateBy(getUserId());
                fireRecord.setCreateTime(new Date());
                fireRecordMapper.insert(fireRecord);
            }else{
                BeanUtils.copyProperties(alarmAddOrUpdateDto,fireRecord);
                fireRecord.setAlarmTime(DateUtils.parseString2Date(alarmAddOrUpdateDto.getAlarmTime(),"yyyy-MM-dd HH:mm:ss"));
                fireRecord.setEndTime(DateUtils.parseString2Date(alarmAddOrUpdateDto.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
                fireRecord.setModifyBy(getUserId());
                fireRecord.setModifyTime(new Date());
                fireRecordMapper.updateById(fireRecord);
            }
        return new SingleResult<>();
    }

    /**
     * 删除出警信息
     * @param deleteAlarmDto
     * @return
     */
    @PostMapping("/deleteAlarm")
    @ApiOperation("删除出警信息")
    public SingleResult<String> deleteAlarm(@RequestBody DeleteAlarmDto deleteAlarmDto){
        fireRecordMapper.deleteBatchIds(deleteAlarmDto.getIds());
        return new SingleResult<>();
    }

    /**
     * 人口统计信息
     * @return
     * @throws Exception
     */
    @GetMapping("/populationInfo")
    @ApiOperation("人口统计信息")
    public SingleResult<PopulationInfo> populationInfo()throws Exception{
        SingleResult<PopulationInfo> result = new SingleResult<>();
        PopulationInfo populationInfo = new PopulationInfo();

        Integer total = 0;
        Integer localNum = 0;
        Integer outTownNum = 0;
        Integer specialNum = 72;

        List<BuildingResident> residents = buildingResidentMapper.findAll();
        if(null != residents && residents.size() > 0){
            total = residents.size();
            for (BuildingResident resident : residents ){
                String residentIdentityCard = resident.getResidentIdentityCard();
                if(StringUtils.isNotBlank(residentIdentityCard)){
                    try {
                        residentIdentityCard = AesEncryptUtil.desEncrypt(residentIdentityCard);
                    }catch (Exception e){
                        residentIdentityCard = residentIdentityCard;
                    }
                    if(residentIdentityCard.contains("5101")){
                        localNum++;
                    }
                }

            }
        }


        total = 16280;
        outTownNum = 11078;

        localNum = total - outTownNum;

        populationInfo.setTotal(total);
        populationInfo.setLocalNum(localNum);
        populationInfo.setOutTownNum(outTownNum);
        populationInfo.setSpecialNum(specialNum);
        result.setData(populationInfo);
        return result;
    }

    /**
     * 人口类型统计信息
     * @return
     * @throws Exception
     */
    @GetMapping("/populationType")
    @ApiOperation("人口类型统计信息")
    public SingleResult<PopulationType> populationType()throws Exception{
        SingleResult<PopulationType> result = new SingleResult<>();
        PopulationType populationType = new PopulationType();

        Integer total = buildUnitMapper.sumFloor();
        //自住
        Integer selfUseNum = 0;
        //出租
        Integer leaseNum = 0;
        //商用
        Integer commercialNum = 0;
        commercialNum = enterpriseMapper.countAll();

        //房屋类型
        List<Integer> residentTypes = new ArrayList<>();
        residentTypes.add(1);//自住
        residentTypes.add(2);//居住

        selfUseNum = buildingResidentMapper.countByResidentType(residentTypes);

        leaseNum = total - selfUseNum - commercialNum;

        selfUseNum = 856;
        leaseNum = 5561;
        commercialNum = 631;

        total = selfUseNum + leaseNum + commercialNum;

        populationType.setCommercialNum(commercialNum);
        populationType.setSelfUseNum(selfUseNum);
        populationType.setLeaseNum(leaseNum);

        result.setData(populationType);
        return result;
    }


    /**
     * 住户分页
     *
     * @param
     * @return
     * @throws Exception
     */
    @GetMapping("/residentPage")
    @ApiOperation("住户分页")
    public SingleResult<Pager<BuildingResident>> residentPage(@Valid ResidentPageDto residentPageDto) throws Exception {
        SingleResult<Pager<BuildingResident>> result = new SingleResult<>();
        String condition = TypeConversion.getCondition(residentPageDto.getCondition());
        Pager<BuildingResident> pager = new Pager<>();
        PageHelper.startPage(residentPageDto.getPage(), residentPageDto.getPageSize());
        Page<BuildingResident> page = (Page<BuildingResident>) buildingResidentMapper.residentList(condition);
        getDatePage(pager, page);
        for (BuildingResident resident : pager.getRows()){
            resident.setResidentIdentityCard(idNumberDesEncrypt(resident.getResidentIdentityCard()));
            resident.setResidentPhone(StringUtils.replaceStr(resident.getResidentPhone(),4,8));
        }
        result.setData(pager);
        return result;
    }

    /**
     * 消防隐患数量统计
     * @return
     * @throws Exception
     */
    @GetMapping("/fireDangerInfo")
    @ApiOperation("消防隐患数量统计")
    public SingleResult<DangerData> fireDangerInfo(String unitId)throws Exception{
        SingleResult<DangerData> result = new SingleResult<>();
        List<String> typeIds = new ArrayList<>();
        typeIds.add("b9cc10fd-22d6-4880-a8ee-143654471e03");
        typeIds.add("s769898e-eac2-44a9-b53c-8141e2173231");
        DangerData dangerData = checkDangerMapper.fireDangerInfo(typeIds,unitId);


        //整改完成率
        Integer rectifyRate = 0;
        if (dangerData.getTotalNum() > 0) {
            Double rate = Arith.div(dangerData.getRectifyNum(), dangerData.getTotalNum());
            rate = rate * 100;
            rectifyRate = rate.intValue();
        }
        dangerData.setRectifyRate(rectifyRate);
        result.setData(dangerData);
        return result;
    }

    @GetMapping("/epidemicXlInfo")
    @ApiOperation("疫情接口")
    public SingleResult<String> epidemicXlInfo()throws Exception{
        SingleResult<String> result = new SingleResult<>();
        String jsonStr = WebUtils.doGet("https://interface.sina.cn//news/wap/fymap2020_data.d.json",new HashMap<>(),new HashMap<>());
        result.setData(jsonStr);
        return result;
    }

    @GetMapping("/unitLevel/{unitId}")
    @ApiOperation("房屋鉴定报告")
    public SingleResult<BuildUnitLevel> unitLevel(@PathVariable String unitId)throws Exception{
        SingleResult<BuildUnitLevel> result = new SingleResult<>();
        BuildUnitLevel buildUnitLevel = buildUnitLevelMapper.findByUnitId(unitId);
        result.setData(buildUnitLevel);
        return result;
    }

    /**
     * 社区消防隐患类型统计
     * @version v1.0
     * @author dong
     * @date 2023/3/28 16:16
     */
    @GetMapping("/fireControlTypeNum")
    @ApiOperation("社区消防隐患类型统计")
    public MultiResult<FireControlTypeNum> fireControlTypeNum()throws Exception{
        MultiResult<FireControlTypeNum> result = new MultiResult<>();
        List<FireControlTypeNum> typeNums = checkDangerMapper.fireControlTypeNum(DangerTypeEnums.FIRE.getType());
        result.setData(typeNums);
        return result;
    }

    /**
     * 消防隐患整改统计
     * @version v1.0
     * @author dong
     * @date 2023/3/28 16:53
     */
    @GetMapping("/fireControlDangerNum")
    @ApiOperation("消防隐患整改统计")
    public SingleResult<DangerData> fireControlDangerNum(@Valid DangerNumDto dangerNumDto)throws Exception{
        SingleResult<DangerData> result = new SingleResult<>();

        DangerData dangerData = new DangerData();


        //隐患总数
        Integer totalNum = 0;
        //已整改隐患
        Integer rectifyNum = checkDangerMapper.countByType(dangerNumDto.getDangerType(),DangerState.RECTIFYED.getState(),dangerNumDto.getUnitId(),dangerNumDto.getDangerTypeId());
        //不能整改隐患
        Integer unableRectify = checkDangerMapper.countByType(dangerNumDto.getDangerType(),DangerState.UNABLE_RECTIFY.getState(),dangerNumDto.getUnitId(),dangerNumDto.getDangerTypeId());
        //整改中
        Integer rectifyingNum = checkDangerMapper.countByType(dangerNumDto.getDangerType(),DangerState.RECTIFYING.getState(),dangerNumDto.getUnitId(),dangerNumDto.getDangerTypeId());
        //未整改数
        Integer notRectifyNum = checkDangerMapper.countByType(dangerNumDto.getDangerType(),DangerState.NOT_RECTIFY.getState(),dangerNumDto.getUnitId(),dangerNumDto.getDangerTypeId());
        totalNum = rectifyNum + unableRectify + rectifyingNum + notRectifyNum;

        dangerData.setTotalNum(totalNum);
        dangerData.setRectifyNum(rectifyNum);
        dangerData.setUnableRectify(unableRectify);
        dangerData.setRectifyingNum(rectifyingNum);
        dangerData.setNotRectifyNum(notRectifyNum);

        //整改完成率
        Integer rectifyRate = 0;
        if(totalNum>0){
            Double rate = Arith.div(rectifyNum,totalNum);
            rectifyRate = rate.intValue();
            rectifyRate = rectifyRate * 100;
        }
        dangerData.setRectifyRate(rectifyRate);

        result.setData(dangerData);
        return result;
    }

    /**
     * 单元隐患数量列表
     * @version v1.0
     * @author dong
     * @date 2023/3/29 19:19
     */
    @GetMapping("/unitDnagerNum")
    @ApiOperation("单元隐患数量列表")
    public MultiResult<BuildUnit> unitDnagerNum(@Valid UnitDnagerNumDto unitDnagerNumDto)throws Exception{
        MultiResult<BuildUnit> result = new MultiResult<>();
        List<BuildUnit> buildUnits = buildUnitMapper.unitDangerNum(unitDnagerNumDto.getDangerType());
        result.setData(buildUnits);
        return result;
    }

    /**
     * 单元隐患类型详情
     * @version v1.0
     * @author dong
     * @date 2023/3/29 19:34
     */
    @GetMapping("/unitDangerInfo/{unitId}")
    @ApiImplicitParam(name ="unitId",value = "单元id",required = true)
    @ApiOperation("单元隐患类型详情")
    public SingleResult<BuildUnit> unitDangerInfo(@PathVariable String unitId)throws Exception{
        SingleResult<BuildUnit> result = new SingleResult<>();

        BuildUnit buildUnit = buildUnitMapper.selectById(unitId);
        if(null != buildUnit){

            //楼栋信息
            Building building = buildingMapper.selectById(buildUnit.getBuildingId());
            if(null != building){
                buildUnit.setBuildName(building.getBuildName());
            }

            //隐患类型
            String dangerInfo = "";
            List<DangerType> dangerTypes = dangerTypeMapper.unitDnagerInfo(unitId,DangerTypeEnums.FIRE.getType());
            if(null != dangerTypes && dangerTypes.size() > 0){
                for (DangerType dangerType : dangerTypes){
                    if(StringUtils.isNotBlank(dangerInfo)) {
                        dangerInfo = dangerInfo + "," + dangerType.getDangerTypeName();
                    }else{
                        dangerInfo = dangerType.getDangerTypeName();
                    }
                }
            }
            buildUnit.setDangerInfo(dangerInfo);

        }
        result.setData(buildUnit);
        return result;
    }

    /**
     * 按照类型查询隐患类型
     * @version v1.0
     * @author dong
     * @date 2023/4/2 16:34
     */
    @GetMapping("/getDangerTypes")
    @ApiImplicitParam(name ="dangetType",value = "隐患类型 1.燃气 3.消防",required = true)
    @ApiOperation("按照类型查询隐患类型")
    public MultiResult<DangerType> getDangerTypes(Integer dangetType)throws Exception{
        MultiResult<DangerType> result = new MultiResult<>();
        List<DangerType> dangerTypes = dangerTypeMapper.findByType(dangetType);
        result.setData(dangerTypes);
        return result;
    }

    @GetMapping("/test")
    @ApiOperation("测试")
    public MultiResult<String> test()throws Exception{
        MultiResult<String> result = new MultiResult<>();
        return result;
    }

}
