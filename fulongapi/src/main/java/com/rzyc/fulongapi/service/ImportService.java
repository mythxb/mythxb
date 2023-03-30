package com.rzyc.fulongapi.service;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.rzyc.fulongapi.controller.BaseController;
import com.rzyc.fulongapi.enums.ResidentType;
import com.rzyc.fulongapi.model.*;
import com.rzyc.fulongapi.model.importmodels.BuildingResidentImModel;
import com.rzyc.fulongapi.model.importmodels.ImportBuildModel;
import com.rzyc.fulongapi.model.importmodels.ImportDangerModel;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("importService")
public class ImportService extends BaseController {

    //导入路由资源
    public  void  importResource(String json,String Pid){
        List<SysResource> sysResources = JSONObject.parseArray(json,SysResource.class);
        System.out.println(sysResources.toString());
        int i = 1;
        for (SysResource s:sysResources) {
            String uid = RandomNumber.getUUid();
            s.setResourceId(uid);
            s.setResourceType(11);
            if (StringUtils.isNotBlank(Pid)){
                s.setParentId(Pid);
            }
            HashMap<String,String> meta = (HashMap<String, String>) s.getMeta();
            if (StringUtils.isBlank(meta.get("link"))){
                meta.put("link",null);
            }
            if (StringUtils.isNotBlank(meta.get("title"))){
                meta.replace("title",'"'+meta.get("title")+'"');
            }
            if (null != meta.get("icon")) {
                meta.replace("icon", '"' + meta.get("icon") + '"');
            }
            if (StringUtils.isNotBlank(meta.get("parentName"))) {
                meta.replace("parentName", '"' + meta.get("parentName") + '"');
            }
            if (StringUtils.isNotBlank(meta.get("activeMenu"))) {
                meta.replace("activeMenu", '"' + meta.get("parentName") + '"');
            }
            String metaStr = meta.toString().replace("=",":");
            s.setMetas(metaStr);
            s.setSortId(i);
            s.setModifyBy("excel");
            s.setCreateBy("excel");
            s.setHiddens(String.valueOf(s.getHidden()));
            s.setModifyTime(new Date());
            s.setCreateTime(new Date());
            sysResourceMapper.insert(s);
            i++;
            if (null != s.getChildren() && s.getChildren().size() > 0){
                importResource(JSONObject.toJSONString(s.getChildren()),uid);
            }
        }
    }

    //导入路由资源
    /*public  void  importResource(String json, String Pid){
        List<SysResource> sysResources = JSONObject.parseArray(json,SysResource.class);
        System.out.println(sysResources.toString());
        int i = 1;
        for (SysResource s:sysResources) {
            String uid = RandomNumber.getUUid();
            s.setResourceId(uid);
            s.setResourceType(11);
            if (StringUtils.isNotBlank(Pid)){
                s.setParentId(Pid);
            }
            HashMap<String, String> meta = (HashMap<String, String>) s.getMeta();
            if (StringUtils.isBlank(meta.get("link"))){
                meta.put("link",null);
            }
            if (StringUtils.isNotBlank(meta.get("title"))){
                meta.replace("title",'"'+meta.get("title")+'"');
            }
            if (null != meta.get("icon")) {
                meta.replace("icon", '"' + meta.get("icon") + '"');
            }
            if (StringUtils.isNotBlank(meta.get("parentName"))) {
                meta.replace("parentName", '"' + meta.get("parentName") + '"');
            }
            if (StringUtils.isNotBlank(meta.get("activeMenu"))) {
                meta.replace("activeMenu", '"' + meta.get("parentName") + '"');
            }
            String metaStr = meta.toString().replace("=",":");
            s.setMetas(metaStr);
            s.setSortId(i);
            s.setModifyBy("excel");
            s.setCreateBy("excel");
            s.setHiddens(String.valueOf(s.getHidden()));
            s.setModifyTime(new Date());
            s.setCreateTime(new Date());
            sysResourceMapper.insert(s);
            i++;
            if (null != s.getChildren() && s.getChildren().size() > 0){
                importResource(JSONObject.toJSONString(s.getChildren()),uid);
            }
        }
    }*/

