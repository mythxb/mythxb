package com.rzyc.fulongapi.controller;

import com.common.utils.DateUtils;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.encryption.AesEncryptUtil;
import com.common.utils.model.SingleResult;
import com.rzyc.fulongapi.mapper.BuildingMapper;
import com.rzyc.fulongapi.mapper.SysDocumentMapper;
import com.rzyc.fulongapi.model.*;
import com.rzyc.fulongapi.service.ImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

import java.io.FileOutputStream;
import java.util.*;

import static com.rzyc.fulongapi.service.ImportDanger.getMergedRegionValue;
import static com.rzyc.fulongapi.service.ImportDanger.isMergedRegion;


@Api(tags = "数据导入")
@CrossOrigin("*")
@RequestMapping("import")
@Controller
@Validated
public class ImportController extends BaseController{
    @Autowired
    ImportService importService;

    //文件接口
    @Autowired
    private SysDocumentMapper sysDocumentMapper;

    /**
     * 导入路由资源
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "导入路由资源", notes = "导入路由资源")
    @PostMapping("importSysResource")
    @ResponseBody
    @Transactional
    public void importSysResource(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> map = req.getFileMap();
        MultipartFile multipartFile = null;

        if (null != map && map.size() > 0) {
            for (Map.Entry<String, MultipartFile> entry : map.entrySet()) {
                multipartFile = entry.getValue();
            }
        }
        String fileName = multipartFile.getOriginalFilename();
        // 获取后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //先将.json文件转为字符串类型
        File file = new File("/"+ fileName);
        //将MultipartFile类型转换为File类型
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file);
        String jsonString = FileUtils.readFileToString(file, "UTF-8");
        importService.importResource(jsonString,null);
    }

    /**
     * 导入疫情数据
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入疫情数据", notes = "导入疫情数据")
    @RequestMapping(value = "/importEpidemic", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public SingleResult<String> importEpidemic(@RequestBody MultipartFile file)throws Exception{
        SingleResult<String> result = new SingleResult<>();
        List<Epidemic> epidemics = getEpidemic(file);

        //先删除今天的数据
        epidemicMapper.delToday();

        //在导入最新数据
        if(null != epidemics && epidemics.size() > 0){
            for (Epidemic epidemic : epidemics){
                epidemicMapper.insert(epidemic);
            }
        }
        return result;
    }


    /**
     * 解析excel 数据
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public List<Epidemic> getEpidemic(MultipartFile multipartFile)throws Exception{
        List<Epidemic> epidemics = new ArrayList<>();

        if(null != multipartFile){
            Workbook wookbook = WorkbookFactory.create(multipartFile.getInputStream());
            Sheet sheet = wookbook.getSheetAt(0);

            //获得表头
            Row rowHead = sheet.getRow(0);

            System.out.println("getPhysicalNumberOfCells -> "+rowHead.getPhysicalNumberOfCells());
            //判断表头是否正确
            if(true){
                //获得数据的总行数
                int totalRowNum = sheet.getLastRowNum();

                if(totalRowNum > 0){

                    Boolean isSubject = true;

                    //获得所有数据
                    for(int i = 3 ; i <= totalRowNum ; i++){
                        //获得第i行对象
                        Row row = sheet.getRow(i);
                        if(null == row){
                            break;
                        }

                        //类型转换
                        DataFormatter dataFormatter = new DataFormatter();

                        Epidemic epidemic = new Epidemic();
                        epidemic.setEpidemicId(RandomNumber.getUUid());
                        epidemic.setCreateBy("excel");
                        epidemic.setCreateTime(new Date());
                        epidemic.setModifyBy("excel");
                        epidemic.setModifyTime(new Date());

                        //街道名
                        String streetName = "";
                        Cell cell = row.getCell((short)1);
                        if(null != cell){
                            streetName = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setStreetName(streetName);

                        //社区名
                        String communityName = "";
                        cell = row.getCell((short)2);
                        if(null != cell){
                            communityName = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setCommunityName(communityName);

                        //姓名
                        String chinaName = "";
                        cell = row.getCell((short)3);
                        if(null != cell){
                            chinaName = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setChinaName(chinaName);

                        //性别
                        String sex = "";
                        cell = row.getCell((short)4);
                        if(null != cell){
                            sex = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setSex(sex);

                        //年龄
                        String age = "";
                        cell = row.getCell((short)5);
                        if(null != cell){
                            age = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setAge(age);

                        //身份证号
                        String cardId = "";
                        cell = row.getCell((short)6);
                        if(null != cell){
                            cardId = dataFormatter.formatCellValue(cell);
                        }
                        cardId = AesEncryptUtil.encrypt(cardId);
                        epidemic.setCardId(cardId);

                        //联系电话
                        String mobile = "";
                        cell = row.getCell((short)7);
                        if(null != cell){
                            mobile = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setMobile(mobile);

                        //境外/经由中高风险地区/重点地区
                        String keyArea = "";
                        cell = row.getCell((short)8);
                        if(null != cell){
                            keyArea = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setKeyArea(keyArea);

                        //14日内旅居史
                        String sojournHistory = "";
                        cell = row.getCell((short)9);
                        if(null != cell){
                            sojournHistory = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setSojournHistory(sojournHistory);

                        //旅居史省
                        String sojournProvince = "";
                        cell = row.getCell((short)10);
                        if(null != cell){
                            sojournProvince = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setSojournProvince(sojournProvince);

                        //旅居史市
                        String sojournCity = "";
                        cell = row.getCell((short)11);
                        if(null != cell){
                            sojournCity = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setSojournCity(sojournCity);

                        //旅居史区
                        String sojournDistrict = "";
                        cell = row.getCell((short)12);
                        if(null != cell){
                            sojournDistrict = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setSojournDistrict(sojournDistrict);

                        //详细地区
                        String sojournAddress = "";
                        cell = row.getCell((short)13);
                        if(null != cell){
                            sojournAddress = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setSojournAddress(sojournAddress);

                        //风险等级（低、中、高）
                        String sojournLevel = "";
                        cell = row.getCell((short)14);
                        if(null != cell){
                            sojournLevel = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setSojournLevel(sojournLevel);

                        //来蓉交通方式
                        String traffic = "";
                        cell = row.getCell((short)15);
                        if(null != cell){
                            traffic = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setTraffic(traffic);

                        //出发时间
                        String setoutTime = "";
                        cell = row.getCell((short)16);
                        if(null != cell){
                            setoutTime = DateUtils.parseDate2String(cell.getDateCellValue(),"yyyy-MM-dd");
                        }
                        epidemic.setSetoutTime(setoutTime);

                        //抵蓉时间
                        String arriveTime = "";
                        cell = row.getCell((short)17);
                        if(null != cell){
                            arriveTime = DateUtils.parseDate2String(cell.getDateCellValue(),"yyyy-MM-dd");
                        }
                        epidemic.setArriveTime(arriveTime);

                        //纳入社区管理时间
                        String managerTime = "";
                        cell = row.getCell((short)18);
                        if(null != cell){
                            managerTime = DateUtils.parseDate2String(cell.getDateCellValue(),"yyyy-MM-dd");
                        }
                        epidemic.setManagerTime(managerTime);

                        //户籍地址
                        String censusRegister = "";
                        cell = row.getCell((short)19);
                        if(null != cell){
                            censusRegister = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setCensusRegister(censusRegister);

                        //在蓉居住地址
                        String arriveAddress = "";
                        cell = row.getCell((short)20);
                        if(null != cell){
                            arriveAddress = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setArriveAddress(arriveAddress);

                        //最近一次核酸检测情况
                        String testingTime = "";
                        cell = row.getCell((short)21);
                        if(null != cell){
                            testingTime = DateUtils.parseDate2String(cell.getDateCellValue(),"yyyy-MM-dd");
                        }
                        epidemic.setTestingTime(testingTime);

                        //完成或未完成
                        String testingState = "";
                        cell = row.getCell((short)22);
                        if(null != cell){
                            testingState = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setTestingState(testingState);

                        //隔离状态
                        String isolateState = "";
                        cell = row.getCell((short)23);
                        if(null != cell){
                            isolateState = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setIsolateState(isolateState);

                        //居观开始时间
                        String startTime = "";
                        cell = row.getCell((short)24);
                        if(null != cell){
                            startTime = DateUtils.parseDate2String(cell.getDateCellValue(),"yyyy-MM-dd");
                        }
                        epidemic.setStartTime(startTime);

                        //社区服务姓名
                        String communityPerson = "";
                        cell = row.getCell((short)25);
                        if(null != cell){
                            communityPerson = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setCommunityPerson(communityPerson);

                        //社区服务联系电话
                        String communityMobile = "";
                        cell = row.getCell((short)26);
                        if(null != cell){
                            communityMobile = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setCommunityMobile(communityMobile);

                        //社区工作单位及职务
                        String communityWork = "";
                        cell = row.getCell((short)27);
                        if(null != cell){
                            communityWork = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setCommunityWork(communityWork);

                        //医疗卫生机构服务姓名
                        String medicalPerson = "";
                        cell = row.getCell((short)28);
                        if(null != cell){
                            medicalPerson = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setMedicalPerson(medicalPerson);

                        //医疗卫生机构服务联系电话
                        String medicalMobile = "";
                        cell = row.getCell((short)29);
                        if(null != cell){
                            medicalMobile = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setMedicalMobile(medicalMobile);

                        //医疗卫生机构工作单位及职务
                        String medicalWork = "";
                        cell = row.getCell((short)30);
                        if(null != cell){
                            medicalWork = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setMedicalWork(medicalWork);

                        //物业联系人
                        String propertyer = "";
                        cell = row.getCell((short)31);
                        if(null != cell){
                            propertyer = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setPropertyer(propertyer);

                        //物业联系电话
                        String propertyPhone = "";
                        cell = row.getCell((short)32);
                        if(null != cell){
                            propertyPhone = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setPropertyPhone(propertyPhone);

                        //街道联系人
                        String streeter = "";
                        cell = row.getCell((short)33);
                        if(null != cell){
                            streeter = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setStreeter(streeter);

                        //街道联系电话
                        String streetPhone = "";
                        cell = row.getCell((short)34);
                        if(null != cell){
                            streetPhone = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setStreetPhone(streetPhone);

                        //社区民警
                        String communityer = "";
                        cell = row.getCell((short)35);
                        if(null != cell){
                            communityer = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setCommunityer(communityer);

                        //社区民警联系电话
                        String communityPhone = "";
                        cell = row.getCell((short)36);
                        if(null != cell){
                            communityPhone = dataFormatter.formatCellValue(cell);
                        }
                        epidemic.setCommunityPhone(communityPhone);

                        System.out.println(chinaName+"---"+mobile);

                        epidemics.add(epidemic);

                    }

                }
            }
        }
        System.out.println("数据解析完成");

        return epidemics;
    }

    /**
     * 2022/10/24日
     * @author Xuwanxin
     * 导入消防隐患图片
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入消防隐患图片", notes = "导入消防隐患图片")
    @RequestMapping(value = "/fireDangerImg", method = RequestMethod.POST)
    @ResponseBody
    public void fireDangerImg(@RequestBody MultipartFile multipartFile)throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {}
            DataFormatter dataFormatter = new DataFormatter();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook("/mnt/rzyc/消防隐患导入.xlsx");
        //XSSFWorkbook xssfWorkbook = new XSSFWorkbook("C:/Users/79493/Documents/WeChat Files/wxid_0bhecv9rnvyq12/FileStorage/File/2022-10/消防隐患导入.xlsx");

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

        new Thread(()->{
            try {
                savePictures(sheet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }



    /**
     * 获取excel的图片
     * @version v1.0
     * @author dong
     * @date 2022/10/22 15:10
     */
    public  void savePictures(XSSFSheet xssfSheet)throws Exception{



        List<XSSFShape> list=xssfSheet.getDrawingPatriarch().getShapes();
        FileOutputStream out = null;
        for (XSSFShape shape:list){
            XSSFPicture picture = (XSSFPicture) shape;
            XSSFClientAnchor xssfClientAnchor=(XSSFClientAnchor) picture.getAnchor();
            XSSFPictureData pdata = picture.getPictureData(); // 行号-列号
            int row = xssfClientAnchor.getRow1();
            int col = xssfClientAnchor.getCol1();
            col = col - 1;
            boolean is  = isMergedRegion(xssfSheet,2,col);
            String  dangerName = null;
            if (is){
                  dangerName  = getMergedRegionValue(xssfSheet,2,col);
            }
            String key = xssfClientAnchor.getRow1() + "-" + xssfClientAnchor.getCol1();
            try {
                String  street = xssfSheet.getRow(row).getCell((short)1).getStringCellValue();
                Integer direciton = 0;
                if (street.equals("伏龙东街")){
                    direciton = 2 ;
                }else if (street.equals("伏龙西街")){
                    direciton = 1;
                }
                int  building = (int)xssfSheet.getRow(row).getCell((short)2).getNumericCellValue();
                int  unit = (int)xssfSheet.getRow(row).getCell((short)3).getNumericCellValue();
                System.out.println(street + "-" +building + "-" + unit + "-" +  dangerName + "-" + key);
                BuildUnit buildUnit = buildUnitMapper.selectBuildingAndUnitNumber(direciton,building,unit);
                if (null == buildUnit){
                    System.err.println(direciton  + "-" + building + "-" +unit + "数据错误");
                    continue;
                }

                //插入隐患
                String uuid = RandomNumber.getUUid();
                CheckDanger checkDanger = new CheckDanger();
                checkDanger.setId(uuid);
                checkDanger.setCheckItem(dangerName);
                checkDanger.setNormalorimportant(1);
                checkDanger.setRectificationState(2);
                //这里写死公共区域其他安全问题
                checkDanger.setDangerTypeId("6dcb0c0f-a40b-11ec-8123-00163e0c1c70");
                checkDanger.setBuildingId(buildUnit.getBuildingId());
                checkDanger.setBuildingUnitId(buildUnit.getUnitId());
                //这里写死检查类型公共区域检查
                checkDanger.setCheckType(2);
                checkDanger.setCreateBy("系统导入");
                checkDanger.setCreateTime(new Date());
                checkDangerMapper.insert(checkDanger);

                String basePath = "/resource/fulong/uploadFile/fire/";
                String fileName = key+"+"+uuid+".png";


                byte[] data =pdata.getData();
                File secondFolder = new File(basePath);
                if(!secondFolder.exists()){
                    secondFolder.mkdirs();
                }
                out = new FileOutputStream(basePath+fileName);
                out.write(data);

                SysDocument sysDocument = new SysDocument();
                sysDocument.setDocumentId(RandomNumber.getUUid());
                sysDocument.setTargetId(uuid);
                //默认整改前
                sysDocument.setTargetType("APPRectifyImgBefore");
                sysDocument.setFilePath(basePath + fileName);
                sysDocument.setName(fileName);
                sysDocument.setFileType(1);
                sysDocument.setCreateTime(new Date());
                sysDocumentMapper.insert(sysDocument);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                out.close();
            }

        }
    }

    /**
     * 导入消防隐患
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "导入消防隐患", notes = "导入消防隐患")
    @RequestMapping(value = "/fireDanger", method = RequestMethod.POST)
    @ResponseBody
    public SingleResult<String> fireDanger(@RequestBody MultipartFile multipartFile)throws Exception {
        SingleResult<String> result = new SingleResult<>();
        if (null != multipartFile) {
            DataFormatter dataFormatter = new DataFormatter();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(multipartFile.getInputStream());
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

            for (int i=4;i<=sheet.getLastRowNum();i++){
                System.out.println("index ------------------- > "+i);

                Row row = sheet.getRow(i);

                Cell cell = row.getCell(1);
                if(null == cell){
                    continue;
                }

                String streetName = dataFormatter.formatCellValue(cell);

                cell = row.getCell(2);
                String buildNum = dataFormatter.formatCellValue(cell);

                cell = row.getCell(3);
                String unitNum = dataFormatter.formatCellValue(cell);
                //21
                System.out.println(streetName+" - "+buildNum+" - "+unitNum);

                System.out.println();
            }
        }
        return new SingleResult<String>();
    }





}
