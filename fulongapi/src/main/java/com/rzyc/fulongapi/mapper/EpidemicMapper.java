package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.Epidemic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 疫情记录 Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-05-12
 */
@Repository
public interface EpidemicMapper extends BaseMapper<Epidemic> {

    /*删除当天的数据*/
    Integer delToday();

    /*疫情记录总数*/
    Integer countByIsolateState(@Param("isolateState") String isolateState);

    /*最新数据*/
    Epidemic newestEpidemic();

    /*总数*/
    Integer countAll();

    /*历史隔离人员身份证号*/
    List<String> historyCardId();

    /*当前隔离人员身份证号*/
    List<String> todayCardId();

    /*前一天隔离人员身份证号*/
    List<String> yestodayCardId(@Param("startTime") String startTime,
                                @Param("endTime") String endTime);

    /*疫情记录列表*/
    List<Epidemic> epidemicList(@Param("condition") String condition);

    /*复制数据到历史表*/
    Integer copyEpidemic();

    /*统一更新数据时间*/
    Integer changeTime();

}