    public Map<String, Object> importResident(HttpServletRequest request,String userId)throws Exception {
        Map<String, Object> map = new HashMap<>();
        map = importResidentExce(request);
        if (null != map.get("excelRow")) {
            return map;
        }
        //插入数据库
        List<BuildingResidentImModel> residentImModels = (List<BuildingResidentImModel>)map.get("residentImModels");
        for (BuildingResidentImModel i:residentImModels) {
            Building building = buildingMapper.selectByDirectionAndNumber(i.getDirection(),i.getBuildingNumber());
            BuildUnit buildUnit = buildUnitMapper.findByBuildingId(building.getBuildId(),i.getBuildingUnitNumber());
            BuildFloor buildFloor = buildFloorMapper.findFloorByUnitIdAndFloorNumber(buildUnit.getUnitId(),i.getBuildingFloorNumber());

            BuildingResident buildingResident = new BuildingResident();
            buildingResident.setResidentId(RandomNumber.getUUid());
            buildingResident.setResidentName(i.getResidentName());
            buildingResident.setResidentPhone(i.getResidentPhone());
            buildingResident.setResidentIdentityCard(i.getIdentifyCode());
            buildingResident.setResidentType(i.getRoomUsingType());
            if (null != buildFloor && null != buildFloor.getFloorId()){
                buildingResident.setFloorId(buildFloor.getFloorId());
            }
            buildingResident.setBuildingUnitId(buildUnit.getUnitId());
            buildingResident.setCreateTime(new Date());
            buildingResident.setCreateBy(userId);
            buildingResident.setModifyTime(new Date());
            buildingResident.setModifyBy(userId);

            buildingResidentMapper.insert(buildingResident);
        }
        map.clear();
        map.put("成功","操作成功");
        return map;

    }


