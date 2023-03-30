package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SpecialBuilding;
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
 * @since 2022-03-03
 */
@Repository
public interface SpecialBuildingMapper extends BaseMapper<SpecialBuilding> {

    List<SpecialBuilding> specialBuildingList();

    SpecialBuilding specialBuildingDetail(String sbId);

    List<SpecialBuilding> specialBuildingTable(@Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("condition") String condition);

    Integer specialBuildingTableCount(@Param("condition") String condition);

    long changeQrCode(@Param("sbId") String sbId, @Param("qrCode") String qrCode);

}
