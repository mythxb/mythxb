package com.rzyc.fulongapi.bean;

import com.rzyc.fulongapi.model.SysDocument;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("文件上传")
public class FileResult {

    @ApiModelProperty("是否上传成功")
    private Boolean success;

    @ApiModelProperty("文件信息")
    private SysDocument data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public SysDocument getData() {
        return data;
    }

    public void setData(SysDocument data) {
        this.data = data;
    }
}
