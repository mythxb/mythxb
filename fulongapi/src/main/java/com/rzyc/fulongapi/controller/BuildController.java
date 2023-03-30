package com.rzyc.fulongapi.controller;

import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.encryption.AesEncryptUtil;
import com.common.utils.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rzyc.fulongapi.bean.build.*;
import com.rzyc.fulongapi.enums.RiskLevel;
import com.rzyc.fulongapi.enums.UserType;
import com.rzyc.fulongapi.model.*;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Time;
import java.util.*;

@Api(tags = "建筑信息")
@CrossOrigin("*")
@RequestMapping("build")
@Controller
@Validated
public class BuildController extends BaseController {

    @ApiOperation(value = "单元楼层列表", notes = "单元楼层列表")
    @ApiImplicitParam(name = "unitId", value = "单元id", required = true)
    @GetMapping("/unitFloor/{unitId}")
    @ResponseBody
    public MultiResult<BuildFloor> unitFloor(@PathVariable String unitId) throws Exception {
        MultiResult<BuildFloor> result = new MultiResult<>();
        List<BuildFloor> floors = buildFloorMapper.findByUnitId(unitId);
        result.setData(floors);
        return result;
    }

    @ApiOperation(value = "楼栋分页", notes = "楼栋分页")
    @GetMapping("/bulidPage")
    @ResponseBody
    public SingleResult<Pager<Building>> bulidPage(@Valid BulidPageDto bulidPageDto) throws Exception {
        SingleResult<Pager<Building>> result = new SingleResult<>();
        String condition = TypeConversion.getCondition(bulidPageDto.getCondition());

        //楼栋id
        List<String> buildIds = new ArrayList<>();

        //判断企业类型
        if (StringUtils.isNotBlank(bulidPageDto.getUserId())) {
            SysUser sysUser = sysUserMapper.selectById(bulidPageDto.getUserId());
            if (null != sysUser) {
                if (UserType.BUILD.getType() == sysUser.getUserType()) {
                    buildIds = getUserTargetId(sysUser.getUserId(), sysUser.getUserType());
                } else if (UserType.UNIT.getType() == sysUser.getUserType()) {
                    buildIds.add(RandomNumber.getUUid());
                }
            }
        }

        Pager<Building> pager = new Pager<>();
        PageHelper.startPage(bulidPageDto.getPage(), bulidPageDto.getPageSize());
        Page<Building> page = (Page<Building>) buildingMapper.buildingList(condition, bulidPageDto.getDirection(), buildIds);

        for (Building building : page) {
            //将号码中4位隐藏
            if (building.getBuildingManagerContact() != null && building.getBuildingManagerContact().length() > 3 && building.getBuildingManagerContact().length() <= 11) {
                building.setBuildingManagerContact(new StringBuilder(building.getBuildingManagerContact()).replace(3, 7, "****").toString());
            }
        }

        getDatePage(pager, page);
        result.setData(pager);
        return result;
    }

    @ApiOperation(value = "楼栋详情", notes = "楼栋详情")
    @GetMapping("/bulidDetail/{buildId}")
    @ResponseBody
    public SingleResult<Building> bulidDetail(@PathVariable String buildId) throws Exception {
        SingleResult<Building> result = new SingleResult<>();
        Building building = buildingMapper.selectById(buildId);

        //楼栋下单元列表
        if (null != building) {
            List<BuildUnit> units = buildUnitMapper.unitList("%%", building.getBuildId(), "");
            if (null != units) {
                building.setUnits(units);
            }
        }

        result.setData(building);
        return result;
    }

    @ApiOperation(value = "楼栋修改", notes = "楼栋修改")
    @PostMapping("/bulidChange")
    @ResponseBody
    public SingleResult<String> bulidChange(@Valid @RequestBody BulidChangeDto bulidChangeDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();

        String userId = getUserId();

        Building building = new Building();
        BeanUtils.copyProperties(building, bulidChangeDto);

        building.setModifyBy(userId);
        building.setModifyTime(new Date());

        buildingMapper.updateById(building);
        return result;
    }

