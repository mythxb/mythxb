package com.rzyc.fulongapi.service;

import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.rzyc.fulongapi.mapper.BuildFloorMapper;
import com.rzyc.fulongapi.mapper.BuildingMapper;
import com.rzyc.fulongapi.mapper.CheckDangerMapper;
import com.rzyc.fulongapi.model.BuildFloor;
import com.rzyc.fulongapi.model.CheckDanger;
import com.rzyc.fulongapi.model.importmodels.DangerProblem;
import com.rzyc.fulongapi.model.importmodels.ImportDangerModel;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service("ImportDanger")
public class ImportDanger {
    @Autowired
    private BuildFloorMapper buildFloorMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private CheckDangerMapper checkDangerMapper;


    //导入隐患
    public Map<String, Object> importDanger(HttpServletRequest request, String userId)throws Exception {
        Map<String, Object> map = new HashMap<>();
        map = importDangerExce(request);
        if (null != map.get("excelRow")) {
            return map;
        }
        //插入数据库
        List<ImportDangerModel> importDangers = (List<ImportDangerModel>)map.get("importDangerModels");
        List<BuildFloor>floors = buildFloorMapper.selectAllBuildFloor();
        List<CheckDanger>tempArrayList = new ArrayList<>();


        for (BuildFloor i:floors) {
            boolean access  = true;
            for (ImportDangerModel id:importDangers) {
                if (null != id.getBuildFloor() && null != id.getBuildFloor().getFloorId()) {
                    if (i.getFloorId().equals(id.getBuildFloor().getFloorId())) {
                        access = false;
                        break;
                    }

                }
            }
            if (null != i && true == access) {
                CheckDanger checkDanger = new CheckDanger();
                checkDanger.setId(RandomNumber.getUUid());
                checkDanger.setCheckItem("未安装燃气报警器");
                checkDanger.setNormalorimportant(1);//一般隐患
                checkDanger.setRectificationState(2);//整改中
                checkDanger.setDangerTypeId("a769898e-eac2-44a9-b53c-8141e2173832");//燃气报警
                checkDanger.setBuildingId(i.getBuildingId());
                checkDanger.setBuildingUnitId(i.getUnitId());
                checkDanger.setFloorFkey(i.getFloorId());
                checkDanger.setCheckType(1);//入户检查
                checkDanger.setCreateTime(new Date());
                checkDanger.setCreateBy(userId);
                checkDanger.setModifyTime(new Date());
                checkDanger.setModifyBy(userId);
                tempArrayList.add(checkDanger);
            }
        }

        for (CheckDanger c:tempArrayList) {
            checkDangerMapper.insert(c);
        }



        map.clear();
        map.put("成功","操作成功");
        return map;
    }

    public Map<String, Object> importDangerExce(HttpServletRequest request) throws Exception {
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
            int sheetNumber = wookbook.getNumberOfSheets();
            for (int s = 0;s<sheetNumber;s++){


            Sheet sheet = wookbook.getSheetAt(s);
            //获得表头
            Row rowHead = sheet.getRow(0);
            System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
            if (rowHead.getPhysicalNumberOfCells() == 5) {
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

                        //被检查用户
                        Cell cell = row.getCell((short)0);
                        if(null != cell) {
                            String userName = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(userName)) {
                                idm.setCheckUser(userName);
                            }
                        }

                        //街道
                        cell = row.getCell((short)1);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String street = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(street)){
                                if (street.equals("伏龙东街")){
                                    idm.setDirection(2);
                                }else if (street.equals("伏龙西街")){
                                    idm.setDirection(1);
                                }

                            }else{
                                excelRow.add(Rowforshow + "行街道未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行街道未检测到数据");
                        }

                        //楼栋号
                        cell = row.getCell((short)2);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String buildingNumber = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(buildingNumber)){
                                idm.setBuildingNumber(Integer.parseInt(buildingNumber));
                            }else{
                                excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                        }

