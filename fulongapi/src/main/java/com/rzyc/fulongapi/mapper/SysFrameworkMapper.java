package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SysFramework;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 架构 Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-05-10
 */
@Repository
public interface SysFrameworkMapper extends BaseMapper<SysFramework> {
    List<SysFramework> selectFrameworkList(@Param("page") Integer page,
                                           @Param("pageSize") Integer pageSize,
                                           @Param("frameworkId") String frameworkId,
                                           @Param("chinaName") String chinaName,
                                           @Param("postName") String postName);

    /*组织架构列表*/
    List<SysFramework> frameworkList(@Param("cindition") String cindition);

}
