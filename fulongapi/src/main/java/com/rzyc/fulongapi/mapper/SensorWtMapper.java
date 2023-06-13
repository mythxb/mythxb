package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SensorWt;
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
 * @since 2023-06-12
 */
@Repository
public interface SensorWtMapper extends BaseMapper<SensorWt> {

    /*通过设备id查询*/
    SensorWt findBySmokeid(@Param("smokeid") String smokeid);


    /*查询所有*/
    List<SensorWt> findAll();

}
