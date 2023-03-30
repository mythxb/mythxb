package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.FireStation;
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
 * @since 2022-02-24
 */
@Repository
public interface FireStationMapper extends BaseMapper<FireStation> {

    //消防站列表
    List<FireStation> fireStations(@Param("page") Integer page, @Param("pageSize") Integer pageSize, @Param("condition") String condition);

    //消防站总数
    Integer fireStationsCount(@Param("condition") String condition);

}
