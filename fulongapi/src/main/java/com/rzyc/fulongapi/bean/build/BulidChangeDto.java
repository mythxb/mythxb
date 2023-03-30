package com.rzyc.fulongapi.bean.build;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel("楼栋修改")
public class BulidChangeDto {

    @NotNull(message = "楼栋不能为空")
    @ApiModelProperty(value = "楼栋id",required = true)
    private String buildId;

    @NotNull(message = "楼栋名不能为空")
    @ApiModelProperty(value = "楼栋名",required = true)
    private String buildName;

    @NotNull(message = "楼栋编号不能为空")
    @ApiModelProperty(value = "楼栋编号",required = true)
    private Integer buildNumber;

    @ApiModelProperty("楼栋管理员")
    private String buildingManager;

    @ApiModelProperty("联系电话")
    private String buildingManagerContact;

    @NotNull(message = "分类不能为空")
    @ApiModelProperty(value = "分类 1.西街 2.东街",required = true)
    private Integer direction;

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public Integer getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getBuildingManager() {
        return buildingManager;
    }

    public void setBuildingManager(String buildingManager) {
        this.buildingManager = buildingManager;
    }

    public String getBuildingManagerContact() {
        return buildingManagerContact;
    }

    public void setBuildingManagerContact(String buildingManagerContact) {
        this.buildingManagerContact = buildingManagerContact;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}
