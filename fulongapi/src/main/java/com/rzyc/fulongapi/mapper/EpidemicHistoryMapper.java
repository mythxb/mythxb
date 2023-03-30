package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.EpidemicHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-05-12
 */
@Repository
public interface EpidemicHistoryMapper extends BaseMapper<EpidemicHistory> {

    /*同步数据*/
    Integer insertData(@Param("time") String time);

}
