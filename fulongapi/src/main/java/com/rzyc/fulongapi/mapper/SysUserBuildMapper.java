package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.SysUserBuild;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户楼栋、单元关系表 Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-03-16
 */
@Repository
public interface SysUserBuildMapper extends BaseMapper<SysUserBuild> {

    /*用户的楼栋 或者 单元*/
    List<SysUserBuild> findByUserId(@Param("userId") String userId,
                                    @Param("relationType") Integer relationType );

}
