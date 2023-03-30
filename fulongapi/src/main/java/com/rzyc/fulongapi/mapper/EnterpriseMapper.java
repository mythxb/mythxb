package com.rzyc.fulongapi.mapper;

import com.rzyc.fulongapi.bean.index.EntNumInfo;
import com.rzyc.fulongapi.model.Enterprise;
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
 * @since 2022-02-23
 */
@Repository
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

    /*企业列表*/
    List<Enterprise> entList(@Param("condition") String condition,
                             @Param("enterpriseId") String enterpriseId,
                             @Param("industryType")String industryType,
                             @Param("buildIds") List<String> buildIds,
                             @Param("unitIds") List<String> unitIds);

    /*修改企业二维码*/
    Integer changeQrCode(@Param("enterpriseId") String enterpriseId,
                         @Param("qrCode") String qrCode);

    /*修改企业风险等级*/
    Integer changeRiskLevel(@Param("enterpriseId") String enterpriseId,
                         @Param("riskLevel") Integer riskLevel);

    /*修改行业*/
    Integer changeIndustry(@Param("enterpriseId") String enterpriseId,
                         @Param("industryId") String industryId);

    /*企业数量*/
    List<EntNumInfo> entNumInfo();

    /*企业总数*/
    Integer countAll();

    /*所有企业*/
    List<Enterprise> findAll();

    /**/
    Enterprise findByName(@Param("entName") String entName);

}
