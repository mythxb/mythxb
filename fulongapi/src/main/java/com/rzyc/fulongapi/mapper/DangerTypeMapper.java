package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.DangerType;
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
 * @since 2022-02-21
 */
@Repository
public interface DangerTypeMapper extends BaseMapper<DangerType> {

    DangerType selectByDangerType(String dangerType);

    List<DangerType>selectDangerTypes(String unitId);

    /*查询隐患分类*/
    List<DangerType> findByIndex(@Param("index") Integer index);

    List<DangerType>allDangerTypes(@Param("type") Integer type);

    /*隐患数量统计*/
    List<DangerType> dangerNumInfo(@Param("unitId") String unitId);

    /*单元消防信息*/
    List<DangerType> unitDnagerInfo(@Param("unitId") String unitId,@Param("dangerType") Integer dangerType);

    /*按照类型查询隐患类型*/
    List<DangerType> findByType(@Param("type") Integer type);
}
