package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-03-01
 */
@Repository
public interface SysResourceMapper extends BaseMapper<SysResource> {

    List<SysResource> allResourceOfTree();
}
