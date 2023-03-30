package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.BuildUnitLevel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-06-12
 */
@Repository
public interface BuildUnitLevelMapper extends BaseMapper<BuildUnitLevel> {

    /*单元房屋信息*/
    BuildUnitLevel findByUnitId(@Param("unitId") String unitId);

}
