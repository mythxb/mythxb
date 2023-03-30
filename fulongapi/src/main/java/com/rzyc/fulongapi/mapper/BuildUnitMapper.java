package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.BuildFloor;
import com.rzyc.fulongapi.model.BuildUnit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzyc.fulongapi.model.BuildUnitHouseholder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-02-17
 */
@Repository
public interface BuildUnitMapper extends BaseMapper<BuildUnit> {

    List<BuildUnit> selectBuildingUnitsInfo(@Param("buildingId") String buildingId, @Param("page")Integer page, @Param("pageSize")Integer pageSize);

    BuildUnit selectBuildingAndUnitNumber(@Param("direction") Integer direction,@Param("buildingNumber") Integer buildingNumber,
                                          @Param("unitNumber")Integer unitNumber);

    /*单元列表*/
    List<BuildUnit> unitList(@Param("condition") String condition,
                             @Param("buildingId") String buildingId,
                             @Param("unitId") String unitId);

    /*单元详细*/
    BuildUnit selectBuildingUnitDetail(String unitId);


    BuildUnit selectByBuildingOrUnitId(@Param("direction") Integer direction,@Param("buildingNumber") Integer buildingNumber,@Param("unitNumber")Integer unitNumber);
    /*修改单元二维码*/
    Integer changeQrCode(@Param("unitId") String unitId,
                         @Param("qrCode") String qrCode);

    /*修改风险等级*/
    Integer changeRiskLevel(@Param("unitId") String unitId,
                            @Param("riskLevel") Integer riskLevel);

    /*查询所有*/
    List<BuildUnit> findAll();

    /*楼栋下单元*/
    BuildUnit findByBuildingId(@Param("buildingId") String buildingId,
                               @Param("unitNumber") Integer unitNumber);

    long selectBuildingUnitsInfoTotal(String buildingId);

    /*总户数*/
    Integer sumFloor();

    BuildUnit selectByParentAndNumber(@Param("buildingId") String buildingId,@Param("unitNumber")Integer unitNumber);

    int updateManager(@Param("bd") BuildUnitHouseholder bd);

    List<BuildUnit>selectTheseNullFloorUnits();

    /*单元列表*/
    List<BuildUnit> unitListPage(@Param("condition") String condition,
                                 @Param("buildingId") String buildingId,
                                 @Param("unitId") String unitId,
                                 @Param("buildIds") List<String> buildIds,
                                 @Param("unitIds") List<String> unitIds);

    /*修改单元管理员信息*/
    Integer changeManger(@Param("unitManager") String unitManager,
                         @Param("unitManagerContact") String unitManagerContact,
                         @Param("buildingId") String buildingId,
                         @Param("unitNumber") Integer unitNumber);

    /*单元消防数量*/
    List<BuildUnit> unitDangerNum(@Param("dangerType") Integer dangerType);

}
