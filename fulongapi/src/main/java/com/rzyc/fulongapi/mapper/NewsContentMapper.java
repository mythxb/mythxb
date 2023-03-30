package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.NewsContent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2022-02-21
 */
@Repository
public interface NewsContentMapper extends BaseMapper<NewsContent> {


    /*新闻内容*/
    NewsContent findByNewsId(@Param("newsId") String newsId);

    /*删除新闻内容*/
    Integer delByNewsId(@Param("newsId") String newsId);

}
