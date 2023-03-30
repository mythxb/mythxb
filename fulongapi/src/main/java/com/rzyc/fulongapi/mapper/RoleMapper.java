package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2022-03-29
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    //角色列表分页
    List<Role>selectRoleList(@Param("page") Integer page, @Param("pageSize")Integer pageSize,@Param("condition") String condition);

    //角色总数
    long selectRoleCount(@Param("condition") String condition);

    //全部角色信息
    List<Role> getRole();

    //通过角色名获取角色
    List<Role> getByName(@Param("name") String name);
}
