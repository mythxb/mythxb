package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.News;
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
 * @since 2022-02-21
 */
@Repository
public interface NewsMapper extends BaseMapper<News> {

    /*banner列表*/
    List<News> findByBannerState(@Param("bannerState") Integer bannerState);

    /*最新一条新闻*/
    List<News> findByTime();

    /*新闻列表*/
    List<News> newsList(@Param("condition") String condition);

    News selectDetail(String newsId);

}
