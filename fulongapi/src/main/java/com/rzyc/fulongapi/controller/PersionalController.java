package com.rzyc.fulongapi.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.common.utils.Constants;
import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.encryption.AesEncryptUtil;
import com.common.utils.encryption.MD5;
import com.common.utils.jwt.JwtUtil;
import com.common.utils.model.*;
import com.common.utils.verification.Verification;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rzyc.fulongapi.bean.user.PcLoginDto;
import com.rzyc.fulongapi.bean.user.WechatLoginDto;
import com.rzyc.fulongapi.bean.user.dto.*;
import com.rzyc.fulongapi.enums.UserType;
import com.rzyc.fulongapi.intercept.LoginAuth;
import com.rzyc.fulongapi.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.*;

@Api(tags = "用户系统")
@CrossOrigin("*")
@RequestMapping("persional")
@Controller
@Validated
public class PersionalController extends BaseController {


    @ApiOperation(value = "验证码", notes = "验证码")
    @GetMapping("/generateCode")
    @ResponseBody
    public void generateCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            BufferedImage image = Verification.getVerify(GENERATE_CODE_KEY, request);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ApiOperation(value = "PC登录", notes = "PC登录")
    @PostMapping(value = "/pcLogin")
    @ResponseBody
    public SingleResult<SysUser> pcLogin(@Valid PcLoginDto pcLoginDto) throws Exception {
        SingleResult<SysUser> result = new SingleResult<>();

        String userName = pcLoginDto.getSysusername();
//        userName = AesEncryptUtil.desEncrypt(userName);
        String syspassword = pcLoginDto.getSyspassword();
//        syspassword = AesEncryptUtil.desEncrypt(userName);
        System.out.println("sessionID1：" + request.getSession().getId());

        String generateCode = request.getSession().getAttribute(GENERATE_CODE_KEY) + "";
        //验证码只能使用一次
        request.getSession().removeAttribute(GENERATE_CODE_KEY);

        if (pcLoginDto.getGenerateCode().equals(generateCode)) {
            SysUser sysUser = sysUserMapper.findByGovAccount(userName, UserType.ENT.getType());

            if (null != sysUser) {
                String paswodStr = MD5.md5(sysUser.getUserId() + syspassword);
                System.out.println("paswodStr -> " + paswodStr);

                if (sysUser.getUserPassword().equals(paswodStr)) {

                    sysUser.setUserPassword("");

                    //获取用户令牌
                    String userToken = JwtUtil.createToken(sysUser.getUserId());
                    sysUser.setUserToken(userToken);

                    result.setData(sysUser);
                } else {
                    result.setCode(Code.PASSWORD_ERROR.getCode());
                    result.setMessage(Message.PASSWORD_ERROR);
                }
            } else {
                result.setCode(Code.PASSWORD_ERROR.getCode());
                result.setMessage(Message.PASSWORD_ERROR);
            }
        } else {
            result.setCode(Code.CODE_ERROT.getCode());
            result.setMessage(Message.CODE_ERROT);
        }
        return result;
    }

    @ApiOperation(value = "小程序登录", notes = "小程序登录")
    @PostMapping(value = "/wechatLogin")
    @ResponseBody
    public SingleResult<SysUser> wechatLogin(@Valid @RequestBody WechatLoginDto pcLoginDto) throws Exception {
        SingleResult<SysUser> result = new SingleResult<>();

        String userName = pcLoginDto.getSysusername();
        String syspassword = pcLoginDto.getSyspassword();
        SysUser sysUser = sysUserMapper.findByAccount(userName);
        if (null != sysUser) {
            String paswodStr = MD5.md5(sysUser.getUserId() + syspassword);
            System.out.println("paswodStr -> " + paswodStr);

            if (sysUser.getUserPassword().equals(paswodStr)) {

                sysUser.setUserPassword("");

                //获取用户令牌
                String userToken = JwtUtil.createToken(sysUser.getUserId());
                sysUser.setUserToken(userToken);

                result.setData(sysUser);
            } else {
                result.setCode(Code.PASSWORD_ERROR.getCode());
                result.setMessage(Message.PASSWORD_ERROR);
            }
        } else {
            result.setCode(Code.PASSWORD_ERROR.getCode());
            result.setMessage(Message.PASSWORD_ERROR);
        }
        return result;
    }


