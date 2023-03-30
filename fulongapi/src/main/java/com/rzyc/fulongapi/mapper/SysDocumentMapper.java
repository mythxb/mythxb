package com.rzyc.fulongapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rzyc.fulongapi.model.SysDocument;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2021-08-25
 */
@Repository
public interface SysDocumentMapper extends BaseMapper<SysDocument> {

    /**
     * 更新目标id 和 目标类型查询
     * @param targetIds
     * @param targetType
     * @return
     */
    List<SysDocument> findByTargetId(@Param("targetIds") List<String> targetIds,
                                     @Param("targetType") String targetType);

    /*按月份查找图片*/
    List<Map<String,String>> selectMonthImgs(@Param("userId") String userId, @Param("limitStart") Integer limitStart, @Param("limitEnd") Integer limitEnd);

}