    @ApiOperation(value = "单元新增或修改", notes = "单元新增或修改")
    @PostMapping("/unitChange")
    @ResponseBody
    public SingleResult<BuildUnit> unitChange(@Valid @RequestBody UnitChangeDto unitChangeDto) throws Exception {
        SingleResult<BuildUnit> result = new SingleResult<>();
        BuildUnit buildUnit = new BuildUnit();
        BeanUtils.copyProperties(buildUnit, unitChangeDto);

        String userId = getUserId();

        buildUnit.setModifyBy(userId);
        buildUnit.setModifyTime(new Date());

        if (StringUtils.isNotBlank(unitChangeDto.getUnitId())) {
            buildUnitMapper.updateById(buildUnit);
        } else {
            buildUnit.setUnitId(RandomNumber.getUUid());
            buildUnit.setCreateBy(userId);
            buildUnit.setCreateTime(new Date());
            buildUnit.setRiskLevel(RiskLevel.BLUE.getLevel());

            //单元二维码
            Map<String, String> qrcodeMap = new HashMap<>();
            qrcodeMap.put("type", "unit");
            qrcodeMap.put("targetId", buildUnit.getUnitId());
            String qecode = createQrCode(qrcodeMap);
            buildUnit.setQrCode(qecode);

            buildUnitMapper.insert(buildUnit);
            result.setData(buildUnit);
        }

/*        //先删除管理员
        buildUnitHouseholderMapper.delByUnitId(buildUnit.getUnitId());

        //如果管理员不为空 则新增管理员
        if (null != unitChangeDto.getHouseholders() && unitChangeDto.getHouseholders().size() > 0) {
            for (HouseholderDto householderDto : unitChangeDto.getHouseholders()) {
                BuildUnitHouseholder householder = new BuildUnitHouseholder();
                BeanUtils.copyProperties(householder, householderDto);
                householder.setCreateTime(new Date());
                householder.setHouseholderId(RandomNumber.getUUid());
                householder.setBuildingUnitId(buildUnit.getUnitId());
                buildUnitHouseholderMapper.insert(householder);
            }
        }*/
        return result;
    }

    @ApiOperation(value = "单元详情", notes = "单元详情")
    @ApiImplicitParam(name = "unitId", value = "单元id", required = true)
    @GetMapping("/unitDetail/{unitId}")
    @ResponseBody
    public SingleResult<BuildUnit> unitDetail(@PathVariable String unitId) throws Exception {
        SingleResult<BuildUnit> result = new SingleResult<>();
        BuildUnit buildUnit = buildUnitMapper.selectById(unitId);
        if (null != buildUnit) {
            //其他管理员
            List<BuildUnitHouseholder> householders = buildUnitHouseholderMapper.findByUnitId(unitId);
            if (null != householders && householders.size() > 0) {
                buildUnit.setHouseholders(householders);
            }
        }
        result.setData(buildUnit);
        return result;
    }

    @ApiOperation(value = "楼栋列表", notes = "楼栋列表")
    @ApiImplicitParam(name = "direction", value = "分类 1.西街 2.东街")
    @GetMapping("/buildList")
    @ResponseBody
    public MultiResult<Building> buildList(Integer direction) throws Exception {
        MultiResult<Building> result = new MultiResult<>();
        List<Building> buildings = buildingMapper.buildingList("%%", direction, null);
        result.setData(buildings);
        return result;
    }

    @ApiOperation(value = "楼栋下单元列表", notes = "楼栋下单元列表")
    @ApiImplicitParam(name = "buildId", value = "楼栋id", required = true)
    @GetMapping("/unitList/{buildId}")
    @ResponseBody
    public MultiResult<BuildUnit> unitList(@PathVariable String buildId) throws Exception {
        MultiResult<BuildUnit> result = new MultiResult<>();
        List<BuildUnit> units = buildUnitMapper.unitList("%%", buildId, "");
        result.setData(units);
        return result;
    }

