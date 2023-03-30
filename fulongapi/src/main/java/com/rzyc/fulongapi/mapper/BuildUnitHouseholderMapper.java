package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.BuildUnitHouseholder;
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
 * @since 2022-02-22
 */
@Repository
public interface BuildUnitHouseholderMapper extends BaseMapper<BuildUnitHouseholder> {

    /*管理员列表*/
    List<BuildUnitHouseholder> findByUnitId(@Param("unitId") String unitId);

    /*删除管理员*/
    Integer delByUnitId(@Param("unitId") String unitId);

    List<BuildUnitHouseholder>selectByUnitId(String unitId);
}
