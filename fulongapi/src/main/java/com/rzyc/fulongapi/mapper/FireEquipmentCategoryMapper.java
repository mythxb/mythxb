package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.FireEquipmentCategory;
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
public interface FireEquipmentCategoryMapper extends BaseMapper<FireEquipmentCategory> {
    /*消防类别列表*/
    List<FireEquipmentCategory>equipmentCategory(@Param("page") Integer page, @Param("pageSize")Integer pageSize);

    /*消防类别总数*/
    Integer equipmentCategoryCount();

    /*消防器材和类别*/
    List<FireEquipmentCategory>equipmentCategoryAndResource(@Param("page") Integer page,@Param("pageSize")Integer pageSize, @Param("condition") String condition);

    /*消防类别总数*/
    int equipmentCategoryAndResourceCount(@Param("condition") String condition);

    /*通过类别名称获取消防类别*/
    List<FireEquipmentCategory> selectByName(@Param("name") String name);
}