                        //单元号
                        cell = row.getCell((short)3);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String unitName = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(unitName)){
                                idm.setUnitNumber(Integer.parseInt(unitName));
                            }else{
                                excelRow.add(Rowforshow + "行单元号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行单元号未检测到数据");
                        }


                        //楼层总数
                        cell = row.getCell((short)4);
                        if(null != cell) {
                            cell.setCellType(CellType.STRING);
                            String floorCount = cell.getStringCellValue();
                            if (StringUtils.isNotBlank(floorCount)){
                                Integer index = floorCount.indexOf("F");
                                String str = floorCount.substring(0,index);
                                idm.setFloorNumber(Integer.parseInt(str));
                            }else{
                                excelRow.add(Rowforshow + "行楼层号未检测到数据");
                            }
                        }else {
                            excelRow.add(Rowforshow + "行楼层号未检测到数据");
                        }

                        BuildFloor buildFloor = buildingMapper.importDangerFindForeign(idm.getDirection(),idm.getBuildingNumber(),idm.getUnitNumber(),idm.getFloorNumber());
                        idm.setBuildFloor(buildFloor);
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
            mapResult.put("importDangerModels",importDangerModels);

        }
        return mapResult;
    }

    /**
     * 导入隐患-模板
     *  地址（伏龙东街/伏龙西街）   楼栋号（栋）  单元  隐患问题    图片
     * @return
     */
    public void importDangerModel(HttpServletRequest request) throws IOException {
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
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            List<XSSFPictureData>picList = (List<XSSFPictureData>) workbook.getAllPictures();
            int sheetNumber = workbook.getNumberOfSheets();
            for (int s = 0;s<sheetNumber;s++){


                Sheet sheet = workbook.getSheetAt(s);
                //获得表头
                Row rowHead = sheet.getRow(0);
                System.out.println("getPhysicalNumberOfCells -> " + rowHead.getPhysicalNumberOfCells());
                if (rowHead.getPhysicalNumberOfCells() == 67) {
                    int totalRowNum = sheet.getLastRowNum();
                    if (totalRowNum > 0) {
                        for (int i = 5; i <= totalRowNum; i++) {
                            //获得第i行对象
                            Row row = sheet.getRow(i);
                            if (null == row) {
                                break;
                            }


                            //行数坐标
                            int Rowforshow = i + 1;
                            ImportDangerModel idm = new ImportDangerModel();
                            String uid = RandomNumber.getUUid();

                            //cell
                            Cell cell = row.getCell((short)0);


                            //街道
                            cell = row.getCell((short)2);
                            if(null != cell) {
                                cell.setCellType(CellType.STRING);
                                String street = cell.getStringCellValue();
                                if (StringUtils.isNotBlank(street)){
                                    if (street.equals("伏龙东街")){
                                        idm.setDirection(2);
                                    }else if (street.equals("伏龙西街")){
                                        idm.setDirection(1);
                                    }
                                }else{
                                    excelRow.add(Rowforshow + "行街道未检测到数据");
                                }
                            }else {
                                excelRow.add(Rowforshow + "行街道未检测到数据");
                            }


                            //楼栋号
                            cell = row.getCell((short)2);
                            if(null != cell) {
                                cell.setCellType(CellType.STRING);
                                String buildingNumber = cell.getStringCellValue();
                                if (StringUtils.isNotBlank(buildingNumber)){
                                    idm.setBuildingNumber(Integer.parseInt(buildingNumber));
                                }else{
                                    excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                                }
                            }else {
                                excelRow.add(Rowforshow + "行楼栋号未检测到数据");
                            }


                            //单元号
                            cell = row.getCell((short)3);
                            if(null != cell) {
                                cell.setCellType(CellType.STRING);
                                String unitName = cell.getStringCellValue();
                                if (StringUtils.isNotBlank(unitName)){
                                    idm.setUnitNumber(Integer.parseInt(unitName));
                                }else{
                                    excelRow.add(Rowforshow + "行单元号未检测到数据");
                                }
                            }else {
                                excelRow.add(Rowforshow + "行单元号未检测到数据");
                            }

                            int [] titleIndex = {15,24,32,37,43,48,52,56,60};
                            int [] imageIndex = {22,30,35,41,46,50,54,58,65};
                            for (int ti :titleIndex) {
                                int index = 0;
                                DangerProblem dangerProblem = new DangerProblem();
                                boolean result = isMergedRegion(sheet,3,ti);
                                if (result){
                                    String titleName = getMergedRegionValue(sheet,3,ti);
                                    dangerProblem.setDangerProblemName(titleName);
                                    XSSFPictureData picData = picList.get(cell.getRowIndex());
                                   // String key = xssfClientAnchor.getRow1() + "-" + xssfClientAnchor.getCol1();
                                    String key = "!";
                                    byte[] data =picData.getData();
                                    System.out.println("key  --> "+key);
                                    String fileName = key+"+"+RandomNumber.getUUid()+".png";
                                    String basePath = "D:/mnt/resource/fulong/uploadFile/fire/";
                                    File secondFolder = new File(basePath);
                                    if(!secondFolder.exists()){
                                        secondFolder.mkdirs();
                                    }
                                    FileOutputStream out = new FileOutputStream(basePath+fileName);
                                    out.write(data);
                                    out.close();
                                }
                                i++;
                            }


                            BuildFloor buildFloor = buildingMapper.importDangerFindForeign(idm.getDirection(),idm.getBuildingNumber(),idm.getUnitNumber(),idm.getFloorNumber());
                            if (null == buildFloor){
                                excelRow.add("街道或楼栋或单元不正确");
                            }
                            idm.setBuildFloor(buildFloor);
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
            }
            //存放
            mapResult.put("importDangerModels",importDangerModels);
        }
    }


    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    public static  boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public  static String getCellValue(Cell cell){
        if(cell == null){
            return "";
        }
        return cell.toString();
    }

    private  static Map<Integer, List<HSSFPictureData>> getPicMap(HSSFWorkbook wb) {


        Map<Integer, List<HSSFPictureData>> picMap = new HashMap<Integer, List<HSSFPictureData>>();
        List<HSSFPictureData> pictures = wb.getAllPictures();
        HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
        for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
            HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
            List<HSSFPictureData> list = new ArrayList<>();
            if (shape instanceof HSSFPicture) {
                HSSFPicture pic = (HSSFPicture) shape;
                HSSFPictureData picData = pictures.get(pic.getPictureIndex()-1);
                //如果通过本次获取的行能得到value，说明本行已存在，
                if (picMap.get(anchor.getRow1())!=null) {
                    //找到本行的集合
                    List<HSSFPictureData> list2 = picMap.get(anchor.getRow1());
//将图片存储进去
                    list2.add(picData);
//进入下一轮循环
                    continue;
                }
                //添加本次循环的图片对象
                list.add(picData);
                //将本张图片的行信息作为key存入map，将图片对象作为值存储
                picMap.put(anchor.getRow1(), list);
            } else {
                //非图片数据则插入null
                picMap.put(anchor.getRow1(), null);
            }
        }
        return picMap;
    }



}
