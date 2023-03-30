package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.StoreType;
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
 * @since 2022-02-24
 */

@Repository
public interface StoreTypeMapper extends BaseMapper<StoreType> {


    /*查询分类*/
    StoreType findByName(@Param("typeName") String typeName);

    /*分类列表*/
    List<StoreType> findAll();

}
