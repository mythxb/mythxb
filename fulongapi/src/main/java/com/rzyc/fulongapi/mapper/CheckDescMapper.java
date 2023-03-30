package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.CheckDesc;
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
 * @since 2022-02-22
 */
@Repository
public interface CheckDescMapper extends BaseMapper<CheckDesc> {

    /*批量插入*/
    Integer insertList(@Param("records") List<CheckDesc> records);


    /*检查项*/
    List<CheckDesc> checkDescList(@Param("checkId") String checkId);
}
