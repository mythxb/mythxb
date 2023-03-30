package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.bean.fire.dto.AlarmRecordDto;
import com.rzyc.fulongapi.bean.fire.vo.AlarmRecordVo;
import com.rzyc.fulongapi.model.FireRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 火灾出警记录 Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-05-17
 */
@Repository
public interface FireRecordMapper extends BaseMapper<FireRecord> {
    /**
     * 出警记录
     * @param alarmRecordDto
     * @return
     */
    List<AlarmRecordVo> alarmRecord(@Param("alarmRecordDto") AlarmRecordDto alarmRecordDto);
}
