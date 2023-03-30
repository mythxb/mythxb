package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.CheckList;
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
public interface CheckListMapper extends BaseMapper<CheckList> {

    /*检查项*/
    CheckList findByListType(@Param("listType") Integer listType);

}
