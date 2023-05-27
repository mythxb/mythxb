package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SersorAlert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 传感器报警信息 Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-05-27
 */
@Repository
public interface SersorAlertMapper extends BaseMapper<SersorAlert> {

    List<SersorAlert> alertList();

}
