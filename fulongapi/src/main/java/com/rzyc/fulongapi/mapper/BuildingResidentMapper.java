package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.bean.map.ResidentDetailVo;
import com.rzyc.fulongapi.bean.map.ResidentTypeTotalVo;
import com.rzyc.fulongapi.bean.user.PopulationType;
import com.rzyc.fulongapi.bean.user.PopulationTypeV1;
import com.rzyc.fulongapi.model.BuildingResident;
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
 * @since 2022-03-10
 */
@Repository
public interface BuildingResidentMapper extends BaseMapper<BuildingResident> {
    /*居民信息*/
    List<BuildingResident> selectResidentList(@Param("page") Integer page,
                                              @Param("pageSize") Integer pageSize,
                                              @Param("unitId") String unitId,
                                              @Param("floorId") String floorId,
                                              @Param("residentType") Integer residentType,
                                              @Param("residentName") String residentName,
                                              @Param("condition") String condition);
    /*居民总数*/
    long selectResidentCount(@Param("unitId") String unitId,
                             @Param("floorId") String floorId,
                             @Param("residentType") Integer residentType,
                             @Param("residentName") String residentName,
                             @Param("condition") String condition);

    /*通过单元楼层删除居民信息*/
    int deleteByUnitOrFloor(@Param("unitId") String unitId, @Param("floorId") String floorId);

    /*大屏小区住房信息，房屋类型的总数*/
    List<ResidentTypeTotalVo> selectResidentTypeTotal();

    /*小区住房信息人口数*/
    long selectResidentTotal();

    /*大屏小区住房分页列表*/
    List<ResidentDetailVo> selectHousingInfoList(@Param("condition") String condition);

    /*所有数据*/
    List<BuildingResident> findAll();

    /*人口类型分布信息*/
    PopulationType populationTypeV1();

    /*住户列表*/
    List<BuildingResident> residentList(@Param("condition") String condition);

    /*房屋总数*/
    Integer countByResidentType(@Param("residentTypes") List<Integer> residentTypes);

}
