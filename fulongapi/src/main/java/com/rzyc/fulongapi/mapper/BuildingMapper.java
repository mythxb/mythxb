package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.bean.index.BuildDangerNum;
import com.rzyc.fulongapi.model.BuildFloor;
import com.rzyc.fulongapi.model.BuildUnit;
import com.rzyc.fulongapi.model.Building;
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
 * @since 2022-02-17
 */
@Repository
public interface BuildingMapper extends BaseMapper<Building> {

    BuildFloor importDangerFindForeign(@Param("direction") Integer direction,@Param("buildingNumber") Integer buildingNumber,
                                       @Param("unitNumber")Integer unitNumber,@Param("floorNumber") Integer floorNumber);



    List<Building> selectDangerAndBuilding(@Param("dangerType") Integer dangerType);

    Building selectByBuildName(String buildingName);

    Building selectByDirectionAndNumber(@Param("direction") Integer direction,@Param("buildingNumber")Integer buildingNumber);

    Building selectBuildingInfo(String id);

    /*修改风险等级*/
    Integer changeRiskLevel(@Param("buildId") String buildId,
                            @Param("riskLevel") Integer riskLevel);

    /*查询所有*/
    List<Building> findAll();

    /*楼栋信息*/
    Building findBuilding(@Param("direction") Integer direction,
                          @Param("buildNumber") Integer buildNumber);

    /*修改二维码*/
    Integer changeQrCode(@Param("buildId") String buildId,
                         @Param("qrCode") String qrCode);

    /*楼栋隐患数据*/
    List<BuildDangerNum> buildDangerNum(@Param("dangerTypeId") String dangerTypeId);

    Integer updateUnitHiddenState(@Param("buildingId")String buildingId,@Param("state") Integer state);

    /*修改大屏消防隐患标志显示或隐藏*/
    Integer updateFireState(@Param("buildingId")String buildingId,@Param("state") Integer state);

    /*楼栋列表*/
    List<Building> buildingList(@Param("condition") String condition,
                                @Param("direction") Integer direction,
                                @Param("buildIds") List<String> buildIds);
}