    public Map<String, Object> importBuildingInfoExce(HttpServletRequest request) throws Exception {
        Map<String, Object> mapResult = new HashMap<>();
        /**错误信息容器*/
        List<String> excelRow = new ArrayList<>();
        List<ImportBuildModel> importBuildModel = new ArrayList<>();
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = req.getFileMap();
        MultipartFile multipartFile = null;

        if (null != map && map.size() > 0) {
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                multipartFile = entry.getValue();
            }
        }
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);
            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            if (rowHead.getPhysicalNumberOfCells() == 6) {
                int totalRowNum = sheet.getLastRowNum();
                if (totalRowNum > 0) {
                    for (int i = 1; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }


                        //行数坐标
                        int Rowforshow = i + 1;
                        ImportBuildModel ibm = new ImportBuildModel();
                        String uid = RandomNumber.getUUid();

                        //楼栋名称
                        Cell cell = row.getCell((short)0);
                        if(null != cell) {
                            String buildingName = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(buildingName)){
                                if (buildingName.contains("伏龙西街")){
                                    ibm.setDirection(1);
                                }if (buildingName.contains("伏龙东街")){
                                    ibm.setDirection(2);
                                }
                                ibm.setBuildingName(buildingName);
                            }else{
                                excelRow.add(Rowforshow + "行楼栋名称未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行楼栋名称未检测到数据");
                        }


                        //楼栋号
                        cell = row.getCell((short)1);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String buildingNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(buildingNumber)){
                                ibm.setBuildingNumber(Integer.parseInt(buildingNumber));
                            }else{
                                excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                        }

                        //单元
                        cell = row.getCell((short)2);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String unit = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(unit)){
                                ibm.setUnit(Integer.parseInt(unit));
                                String CHNUMBER = GetCH(Integer.parseInt(unit));
                                ibm.setUnitName(CHNUMBER +  "单元");
                            }else{
                                excelRow.add(Rowforshow + "行单元号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行单元号未检测到数据");
                        }

                        //楼长名称
                        cell = row.getCell((short)3);
                        if(null != cell) {
                            String managerName = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(managerName)){
                                ibm.setManagerName(managerName);
                            }
                        }

                        //楼长电话
                        cell = row.getCell((short)4);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String managerPhone = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(managerPhone)){
                                ibm.setManagerPhone(managerPhone);
                            }
                        }

                        //楼层总数
                        cell = row.getCell((short)5);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String floorCount = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(floorCount)){
                                ibm.setFloorCount(Integer.parseInt(floorCount));
                            }
                        }



                        importBuildModel.add(ibm);
                    }
                } else {
                    excelRow.add("未检测到数据");
                }
            } else {
                excelRow.add("列数不正确");
            }
            if (excelRow.size() > 0) {
                mapResult.put("excelRow", excelRow);
                return mapResult;
            }
            //存放
            mapResult.put("importBuildModels",importBuildModel);
        }
        return mapResult;
    }


    //导入楼栋，单元，楼层
    public Map<String, Object> importBuildingInfo(HttpServletRequest request,String userId)throws Exception {
        Map<String, Object> map = new HashMap<>();
        map = importBuildingInfoExce(request);
        if (null != map.get("excelRow")) {
            return map;
        }
        //插入数据库
        List<ImportBuildModel> importBuildModels = (List<ImportBuildModel>)map.get("importBuildModels");
        for (ImportBuildModel i:importBuildModels) {



            Building building = new Building();
            BuildUnit buildUnit = new BuildUnit();

            Building bd = buildingMapper.selectByBuildName(i.getBuildingName());
            String bdId = null;
            if (null == bd){
                //建筑体
                bdId = RandomNumber.getUUid();
                building.setBuildId(bdId);
                building.setBuildName(i.getBuildingName());
                building.setBuildNumber(i.getBuildingNumber());
                building.setStreetParentId("d263c928-beb3-11eb-9d3c-00163e0c1c62");
                building.setCreateBy(userId);
                building.setCreateTime(new Date());
                building.setModifyBy(userId);
                building.setModifyTime(new Date());
                building.setDirection(i.getDirection());
                buildingMapper.insert(building);
            }else {
                bdId = bd.getBuildId();
            }

            BuildUnit bu = buildUnitMapper.selectByBuildingOrUnitId(i.getDirection(),i.getBuildingNumber(),i.getUnit());
            if (null == bu){
                //单元
                String unitUUID = RandomNumber.getUUid();
                buildUnit.setUnitId(unitUUID);
                buildUnit.setUnitManager(i.getManagerName());
                buildUnit.setUnitManagerContact(i.getManagerPhone());
                buildUnit.setBuildingId(bdId);
                buildUnit.setUnitNumber(i.getUnit());
                buildUnit.setUnitName(i.getUnitName());
                buildUnit.setFloorCount(i.getFloorCount());
                buildUnit.setCreateBy(userId);
                buildUnit.setCreateTime(new Date());
                buildUnit.setModifyBy(userId);
                buildUnit.setModifyTime(new Date());
                Integer floor = i.getFloorCount();
                if (null != floor) {
                    while (floor > 0) {
                        BuildFloor buildFloor = new BuildFloor();
                        buildFloor.setFloorId(RandomNumber.getUUid());
                        buildFloor.setBuildingId(bdId);
                        buildFloor.setUnitId(unitUUID);
                        buildFloor.setUnitNumber(i.getUnit());
                        buildFloor.setFloorNumber(floor);
                        buildFloor.setCreateTime(new Date());
                        buildFloor.setCreateBy(userId);
                        buildFloor.setModifyTime(new Date());
                        buildFloor.setModifyBy(userId);
                        buildFloorMapper.insert(buildFloor);
                        floor--;
                    }
                }
                buildUnitMapper.insert(buildUnit);
            }else {
                BuildUnitHouseholder buildUnitHouseholder = new BuildUnitHouseholder();
                buildUnitHouseholder.setCreateTime(new Date());
                buildUnitHouseholder.setBuildingUnitId(bu.getUnitId());
                buildUnitHouseholder.setHouseholderId(RandomNumber.getUUid());
                buildUnitHouseholder.setHouseholderName(i.getManagerName());
                buildUnitHouseholder.setHouseholderPhone(i.getManagerPhone());
                buildUnitHouseholderMapper.insert(buildUnitHouseholder);
            }
        }
        map.clear();
        map.put("成功","操作成功");
        return map;
    }

    public Map<String, Object> importResidentExce(HttpServletRequest request) throws Exception {
        Map<String, Object> mapResult = new HashMap<>();
        /**错误信息容器*/
        List<String> excelRow = new ArrayList<>();
        List<BuildingResidentImModel> residentImModels = new ArrayList<>();
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = req.getFileMap();
        MultipartFile multipartFile = null;

        if (null != map && map.size() > 0) {
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                multipartFile = entry.getValue();
            }
        }
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);
            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            if (rowHead.getPhysicalNumberOfCells() == 10) {
                int totalRowNum = sheet.getLastRowNum();
                if (totalRowNum > 0) {
                    for (int i = 1; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }


                        //行数坐标
                        int Rowforshow = i + 1;
                        BuildingResidentImModel bdim =  new BuildingResidentImModel();
                        String uid = RandomNumber.getUUid();

                        //街道
                        Cell cell = row.getCell((short)0);
                        if(null != cell) {
                            String street = cell.getStringCellValue();
                            if (street.contains("伏龙西街")){
                                bdim.setDirection(1);
                            }if (street.contains("伏龙东街")){
                                bdim.setDirection(2);
                            }
                            if (StringUtils.isNotBlank(street)){
                                bdim.setStreet(street);
                            }else{
                                excelRow.add(Rowforshow + "行街道名称未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行街道名称未检测到数据");
                        }


                        //楼栋号
                        cell = row.getCell((short)1);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String buildingNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(buildingNumber)){
                                bdim.setBuildingNumber(Integer.parseInt(buildingNumber));
                            }else{
                                excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                        }

                        //单元号
                        cell = row.getCell((short)2);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String unitNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(unitNumber)){
                                bdim.setBuildingUnitNumber(Integer.parseInt(unitNumber));
                            }else{
                                excelRow.add(Rowforshow + "行单元号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行单元号未检测到数据");
                        }

                        //单元楼层
                        cell = row.getCell((short)3);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String floorNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(floorNumber)){
                                bdim.setBuildingFloorNumber(Integer.parseInt(floorNumber));
                            }else{
                                excelRow.add(Rowforshow + "行单元楼层号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行单元楼层未检测到数据");
                        }

                        //单元长名称
                        cell = row.getCell((short)4);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String managerName = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(managerName)){
                                bdim.setUnitManagerName(managerName);
                            }else{
                                excelRow.add(Rowforshow + "行单元长名称未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行单元长名称未检测到数据");
                        }

                        //单元长电话
                        cell = row.getCell((short)5);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String managerPhone = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(managerPhone)){
                                bdim.setUnitManagerPhone(managerPhone);
                            }else{
                                excelRow.add(Rowforshow + "行单元长电话未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行单元长电话未检测到数据");
                        }

                        //房屋使用类型
                        cell = row.getCell((short)6);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String roomUsingType = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(roomUsingType)){
                                Integer resultType = 1;
                                if (roomUsingType == "自用"){
                                    resultType = ResidentType.selfUse.getType();
                                }else if (roomUsingType == "居住"){
                                    resultType = ResidentType.live.getType();
                                }else if (roomUsingType == "合租"){
                                    resultType = ResidentType.Sharing.getType();
                                }else if (roomUsingType == "出租"){
                                    resultType = ResidentType.lease.getType();
                                }else if (roomUsingType == "整租"){
                                    resultType = ResidentType.Wholerent.getType();
                                }else if (roomUsingType == "商用"){
                                    resultType = ResidentType.commercial.getType();
                                }
                                bdim.setRoomUsingType(resultType);
                            }
                        }

                        //居住人员姓名
                        cell = row.getCell((short)7);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String residentName = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(residentName)){
                                bdim.setResidentName(residentName);
                            }
                        }

                        //居住人员电话
                        cell = row.getCell((short)8);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String residentPhone = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(residentPhone)){
                                bdim.setResidentPhone(residentPhone);
                            }
                        }


                        //居住人员身份证
                        cell = row.getCell((short)9);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String residentIdentifyCode = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(residentIdentifyCode)){
                                bdim.setIdentifyCode(residentIdentifyCode);
                            }
                        }

                        residentImModels.add(bdim);
                    }
                } else {
                    excelRow.add("未检测到数据");
                }
            } else {
                excelRow.add("列数不正确");
            }
            if (excelRow.size() > 0) {
                mapResult.put("excelRow", excelRow);
                return mapResult;
            }
            //存放
            mapResult.put("residentImModels",residentImModels);
        }
        return mapResult;
    }

    private static String GetCH(int input)
    {
        String sd = "";
        switch (input)
        {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            case 10:
                sd = "十";
                break;
            case 11:
                sd = "十一";
                break;
            case 12:
                sd = "十二";
                break;
            case 13:
                sd = "十三";
                break;
            case 14:
                sd = "十四";
                break;
            case 15:
                sd = "十五";
                break;
            case 16:
                sd = "十六";
                break;
            case 17:
                sd = "十七";
                break;
            case 18:
                sd = "十八";
                break;
            case 19:
                sd = "十九";
                break;
            case 20:
                sd = "二十";
                break;
            default:
                break;
        }
        return sd;
    }

    private static int chineseNumber2Int(String chineseNumber){
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }


    //导入隐患信息
    public Map<String, Object> importDangerInfo(HttpServletRequest request,String userId)throws Exception {
        Map<String, Object> map = new HashMap<>();
//        map = importDangerInfoExce(request);
        map = importDangerInfoSupplementExce(request);

        if (null != map.get("excelRow")) {
            return map;
        }
        //插入数据库
        List<ImportDangerModel> importDangerModels = (List<ImportDangerModel>)map.get("importDangerModels");
        //因为类型
        List<DangerType> dangerTypes = dangerTypeMapper.selectDangerTypes(null);
        for (ImportDangerModel i:importDangerModels) {

            //找出楼栋，单元，楼层外键
            BuildFloor buildFloor = buildingMapper.importDangerFindForeign(i.getDirection(),i.getBuildingNumber(),i.getUnitNumber(),i.getFloorNumber());
                if (null != buildFloor){
                CheckDanger checkDanger  = new CheckDanger();
                checkDanger.setId(RandomNumber.getUUid());
                checkDanger.setCheckItem(i.getDangerProblem());
                checkDanger.setNormalorimportant(1);
                checkDanger.setRectificationState(2);
                checkDanger.setBuildingId(buildFloor.getBuildingId());
                checkDanger.setBuildingUnitId(buildFloor.getUnitId());
                checkDanger.setFloorFkey(buildFloor.getFloorId());
                checkDanger.setCreateBy(userId);
                checkDanger.setCreateTime(new Date());
                checkDanger.setModifyBy(userId);
                checkDanger.setModifyTime(new Date());




            if(null != i.getDangerType()){
                for (DangerType d:dangerTypes) {
                    if (i.getDangerType() .equals(d.getDangerTypeName())){
                        checkDanger.setDangerTypeId(d.getDangerTypeId());
                    }
                }
            }


            //隐患类型的存储
           /* Set<String> set = new HashSet<String>();
            if(null != i.getDangerType()){
                set.add(i.getDangerType());
            }
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                String dangerTypeStr = (String)iterator.next();
                DangerType dangerType = dangerTypeMapper.selectByDangerType(dangerTypeStr);
                if (dangerType == null){
                    dangerType = new DangerType();
                    String UUID = RandomNumber.getUUid();
                    dangerType.setDangerTypeName(dangerTypeStr);
                    dangerType.setDangerTypeId(UUID);
                    dangerType.setCreateBy(userId);
                    dangerType.setCreateTime(new Date());
                    checkDanger.setDangerTypeId(UUID);
                    dangerTypeMapper.insert(dangerType);
                }else {
                    checkDanger.setDangerTypeId(dangerType.getDangerTypeId());
                }

            }*/

            checkDangerMapper.insert(checkDanger);
            }
        }
        map.clear();
        map.put("成功","操作成功");
        return map;
    }

    public Map<String, Object> importDangerInfoExce(HttpServletRequest request) throws Exception {
        Map<String, Object> mapResult = new HashMap<>();
        /**错误信息容器*/
        List<String> excelRow = new ArrayList<>();
        List<ImportDangerModel> importDangerModels = new ArrayList<>();
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = req.getFileMap();
        MultipartFile multipartFile = null;

        if (null != map && map.size() > 0) {
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                multipartFile = entry.getValue();
            }
        }
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            int sheets = wookbook.getNumberOfSheets();
            for (int s = 0; s < sheets; s++) {

            Sheet sheet = wookbook.getSheetAt(s);
            System.out.println(sheet.getSheetName());
            //获得表头
            Row rowHead = sheet.getRow(0);
            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            if (rowHead.getPhysicalNumberOfCells() == 8) {
                int totalRowNum = sheet.getLastRowNum();
                if (totalRowNum > 0) {
                    for (int i = 1; i <= totalRowNum; i++) {
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if (null == row) {
                            break;
                        }


                        //行数坐标
                        int Rowforshow = i + 1;
                        ImportDangerModel idm = new ImportDangerModel();
                        String uid = RandomNumber.getUUid();

                        //检查用户
                        Cell cell = row.getCell((short) 0);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String checkUser = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(checkUser)) {
                                idm.setCheckUser(checkUser);
                            }
                        }

                        try {
                        //房屋类型
                        cell = row.getCell((short) 1);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String roomType = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(roomType)) {
                                idm.setRoomType(roomType);
                            }
                        }

                        //街道
                        cell = row.getCell((short) 2);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String street = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(street)) {
                                idm.setDirection(street.equals("伏龙西街") ? 1 : 2);
                            } else {
                                excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行街道未检测到数据");
                            }
                        } else {
                            excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行街道未检测到数据");
                        }

                        //楼栋号
                        cell = row.getCell((short) 3);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String buildingNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(buildingNumber)) {
                                idm.setBuildingNumber(Integer.parseInt(buildingNumber));
                            } else {
                                excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行楼栋号未检测到数据");
                            }
                        } else {
                            excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行楼栋号未检测到数据");
                        }

                        //单元号
                        cell = row.getCell((short) 4);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String unitNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(unitNumber)) {
                                unitNumber = unitNumber.replace("单元", "");
                                idm.setUnitNumber(Integer.parseInt(unitNumber));
                            } else {
                                excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行单元号未检测到数据");
                            }
                        } else {
                            excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行单元号未检测到数据");
                        }

                        //楼层号
                        cell = row.getCell((short) 5);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String floorNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(floorNumber)) {
                                idm.setFloorNumber(Integer.parseInt(floorNumber));
                            } else {
                                excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行楼层号未检测到数据");
                            }
                        } else {
                            excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行楼层号未检测到数据");
                        }

                        //隐患问题
                        cell = row.getCell((short) 6);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String dangerProblem = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(dangerProblem)) {
                                idm.setDangerProblem(replaceBlank(dangerProblem));
                            } else {
                                excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行隐患问题未检测到数据");
                            }
                        } else {
                            excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行隐患问题未检测到数据");
                        }


                        //隐患分类
                        cell = row.getCell((short) 7);
                        if (null != cell) {
                            cell.setCellType(CellType.STRING);
                            String dangerType = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(dangerType)) {
                                idm.setDangerType(dangerType);
                            }
                        }
                        }catch (NullPointerException e){
                            System.err.println(sheet.getSheetName()+"sheet第"+Rowforshow + "行发生错误");
                            e.printStackTrace();
                        }catch (NumberFormatException e){
                            System.err.println(sheet.getSheetName()+"sheet第"+Rowforshow + "行发生错误");
                            e.printStackTrace();
                        }

                        importDangerModels.add(idm);
                    }
                } else {
                    excelRow.add("未检测到数据");
                }
            } else {
                excelRow.add("列数不正确");
            }

            }
            if (excelRow.size() > 0) {
                mapResult.put("excelRow", excelRow);
                return mapResult;
            }
            //存放
            mapResult.put("importDangerModels", importDangerModels);

        }
        return mapResult;
    }


    public Map<String, Object> importDangerInfoSupplementExce(HttpServletRequest request) throws Exception {
        Map<String, Object> mapResult = new HashMap<>();
        /**错误信息容器*/
        List<String> excelRow = new ArrayList<>();
        List<ImportDangerModel> importDangerModels = new ArrayList<>();
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = req.getFileMap();
        MultipartFile multipartFile = null;

        if (null != map && map.size() > 0) {
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                multipartFile = entry.getValue();
            }
        }
        if (null != multipartFile) {
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            int sheets = wookbook.getNumberOfSheets();
            for (int s = 0; s < sheets; s++) {

                Sheet sheet = wookbook.getSheetAt(s);
                System.out.println(sheet.getSheetName());
                //获得表头
                Row rowHead = sheet.getRow(0);
                System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
                if (rowHead.getPhysicalNumberOfCells() == 10) {
                    int totalRowNum = sheet.getLastRowNum();
                    if (totalRowNum > 0) {
                        for (int i = 1; i <= totalRowNum; i++) {
                            //获得第i行对象
                            Row row = sheet.getRow(i);
                            if (null == row) {
                                break;
                            }




                            //行数坐标
                            int Rowforshow = i + 1;
                            ImportDangerModel idm = new ImportDangerModel();
                            List<String> floorId = new ArrayList<>();
                            Integer problemNumber;
                            String [] p = null;

                            //检查用户
                            Cell cell = row.getCell((short) 0);
                            if (null != cell) {
                                cell.setCellType(CellType.STRING);
                                String checkUser = cell.getStringCellValue();
                                if (StringUtils.isNotBlank(checkUser)) {
                                    idm.setCheckUser(checkUser);
                                }
                            }






                            try {
                                //房屋类型
                                cell = row.getCell((short) 1);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String roomType = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(roomType)) {
                                        idm.setRoomType(roomType);
                                    }
                                }

                                //街道
                                cell = row.getCell((short) 2);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String street = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(street)) {
                                        idm.setDirection(street.equals("伏龙西街") ? 1 : 2);
                                    } else {
                                        excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行街道未检测到数据");
                                    }
                                } else {
                                    excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行街道未检测到数据");
                                }

                                //楼栋号
                                cell = row.getCell((short) 3);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String buildingNumber = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(buildingNumber)) {
                                        idm.setBuildingNumber(Integer.parseInt(buildingNumber));
                                    } else {
                                        excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行楼栋号未检测到数据");
                                    }
                                } else {
                                    excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行楼栋号未检测到数据");
                                }

                                //单元号
                                cell = row.getCell((short) 4);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String unitNumber = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(unitNumber)) {
                                        unitNumber = unitNumber.replace("单元", "");
                                        Integer unit = chineseNumber2Int(unitNumber);
                                        idm.setUnitNumber(unit);
                                    } else {
                                        excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行单元号未检测到数据");
                                    }
                                } else {
                                    excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行单元号未检测到数据");
                                }

                                //楼层号
                                cell = row.getCell((short) 5);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String floorNumber = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(floorNumber)) {
                                        idm.setFloorNumber(Integer.parseInt(floorNumber));
                                    }
                                }

                                //隐患问题
                                cell = row.getCell((short) 6);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String dangerProblem = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(dangerProblem)) {
                                        idm.setDangerProblem(replaceBlank(dangerProblem));
                                    } else {
                                        excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行隐患问题未检测到数据");
                                    }
                                } else {
                                    excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行隐患问题未检测到数据");
                                }


                                //隐患分类
                                cell = row.getCell((short) 7);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String dangerType = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(dangerType)) {
                                        idm.setDangerType(dangerType);
                                    }
                                }


                            try {
                            //隐患旧问题
                            cell = row.getCell((short) 9);
                                if (null != cell) {
                                    cell.setCellType(CellType.STRING);
                                    String oldDangerProblem = cell.getStringCellValue();
                                    if (StringUtils.isNotBlank(oldDangerProblem)) {
                                       Building building =  buildingMapper.selectByDirectionAndNumber(idm.getDirection(), idm.getBuildingNumber());
                                        BuildUnit buildUnit =  buildUnitMapper.selectByParentAndNumber(building.getBuildId(), idm.getUnitNumber());
                                        List<CheckDanger> checkDanger = checkDangerMapper.findInfo(building.getBuildId(),buildUnit.getUnitId(),oldDangerProblem);
                                        for (CheckDanger ck:checkDanger) {
                                            floorId.add(ck.getFloorFkey());
                                            Integer result = checkDangerMapper.updateDelState(ck.getId());
                                        }

                                    }
                                }
                            }catch (Exception e){
                                excelRow.add(sheet.getSheetName()+"sheet第"+Rowforshow + "行补加数据错误");
                                e.printStackTrace();
                            }

                            //隐患数量
                            cell = row.getCell((short) 8);
                            if (null != cell) {
                                cell.setCellType(CellType.STRING);
                                String dangerNumber = cell.getStringCellValue();
                                if (StringUtils.isNotBlank(dangerNumber)) {
                                    problemNumber = Integer.parseInt(dangerNumber);
                                    if (problemNumber > 1){
                                         p = idm.getDangerProblem().split("；");
                                    }
                                }
                            }

                            if (null != p &&p.length >0){
                                for (String floor:floorId) {
                                    for (String x:p) {
                                        if (StringUtils.isNotBlank(x)){
                                            if (HasDigit(x)){
                                                String number = getNumeric(x);
                                                Integer intNumber = Integer.parseInt(number);
                                                    for (int a = 0;a<intNumber;a++){
                                                        ImportDangerModel newIdm  = new ImportDangerModel();
                                                        BeanUtils.copyProperties(newIdm,idm);
                                                        String tempStr = x ;
                                                        tempStr = tempStr.replaceAll("\\d+","");
                                                        tempStr = tempStr.replace("台","");
                                                        tempStr = tempStr.replace("处","");
                                                        if (tempStr.contains("窗")){
                                                            tempStr = "软管暗设或穿墙"+tempStr;
                                                        }
                                                        tempStr = "("+newIdm.getDangerType()+")"+tempStr;
                                                        newIdm.setDangerProblem(tempStr);
                                                        BuildFloor buildFloor = buildFloorMapper.selectById(floor);
                                                        newIdm.setFloorNumber(buildFloor.getFloorNumber());

                                                        importDangerModels.add(newIdm);
                                                    }
                                            }else {
                                                ImportDangerModel newIdm  = new ImportDangerModel();
                                                BeanUtils.copyProperties(newIdm,idm);
                                                String tempStr = x ;
                                                if (tempStr.contains("窗")){
                                                    tempStr = "软管暗设或穿墙"+tempStr;
                                                }
                                                newIdm.setDangerProblem("("+newIdm.getDangerType()+")"+tempStr);
                                                BuildFloor buildFloor = buildFloorMapper.selectById(floor);
                                                newIdm.setFloorNumber(buildFloor.getFloorNumber());
                                                importDangerModels.add(newIdm);
                                            }
                                        }
                                    }
                                }
                            }
                            }catch (NullPointerException e){
                                System.err.println(sheet.getSheetName()+"sheet第"+Rowforshow + "行发生错误");
                                e.printStackTrace();
                            }catch (NumberFormatException e){
                                System.err.println(sheet.getSheetName()+"sheet第"+Rowforshow + "行发生错误");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        excelRow.add("未检测到数据");
                    }
                } else {
                    excelRow.add("列数不正确");
                }

            }
            if (excelRow.size() > 0) {
                mapResult.put("excelRow", excelRow);
                return mapResult;
            }
            //存放
            mapResult.put("importDangerModels", importDangerModels);

        }
        return mapResult;
    }

    //提取数字
    public static String getNumeric(String str) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    //判断是否有数字
    public boolean HasDigit(String content) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            flag = true;
        }
        return flag;
    }


    public static String replaceBlank(String str) throws Exception{
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        dest.replaceAll(" ","");
        return dest;
    }
}

