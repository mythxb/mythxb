package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.model.QuestionSubmit;
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
 * @since 2022-03-16
 */
@Repository
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

    List<QuestionSubmit>selectSubmitQuestionList(@Param("condition") String condition, @Param("page")Integer page,@Param("pageSize") Integer pageSize);

    long selectSubmitQuestionListCount(String condition);

    /*未处理问题*/
    List<QuestionSubmit> findByQsState();

}


