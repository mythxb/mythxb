package com.rzyc.fulongapi.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.api.R;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.encryption.AesEncryptUtil;
import com.common.utils.encryption.MD5;
import com.common.utils.jwt.JwtUtil;
import com.common.utils.model.Pager;
import com.common.utils.model.SingleResult;
import com.common.utils.orcode.QRCodeUtil;
import com.common.utils.upload.FileUpload;
import com.github.pagehelper.Page;
import com.rzyc.fulongapi.bean.check.DangerList;
import com.rzyc.fulongapi.config.ConstantsConfigure;
import com.rzyc.fulongapi.enums.RiskLevel;
import com.rzyc.fulongapi.enums.UserType;
import com.rzyc.fulongapi.mapper.*;
import com.rzyc.fulongapi.model.*;
import com.rzyc.fulongapi.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.*;

@Repository
public class BaseController {

    //楼栋
    @Autowired
    protected BuildingMapper buildingMapper;

    //单元
    @Autowired
    protected BuildUnitMapper buildUnitMapper;

    //楼层
    @Autowired
    protected BuildFloorMapper buildFloorMapper;

    //隐患
    @Autowired
    protected CheckDangerMapper checkDangerMapper;

    //单元多户
    @Autowired
    protected BuildUnitHouseholderMapper buildUnitHouseholderMapper;

    //消防器材
    @Autowired
    protected FireEquipmentMapper fireEquipmentMapper;

    //消防器材分类
    @Autowired
    protected FireEquipmentCategoryMapper fireEquipmentCategoryMapper;

    //消防站
    @Autowired
    protected FireStationMapper fireStationMapper;

    //用户
    @Autowired
    protected SysUserMapper sysUserMapper;

    //新闻
    @Autowired
    protected NewsMapper newsMapper;

    //新闻内容
    @Autowired
    protected NewsContentMapper newsContentMapper;

    //检查类型
    @Autowired
    protected DangerTypeMapper dangerTypeMapper;

    //检查项
    @Autowired
    protected CheckItemMapper checkItemMapper;

    //检查清单
    @Autowired
    protected CheckListMapper checkListMapper;

    //检查清单项
    @Autowired
    protected CheckListItemMapper checkListItemMapper;

    //检查
    @Autowired
    protected ChecksMapper checksMapper;

    //检查项
    @Autowired
    protected CheckDescMapper checkDescMapper;

    //企业
    @Autowired
    protected EnterpriseMapper enterpriseMapper;

    //分类
    @Autowired
    protected StoreTypeMapper storeTypeMapper;

    //组织架构
    @Autowired
    protected OrganizationMapper organizationMapper;

    //用户楼栋、单元关系表
    @Autowired
    protected SysUserBuildMapper sysUserBuildMapper;


    //配置文件
    @Autowired
    protected ConstantsConfigure constantsConfigure;

    //浏览器对象
    @Autowired
    protected HttpServletRequest request;

    //路由资源
    @Autowired
    protected SysResourceMapper sysResourceMapper;

    //架构
    @Autowired
    protected SysFrameworkMapper sysFrameworkMapper;

    //路由角色资源
    @Autowired
    protected SysRoleResourceMapper sysRoleResourceMapper;

    //特殊建筑体
    @Autowired
    protected SpecialBuildingMapper specialBuildingMapper;

    //住户
    @Autowired
    protected BuildingResidentMapper buildingResidentMapper;

    //燃气设备
    @Autowired
    protected GasEquipmentMapper gasEquipmentMapper;

    //问题上报
    @Autowired
    protected QuestionSubmitMapper questionSubmitMapper;

    //疫情数据
    @Autowired
    protected EpidemicMapper epidemicMapper;

    //疫情历史数据
    @Autowired
    protected EpidemicHistoryMapper epidemicHistoryMapper;

    //日志
    @Autowired
    protected LogMapper logMapper;

    //角色
    @Autowired
    protected RoleMapper roleMapper;
    //出警记录
    @Autowired
    protected FireRecordMapper fireRecordMapper;

    //出警记录
    @Autowired
    protected BuildUnitLevelMapper buildUnitLevelMapper;


    //验证码KEY
    public String GENERATE_CODE_KEY = "VERIFY_FULONG_CODE_RESULT";


    /*密码正则*/
    public static String PASSWORD_REGULAR = "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{6,}$";


    //三类较大隐患
    protected List<String> dangerTypeIds = new ArrayList<>();

