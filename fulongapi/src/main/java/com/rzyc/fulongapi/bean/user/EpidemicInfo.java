package com.rzyc.fulongapi.bean.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("疫情数据")
public class EpidemicInfo {


    @ApiModelProperty("数据更新时间")
    private String changeTime;

    @ApiModelProperty("确诊数")
    private Integer diagnosisNum;

    @ApiModelProperty("隔离数")
    private Integer quarantineNum;

    @ApiModelProperty("隔离完成数")
    private Integer quarantinedNum;

    @ApiModelProperty("较昨天隔离变化数")
    private String isolationNum;

    @ApiModelProperty("较昨日隔离完成变化数")
    private String isolationedNum;

    public Integer getQuarantineNum() {
        return quarantineNum;
    }

    public void setQuarantineNum(Integer quarantineNum) {
        this.quarantineNum = quarantineNum;
    }

    public Integer getQuarantinedNum() {
        return quarantinedNum;
    }

    public void setQuarantinedNum(Integer quarantinedNum) {
        this.quarantinedNum = quarantinedNum;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getDiagnosisNum() {
        return diagnosisNum;
    }

    public void setDiagnosisNum(Integer diagnosisNum) {
        this.diagnosisNum = diagnosisNum;
    }

    public String getIsolationNum() {
        return isolationNum;
    }

    public void setIsolationNum(String isolationNum) {
        this.isolationNum = isolationNum;
    }

    public String getIsolationedNum() {
        return isolationedNum;
    }

    public void setIsolationedNum(String isolationedNum) {
        this.isolationedNum = isolationedNum;
    }
}
