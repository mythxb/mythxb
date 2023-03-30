package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.GasEquipment;
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
 * @since 2022-03-10
 */
@Repository
public interface GasEquipmentMapper extends BaseMapper<GasEquipment> {

    List<GasEquipment>selectGasEquipmentList(@Param("page") Integer page,@Param("pageSize") Integer pageSize,@Param("floorId") String floorId, @Param("unitId")String unitId);

    long selectGasEquipmentCount(@Param("floorId") String floorId, @Param("unitId")String unitId);

    int deleteByUnitOrFloor(@Param("unitId") String unitId,@Param("floorId")String floorId);
}