    /**
     * 修改密码
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PostMapping(value = "changePasswd")
    @ResponseBody
    public SingleResult<String> changePasswd(@Valid @RequestBody ChangePasswdDto changePasswdDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        SysUser sysUser = sysUserMapper.selectById(changePasswdDto.getUserId());
        if (null != sysUser) {
            if (changePasswdDto.getNowPasswd().matches(PASSWORD_REGULAR)) {
                String oldPasswd = MD5.md5(sysUser.getUserId() + changePasswdDto.getOldPasswd());
                if (sysUser.getUserPassword().equals(oldPasswd)) {
                    String nowPasswd = MD5.md5(sysUser.getUserId() + changePasswdDto.getNowPasswd());
                    sysUserMapper.changePasswd(sysUser.getUserId(), nowPasswd);
                } else {
                    result.setCode(Code.PASSWORD_ERROR.getCode());
                    result.setMessage(Message.PASSWORD_ERROR);
                }
            } else {
                result.setCode(Code.ERROR.getCode());
                result.setMessage(Message.PASSWORD);
            }

        } else {
            result.setCode(Code.NO_DATA.getCode());
            result.setMessage(Message.NO_DATA);
        }
        return result;
    }

    /**
     * 用户组织架构
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "用户组织架构列表", notes = "用户组织架构列表")
    @GetMapping(value = "orgList/{userId}")
    @ResponseBody
    public MultiResult<Organization> orgList(@PathVariable String userId) throws Exception {
        MultiResult<Organization> result = new MultiResult<>();
        //用户
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (null != sysUser) {
            String organizationId = sysUser.getOrganization();
            //用户岗位
            Organization userPost = organizationMapper.selectById(organizationId);
            //用户岗位
            Organization userUnit = organizationMapper.selectById(userPost.getParentId());
            if (null != userUnit) {
                //部门下组织架构
                List<Organization> organizations = organizationMapper.findByParentId(userUnit.getOrganizationId());
                organizations.add(userUnit);
                JSONArray jsonArray = organizationTree(organizations);
                organizations = JSONArray.parseArray(jsonArray.toJSONString(), Organization.class);
                result.setData(organizations);
            }
        }

        return result;
    }


    /**
     * 组织架构树形结构
     *
     * @param organizations
     * @return
     * @throws Exception
     */
    private JSONArray organizationTree(List<Organization> organizations) throws Exception {
        //以map的形式存储集合
        List<Map<String, Object>> data = new ArrayList<>();
        //遍历地区列表
        for (Organization organization : organizations) {
            if (StringUtils.isBlank(organization.getParentId())) {
                organization.setParentId("");
            }
            //将地区编码、地区名、地区等级、父级地区id存进集合
            Map<String, Object> entUserMap = new HashMap<String, Object>();
            entUserMap.put("organizationId", organization.getOrganizationId());
            entUserMap.put("organizationName", organization.getOrganizationName());
            entUserMap.put("parentId", organization.getParentId());
            entUserMap.put("organizationType", organization.getOrganizationType());
            entUserMap.put("personName", organization.getPersonName());
            entUserMap.put("jobContent", organization.getJobContent());
            data.add(entUserMap);
        }
        //转为树状结构
        JSONArray result = TypeConversion.listToTree(JSONArray.parseArray(JSON.toJSONString(data)), "organizationId", "parentId", "children");
        return result;
    }

