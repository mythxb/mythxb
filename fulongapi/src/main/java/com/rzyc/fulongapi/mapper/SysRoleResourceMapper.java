package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SysRoleResource;
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
 * @since 2022-03-31
 */

@Repository
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResource> {
    List<SysRoleResource> selectResource(@Param("roleId") String roleId);

    int deleteByRoleId(@Param("roleId") String roleId);

    int insert(@Param("record") SysRoleResource record);
}
