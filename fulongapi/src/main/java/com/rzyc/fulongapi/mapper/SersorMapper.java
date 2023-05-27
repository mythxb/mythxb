package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.Sersor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 传感器信息 Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-05-27
 */
@Repository
public interface SersorMapper extends BaseMapper<Sersor> {

    /*通过类型差*/
    List<Sersor> findByType(@Param("sersorType") Integer sersorType);

    /*查询所有数据*/
    List<Sersor> findAll();

}
