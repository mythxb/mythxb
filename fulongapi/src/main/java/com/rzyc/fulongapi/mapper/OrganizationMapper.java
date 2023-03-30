package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.Organization;
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
 * @since 2022-03-01
 */
@Repository
public interface OrganizationMapper extends BaseMapper<Organization> {


    /*下级组织架构*/
    List<Organization> findByParentId(@Param("parentId") String parentId);

}
