package com.rzyc.fulongapi.model;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2022-02-15
 */
@ApiModel(value = "Log对象", description = "")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String ip;

    private String userId;

    private String behavior;

    @ApiModelProperty(value = "参数")
    private String parameter;

    @ApiModelProperty(value = "返回")
    private String responseStr;

    private Date createTime;

    @TableField(exist = false)
    private String createTimeToString;

    public String getCreateTimeToString() {
        return createTimeToString;
    }

    public void setCreateTimeToString(String createTimeToString) {
        this.createTimeToString = createTimeToString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", userId='" + userId + '\'' +
                ", behavior='" + behavior + '\'' +
                ", parameter='" + parameter + '\'' +
                ", responseStr='" + responseStr + '\'' +
                ", createTime=" + createTime +
                ", createTimeToString='" + createTimeToString + '\'' +
                '}';
    }
}
