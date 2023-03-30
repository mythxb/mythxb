package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.bean.check.ChecksList;
import com.rzyc.fulongapi.model.CheckList;
import com.rzyc.fulongapi.model.Checks;
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
public interface ChecksMapper extends BaseMapper<Checks> {

    /*检查记录*/
    List<ChecksList> checkList(@Param("condition") String condition,
                               @Param("buildIds") List<String> buildIds,
                               @Param("unitIds") List<String> unitIds,
                               @Param("startTime") String startTime,
                               @Param("endTime") String endTime);

    /*企业检查记录*/
    List<ChecksList> encCheckList(@Param("condition") String condition,
                               @Param("floorFkey") String floorFkey,
                               @Param("startTime") String startTime,
                               @Param("endTime") String endTime);

    /*检查列表*/
    ChecksList checkDetail(@Param("checkId") String checkId);
}
