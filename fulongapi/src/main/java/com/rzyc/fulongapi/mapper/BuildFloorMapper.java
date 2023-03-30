package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.BuildFloor;
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
 * @since 2022-02-18
 */
@Repository
public interface BuildFloorMapper extends BaseMapper<BuildFloor> {


    /*单元楼层*/
    List<BuildFloor> findByUnitId(@Param("unitId") String unitId);

    /*修改风险等级*/
    Integer changeRiskLevel(@Param("floorId") String floorId,
                            @Param("riskLevel") Integer riskLevel);

    /*查询所有*/
    List<BuildFloor> findAll();

    /*修改楼层信息*/
    Integer changeNum(@Param("record") BuildFloor record);

    List<BuildFloor> selectBuildingUnitFloorTable(@Param("unitId") String unitId, @Param("page") Integer page, @Param("pageSize") Integer pageSize);


    long selectBuildingUnitFloorTableCount(@Param("unitId") String unitId);

    Integer selectHouseHold(@Param("buildingId") String buildingId);


    BuildFloor findFloorByUnitIdAndFloorNumber(@Param("unitId") String unitId, @Param("floorNumber") Integer floorNumber);

    List<BuildFloor> selectAllBuildFloor();

    String getFloorIdByBuildingIdAndUnitIdAndFloor(@Param("buildingId") String buildingId,@Param("unitId") String unitId,@Param("floor") Integer floor);

    /*已整改户数*/
    Integer sumByHouseholdSize();

    /*燃气点数*/
    Integer sumByUsertableNum();

    /*修改人数*/
    void changePersonNum(@Param("floorId") String floorId);
}