    @ApiOperation(value = "单元楼层新增或修改", notes = "单元楼层新增或修改")
    @PostMapping("/unitFloorChange")
    @ResponseBody
    public SingleResult<BuildFloor> unitFloorChange(@Valid @RequestBody UnitFloorChangeDto unitFloorChangeDto) throws Exception {
        SingleResult<BuildFloor> result = new SingleResult<>();

        BuildFloor floor = new BuildFloor();
        BeanUtils.copyProperties(floor, unitFloorChangeDto);

        String userId = getUserId();

        //单元信息
        BuildUnit unit = buildUnitMapper.selectById(floor.getUnitId());
        if (null != unit) {
            floor.setBuildingId(unit.getBuildingId());
            floor.setUnitNumber(unit.getUnitNumber());
        }

        floor.setModifyBy(userId);
        floor.setModifyTime(new Date());

        if (StringUtils.isNotBlank(floor.getFloorId())) {
            //修改
            buildFloorMapper.updateById(floor);
        } else {
            //新增
            floor.setFloorId(RandomNumber.getUUid());
            floor.setCreateBy(userId);
            floor.setCreateTime(new Date());
            floor.setRiskLevel(1);
            buildFloorMapper.insert(floor);
        }
        return result;
    }

    @ApiOperation(value = "企业分类", notes = "企业分类")
    @GetMapping("/entStoreType")
    @ResponseBody
    public MultiResult<StoreType> entStoreType() throws Exception {
        MultiResult<StoreType> result = new MultiResult<>();
        List<StoreType> storeTypes = storeTypeMapper.findAll();
        result.setData(storeTypes);
        return result;
    }

    @ApiOperation(value = "企业修改", notes = "企业修改")
    @PostMapping("/entChange")
    @ResponseBody
    public SingleResult<String> entChange(@Valid @RequestBody EntChangeDto entChangeDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();

        Enterprise enterprise = new Enterprise();
        System.out.println("entChangeDto对象：" + entChangeDto);
        BeanUtils.copyProperties(enterprise, entChangeDto);
        System.out.println("enterprise对象：" + enterprise);


        String userId = getUserId();

        enterprise.setModifyBy(userId);
        enterprise.setModifyTime(new Date());

        if (StringUtils.isNotBlank(enterprise.getEnterpriseId())) {
            enterpriseMapper.updateById(enterprise);
            //更新企业用户
            addEntUser(enterprise.getEnterpriseId());
        } else {
            enterprise.setEnterpriseId(RandomNumber.getUUid());
            enterprise.setCreateBy(userId);
            enterprise.setCreateTime(new Date());
            enterprise.setRiskLevel(RiskLevel.BLUE.getLevel());
            //企业二维码
            Map<String, String> qrcodeMap = new HashMap<>();
            qrcodeMap.put("type", "enterprise");
            qrcodeMap.put("targetId", enterprise.getEnterpriseId());
            String qecode = createQrCode(qrcodeMap);
            enterprise.setQrCode(qecode);
            enterpriseMapper.insert(enterprise);

            //更新企业用户
            addEntUser(enterprise.getEnterpriseId());
        }
        return result;
    }