    /**
     * 下级组织架构
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "下级组织架构", notes = "下级组织架构")
    @GetMapping(value = "orgChildren/{organizationId}")
    @ResponseBody
    public MultiResult<Organization> orgChildren(@PathVariable String organizationId) throws Exception {
        MultiResult<Organization> result = new MultiResult<>();
        List<Organization> organizations = organizationMapper.findByParentId(organizationId);
        result.setData(organizations);
        return result;
    }

    @ApiOperation(value = "组织架构新增修改", notes = "组织架构新增修改")
    @PostMapping(value = "orgChange")
    @ResponseBody
    public SingleResult<String> orgChange(@Valid @RequestBody OrgChangeDto orgChangeDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();

        Organization organization = new Organization();
        BeanUtils.copyProperties(organization, orgChangeDto);

        String userId = getUserId();

        organization.setModifyBy(userId);
        organization.setModifyTime(new Date());

        if (StringUtils.isNotBlank(organization.getOrganizationId())) {
            //修改
            organizationMapper.updateById(organization);
        } else {
            //新增
            organization.setOrganizationId(RandomNumber.getUUid());
            organization.setCreateBy(userId);
            organization.setCreateTime(new Date());
            organizationMapper.insert(organization);
        }
        return result;
    }

    @ApiOperation(value = "用户列表", notes = "用户列表")
    @PostMapping(value = "userPage")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", defaultValue = "1", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", defaultValue = "10", required = true, dataType = "int"),
            @ApiImplicitParam(name = "userName", value = "userName", dataType = "string"),
            @ApiImplicitParam(name = "condition", value = "condition", dataType = "string"),
    })
    public SingleResult<List<SysUser>> userPage(Integer page, Integer pageSize, String userName, String condition) throws Exception {
        SingleResult<List<SysUser>> result = new SingleResult<>();
        String userNameCondition = TypeConversion.getCondition(userName);
        String conditions = TypeConversion.getCondition(condition);
        page = pageSize * (page - 1);
        List<SysUser> userList = sysUserMapper.selectUserList(page, pageSize, userNameCondition, conditions);
        long count = sysUserMapper.selectUserCount(userNameCondition, conditions);

        for (SysUser sysUser : userList) {
            //将号码中4位隐藏
            if (sysUser.getUserPhone() != null && sysUser.getUserPhone().length() > 3 && sysUser.getUserPhone().length() <= 11) {
                sysUser.setUserPhone(new StringBuilder(sysUser.getUserPhone()).replace(3, 7, "****").toString());
            }
        }

        result.setData(userList);
        result.setCount(count);
        return result;
    }

    @ApiOperation(value = "新增/修改用户信息", notes = "新增/修改用户信息")
    @PostMapping(value = "addOrEditUserInfo")
    @ResponseBody
    public SingleResult<String> addOrEditUserInfo(@Valid @RequestBody UserChangeDto userChangeDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();

        SysUser user = new SysUser();
        BeanUtils.copyProperties(user, userChangeDto);

        //操作人
        String userId = getUserId();
        user.setModifyBy(userId);
        user.setModifyTime(new Date());

        String changeUserId = userChangeDto.getUserId();
        String pwd = userChangeDto.getUserPassword();

        if (StringUtils.isNotBlank(changeUserId)) {
            SysUser sysUser = sysUserMapper.selectById(changeUserId);
            if (sysUser != null) {
                // 修改
                SysUser users = sysUserMapper.getByName(userChangeDto.getUserName());
                // 前端传来的id不等于这个名字查找出来的id，则说明名字重复
                if (users != null && (!users.getUserId().equals(changeUserId))) {
                    result.setCode(Code.HAS_USER.getCode());
                    result.setMessage("账户名重复");
                    return result;
                }
                if (!pwd.equals(sysUser.getUserPassword())) {
                    user.setUserPassword(MD5.md5(sysUser.getUserId() + pwd));
                }
                sysUserMapper.updateById(user);
            } else {
                result.setCode(Code.EX_PARAM.getCode());
                result.setMessage("参数错误");
            }
        } else {
            SysUser users = sysUserMapper.getByName(userChangeDto.getUserName());
            if (users != null) {
                result.setCode(Code.HAS_USER.getCode());
                result.setMessage("账户名重复");
                return result;
            } else {
                user.setCreateBy(userId);
                user.setCreateTime(new Date());
                user.setUserId(RandomNumber.getUUid());
                user.setUserPassword(MD5.md5(user.getUserId() + pwd));
                sysUserMapper.insert(user);
            }
        }

        return result;
    }


    @ApiOperation(value = "角色列表(不传分页查询全部)", notes = "角色列表(不传分页查询全部)")
    @GetMapping(value = "roleList")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false, dataType = "int"),
            @ApiImplicitParam(name = "condition", value = "condition", dataType = "string"),
    })
    public SingleResult roleList(Integer page, Integer pageSize, String condition) throws Exception {
        SingleResult singleResult = new SingleResult();
        page = pageSize * (page - 1);
        String conditions = TypeConversion.getCondition(condition);
        List<Role> roles = roleMapper.selectRoleList(page, pageSize, conditions);
        long roleCount = roleMapper.selectRoleCount(conditions);
        singleResult.setCount(roleCount);
        singleResult.setData(roles);
        return singleResult;
    }

    @ApiOperation(value = "新建/修改角色", notes = "新建/修改角色")
    @PostMapping(value = "addOrEditRoleList")
    @ResponseBody
    public SingleResult addOrEditRoleList(@RequestBody Role role) throws Exception {
        SingleResult singleResult = new SingleResult();

        List<Role> roleName = roleMapper.getByName(role.getRoleName());
        if (roleName.size() == 0) {
            if (StringUtils.isNotBlank(role.getRoleId())) {
                role.setModifyBy(getUserId());
                role.setModifyTime(new Date());
                roleMapper.updateById(role);
            } else {
                role.setCreateBy(getUserId());
                role.setCreateTime(new Date());
                roleMapper.insert(role);
            }
        } else {
            singleResult.setCode(Code.HAS_USER.getCode());
            singleResult.setMessage("角色名字重复！");
        }
        return singleResult;
    }

    @ApiOperation(value = "角色查询", notes = "角色查询")
    @GetMapping(value = "getRole")
    @ResponseBody
    public SingleResult getRole() throws Exception {
        SingleResult result = new SingleResult();
        result.setData(roleMapper.getRole());
        return result;
    }

    /**
     * 角色配置权限路由
     */
    @ApiOperation(value = "角色配置权限路由", notes = "角色配置权限路由")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "string")
    })
    @PostMapping("/editForward")
    @ResponseBody
    @Transactional
    //@LoginAuth
    public SingleResult<Pager> editForward(String roleId, @RequestBody List<String> resourceId) throws Exception {
        SingleResult singleResult = new SingleResult();
        sysRoleResourceMapper.deleteByRoleId(roleId);
        for (String s : resourceId) {
            SysRoleResource record = new SysRoleResource();
            record.setRoleResourceId(RandomNumber.getUUid());
            record.setRoleId(roleId);
            record.setResourceId(s);
            record.setModified(getUserId());
            record.setCreated(getUserId());
            record.setModifyTime(new Date());
            record.setCreateTime(new Date());
            sysRoleResourceMapper.insert(record);
        }
        return singleResult;
    }


    /**
     * 所有资源路由(树形结构)
     */
    @ApiOperation(value = "所有资源路由(树形结构)", notes = "所有资源路由(树形结构)")
    @GetMapping("/allResourceOfTree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = false, dataType = "string")
    })
    @ResponseBody
    @Transactional
    //@LoginAuth
    public SingleResult<String> allResourceOfTree(String roleId) throws Exception {
        SingleResult singleResult = new SingleResult();
        List<SysResource> sysResources = sysResourceMapper.allResourceOfTree();
        List<SysRoleResource> sysRoleResources = sysRoleResourceMapper.selectResource(roleId);
        List<Map<String, Object>> data = new ArrayList<>();
        for (SysResource r : sysResources) {
            Map<String, Object> entUserMap = new HashMap<String, Object>();
            for (SysRoleResource f : sysRoleResources) {
                if (f.getResourceId().equals(r.getResourceId())) {
                    entUserMap.put("check", true);
                }
            }
            if (StringUtils.isBlank(r.getParentId())) {
                r.setParentId("");
            }
            Map<String, String> metaMap = JSONArray.parseObject(r.getMetas(), HashMap.class);

            entUserMap.put("resourceId", r.getResourceId());
            entUserMap.put("name", r.getName());
            entUserMap.put("parentId", r.getParentId());
            entUserMap.put("metas", metaMap);
            entUserMap.put("sortId", r.getSortId());
            entUserMap.put("hiddens", r.getHiddens());
            entUserMap.put("path", r.getPath());
            entUserMap.put("component", r.getComponent());
            if (!"".equals(r.getAlwaysShow())) {
                Map<String, String> showMap = JSONArray.parseObject(r.getAlwaysShow(), HashMap.class);
                entUserMap.put("alwaysShow", showMap);
            }
            data.add(entUserMap);
        }
        JSONArray result = TypeConversion.listToTree(JSONArray.parseArray(JSON.toJSONString(data)), "resourceId", "parentId", "children");
        singleResult.setData(result);
        return singleResult;
    }

    /**
     * 单个用户的资源路由(树形结构)
     */
    @ApiOperation(value = "单个用户的资源路由(树形结构)", notes = "单个用户的资源路由(树形结构)")
    @GetMapping("/oneResourceOfTree")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, dataType = "string")
    })
    @ResponseBody
    @Transactional
    @LoginAuth
    public SingleResult<String> oneResourceOfTree(String roleId) throws Exception {
        SingleResult singleResult = new SingleResult();
        List<SysResource> sysResources = sysResourceMapper.allResourceOfTree();
        List<SysRoleResource> sysRoleResources = sysRoleResourceMapper.selectResource(roleId);
        List<Map<String, Object>> data = new ArrayList<>();
        for (SysResource r : sysResources) {
            Map<String, Object> entUserMap = new HashMap<String, Object>();
            if (StringUtils.isBlank(r.getParentId())) {
                r.setParentId("");
            }
            for (SysRoleResource f : sysRoleResources) {
                if (f.getResourceId().equals(r.getResourceId())) {
                    entUserMap.put("check", true);
                    Map<String, String> metaMap = JSONArray.parseObject(r.getMetas(), HashMap.class);

                    entUserMap.put("resourceId", r.getResourceId());
                    entUserMap.put("name", r.getName());
                    entUserMap.put("parentId", r.getParentId());
                    entUserMap.put("metas", metaMap);
                    entUserMap.put("sortId", r.getSortId());
                    entUserMap.put("hiddens", r.getHiddens());
                    entUserMap.put("path", r.getPath());
                    entUserMap.put("component", r.getComponent());
                    if (!"".equals(r.getAlwaysShow())) {
                        System.out.println("alwaysShow -> "+r.getAlwaysShow());
                        Map<String, String> showMap = JSONArray.parseObject(r.getAlwaysShow(), HashMap.class);
                        entUserMap.put("alwaysShow", showMap);
                    }
                    data.add(entUserMap);
                }
            }
        }
        JSONArray result = TypeConversion.listToTree(JSONArray.parseArray(JSON.toJSONString(data)), "resourceId", "parentId", "children");
        singleResult.setData(result);
        return singleResult;
    }

    @ApiOperation(value = "架构新增/修改", notes = "架构新增/修改")
    @PostMapping(value = "frameworkChange")
    @ResponseBody
    public SingleResult<String> frameworkChange(@RequestBody FrameworkChangeDto frameworkChangeDto) throws Exception {
        SingleResult<String> result = new SingleResult<>();

        SysFramework framework = new SysFramework();
        BeanUtils.copyProperties(framework, frameworkChangeDto);

        //操作人
        String userId = getUserId();
        framework.setModifyBy(userId);
        framework.setModifyTime(new Date());

        if (StringUtils.isNotBlank(framework.getFrameworkId())) {
            //修改
            sysFrameworkMapper.updateById(framework);
        } else {
            //新增
            framework.setFrameworkId(RandomNumber.getUUid());
            framework.setCreateBy(userId);
            framework.setCreateTime(new Date());
            sysFrameworkMapper.insert(framework);
        }
        return result;
    }

    @ApiOperation(value = "架构新增/修改", notes = "架构新增/修改")
    @PostMapping(value = "frameworkChangeV1")
    @ResponseBody
    public SingleResult<String> frameworkChangeV1(@RequestBody SysFramework sysFramework) throws Exception {
        SingleResult<String> result = new SingleResult<>();

        SysFramework framework = new SysFramework();
        BeanUtils.copyProperties(framework, sysFramework);

        //操作人
        String userId = getUserId();
        framework.setModifyBy(userId);
        framework.setModifyTime(new Date());

        if (StringUtils.isNotBlank(sysFramework.getFrameworkId())) {
            //修改
            sysFrameworkMapper.updateById(framework);
        } else {
            //新增
            framework.setFrameworkId(RandomNumber.getUUid());
            framework.setCreateBy(userId);
            framework.setCreateTime(new Date());
            sysFrameworkMapper.insert(framework);
        }
        return result;
    }

    /**
     * @param frameworkId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "架构删除", notes = "架构删除")
    @PostMapping(value = "frameworkDel")
    @ApiImplicitParams({@ApiImplicitParam(name = "frameworkId", value = "架构ID", required = true, dataType = "string")
    })
    @ResponseBody
    public SingleResult<String> frameworkDel(String frameworkId) throws Exception {
        SingleResult<String> result = new SingleResult<>();
        sysFrameworkMapper.deleteById(frameworkId);
        return result;
    }


    @ApiOperation(value = "架构列表", notes = "架构列表")
    @PostMapping(value = "frameworkList")
    @ResponseBody
    public SingleResult<List<SysFramework>> frameworkList(FrameworkListDto frameworkListDto) throws Exception {
        SingleResult<List<SysFramework>> result = new SingleResult<>();
        Integer page = frameworkListDto.getPage();
        Integer pageSize = frameworkListDto.getPageSize();
        page = pageSize * (page - 1);

        List<SysFramework> sysFrameworks = sysFrameworkMapper.selectFrameworkList(page, pageSize, frameworkListDto.getFrameworkId(), frameworkListDto.getChinaName(), frameworkListDto.getPostName());
        result.setData(sysFrameworks);
        return result;
    }

    @ApiOperation(value = "架构分页", notes = "架构分页")
    @GetMapping(value = "frameworkPage")
    @ResponseBody
    public SingleResult<Pager<SysFramework>> frameworkPage(@Valid FrameworkPageDto FrameworkPageDto) throws Exception {
        SingleResult<Pager<SysFramework>> result = new SingleResult<>();

        String condition = TypeConversion.getCondition(FrameworkPageDto.getCondition());
        Pager<SysFramework> pager = new Pager<>();
        PageHelper.startPage(FrameworkPageDto.getPage(), FrameworkPageDto.getPageSize());
        Page<SysFramework> page = (Page<SysFramework>) sysFrameworkMapper.frameworkList(condition);
        getDatePage(pager, page);
        result.setData(pager);
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
     * 登录验证
     * @return
     * @throws Exception
     */
    @LoginAuth
    @ApiOperation(value = "登录验证", notes = "登录验证")
    @PostMapping(value = "/loginVerification")
    @ResponseBody
    public SingleResult<String> loginVerification()throws Exception{
        SingleResult<String> result = new SingleResult<String>();
        return result;
    }

    /**
     * 用户信息
     * @param userId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "用户信息", notes = "用户信息")
    @PostMapping(value = "/userInfo/{userId}")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @ResponseBody
    public SingleResult<SysUser> userInfo(@PathVariable String userId)throws Exception{
        SingleResult<SysUser> result = new SingleResult<>();
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (null != sysUser) {
            sysUser.setUserPassword("");

            //获取用户令牌
            String userToken = JwtUtil.createToken(sysUser.getUserId());
            sysUser.setUserToken(userToken);

            result.setData(sysUser);
        }
        return result;
    }
}
