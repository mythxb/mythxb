package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.bean.check.DangerData;
import com.rzyc.fulongapi.bean.check.DangerList;
import com.rzyc.fulongapi.bean.fire.vo.FireControlTypeNum;
import com.rzyc.fulongapi.model.BuildUnit;
import com.rzyc.fulongapi.model.CheckDanger;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.rzyc.fulongapi.model.statistic.DangerStatistic;
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
public interface CheckDangerMapper extends BaseMapper<CheckDanger> {

    /*隐患总数*/
    Integer countByState(@Param("rectificationState") Integer rectificationState,
                         @Param("buildIds") List<String> buildIds,
                         @Param("unitIds") List<String> unitIds);

    /*企业隐患总数*/
    Integer countEntByState(@Param("rectificationState") Integer rectificationState,
                         @Param("floorFkey") String floorFkey);

    DangerStatistic mapDangersStatistic(@Param("dangerType") String dangerType, @Param("unitId") String unitId);

    List<CheckDanger> selectDanger(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                   @Param("dangerType") String dangerType, @Param("normalOrImportant") String normalOrImportant,
                                   @Param("buildingId") String buildingId, @Param("unitId") String unitId,
                                   @Param("page") Integer page, @Param("pageSize") Integer pageSize);

    /*隐患等级数量*/
    Integer countByLevel(@Param("level") Integer level,
                         @Param("buildIds") List<String> buildIds,
                         @Param("unitIds") List<String> unitIds);

    /*企业隐患等级数量*/
    Integer countEntByLevel(@Param("level") Integer level,
                         @Param("floorFkey") String floorFkey);

    /*隐患列表*/
    List<DangerList> dangerList(@Param("condition") String condition,
                                @Param("targetId") String targetId,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime,
                                @Param("state") Integer state,
                                @Param("riskLevel") Integer riskLevel,
                                @Param("buildIds") List<String> buildIds,
                                @Param("unitIds") List<String> unitIds);

    /*隐患列表*/
    List<DangerList> entDangerList(@Param("condition") String condition,
                                @Param("targetId") String targetId,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime,
                                @Param("state") Integer state,
                                @Param("riskLevel") Integer riskLevel);

    /*隐患详情*/
    DangerList dangerDetail(@Param("id") String id);

    /*隐患数量*/
    Integer dangerData(@Param("rectificationState") Integer rectificationState,
                       @Param("condition") String condition);

    /*修改隐患状态*/
    Integer changeState(@Param("record") CheckDanger record);

    /*修改整改信息*/
    Integer changeRectifyInfo(@Param("record") CheckDanger record);

    /*批量插入*/
    Integer insertList(@Param("records") List<CheckDanger> records);

    /*单元隐患*/
    List<CheckDanger> findByUnitId(@Param("unitId") String unitId);

    long selectDangerTotal(@Param("startTime") String startTime, @Param("endTime") String endTime,
                           @Param("dangerType") String dangerType, @Param("normalOrImportant") String normalOrImportant,
                           @Param("buildingId") String buildingId, @Param("unitId") String unitId);

    /*楼栋隐患*/
    List<CheckDanger> findByBuildId(@Param("buildId") String buildId);

    /*单元楼层隐患*/
    List<CheckDanger> findByFloorId(@Param("floorId") String floorId);


    /*所有隐患*/
    List<CheckDanger> findAll();

    /*修改隐患状态*/
    Integer changeLevel(@Param("record") CheckDanger record);

    List<CheckDanger> findInfo(@Param("buildingId") String buildingId, @Param("buildingUnitId") String buildingUnitId, @Param("checkItem") String checkItem);

    Integer updateDelState(String checkDangerId);

    /*通过xlsx文件内容修改rectification_state整治状态*/
    String getBuildId(@Param("direction") Integer direction, @Param("buildNumber") Integer buildNumber);

    String getUnitId(@Param("buildingId") String buildingId, @Param("unitNum") Integer unitNum);

    Integer updateRectificationState(@Param("buildingId") String buildingId, @Param("buildingUnitId") String buildingUnitId);

    /*隐患数量统计*/
    Integer countByTypeId(@Param("dangerTypeIds") List<String> dangerTypeIds,
                          @Param("rectificationState") Integer rectificationState,
                          @Param("unitId") String unitId);

    /*消防隐患情况*/
    DangerData fireDangerInfo(@Param("typeIds") List<String> typeIds,
                              @Param("unitId") String unitId);

    /*消防隐患列表*/
    List<DangerList> fireDangerList(@Param("condition") String condition,
                                @Param("targetId") String targetId,
                                @Param("startTime") String startTime,
                                @Param("endTime") String endTime,
                                @Param("state") Integer state,
                                @Param("riskLevel") Integer riskLevel,
                                @Param("buildIds") List<String> buildIds,
                                @Param("unitIds") List<String> unitIds,
                                @Param("typeIds") List<String> typeIds,
                                    @Param("dangerType") Integer dangerType);

    /*社区消防隐患类型统计*/
    List<FireControlTypeNum> fireControlTypeNum(@Param("dangerType") Integer dangerType);


    /*通过隐患类型统计隐患数量*/
    Integer countByType(@Param("dangerType") Integer dangerType,
                        @Param("rectificationState") Integer rectificationState,
                        @Param("unitId") String unitId,
                        @Param("dangerTypeId") String dangerTypeId);

    /*修改隐患状态*/
    Integer changeGasState(@Param("unitId") String unitId);




}
