package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-02-21
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    /*查询用户信息*/
    SysUser findByAccount(@Param("userAccount") String userAccount);

    /*政府用户*/
    SysUser findByGovAccount(@Param("userAccount") String userAccount,
                             @Param("userType") Integer userType);

    /*修改密码*/
    Integer changePasswd(@Param("userId") String userId,
                         @Param("userPassword") String userPassword);

    /*用户列表*/
    List<SysUser> selectUserList(@Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("userNameCondition") String userNameCondition,@Param("condition") String condition);

    /*用户总数*/
    long selectUserCount(@Param("userNameCondition") String userNameCondition,@Param("condition") String condition);

    /*通过名字获取用户*/
    SysUser getByName(@Param("name") String name);

    /*查询企业用户*/
    SysUser findByEnterpriseId(@Param("enterpriseId") String enterpriseId);

    /*查询其他账号电话*/
    SysUser findNotUserId(@Param("userAccount") String userAccount,
                          @Param("userId") String userId);

}