    @ApiOperation(value = "消防站列表", notes = "消防站列表")
    @GetMapping("/fireStationList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "condition", value = "condition", dataType = "string"),
    })
    @ResponseBody
    public SingleResult<FireStation> fireStationList(Integer page, Integer pageSize, String condition) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        String conditions = TypeConversion.getCondition(condition);
        List<FireStation> fireStations = fireStationMapper.fireStations(page, pageSize, conditions);
        Integer count = fireStationMapper.fireStationsCount(conditions);
        singleResult.setData(fireStations);
        singleResult.setCount(count);
        return singleResult;
    }


    @ApiOperation(value = "新增消防站信息", notes = "新增消防站信息")
    @PostMapping("/addFireStation")
    @ResponseBody
    public SingleResult<FireStation> addFireStation(@Valid @RequestBody FireStation fireStation) throws Exception {
        SingleResult singleResult = new SingleResult();
        String userId = getUserId();
        int result = 0;
        if (null != fireStation) {
            fireStation.setFsId(RandomNumber.getUUid());
            fireStation.setCreateBy(userId);
            fireStation.setCreateTime(new Date());
            fireStation.setModifyBy(userId);
            fireStation.setModifyTime(new Date());
            result = fireStationMapper.insert(fireStation);
        }
        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }


    @ApiOperation(value = "修改消防站信息", notes = "修改消防站信息")
    @PostMapping("/editFireStation")
    @ResponseBody
    public SingleResult<FireStation> editFireStation(@Valid @RequestBody FireStation fireStation) throws Exception {
        SingleResult singleResult = new SingleResult();
        String userId = getUserId();
        int result = 0;
        if (null != fireStation) {
            fireStation.setModifyTime(new Date());
            fireStation.setModifyBy(userId);
            result = fireStationMapper.updateById(fireStation);
        }
        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    @ApiOperation(value = "消防物质分类", notes = "消防物质分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "condition", value = "condition", dataType = "string"),
    })
    @GetMapping("/fireStationResource")
    @ResponseBody
    public SingleResult<FireEquipmentCategory> fireStationResource(Integer page, Integer pageSize, String condition) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        String conditions = TypeConversion.getCondition(condition);
        List<FireEquipmentCategory> categories = fireEquipmentCategoryMapper.equipmentCategoryAndResource(page, pageSize, conditions);
        Integer count = fireEquipmentCategoryMapper.equipmentCategoryAndResourceCount(conditions);
        singleResult.setData(categories);
        singleResult.setCount(count);
        return singleResult;
    }

    @ApiOperation(value = "新增/修改消防物质分类", notes = "新增/修改消防物质分类")
    @PostMapping("/addOrEditFireEquipmentCategory")
    @ResponseBody
    public SingleResult<FireEquipmentCategory> addOrEditFireEquipmentCategory(@Valid @RequestBody FireEquipmentCategory fireEquipmentCategory) throws Exception {
        SingleResult singleResult = new SingleResult();
        String userId = getUserId();
        int result = 0;
        if (null != fireEquipmentCategory) {
            List<FireEquipmentCategory> cateName = fireEquipmentCategoryMapper.selectByName(fireEquipmentCategory.getFecName());
            if (cateName.size() == 0) {
                if (StringUtils.isBlank(fireEquipmentCategory.getFecId())) {
                    singleResult.setMessage("fecId不能为空");
                } else {
                    if (fireEquipmentCategoryMapper.selectById(fireEquipmentCategory.getFecId()) == null) {
                        fireEquipmentCategory.setFecName(fireEquipmentCategory.getFecName());
                        fireEquipmentCategory.setCreateBy(userId);
                        fireEquipmentCategory.setCreateTime(new Date());
                        result = fireEquipmentCategoryMapper.insert(fireEquipmentCategory);
                        singleResult.setData(fireEquipmentCategory);
                    } else {
                        fireEquipmentCategory.setFecName(fireEquipmentCategory.getFecName());
                        fireEquipmentCategory.setCreateBy(userId);
                        fireEquipmentCategory.setCreateTime(new Date());
                        result = fireEquipmentCategoryMapper.updateById(fireEquipmentCategory);
                    }
                }
            } else {
                singleResult.setMessage("名字重复！");
            }
        }
        return singleResult;
    }

    @ApiOperation(value = "新增/修改消防物", notes = "新增/修改消防物")
    @PostMapping("/addOrEditFireEquipment")
    @ResponseBody
    public SingleResult<FireEquipment> addOrEditFireEquipment(@Valid @RequestBody FireEquipment fireEquipment) throws
            Exception {
        System.out.println("fireEquipment：" + fireEquipment);
        SingleResult singleResult = new SingleResult();
        String userId = getUserId();
        if (null != fireEquipment) {
            FireEquipment fireEquipment1 = fireEquipmentMapper.selectById(fireEquipment.getFeId());

            if (fireEquipment1 == null) {
                if (fireEquipment.getFeId() == "" || fireEquipment.getFeId() == null) {
                    singleResult.setCode(Code.EX_PARAM.getCode());
                    singleResult.setMessage(Message.EX_PARAM);
                    return singleResult;
                } else {
                    fireEquipment.setCreateBy(userId);
                    fireEquipment.setCreateTime(new Date());
                    fireEquipmentMapper.insert(fireEquipment);
                }
            } else {
                fireEquipmentMapper.updateById(fireEquipment);
            }
        }
        return singleResult;
    }


    @ApiOperation(value = "特殊建筑体列表", notes = "特殊建筑体列表")
    @GetMapping("/specialBuildingTable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "condition", value = "condition", dataType = "string"),
    })
    @ResponseBody
    public SingleResult<FireEquipmentCategory> specialBuildingTable(Integer page, Integer pageSize, String condition) throws
            Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        String conditions = TypeConversion.getCondition(condition);
        List<SpecialBuilding> specialBuildings = specialBuildingMapper.specialBuildingTable(page, pageSize, conditions);
        Integer count = specialBuildingMapper.specialBuildingTableCount(conditions);
        singleResult.setData(specialBuildings);
        singleResult.setCount(count);
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
    @ResponseBody
    public SingleResult<SpecialBuilding> specialBuildingDetail(String sbId) throws Exception {
        SingleResult singleResult = new SingleResult();
        SpecialBuilding specialBuilding = specialBuildingMapper.specialBuildingDetail(sbId);
        singleResult.setData(specialBuilding);
        return singleResult;
    }

    @ApiOperation(value = "新增/修改特殊建筑体", notes = "新增/修改特殊建筑体")
    @PostMapping("/addOrEditSpecialBuilding")
    @ResponseBody
    public SingleResult<FireEquipment> addOrEditSpecialBuilding(@Valid @RequestBody SpecialBuilding specialBuilding) throws Exception {
        SingleResult singleResult = new SingleResult();
        String userId = getUserId();
        int result = 0;
        if (null != specialBuilding) {
            if (StringUtils.isBlank(specialBuilding.getSbId())) {
                specialBuilding.setSbId(RandomNumber.getUUid());
                specialBuilding.setCreateBy(userId);
                specialBuilding.setCreateTime(new Date());
                //二维码
                Map<String, String> qrcodeMap = new HashMap<>();
                qrcodeMap.put("type", "specialBuilding");
                qrcodeMap.put("targetId", specialBuilding.getSbId());
                String qecode = createQrCode(qrcodeMap);
                specialBuilding.setQrCode(qecode);

                result = specialBuildingMapper.insert(specialBuilding);

            } else {
                result = specialBuildingMapper.updateById(specialBuilding);
            }
        }
        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    /**
     * 单元附属户主(管理员)
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/subsidiaryManager")
    @ApiOperation("单元附属户主(管理员)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "单元ID", value = "unitId", required = true, dataType = "string"),
    })
    @ResponseBody
    public SingleResult<BuildUnitHouseholder> subsidiaryManager(String unitId) throws Exception {
        SingleResult singleResult = new SingleResult();
        List<BuildUnitHouseholder> buildUnitHouseholder = buildUnitHouseholderMapper.selectByUnitId(unitId);
        for (BuildUnitHouseholder householder : buildUnitHouseholder) {
            //将号码中4位隐藏
            if (householder.getHouseholderPhone() != null && householder.getHouseholderPhone().length() > 3 && householder.getHouseholderPhone().length() <= 11) {
                householder.setHouseholderPhone(new StringBuilder(householder.getHouseholderPhone()).replace(3, 7, "****").toString());
            }
        }
        singleResult.setData(buildUnitHouseholder);
        return singleResult;
    }

    /**
     * 新增单元附属户主(管理员)
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/addSubsidiaryManager")
    @ApiOperation("新增单元附属户主(管理员)")
    @ResponseBody
    public SingleResult<BuildUnitHouseholder> addSubsidiaryManager
    (@RequestBody List<BuildUnitHouseholder> buildUnitHouseholder) throws Exception {
        SingleResult singleResult = new SingleResult();
        int result = 0;
        for (BuildUnitHouseholder bd : buildUnitHouseholder) {
            bd.setCreateTime(new Date());
            /*String id = RandomNumber.getUUid();
            bd.setHouseholderId(id);*/
            result = buildUnitHouseholderMapper.insert(bd);
        }
        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    /**
     * 修改单元附属户主(管理员)
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/editSubsidiaryManager")
    @ApiOperation("修改单元附属户主(管理员)")
    @ResponseBody
    public SingleResult<BuildUnitHouseholder> editSubsidiaryManager(@RequestBody BuildUnitHouseholder buildUnitHouseholder) throws Exception {
        SingleResult singleResult = new SingleResult();
        if (buildUnitHouseholderMapper.updateById(buildUnitHouseholder) < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    @ApiOperation(value = "企业详情", notes = "企业详情")
    @ApiModelProperty(name = "enterpriseId", value = "企业id", required = true)
    @GetMapping("/entDetail")
    @ResponseBody
    public SingleResult<Enterprise> entDetail(String enterpriseId) throws Exception {
        SingleResult<Enterprise> result = new SingleResult<>();
        Enterprise enterprise = enterpriseMapper.selectById(enterpriseId);

        if (null != enterprise) {

            //行业
            StoreType storeType = storeTypeMapper.selectById(enterprise.getIndustryId());
            if (null != storeType) {
                enterprise.setIndustryName(storeType.getTypeName());
            }
        }

        result.setData(enterprise);
        return result;
    }




    @ApiOperation(value = "后台(住户信息列表)", notes = "小程序(住户信息列表)")
    @GetMapping("/residentList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "单元ID", value = "unitId", required = false, dataType = "string"),
            @ApiImplicitParam(name = "楼层ID", value = "floorId", required = false, dataType = "string"),
            @ApiImplicitParam(name = "房屋使用性质", value = "residentType 1.自用 2.居住 3.合租 4.出租 5.整租 6.商用", required = false, dataType = "string"),
            @ApiImplicitParam(name = "居民名称", value = "residentName", required = false, dataType = "string"),
            @ApiImplicitParam(name = "condition", value = "condition", dataType = "string"),
    })
    @ResponseBody
    public SingleResult<List<BuildingResident>> residentList(Integer page, Integer pageSize, String unitId, String
            floorId, Integer residentType, String residentName, String condition) throws Exception {
        SingleResult<List<BuildingResident>> singleResult = new SingleResult<>();
        page = pageSize * (page - 1);
        String conditions = TypeConversion.getCondition(condition);
        List<BuildingResident> residents = buildingResidentMapper.selectResidentList(page, pageSize, unitId, floorId, residentType, residentName, conditions);
        long count = buildingResidentMapper.selectResidentCount(unitId, floorId, residentType, residentName, conditions);

        for (BuildingResident resident : residents) {
            //将号码中4位隐藏
            if (resident.getResidentPhone() != null && resident.getResidentPhone().length() > 3 && resident.getResidentPhone().length() <= 11) {
                resident.setResidentPhone(new StringBuilder(resident.getResidentPhone()).replace(3, 7, "****").toString());
            }
            // 身份证解密
            String IdentityCard = resident.getResidentIdentityCard();
            resident.setResidentIdentityCard(idNumberDesEncrypt(IdentityCard));
        }

        singleResult.setData(residents);
        singleResult.setCount(count);
        return singleResult;
    }

    @ApiOperation(value = "后台(新增/修改住户信息)", notes = "小程序(新增/修改住户信息)")
    @PostMapping("/addOrEditResident")
    @ResponseBody
    public SingleResult<BuildingResident> addOrEditResident(@RequestBody BuildingResident bd) throws Exception {
        SingleResult<BuildingResident> singleResult = new SingleResult<>();
        String userId = getUserId();
        int result = 0;

        if (StringUtils.isBlank(bd.getResidentId())) {
            bd.setCreateBy(userId);

            if(StringUtils.isNotBlank(bd.getResidentIdentityCard())){
                bd.setResidentIdentityCard(AesEncryptUtil.encrypt(bd.getResidentIdentityCard()));
            }


            bd.setCreateTime(new Date());
            bd.setModifyBy(userId);
            bd.setModifyTime(new Date());
            result = buildingResidentMapper.insert(bd);
        } else {

            if(bd.getResidentPhone().contains("*")){
                bd.setResidentPhone(null);
            }

            if(bd.getResidentIdentityCard().contains("*")){
                bd.setResidentIdentityCard(null);
            }

            bd.setModifyBy(userId);
            bd.setModifyTime(new Date());
            result = buildingResidentMapper.updateById(bd);
        }

        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    @ApiOperation(value = "后台(新增/修改设备信息)", notes = "小程序(新增/修改设备信息)")
    @PostMapping("/addOrEditGasEquipment")
    @ResponseBody
    public SingleResult<GasEquipment> addOrEditGasEquipment(@RequestBody GasEquipment gs) throws Exception {
        SingleResult<GasEquipment> singleResult = new SingleResult<>();
        int result = 0;
        String userId = getUserId();

        if (StringUtils.isBlank(gs.getGeId())) {
            gs.setCreateBy(userId);
            gs.setCreateTime(new Date());
            gs.setModifyBy(userId);
            gs.setModifyTime(new Date());
            result = gasEquipmentMapper.insert(gs);
        } else {
            gs.setModifyBy(userId);
            gs.setModifyTime(new Date());
            result = gasEquipmentMapper.updateById(gs);
        }

        if (result < 1) {
            singleResult.setCode(Code.ERROR.getCode());
            singleResult.setMessage(Message.ERROR);
        }
        return singleResult;
    }

    @ApiOperation(value = "后台(燃气设备信息列表)", notes = "小程序(燃气设备信息列表)")
    @GetMapping("/gasEquipmentList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "楼层ID", value = "floorId", required = false, dataType = "string"),
            @ApiImplicitParam(name = "单元ID", value = "unitId", required = false, dataType = "string"),
    })
    @ResponseBody
    public SingleResult<List<GasEquipment>> gasEquipmentList(Integer page, Integer pageSize, String floorId, String
            unitId) throws Exception {
        SingleResult<List<GasEquipment>> singleResult = new SingleResult<>();
        List<GasEquipment> gasEquipments = gasEquipmentMapper.selectGasEquipmentList(page, pageSize, floorId, unitId);
        long count = gasEquipmentMapper.selectGasEquipmentCount(floorId, unitId);
        singleResult.setData(gasEquipments);
        singleResult.setCount(count);
        return singleResult;
    }

    /**
     * 企业信息
     * @param enterpriseId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "企业信息", notes = "企业信息")
    @GetMapping("/enterpriseInfo/{enterpriseId}")
    @ApiImplicitParam(name = "enterpriseId",value = "企业id",required = true)
    @ResponseBody
    public SingleResult<Enterprise> enterpriseInfo(@PathVariable String enterpriseId)throws Exception{
        SingleResult<Enterprise> result = new SingleResult<>();
        Enterprise enterprise = enterpriseMapper.selectById(enterpriseId);
        result.setData(enterprise);
        return result;
    }

}
