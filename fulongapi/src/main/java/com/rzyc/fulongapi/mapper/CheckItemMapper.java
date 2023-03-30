package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.CheckItem;
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
 * @since 2022-02-21
 */
@Repository
public interface CheckItemMapper extends BaseMapper<CheckItem> {

    /*所有检查项*/
    List<CheckItem> findAll();

    /*检查清单下检查项*/
    List<CheckItem> findByClassId(@Param("classId") String classId,
                                  @Param("page") Integer page,
                                  @Param("pageSize")Integer pageSize);

    List<CheckItem>findByClassIdForWeb(@Param("classId") String classId,
                                       @Param("condition") String condition,
                                       @Param("page") Integer page,
                                       @Param("pageSize")Integer pageSize);


    long findByClassIdCount(@Param("classId") String classId,@Param("condition") String condition);

    /*行业检查项*/
    List<CheckItem> findByIndustryId(@Param("industryId") String industryId);

}
