package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.Log;
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
 * @since 2022-02-15
 */
@Repository
public interface LogMapper extends BaseMapper<Log> {

    List<Log>logList(@Param("startTime") String startTime, @Param("endTime")String endTime,
                     @Param("behavior")String behavior, @Param("param") String param,
                     @Param("page") Integer page,@Param("pageSize")Integer pageSize);

    long logListCount(@Param("startTime") String startTime, @Param("endTime")String endTime,
                      @Param("behavior")String behavior, @Param("param") String param);

}