    {
        //连接软管
        dangerTypeIds.add("b5b7da2a-1f2e-4fe9-9828-3813956dc81c");
        //灶具
        dangerTypeIds.add("95c924f2-995e-4827-9aa2-b0d684ae8333");
        //热水器
        dangerTypeIds.add("1df42d61-61c3-46e2-9aca-81b2ddd9ed82");
    }


    /**
     * 新都文件地址处理
     *
     * @param url
     * @return
     */
    protected String delServiceFile(String url) {
//        url = url.replace(Constants.SERVICE_FILE_HEADER, "");
        url = url.replace(constantsConfigure.getServiceFileHeader(), "");
        return url;
    }

    /**
     * 新都文件地址处理
     *
     * @param url
     * @return
     */
    protected String setServiceFile(String url) {
//        url = Constants.SERVICE_FILE_HEADER + url;
        url = constantsConfigure.getServiceFileHeader() + url;
        return url;
    }


    /**
     * 统一处理分页
     *
     * @param pager api返回的对象
     * @param page  分页插件对象
     * @throws Exception
     */
    protected void getDatePage(Pager pager, Page page) throws Exception {
        pager.setTotal(page.getTotal());
        pager.setPage(page.getPages());
        pager.setPageSize(page.getPageSize());
        pager.setRows(page.getResult());
    }

    /**
     * 创建二维码
     *
     * @throws Exception
     */
    public String createQrCode(Map<String, String> entMap) throws Exception {
        String content = JSONArray.toJSONString(entMap);
        InputStream is = QRCodeUtil.getBase64QRCode(content);
        String name = RandomNumber.randomUUidPK();
        name += ".png";
        String url = "";
        url = FileUpload.uploadFile(is, name, constantsConfigure.getFileHeader(), constantsConfigure.getFileLocation());
        return url;
    }

    /**
     * 没有隐患为蓝色风险
     * 存在隐患
     * 如果隐患是三类较大隐患 则为橙色风险 否则为黄色风险
     *
     * @param checkDangers
     * @return
     * @throws Exception
     */
    public Integer getRiskLevel(List<CheckDanger> checkDangers) throws Exception {

        Integer riskLevel = RiskLevel.YELLOW.getLevel();
        if (null != checkDangers && checkDangers.size() > 0) {
            for (CheckDanger checkDanger : checkDangers) {
                if (dangerTypeIds.contains(checkDanger.getDangerTypeId())) {
                    riskLevel = RiskLevel.ORANGE.getLevel();
                    break;
                }
            }
        } else {
            riskLevel = RiskLevel.BLUE.getLevel();
        }
        return riskLevel;
    }

    /**
     * 修改单元风险等级
     *
     * @param unitId
     * @throws Exception
     */
    public void unitRiskLevel(String unitId) throws Exception {
        List<CheckDanger> checkDangers = checkDangerMapper.findByUnitId(unitId);
        Integer riskLevel = getRiskLevel(checkDangers);
        buildUnitMapper.changeRiskLevel(unitId, riskLevel);
    }

    /**
     * 修改楼栋风险等级
     *
     * @param buildId
     * @throws Exception
     */
    public void bulidRiskLevel(String buildId) throws Exception {
        List<CheckDanger> checkDangers = checkDangerMapper.findByBuildId(buildId);
        Integer riskLevel = getRiskLevel(checkDangers);
        buildingMapper.changeRiskLevel(buildId, riskLevel);
    }

    /**
     * 修改单元楼层风险等级
     * @param floorId
     * @throws Exception
     */
    /**
     * 通过userToken获取用户信息
     *
     * @return
     * @throws Exception
     */
    public void floorRiskLevel(String floorId) throws Exception {
        List<CheckDanger> checkDangers = checkDangerMapper.findByFloorId(floorId);
        Integer riskLevel = getRiskLevel(checkDangers);
        buildFloorMapper.changeRiskLevel(floorId, riskLevel);
    }


    protected String getUserId() throws Exception {
        String userId = "";
        String userToken = request.getHeader("userToken");
        if (StringUtils.isNotBlank(userToken)) {
            userToken = userToken.replaceAll("Bearer ", "");
            userId = JwtUtil.getTokenMsg(userToken);
        }
        return userId;
    }


    /**
     * 修改企业风险等级
     *
     * @param entId
     * @throws Exception
     */
    public void entRiskLevel(String entId) throws Exception {
        List<CheckDanger> checkDangers = checkDangerMapper.findByFloorId(entId);
        Integer riskLevel = getRiskLevel(checkDangers);
        enterpriseMapper.changeRiskLevel(entId, riskLevel);
    }

