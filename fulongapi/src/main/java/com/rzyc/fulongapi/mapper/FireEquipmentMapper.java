package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.FireEquipment;
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
public interface FireEquipmentMapper extends BaseMapper<FireEquipment> {

    List<FireEquipment>equipmentTable(@Param("feCategoryId") String feCategoryId, @Param("page") Integer page, @Param("pageSize")Integer pageSize);

    Integer equipmentTableCount(String feCategoryId);

}
