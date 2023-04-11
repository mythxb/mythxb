package com.rzyc.fulongapi.controller;

import com.common.utils.DateUtils;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.encryption.AesEncryptUtil;
import com.common.utils.encryption.MD5;
import com.common.utils.model.SingleResult;
import com.common.utils.upload.ImageFromNetWork;
import com.rzyc.fulongapi.enums.DangerLevel;
import com.rzyc.fulongapi.enums.RiskLevel;
import com.rzyc.fulongapi.enums.UserType;
import com.rzyc.fulongapi.mapper.CheckListItemMapper;
import com.rzyc.fulongapi.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "数据处理")
@CrossOrigin("*")
@RequestMapping("index")
@Controller
@Validated
public class DataController extends BaseController {


    @ApiOperation(value = "导入检查项", notes = "导入检查项")
    @RequestMapping(value = "/inportCheckItem", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> inportCheckItem(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        handleCheckItemData(multipartFile);
        return result;
    }


    public void handleCheckItemData(MultipartFile multipartFile) throws Exception {
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            //判断表头是否正确
            if (true) {
                //获得数据的总行数
                int totalRowNum = sheet.getLastRowNum();

                if (totalRowNum > 0) {

                    DataFormatter dataFormatter = new DataFormatter();


                    Map<String, String> inherentRiskMap = new HashMap<>();
                    inherentRiskMap.put("红", "4");
                    inherentRiskMap.put("红色", "4");
                    inherentRiskMap.put("橙", "3");
                    inherentRiskMap.put("橙色", "3");
                    inherentRiskMap.put("黄", "2");
                    inherentRiskMap.put("黄色", "2");
                    inherentRiskMap.put("蓝", "1");
                    inherentRiskMap.put("蓝色", "1");


                    Integer index = 1;
                    //检查分类
                    String checkType = "";
                    //获得所有数据
                    for (int i = 1; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }


                        Cell cell = row.getCell((short) 0);
                        if (null != cell) {
                            String checkTypeStr = dataFormatter.formatCellValue(cell);
                            if (StringUtils.isNotBlank(checkTypeStr)) {
                                checkType = checkTypeStr;
                            }
                        }

                        //检查项
                        String checkItem = "";
                        cell = row.getCell((short) 1);
                        if (null != cell) {
                            checkItem = dataFormatter.formatCellValue(cell);
                        }


                        System.out.println(checkType + "----" + checkItem);
                        DangerType dangerType = dangerTypeMapper.selectByDangerType(checkType);
                        if (null != dangerType) {

                            CheckItem item = new CheckItem();
                            item.setItemId(RandomNumber.getUUid());
                            item.setCheckItemName(checkItem);
                            item.setClassId(dangerType.getDangerTypeId());
                            item.setItemContent(checkItem);
                            item.setItemLegalBasis("");
                            item.setCreateTime(new Date());
                            item.setCreateBy("导入");
                            checkItemMapper.insert(item);

                        } else {
                            System.out.println("-----------------------------------------------------------");
                        }


                    }
                    System.out.println(index);
                }
            }
        }
        System.out.println("数据解析完成");
    }

    @ApiOperation(value = "创建清单", notes = "创建清单")
    @RequestMapping(value = "/createList", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> createList() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<CheckItem> checkItems = checkItemMapper.findAll();
        for (CheckItem checkItem : checkItems) {
            CheckListItem listItem = new CheckListItem();
            listItem.setId(RandomNumber.getUUid());
            listItem.setCreateBy("导入");
            listItem.setCreateTime(new Date());
            listItem.setClassId("a2a2c0ea-92f3-11ec-b82b-00163e0c1c65");
            listItem.setItemId(checkItem.getItemId());
            checkListItemMapper.insert(listItem);
        }
        return result;
    }


    @ApiOperation(value = "批量生产二维码", notes = "批量生产二维码")
    @RequestMapping(value = "/unitQrCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> unitQrCode() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<BuildUnit> units = buildUnitMapper.unitList("%%", "", "");
        System.out.println("unitList -> " + units.size());
        Map<String, String> qrcodeMap = new HashMap<>();
        qrcodeMap.put("type", "unit");
        for (BuildUnit buildUnit : units) {
            qrcodeMap.put("targetId", buildUnit.getUnitId());
            String qecode = createQrCode(qrcodeMap);
            System.out.println("qecode -> " + qecode);
            buildUnitMapper.changeQrCode(buildUnit.getUnitId(), qecode);
        }
        return result;
    }

    @ApiOperation(value = "批量生产特殊建筑体二维码", notes = "批量生产特殊建筑体二维码")
    @RequestMapping(value = "/specialBuildingQrCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> specialBuildingQrCode() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<SpecialBuilding> specialBuildings = specialBuildingMapper.specialBuildingList();
        Map<String, String> qrcodeMap = new HashMap<>();
        qrcodeMap.put("type", "specialBuilding");
        for (SpecialBuilding sb : specialBuildings) {
            qrcodeMap.put("targetId", sb.getSbId());
            String qecode = createQrCode(qrcodeMap);
            System.out.println("qecode -> " + qecode);
            specialBuildingMapper.changeQrCode(sb.getSbId(), qecode);
        }
        return result;
    }

    @ApiOperation(value = "批量生成楼栋二维码", notes = "批量生成楼栋二维码")
    @RequestMapping(value = "/buildingQrCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> buildingQrCode() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<Building> buildings = buildingMapper.findAll();
        Map<String, String> qrcodeMap = new HashMap<>();
        qrcodeMap.put("type", "building");
        for (Building building : buildings) {
            qrcodeMap.put("targetId", building.getBuildId());
            String qecode = createQrCode(qrcodeMap);
            System.out.println("qecode -> " + qecode);
            buildingMapper.changeQrCode(building.getBuildId(), qecode);
        }
        return result;
    }

    @ApiOperation(value = "批量生成企业二维码", notes = "批量生成企业二维码")
    @RequestMapping(value = "/entQrCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> entQrCode() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<Enterprise> enterprises = enterpriseMapper.entList("%%", "", null, null, null);
        Map<String, String> qrcodeMap = new HashMap<>();
        qrcodeMap.put("type", "enterprise");
        for (Enterprise enterprise : enterprises) {
            qrcodeMap.put("targetId", enterprise.getEnterpriseId());
            String qecode = createQrCode(qrcodeMap);
            System.out.println("qecode -> " + qecode);
            enterpriseMapper.changeQrCode(enterprise.getEnterpriseId(), qecode);
        }
        return result;
    }


    @ApiOperation(value = "修改风险等级", notes = "修改风险等级")
    @RequestMapping(value = "/changeRiskLevel", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> changeRiskLevel() throws Exception {
        SingleResult<String> result = new SingleResult<>();

//        楼栋风险等级
        List<Building> buildings = buildingMapper.findAll();
        for (Building building : buildings) {
            bulidRiskLevel(building.getBuildId());
        }

        //单元风险等级
        List<BuildUnit> buildUnits = buildUnitMapper.findAll();
        for (BuildUnit buildUnit : buildUnits) {
            unitRiskLevel(buildUnit.getUnitId());
        }

        //单元楼层风险等级
        List<BuildFloor> floors = buildFloorMapper.findAll();
        for (BuildFloor floor : floors) {
            floorRiskLevel(floor.getFloorId());
        }


        return result;
    }


    @ApiOperation(value = "导入住户信息", notes = "导入住户信息")
    @RequestMapping(value = "/inportFloorInfo", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> inportFloorInfo(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        handleFloorInfo(multipartFile);
        return result;
    }


    public void handleFloorInfo(MultipartFile multipartFile) throws Exception {
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            //判断表头是否正确
            if (true) {
                //获得数据的总行数
                int totalRowNum = sheet.getLastRowNum();

                if (totalRowNum > 0) {

                    DataFormatter dataFormatter = new DataFormatter();


                    Map<String, String> unitNumMap = new HashMap<>();
                    unitNumMap.put("一", "1");
                    unitNumMap.put("二", "2");
                    unitNumMap.put("三", "3");
                    unitNumMap.put("四", "4");
                    unitNumMap.put("五", "5");
                    unitNumMap.put("六", "6");
                    unitNumMap.put("七", "7");
                    unitNumMap.put("八", "8");
                    unitNumMap.put("九", "9");
                    unitNumMap.put("十", "10");
                    unitNumMap.put("十一", "11");
                    unitNumMap.put("十二", "12");
                    unitNumMap.put("十三", "13");
                    unitNumMap.put("十四", "14");
                    unitNumMap.put("十五", "15");
                    unitNumMap.put("十六", "16");
                    unitNumMap.put("十七", "17");
                    unitNumMap.put("十八", "18");
                    unitNumMap.put("十九", "19");
                    unitNumMap.put("二十", "20");


                    Integer index = 1;
                    //检查分类
                    String addressText = "";
                    //获得所有数据
                    for (int i = 2; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }

                        System.out.println("index -> " + index);
                        index++;


                        Cell cell = row.getCell((short) 1);
                        if (null != cell) {
                            String addressStr = dataFormatter.formatCellValue(cell);
                            if (StringUtils.isNotBlank(addressStr)) {
                                addressText = addressStr;
                            }
                        }

                        //楼层
                        String floorNumber = "";
                        cell = row.getCell((short) 4);
                        if (null != cell) {
                            floorNumber = dataFormatter.formatCellValue(cell);
                        }

                        //楼层
                        String householdSize = "";
                        cell = row.getCell((short) 5);
                        if (null != cell) {
                            householdSize = dataFormatter.formatCellValue(cell);
                        }

                        //居住人数
                        String personNum = "";
                        cell = row.getCell((short) 6);
                        if (null != cell) {
                            personNum = dataFormatter.formatCellValue(cell);
                        }

                        //燃气灶具数
                        String cookerNum = "";
                        cell = row.getCell((short) 7);
                        if (null != cell) {
                            cookerNum = dataFormatter.formatCellValue(cell);
                        }

                        //燃气户表数
                        String usertableNum = "";
                        cell = row.getCell((short) 8);
                        if (null != cell) {
                            usertableNum = dataFormatter.formatCellValue(cell);
                        }

                        //热水器数
                        String heaterNum = "";
                        cell = row.getCell((short) 9);
                        if (null != cell) {
                            heaterNum = dataFormatter.formatCellValue(cell);
                        }


//                        System.out.println(address+"-"+floorNumber+"-"+householdSize+"-"+personNum+"-"+cookerNum+"-"+usertableNum+"-"+heaterNum);

                        //2、东街 1、西街
                        Integer direction = 2;
                        //栋数
                        String buildNum = "";

                        //单元数
                        String unitName = "";

/*                        String address = addressText;
                        address = address.replace("东街","");
                        Integer unitLength = address.indexOf("单元");
                        address = address.substring(0,unitLength);
                        String[] strs = address.split("栋");
                        buildNum = strs[0];
                        unitName = strs[1];*/

                        String address = addressText;
                        address = address.replace("伏龙东街1号", "");
                        address = address.replace("街", "");
                        address = address.replace("街", "");
                        String[] bulidStrs = address.split("栋");

                        buildNum = bulidStrs[0];

                        if (bulidStrs.length >= 2) {
                            String unitNameStr = bulidStrs[1];
                            String[] unitNameStrs = unitNameStr.split("单元");
                            unitName = unitNameStrs[0];
                        }


                        String unitNames = unitNumMap.get(unitName);
                        if (StringUtils.isNotBlank(unitNames)) {
                            unitName = unitNames;
                        }
                        System.out.println(buildNum + " --- " + unitName);


                        Building building = buildingMapper.findBuilding(direction, TypeConversion.StringToInteger(buildNum));
                        if (null != building) {
                            BuildUnit buildUnit = buildUnitMapper.findByBuildingId(building.getBuildId(), TypeConversion.StringToInteger(unitName));
                            if (null != buildUnit) {
                                System.out.println(building.getBuildId() + "," + buildUnit.getUnitId() + "," + floorNumber);
                                BuildFloor buildFloor = new BuildFloor();
                                buildFloor.setBuildingId(building.getBuildId());
                                buildFloor.setUnitId(buildUnit.getUnitId());
                                buildFloor.setFloorNumber(TypeConversion.StringToInteger(floorNumber));

                                if (StringUtils.isNotBlank(householdSize)) {
                                    buildFloor.setHouseholdSize(TypeConversion.StringToInteger(householdSize));
                                }
                                if (StringUtils.isNotBlank(personNum)) {
                                    buildFloor.setPersonNum(TypeConversion.StringToInteger(personNum));
                                }
                                if (StringUtils.isNotBlank(cookerNum)) {
                                    buildFloor.setCookerNum(TypeConversion.StringToInteger(cookerNum));
                                }
                                if (StringUtils.isNotBlank(usertableNum)) {
                                    buildFloor.setUsertableNum(TypeConversion.StringToInteger(usertableNum));
                                }
                                if (StringUtils.isNotBlank(heaterNum)) {
                                    buildFloor.setHeaterNum(TypeConversion.StringToInteger(heaterNum));
                                }
                                buildFloorMapper.changeNum(buildFloor);

                            }
                        }


                    }
                    System.out.println();
                }
            }
        }
        System.out.println("数据解析完成");
    }


    @ApiOperation(value = "导入企业信息", notes = "导入企业信息")
    @RequestMapping(value = "/importEnt", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> importEnt(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        handleEnt(multipartFile);
        return result;
    }


    public void handleEnt(MultipartFile multipartFile) throws Exception {
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            //判断表头是否正确
            if (true) {
                //获得数据的总行数
                int totalRowNum = sheet.getLastRowNum();

                if (totalRowNum > 0) {

                    DataFormatter dataFormatter = new DataFormatter();


                    Integer index = 1;
                    //检查分类
                    String addressText = "";
                    //获得所有数据
                    for (int i = 3; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }

                        System.out.println("index ------------------------> " + index);


                        //企业名
                        String entName = "";
                        //行业
                        String industry = "";
                        //负责人
                        String contacts = "";
                        //联系方式
                        String contactMobile = "";
                        //就业人数
                        String workNum = "";
                        //居住人数
                        String personNum = "";
                        //燃气灶具数
                        String cookerNum = "";
                        //燃气户表数
                        String usertableNum = "";
                        //热水器数
                        String heaterNum = "";

                        Cell cell = row.getCell((short) 1);
                        if (null != cell) {
                            entName = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 2);
                        if (null != cell) {
                            industry = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 3);
                        if (null != cell) {
                            String addressStr = dataFormatter.formatCellValue(cell);
                            if (StringUtils.isNotBlank(addressStr)) {
                                addressText = addressStr;
                            }
                        }

                        cell = row.getCell((short) 4);
                        if (null != cell) {
                            contacts = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 5);
                        if (null != cell) {
                            contactMobile = dataFormatter.formatCellValue(cell);
                        }



                        cell = row.getCell((short) 6);
                        if (null != cell) {
                            workNum = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 7);
                        if (null != cell) {
                            personNum = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 8);
                        if (null != cell) {
                            cookerNum = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 9);
                        if (null != cell) {
                            usertableNum = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 9);
                        if (null != cell) {
                            heaterNum = dataFormatter.formatCellValue(cell);
                        }

                        //企业不为空
                        if (StringUtils.isNotBlank(entName)) {
//                            System.out.println(entName + "--" + addressText);

                            //2、东街 1、西街
                            Integer direction = 2;

                            if(addressText.contains("伏龙西街")){
                                direction = 1;
                            }


                            //栋数
                            String buildNum = "";


/*                            String address = addressText;
                            address = address.replace("西","");
                            address = address.replace("街","");
                            Integer bulidLength = address.indexOf("栋");
                            if(bulidLength > 0){
                                address = address.substring(0,bulidLength);
                            }else{
                                address = "";
                            }*/

                            /*String address = addressText;
                            if(address.contains("栋")){
                                String[] buildStrs = address.split("栋");
                                address = buildStrs[0];

                                address = address.replace("伏龙东街","");
                                if(address.contains("号")){
                                    String[] numStrs = address.split("号");
                                    address = numStrs[1];
                                }
                            }else{
                                address = "";
                            }*/

                            String address = addressText;

                            address = address.replace("街","");
                            address = address.replace("伏龙栋","");
                            address = address.replace("伏龙东街","");
                            address = address.replace("伏龙西","");
                            address = address.replace("伏龙东","");
                            address = address.replace("伏龙","");
                            address = address.replace("伏龙小区","");
                            address = address.replace("小区","");

                            if (address.contains("栋")) {
                                String[] buildStrs = address.split("栋");
                                address = buildStrs[0];

                                address = address.replace("东街1号", "");
                                if (address.contains("号")) {
                                    String[] numStrs = address.split("号");
                                    address = numStrs[1];
                                }
                            } else {
                                address = "";
                            }


                            buildNum = address;


                            System.out.println("buildNum -> "+buildNum);
                            System.out.println("direction -> "+direction);


                            Enterprise enterprise = new Enterprise();
                            enterprise.setEnterpriseId(RandomNumber.getUUid());

                            if (StringUtils.isNotBlank(buildNum)) {
                                Building building = buildingMapper.findBuilding(direction, TypeConversion.StringToInteger(buildNum));
                                if (null != building) {
                                    enterprise.setBuildingId(building.getBuildId());
                                }
                            }

                            enterprise.setEntName(entName);
                            enterprise.setIndustryId(industry);
                            enterprise.setAddress(addressText);
                            enterprise.setContacts(contacts);
                            enterprise.setContactMobile(contactMobile);

                            if (StringUtils.isNotBlank(workNum)) {
                                enterprise.setWorkNum(TypeConversion.StringToInteger(workNum));
                            }
                            if (StringUtils.isNotBlank(personNum)) {
                                enterprise.setPersonNum(TypeConversion.StringToInteger(personNum));
                            }
                            if (StringUtils.isNotBlank(cookerNum)) {
                                enterprise.setCookerNum(TypeConversion.StringToInteger(cookerNum));
                            }
                            if (StringUtils.isNotBlank(usertableNum)) {
                                enterprise.setUsertableNum(TypeConversion.StringToInteger(usertableNum));
                            }
                            if (StringUtils.isNotBlank(heaterNum)) {
                                enterprise.setHeaterNum(TypeConversion.StringToInteger(heaterNum));
                            }
                            enterprise.setRiskLevel(RiskLevel.BLUE.getLevel());
                            enterprise.setCreateBy("导入");
                            enterprise.setCreateTime(new Date());
                            enterprise.setModifyTime(new Date());
                            enterprise.setModifyBy("导入");
                            enterpriseMapper.insert(enterprise);

                            index++;
                        }
                    }


                }
            }
        }
        System.out.println("数据解析完成");
    }

    @ApiOperation(value = "修改隐患状态", notes = "修改隐患状态")
    @RequestMapping(value = "/changeDanger", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> changeDanger() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<CheckDanger> checkDangers = checkDangerMapper.findAll();
        for (CheckDanger checkDanger : checkDangers) {
            if (dangerTypeIds.contains(checkDanger.getDangerTypeId())) {
                checkDanger.setNormalorimportant(DangerLevel.MAJOR.getState());
                checkDangerMapper.changeLevel(checkDanger);
            }
        }
        return result;
    }


    @ApiOperation(value = "企业行业", notes = "企业行业")
    @RequestMapping(value = "/entIndustry", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> entIndustry() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<Enterprise> enterprises = enterpriseMapper.entList("%%", "", null, null, null);
        for (Enterprise enterprise : enterprises) {
            String industryId = enterprise.getIndustryId();

            if (StringUtils.isBlank(industryId) || "无".equals(industryId)) {
                industryId = "其他";
            }
            StoreType storeType = storeTypeMapper.findByName(industryId);
            if (null == storeType) {
                storeType = new StoreType();
                storeType.setId(RandomNumber.getUUid());
                storeType.setTypeName(industryId);
                storeType.setSortId(1);
                storeType.setCreateBy("导入");
                storeType.setCreateTime(new Date());
                storeTypeMapper.insert(storeType);

            }
            enterpriseMapper.changeIndustry(enterprise.getEnterpriseId(), storeType.getId());
        }
        return result;
    }

    @ApiOperation(value = "将不存在楼层的单元进行自动生成", notes = "将不存在楼层的单元进行自动生成")
    @PostMapping(value = "/generateFloorByUnit")
    @ResponseBody
    public long generateFloorByUnit() throws Exception {
        long result = 0;
        List<BuildUnit> buildUnits = buildUnitMapper.selectTheseNullFloorUnits();
        for (BuildUnit bd : buildUnits) {
            int index = 1;
            for (int i = 0; i < bd.getFloorCount(); i++) {
                BuildFloor buildFloor = new BuildFloor();
                buildFloor.setFloorId(RandomNumber.getUUid());
                buildFloor.setBuildingId(bd.getBuildingId());
                buildFloor.setUnitId(bd.getUnitId());
                buildFloor.setUnitNumber(bd.getUnitNumber());
                buildFloor.setFloorNumber(index);
                buildFloor.setHouseType(1);
                buildFloor.setRiskLevel(1);
                buildFloor.setCreateBy("导入");
                buildFloor.setModifyBy("导入");
                buildFloor.setCreateTime(new Date());
                buildFloor.setModifyTime(new Date());
                buildFloorMapper.insert(buildFloor);
                index++;
            }

        }
        return result;
    }

    @ApiOperation(value = "创建楼栋用户", notes = "创建楼栋用户")
    @PostMapping(value = "/buildUser")
    @ResponseBody
    public SingleResult<String> buildUser() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<Building> buildings = buildingMapper.buildingList("%%", null, null);
        for (Building building : buildings) {
            System.out.println(building.getBuildingManager() + " - " + building.getBuildingManagerContact());
            SysUser sysUser = sysUserMapper.findByAccount(building.getBuildingManagerContact());
            if (null == sysUser) {
                sysUser = new SysUser();
                sysUser.setUserId(RandomNumber.getUUid());
                sysUser.setUserName(building.getBuildingManager());
                sysUser.setUserAccount(building.getBuildingManagerContact());
                String passwd = MD5.md5(sysUser.getUserId() + building.getBuildingManagerContact());
                sysUser.setUserPassword(passwd);
                sysUser.setUserPhone(building.getBuildingManagerContact());
                sysUser.setUserType(UserType.BUILD.getType());
                sysUser.setCreateBy("导入");
                sysUser.setCreateTime(new Date());
                sysUser.setModifyBy("导入");
                sysUser.setModifyTime(new Date());
                sysUserMapper.insert(sysUser);
            }

            SysUserBuild userBuild = new SysUserBuild();
            userBuild.setRelationId(RandomNumber.getUUid());
            userBuild.setUserId(sysUser.getUserId());
            userBuild.setTargetId(building.getBuildId());
            userBuild.setRelationType(UserType.BUILD.getType());
            userBuild.setCreateBy("导入");
            userBuild.setCreateTime(new Date());
            sysUserBuildMapper.insert(userBuild);


        }
        return result;
    }


    @ApiOperation(value = "创建单元用户", notes = "创建单元用户")
    @PostMapping(value = "/unitUser")
    @ResponseBody
    public SingleResult<String> unitUser() throws Exception {
        SingleResult<String> result = new SingleResult<>();
        List<BuildUnit> units = buildUnitMapper.unitList("%%", "", "");
        for (BuildUnit unit : units) {
            System.out.println(unit.getUnitManager() + " - " + unit.getUnitManagerContact());
            if (StringUtils.isNotBlank(unit.getUnitManagerContact())) {
                SysUser sysUser = sysUserMapper.findByAccount(unit.getUnitManagerContact());
                if (null == sysUser) {
                    sysUser = new SysUser();
                    sysUser.setUserId(RandomNumber.getUUid());
                    sysUser.setUserName(unit.getUnitManager());
                    sysUser.setUserAccount(unit.getUnitManagerContact());
                    String passwd = MD5.md5(sysUser.getUserId() + unit.getUnitManagerContact());
                    sysUser.setUserPassword(passwd);
                    sysUser.setUserPhone(unit.getUnitManagerContact());
                    sysUser.setUserType(UserType.UNIT.getType());
                    sysUser.setCreateBy("导入");
                    sysUser.setCreateTime(new Date());
                    sysUser.setModifyBy("导入");
                    sysUser.setModifyTime(new Date());
                    sysUserMapper.insert(sysUser);
                } else {
                    SysUser user = new SysUser();
                    user.setUserId(sysUser.getUserId());
                    user.setUserType(UserType.UNIT.getType());
                    user.setUserName(unit.getUnitManager());
                    user.setUserAccount(unit.getUnitManagerContact());
                    String passwd = MD5.md5(user.getUserId() + unit.getUnitManagerContact());
                    user.setUserPassword(passwd);
                    user.setUserPhone(unit.getUnitManagerContact());
                    sysUserMapper.updateById(user);
                }

                SysUserBuild userBuild = new SysUserBuild();
                userBuild.setRelationId(RandomNumber.getUUid());
                userBuild.setUserId(sysUser.getUserId());
                userBuild.setTargetId(unit.getUnitId());
                userBuild.setRelationType(UserType.UNIT.getType());
                userBuild.setCreateBy("导入");
                userBuild.setCreateTime(new Date());
                sysUserBuildMapper.insert(userBuild);
            }
        }
        return result;
    }


    @ApiOperation(value = "修改户主电话", notes = "修改户主电话")
    @RequestMapping(value = "/changeUnitManger", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> changeUnitManger(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        handleUnitManger(multipartFile);
        return result;
    }


    public void handleUnitManger(MultipartFile multipartFile) throws Exception {
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            //判断表头是否正确
            if (true) {
                //获得数据的总行数
                int totalRowNum = sheet.getLastRowNum();

                if (totalRowNum > 0) {

                    DataFormatter dataFormatter = new DataFormatter();


                    Integer index = 1;
                    //检查分类
                    //获得所有数据
                    for (int i = 1; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }

                        String buildName = "";

                        Cell cell = row.getCell((short) 0);
                        if (null != cell) {
                            buildName = dataFormatter.formatCellValue(cell);
                        }

                        //检查项
                        String unitNum = "";
                        cell = row.getCell((short) 2);
                        if (null != cell) {
                            unitNum = dataFormatter.formatCellValue(cell);
                        }


                        String name = "";
                        cell = row.getCell((short) 3);
                        if (null != cell) {
                            name = dataFormatter.formatCellValue(cell);
                        }


                        String mobile = "";
                        cell = row.getCell((short) 4);
                        if (null != cell) {
                            mobile = dataFormatter.formatCellValue(cell);
                        }

                        if (StringUtils.isNotBlank(mobile)) {

                            //1.西街 2.东街
                            Integer direction = 1;
                            if (buildName.contains("伏龙东街")) {
                                direction = 2;
                            }

                            buildName = buildName.replaceAll("伏龙东街1号", "");
                            buildName = buildName.replaceAll("伏龙西街2号", "");
                            buildName = buildName.replaceAll("栋", "");

                            Building building = buildingMapper.findBuilding(direction, TypeConversion.StringToInteger(buildName));
                            if (null != building) {
                                buildUnitMapper.changeManger(name, mobile, building.getBuildId(), TypeConversion.StringToInteger(unitNum));
                            }
                            System.out.println(direction + " -> " + buildName);
                            index++;
                        }

                    }
                    System.out.println(index);
                }
            }
        }
        System.out.println("数据解析完成");
    }


    @ApiOperation(value = "导入住户信息表")
    @RequestMapping(value = "/importResident", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> importResident(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);
            System.out.println("表头 -> " + rowHead.getPhysicalNumberOfCells());

            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();  // getPhysicalNumberOfRows();
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

                    //街道
                    String street = "";
                    //楼栋
                    Integer building = null;
                    //单元
                    Integer unit = null;
                    //楼层
                    Integer floor = null;
                    //单元房主
                    String unitManager = "";
                    //房主电话
                    String unitManagerContact = "";
                    //房屋使用类型
                    String residentType = "";
                    //居住人姓名
                    String residentName = "";
                    //居住人电话
                    String residentPhone = "";
                    //居住人身份证
                    String residentIdentityCard = "";


                    // 获取这一行的第一列单元格的信息
                    Cell cell = row.getCell(0);
                    if (null != cell) {
                        // 获取这个单元格的内容
                        street = dataFormatter.formatCellValue(cell);
                    }

                    // 获取这一行的第二列单元格的信息
                    cell = row.getCell(1);
                    if (null != cell) {
                        // 获取这个单元格的内容
                        building = Integer.parseInt(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(2);
                    if (null != cell) {
                        unit = Integer.parseInt(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(3);
                    if (null != cell) {
                        floor = Integer.parseInt(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(4);
                    if (null != cell) {
                        unitManager = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(5);
                    if (null != cell) {
                        unitManagerContact = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(6);
                    if (null != cell) {
                        residentType = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(7);
                    if (null != cell) {
                        residentName = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(8);
                    if (null != cell) {
                        residentPhone = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(9);
                    if (null != cell) {
                        residentIdentityCard = dataFormatter.formatCellValue(cell);
                    }

                    BuildingResident buildingResident = new BuildingResident();

                    //楼栋id
                    String buildingId = "";
                    //单元id
                    String unitId = "";
                    //楼层id
                    String floorId = "";

                    if (StringUtils.isNotBlank(street) && StringUtils.isNotBlank(String.valueOf(building))
                            && StringUtils.isNotBlank(String.valueOf(unit)) && StringUtils.isNotBlank(String.valueOf(floor))) {
                        //2、东街 1、西街
                        Integer direction = 2;

                        if (street.contains("西")) {
                            direction = 1;
                        }

                        buildingId = checkDangerMapper.getBuildId(direction, building);
                        unitId = checkDangerMapper.getUnitId(buildingId, unit);
                        floorId = buildFloorMapper.getFloorIdByBuildingIdAndUnitIdAndFloor(buildingId, unitId, floor);
                    }

                    buildingResident.setResidentId(RandomNumber.getUUid());
                    buildingResident.setResidentName(residentName);
                    buildingResident.setResidentPhone(residentPhone);
                    buildingResident.setResidentIdentityCard(AesEncryptUtil.encrypt(residentIdentityCard));
                    if (residentType.contains("自用") || residentType.contains("自住")) {
                        buildingResident.setResidentType(1);
                    } else if (residentType.contains("居住")) {
                        buildingResident.setResidentType(2);
                    } else if (residentType.contains("合租")) {
                        buildingResident.setResidentType(3);
                    } else if (residentType.contains("出租")) {
                        buildingResident.setResidentType(4);
                    } else if (residentType.contains("整租")) {
                        buildingResident.setResidentType(5);
                    } else if (residentType.contains("商用")) {
                        buildingResident.setResidentType(6);
                    }
                    buildingResident.setWorkPlace("");
                    buildingResident.setFloorId(floorId);
                    buildingResident.setBuildingUnitId(unitId);
                    buildingResident.setCreateBy("导入");
                    buildingResident.setCreateTime(new Date());
                    buildingResident.setModifyBy("导入");
                    buildingResident.setModifyTime(new Date());

                    System.out.println("buildingResident-->" + buildingResident);
                    buildingResidentMapper.insert(buildingResident);

                    index++;
                }
                System.out.println("数据整改完毕");
            }
        }
        return result;
    }

    /**
     * 导入隐患
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入隐患")
    @RequestMapping(value = "/importDanger", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> importDanger(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);
            System.out.println("表头 -> " + rowHead.getPhysicalNumberOfCells());

            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();  // getPhysicalNumberOfRows();
            System.out.println("总行数" + totalRowNum);

            if (totalRowNum > 0) {
                DataFormatter dataFormatter = new DataFormatter();
                Integer index = 1;


                Map<String,String> dangerTypeMap = new HashMap<>();

                dangerTypeMap.put("燃气管道","a769898e-eac2-44a9-b53c-8141e217383e");
                dangerTypeMap.put("燃气燃烧灶具","95c924f2-995e-4827-9aa2-b0d684ae8333");
                dangerTypeMap.put("连接软管","b5b7da2a-1f2e-4fe9-9828-3813956dc81c");

                //1.未整改 2.整改中 3.已整改 4.无法整改
                Map<String,Integer> checkStateMap = new HashMap<>();
                checkStateMap.put("未整改",1);
                checkStateMap.put("整改中",2);
                checkStateMap.put("已整改",3);

                //获得所有数据
                for (int i = 1; i <= totalRowNum; i++) {
                    //获得第i行对象
                    Row row = sheet.getRow(i);
                    if (null == row) {
                        break;
                    }

                    System.out.println("index --------------------------------> " + index);

                    //街道
                    String address = "";
                    String dangerType = "";
                    String dangerDesc = "";
                    String rectifyDesc = "";
                    Date rectifyTime = new Date();
                    String rectifyState = "";


                    Cell cell = row.getCell(0);
                    if (null != cell) {
                        address = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(1);
                    if (null != cell) {
                        dangerType = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(2);
                    if (null != cell) {
                        dangerDesc = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(3);
                    if (null != cell) {
                        rectifyDesc = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(4);
                    if (null != cell) {

                        rectifyTime = cell.getDateCellValue();
                    }

                    cell = row.getCell(5);
                    if (null != cell) {
                        rectifyState = dataFormatter.formatCellValue(cell);
                    }

                    //2、东街 1、西街
                    Integer direction = 2;

                    if(address.contains("伏龙西街")){
                        direction = 1;
                    }


                    address = address.replace("伏龙东街1号","");
                    address = address.replace("单元","");
                    address = address.replace("伏龙西街2号","");
                    address = address.replace("伏龙西街1号","");
                    address = address.replace("伏龙西街","");
                    address = address.replace("伏龙东街","");
                    address = address.replace("单","");
                    address = address.replace("伏龙东","");
                    address = address.replace(" ","");

                    System.out.println("address -> "+address);
//                    System.out.println("dangerType -> "+dangerType);
//                    System.out.println("dangerDesc -> "+dangerDesc);
//                    System.out.println("rectifyDesc -> "+rectifyDesc);
//                    System.out.println("rectifyTime -> "+ DateUtils.parseDate2String(rectifyTime,"yyyy-MM-dd"));
//                    System.out.println("rectifyState -> "+rectifyState);

                    CheckDanger checkDanger = new CheckDanger();
                    checkDanger.setCheckDescId(RandomNumber.getUUid());
                    checkDanger.setCreateBy("excel");
                    checkDanger.setModifyBy("excel");
                    checkDanger.setCreateTime(new Date());
                    checkDanger.setModifyTime(new Date());
                    checkDanger.setCheckItem(dangerDesc);
                    checkDanger.setNormalorimportant(1);
                    checkDanger.setRectificationState(checkStateMap.get(rectifyState));
                    checkDanger.setDangerTypeId(dangerTypeMap.get(dangerType));
                    checkDanger.setRectifyTerm(rectifyTime);
                    checkDanger.setCheckType(1);


                    String buildNum = "";
                    String unitNum = "";
                    String[] strs = address.split("栋");
                    if(null != strs && strs.length >=2){
                        buildNum = strs[0];
                        unitNum = strs[1];
                    }

                    if(unitNum.contains("附") || unitNum.contains("号")){
                        unitNum = 0+"";
                    }

                    System.out.println("buildNum -> "+TypeConversion.StringToInteger(buildNum));

                    System.out.println("unitNum -> "+TypeConversion.StringToInteger(unitNum));

                    Building building = buildingMapper.findBuilding(direction,TypeConversion.StringToInteger(buildNum));
                    if(null != building){
                        checkDanger.setBuildingId(building.getBuildId());

                        BuildUnit buildUnit = buildUnitMapper.selectByBuildingOrUnitId(direction,
                                TypeConversion.StringToInteger(buildNum),TypeConversion.StringToInteger(unitNum));
                        if(null != buildUnit){
                            checkDanger.setBuildingUnitId(buildUnit.getUnitId());
                        }
                    }

                    checkDangerMapper.insert(checkDanger);


                    index++;
                }
            }
        }
        return result;
    }


    /**
     * 导入房屋鉴定信息
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入房屋鉴定信息")
    @RequestMapping(value = "/importUnitLevel", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> importUnitLevel(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);
            System.out.println("表头 -> " + rowHead.getPhysicalNumberOfCells());

            //获得数据的总行数
            int totalRowNum = sheet.getLastRowNum();  // getPhysicalNumberOfRows();
            System.out.println("总行数" + totalRowNum);

            if (totalRowNum > 0) {
                DataFormatter dataFormatter = new DataFormatter();
                Integer index = 1;


                Map<String,String> dangerTypeMap = new HashMap<>();

                dangerTypeMap.put("燃气管道","a769898e-eac2-44a9-b53c-8141e217383e");
                dangerTypeMap.put("燃气燃烧灶具","95c924f2-995e-4827-9aa2-b0d684ae8333");
                dangerTypeMap.put("连接软管","b5b7da2a-1f2e-4fe9-9828-3813956dc81c");

                //1.未整改 2.整改中 3.已整改 4.无法整改
                Map<String,Integer> checkStateMap = new HashMap<>();
                checkStateMap.put("未整改",1);
                checkStateMap.put("整改中",2);
                checkStateMap.put("已整改",3);


                Map<String, String> unitNumMap = new HashMap<>();
                unitNumMap.put("一", "1");
                unitNumMap.put("二", "2");
                unitNumMap.put("三", "3");
                unitNumMap.put("四", "4");
                unitNumMap.put("五", "5");
                unitNumMap.put("六", "6");
                unitNumMap.put("七", "7");
                unitNumMap.put("八", "8");
                unitNumMap.put("九", "9");
                unitNumMap.put("十", "10");
                unitNumMap.put("十一", "11");
                unitNumMap.put("十二", "12");
                unitNumMap.put("十三", "13");
                unitNumMap.put("十四", "14");
                unitNumMap.put("十五", "15");
                unitNumMap.put("十六", "16");
                unitNumMap.put("十七", "17");
                unitNumMap.put("十八", "18");
                unitNumMap.put("十九", "19");
                unitNumMap.put("二十", "20");

                Map<String, String> buildNumMap = new HashMap<>();
                buildNumMap.put("一", "1");
                buildNumMap.put("二", "2");
                buildNumMap.put("三", "3");
                buildNumMap.put("四", "4");
                buildNumMap.put("五", "5");
                buildNumMap.put("六", "6");
                buildNumMap.put("七", "7");
                buildNumMap.put("八", "8");
                buildNumMap.put("九", "9");
                buildNumMap.put("十", "10");
                buildNumMap.put("十一", "11");
                buildNumMap.put("十二", "12");
                buildNumMap.put("十三", "13");
                buildNumMap.put("十四", "14");
                buildNumMap.put("十五", "15");
                buildNumMap.put("十六", "16");
                buildNumMap.put("十七", "17");
                buildNumMap.put("十八", "18");
                buildNumMap.put("十九", "19");
                buildNumMap.put("二十", "20");
                buildNumMap.put("二十一", "21");
                buildNumMap.put("二十二", "22");
                buildNumMap.put("二十三", "23");
                buildNumMap.put("二十四", "24");
                buildNumMap.put("二十五", "25");
                buildNumMap.put("二十六", "26");
                buildNumMap.put("二十七", "27");
                buildNumMap.put("二十八", "28");
                buildNumMap.put("二十九", "29");

                //获得所有数据
                for (int i = 3; i <= totalRowNum; i++) {
                    //获得第i行对象
                    Row row = sheet.getRow(i);
                    if (null == row) {
                        break;
                    }



                    System.out.println("index --------------------------------> " + index);

                    //街道
                    String address = "";


                    BuildUnitLevel unitLevel = new BuildUnitLevel();
                    unitLevel.setLevelId(RandomNumber.getUUid());
                    unitLevel.setCreateBy("excel");
                    unitLevel.setModifyBy("excel");
                    unitLevel.setCreateTime(new Date());
                    unitLevel.setModifyTime(new Date());

                    Cell cell = row.getCell(1);
                    if (null != cell) {
                        address = dataFormatter.formatCellValue(cell);
                    }

                    cell = row.getCell(5);
                    if (null != cell) {
                        unitLevel.setHouseType(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(6);
                    if (null != cell) {
                        unitLevel.setPlanInfo(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(7);
                    if (null != cell) {
                        unitLevel.setAreaNum(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(8);
                    if (null != cell) {
                        unitLevel.setStructureType(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(9);
                    if (null != cell) {
                        unitLevel.setLayerNum(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(10);
                    if (null != cell) {
                        unitLevel.setAdditional(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(11);
                    if (null != cell) {
                        unitLevel.setAdditionalNum(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(12);
                    if (null != cell) {
                        unitLevel.setAdditionalTime(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(13);
                    if (null != cell) {
                        unitLevel.setChangeFunction(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(14);
                    if (null != cell) {
                        unitLevel.setUseInfo(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(15);
                    if (null != cell) {
                        unitLevel.setReform(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(16);
                    if (null != cell) {
                        unitLevel.setReformInfo(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(17);
                    if (null != cell) {
                        unitLevel.setReformTime(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(18);
                    if (null != cell) {
                        unitLevel.setFoundation(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(19);
                    if (null != cell) {
                        unitLevel.setSuperstructure(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(20);
                    if (null != cell) {
                        unitLevel.setEntirety(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(21);
                    if (null != cell) {
                        unitLevel.setProblemImg(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(22);
                    if (null != cell) {
                        unitLevel.setSafetyAppraisal(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(23);
                    if (null != cell) {
                        unitLevel.setProposalDesc(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(24);
                    if (null != cell) {
                        unitLevel.setFacadeImg(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(25);
                    if (null != cell) {
                        unitLevel.setBaseImg(dataFormatter.formatCellValue(cell));
                    }

                    cell = row.getCell(26);
                    if (null != cell) {
                        unitLevel.setPartitionDesc(dataFormatter.formatCellValue(cell));
                    }


                    //2、东街 1、西街
                    Integer direction = 2;

                    if(address.contains("西")){
                        direction = 1;
                    }


                    address = address.replace("伏龙东街1号","");
                    address = address.replace("单元","");
                    address = address.replace("伏龙西街2号","");
                    address = address.replace("伏龙西街1号","");
                    address = address.replace("伏龙西街","");
                    address = address.replace("伏龙东街","");
                    address = address.replace("单","");
                    address = address.replace("伏龙东","");
                    address = address.replace("东街","");
                    address = address.replace("（无法进入）","");
                    address = address.replace(" ","");

                    System.out.println("address -> "+address);
//                    System.out.println("dangerType -> "+dangerType);
//                    System.out.println("dangerDesc -> "+dangerDesc);
//                    System.out.println("rectifyDesc -> "+rectifyDesc);
//                    System.out.println("rectifyTime -> "+ DateUtils.parseDate2String(rectifyTime,"yyyy-MM-dd"));
//                    System.out.println("rectifyState -> "+rectifyState);




                    String buildNum = "";
                    String unitNum = "";
                    String[] strs = address.split("栋");
                    if(null != strs && strs.length >=2){
                        buildNum = strs[0];
                        unitNum = strs[1];
                    }

                    if(unitNum.contains("附") || unitNum.contains("号")){
                        unitNum = 0+"";
                    }

                    if(buildNumMap.containsKey(buildNum)){
                        buildNum = buildNumMap.get(buildNum);
                    }

                    if(unitNumMap.containsKey(unitNum)){
                        unitNum = unitNumMap.get(unitNum);
                    }

                    System.out.println("buildNum -> "+TypeConversion.StringToInteger(buildNum));

                    System.out.println("unitNum -> "+TypeConversion.StringToInteger(unitNum));


                    Building building = buildingMapper.findBuilding(direction,TypeConversion.StringToInteger(buildNum));
                    if(null != building){
                        unitLevel.setBuildingId(building.getBuildId());

                        BuildUnit buildUnit = buildUnitMapper.selectByBuildingOrUnitId(direction,
                                TypeConversion.StringToInteger(buildNum),TypeConversion.StringToInteger(unitNum));
                        if(null != buildUnit){
                            unitLevel.setUnitId(buildUnit.getUnitId());
                        }
                    }

                    buildUnitLevelMapper.insert(unitLevel);


                    index++;
                }
            }
        }
        return result;
    }

    @ApiOperation(value = "处理居住人数")
    @RequestMapping(value = "/handlePersonNum", method = RequestMethod.GET)
    @ResponseBody
    public SingleResult<String> handlePersonNum()throws Exception{
        SingleResult<String> result = new SingleResult<>();

        List<BuildFloor> buildFloors =  buildFloorMapper.findAll();
        if(null != buildFloors && buildFloors.size() > 0){
            for (BuildFloor buildFloor : buildFloors){
                buildFloorMapper.changePersonNum(buildFloor.getFloorId());
            }
        }

        return result;
    }

    @ApiOperation(value = "处理企业用户")
    @RequestMapping(value = "/handleEntUser", method = RequestMethod.GET)
    @ResponseBody
    public SingleResult<String> handleEntUser()throws Exception{
        SingleResult<String> result = new SingleResult<>();

        List<Enterprise> enterprises =  enterpriseMapper.findAll();
        if(null != enterprises && enterprises.size() > 0){
            for (Enterprise enterprise : enterprises){
                addEntUser(enterprise.getEnterpriseId());
            }
        }
        return result;
    }


    @ApiOperation(value = "导入企业信息", notes = "导入企业信息")
    @RequestMapping(value = "/importEntInfo", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> importEntInfo(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            //判断表头是否正确
            if (true) {
                //获得数据的总行数
                int totalRowNum = sheet.getLastRowNum();

                if (totalRowNum > 0) {

                    DataFormatter dataFormatter = new DataFormatter();


                    Integer index = 1;
                    //检查分类
                    String addressText = "";
                    //获得所有数据
                    for (int i = 1; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }

                        System.out.println("index ------------------------> " + index);


                        //企业名
                        String entName = "";
                        //行业
                        String industry = "";
                        //从业人员
                        String workNum = "";
                        //负责人
                        String contacts = "";
                        //联系方式
                        String contactMobile = "";


                        Cell cell = row.getCell((short) 0);
                        if (null != cell) {
                            entName = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 1);
                        if (null != cell) {
                            addressText = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 2);
                        if (null != cell) {
                            industry = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 3);
                        if (null != cell) {
                            workNum = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 4);
                        if (null != cell) {
                            contacts = dataFormatter.formatCellValue(cell);
                        }

                        cell = row.getCell((short) 6);
                        if (null != cell) {
                            contactMobile = dataFormatter.formatCellValue(cell);
                        }





                        //企业不为空
                        if (StringUtils.isNotBlank(entName)) {
//                            System.out.println(entName + "--" + addressText);

                            entName = StringUtils.replaceBlank(entName);

                            //2、东街 1、西街
                            Integer direction = 2;

                            if(addressText.contains("西")){
                                direction = 1;
                            }


                            //栋数
                            String buildNum = "";


/*                            String address = addressText;
                            address = address.replace("西","");
                            address = address.replace("街","");
                            Integer bulidLength = address.indexOf("栋");
                            if(bulidLength > 0){
                                address = address.substring(0,bulidLength);
                            }else{
                                address = "";
                            }*/

                            /*String address = addressText;
                            if(address.contains("栋")){
                                String[] buildStrs = address.split("栋");
                                address = buildStrs[0];

                                address = address.replace("伏龙东街","");
                                if(address.contains("号")){
                                    String[] numStrs = address.split("号");
                                    address = numStrs[1];
                                }
                            }else{
                                address = "";
                            }*/

                            String address = addressText;

                            address = address.replace("街","");
                            address = address.replace("伏龙栋","");
                            address = address.replace("伏龙东街","");
                            address = address.replace("伏龙西","");
                            address = address.replace("伏龙东","");
                            address = address.replace("伏龙","");
                            address = address.replace("伏龙小区","");
                            address = address.replace("小区","");

                            if (address.contains("栋")) {
                                String[] buildStrs = address.split("栋");
                                address = buildStrs[0];

                                address = address.replace("东街1号", "");
                                if (address.contains("号")) {
                                    String[] numStrs = address.split("号");
                                    address = numStrs[1];
                                }
                            } else {
                                address = "";
                            }


                            buildNum = address;


                            buildNum = StringUtils.replaceBlank(buildNum);
                            System.out.println("buildNum -> "+buildNum);
                            System.out.println("direction -> "+direction);

                            Enterprise ent = enterpriseMapper.findByName(entName);
                            if(null == ent){
                                Enterprise enterprise = new Enterprise();
                                enterprise.setEnterpriseId(RandomNumber.getUUid());

                                if (StringUtils.isNotBlank(buildNum)) {
                                    Building building = buildingMapper.findBuilding(direction, TypeConversion.StringToInteger(buildNum));
                                    if (null != building) {
                                        enterprise.setBuildingId(building.getBuildId());
                                    }
                                }

                                enterprise.setEntName(entName);
                                enterprise.setIndustryId(industry);
                                enterprise.setAddress(addressText);
                                enterprise.setContacts(contacts);
                                enterprise.setContactMobile(contactMobile);

                                if (StringUtils.isNotBlank(workNum)) {
                                    enterprise.setWorkNum(TypeConversion.StringToInteger(workNum));
                                }
                                enterprise.setRiskLevel(RiskLevel.BLUE.getLevel());
                                enterprise.setCreateBy("导入");
                                enterprise.setCreateTime(new Date());
                                enterprise.setModifyTime(new Date());
                                enterprise.setModifyBy("导入");
                                enterpriseMapper.insert(enterprise);
                            }

                            index++;
                        }
                    }


                }
            }
        }
        return result;
    }

    @ApiOperation(value = "导出企业二维码", notes = "导出企业二维码")
    @RequestMapping(value = "/downQrcode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> downQrcode()throws Exception{
        SingleResult<String> result = new SingleResult<>();
        List<Enterprise> enterprises = enterpriseMapper.findAll();
        for (Enterprise enterprise : enterprises){
            String imgUrl = "http://121.40.106.103/"+enterprise.getQrCode();
            String fileName = enterprise.getEntName()+"--"+enterprise.getAddress()+".png";
            ImageFromNetWork.writeImageToDisk(ImageFromNetWork.getImageFromNetByUrl(imgUrl),"C:/mnt/resource/fulong/qrcode/"+fileName);
        }
        return result;
    }


    /**
     *
     * @version v1.0
     * @author dong
     * @date 2023/4/11 21:09
     */
    @ApiOperation(value = "导入燃气隐患", notes = "导入燃气隐患")
    @RequestMapping(value = "/inportGasDanger", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> inportGasDanger(@RequestBody MultipartFile multipartFile) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            //判断表头是否正确
            if (true) {
                //获得数据的总行数
                int totalRowNum = sheet.getLastRowNum();

                if (totalRowNum > 0) {

                    DataFormatter dataFormatter = new DataFormatter();


                    Map<String, String> unitNumMap = new HashMap<>();
                    unitNumMap.put("一", "1");
                    unitNumMap.put("二", "2");
                    unitNumMap.put("三", "3");
                    unitNumMap.put("四", "4");
                    unitNumMap.put("五", "5");
                    unitNumMap.put("六", "6");
                    unitNumMap.put("七", "7");
                    unitNumMap.put("八", "8");
                    unitNumMap.put("九", "9");
                    unitNumMap.put("十", "10");
                    unitNumMap.put("十一", "11");
                    unitNumMap.put("十二", "12");
                    unitNumMap.put("十三", "13");
                    unitNumMap.put("十四", "14");
                    unitNumMap.put("十五", "15");
                    unitNumMap.put("十六", "16");
                    unitNumMap.put("十七", "17");
                    unitNumMap.put("十八", "18");
                    unitNumMap.put("十九", "19");
                    unitNumMap.put("二十", "20");


                    Integer index = 1;
                    //检查分类
                    String addressText = "";
                    //获得所有数据
                    for (int i = 0; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }

                        System.out.println("index -> " + index);
                        index++;


                        Cell cell = row.getCell((short) 0);
                        if (null != cell) {
                            String addressStr = dataFormatter.formatCellValue(cell);
                            if (StringUtils.isNotBlank(addressStr)) {
                                addressText = addressStr;
                            }
                        }

                        System.out.println("addressText ---> "+addressText);
                        //2、东街 1、西街
                        Integer direction = 2;

                        if(addressText.contains("伏龙西街")){
                            direction = 1;
                        }

                        addressText = addressText.replaceAll("伏龙东街1号","");
                        addressText = addressText.replaceAll("伏龙西街2号","");
                        addressText = addressText.replaceAll("伏龙西街1号","");
                        addressText = addressText.replaceAll("单元","");
                        addressText = addressText.replaceAll("单","");
                        addressText = addressText.replaceAll("单 ","");
                        addressText = addressText.replaceAll(" ","");
                        addressText = addressText.replaceAll("街","");
                        addressText = addressText.replaceAll("单无","");
                        addressText = addressText.replaceAll("无","");

                        System.out.println("addressText after ---> "+addressText);

                        String[] strs = addressText.split("栋");

                        if(null != strs && strs.length == 2){
                            String buildStr = strs[0];
                            String unitStr = strs[1];
                            System.out.println(direction + " - " + buildStr + " - " + unitStr);

                            Building building = buildingMapper.findBuilding(direction,TypeConversion.StringToInteger(buildStr));
                            if(null != building){
                                BuildUnit buildUnit = buildUnitMapper.findByBuildingId(building.getBuildId(),TypeConversion.StringToInteger(unitStr));
                                if(null != buildUnit){
                                    checkDangerMapper.changeGasState(buildUnit.getUnitId());
                                }
                            }
                        }

                    }
                    System.out.println();
                }
            }
        }
        return result;
    }





}