    /**
     * 查询用户的楼栋或者单元
     *
     * @param userId
     * @param userType
     * @return
     * @throws Exception
     */
    public List<String> getUserTargetId(String userId, Integer userType) throws Exception {
        List<String> targetIds = new ArrayList<>();
        List<SysUserBuild> userBuilds = sysUserBuildMapper.findByUserId(userId, userType);
        if (null != userBuilds && userBuilds.size() > 0) {
            for (SysUserBuild userBuild : userBuilds) {
                targetIds.add(userBuild.getTargetId());
            }
        }
        return targetIds;
    }

    /**
     * 对加密过后的身份证号进行解密，并隐藏出生年月日
     *
     * @param encryptNumber
     * @return
     * @throws Exception
     */
    public String idNumberDesEncrypt(String encryptNumber) throws Exception {
        if (StringUtils.isNotBlank(encryptNumber)) {
            String idNumber = "";
            try{
                idNumber = AesEncryptUtil.desEncrypt(encryptNumber);
            }catch (Exception e){
                idNumber = encryptNumber;
            }
            StringBuilder str = new StringBuilder(idNumber);
            if(str.length() > 6){
                str =  str.replace(6, 14, "********");
            }
            return str.toString();
        } else {
            return "";
        }
    }


    /**
     * 对加密过后的身份证号进行解密，并隐藏出生年月日
     *
     * @param encryptNumber
     * @return
     * @throws Exception
     */
    public String idNumberDesEncryptV1(String encryptNumber) throws Exception {
        if (StringUtils.isNotBlank(encryptNumber)) {

            try{
                String idNumber = AesEncryptUtil.desEncrypt(encryptNumber);
                StringBuilder str = new StringBuilder(idNumber);
                str =  str.replace(6, 14, "********");
                return str.toString();
            }catch (Exception e){
                StringBuilder str = new StringBuilder(encryptNumber);
                str = str.replace(6, 14, "********");
                return str.toString();
            }

        } else {
            return "";
        }
    }

    /**
     * 处理身份证号
     * @param epidemics
     * @throws Exception
     */
    protected void handleEpidemic(List<Epidemic> epidemics)throws Exception{
        if(null != epidemics && epidemics.size() > 0){
            for (Epidemic epidemic : epidemics){
                epidemic.setCardId(idNumberDesEncrypt(epidemic.getCardId()));
                epidemic.setMobile(StringUtils.replaceStr(epidemic.getMobile(),4,8));
            }
        }
    }

    /**
     * 创建企业用户
     * @param enterpriseId
     * @throws Exception
     */
    public void addEntUser(String enterpriseId)throws Exception{


        //登录用户id
    String loginId = getUserId();

        Enterprise enterprise = enterpriseMapper.selectById(enterpriseId);
        if(null != enterprise && StringUtils.isNotBlank(enterprise.getContactMobile())){



            String mobile = enterprise.getContactMobile();
            mobile = StringUtils.replaceBlank(mobile);
            SysUser sysUser = sysUserMapper.findByEnterpriseId(enterprise.getEnterpriseId());
            if(null != sysUser){

                SysUser user = sysUserMapper.findNotUserId(mobile,sysUser.getUserId());
                if(null == user){

                    user = new SysUser();
                    user.setUserId(sysUser.getUserId());
                    user.setUserAccount(mobile);
                    sysUserMapper.updateById(user);
                }
            }else{
                String userId = RandomNumber.getUUid();
                SysUser user = sysUserMapper.findNotUserId(mobile,userId);
                if(null == user){
                    user = new SysUser();
                    user.setUserId(userId);
                    user.setUserName(enterprise.getEntName());
                    user.setUserAccount(mobile);
                    user.setUserPassword(MD5.md5(user.getUserId() + mobile));
                    user.setUserPhone(mobile);
                    user.setUserType(UserType.ENT.getType());
                    user.setEnterpriseId(enterprise.getEnterpriseId());
                    user.setCreateTime(new Date());
                    user.setModifyTime(new Date());
                    user.setCreateBy(loginId);
                    user.setModifyBy(loginId);
                    sysUserMapper.insert(user);
                }
            }
        }

    }

    /**
     * 处理隐患信息
     * @param dangerLists
     * @throws Exception
     */
    public void handleDnagerInfo(List<DangerList> dangerLists)throws Exception{
        if(null != dangerLists && dangerLists.size() > 0){
            for (DangerList dangerList : dangerLists){
                if(StringUtils.isBlank(dangerList.getFloorNumber())){
                    dangerList.setFloorNumber("--");
                }

                if(StringUtils.isBlank(dangerList.getUnitNumber())){
                    dangerList.setUnitNumber("---");
                }
            }
        }
    }


}
