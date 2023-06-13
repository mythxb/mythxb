package com.rzyc.fulongapi.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2023-06-12
 */
@TableName("sensor_wt")
@ApiModel(value="SensorWt对象", description="")
public class SensorWt implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "传感器id")
    @TableId("sersor_id")
    private String sersorId;

    @TableField("hxmonitorid")
    private String hxmonitorid;

    @ApiModelProperty(value = "设备ID")
    @TableField("smokeid")
    private String smokeid;

    @ApiModelProperty(value = "mac地址")
    @TableField("mac")
    private String mac;

    @ApiModelProperty(value = "电压具体值 电压值mV")
    @TableField("voltagevalue")
    private String voltagevalue;

    @TableField("type")
    private String type;

    @TableField("codetype")
    private String codetype;

    @TableField("code")
    private String code;

    @ApiModelProperty(value = "传感器名")
    @TableField("monitorname")
    private String monitorname;

    @ApiModelProperty(value = "传感器值")
    @TableField("data")
    private String data;

    @ApiModelProperty(value = "传感器单位")
    @TableField("dataunit")
    private String dataunit;

    @ApiModelProperty(value = "创建时间")
    @TableField("createtime")
    private String createtime;

    @ApiModelProperty(value = "监测温度，单位°C")
    @TableField("tvalue")
    private String tvalue;

    @ApiModelProperty(value = "水压/水位状态0：水压正常   1:欠压警告    2:过压警告")
    @TableField("wpstatus")
    private String wpstatus;

    @ApiModelProperty(value = "变化率状态  0：正常状态  1：设备状态报警")
    @TableField("wrstatus")
    private String wrstatus;

    @ApiModelProperty(value = "倾斜状态  0：正常状态  1：设备状态报警")
    @TableField("wdstatus")
    private String wdstatus;

    @ApiModelProperty(value = "震动报警  0：正常状态  1：设备状态报警")
    @TableField("wkstatus")
    private String wkstatus;

    @ApiModelProperty(value = "漏水状态  0：正常状态  1：设备状态报警")
    @TableField("wostatus")
    private String wostatus;

    @TableField("wbstatus")
    private String wbstatus;

    @ApiModelProperty(value = "信号强度")
    @TableField("dsignal")
    private String dsignal;

    @ApiModelProperty(value = "本次流量")
    @TableField("singleflow")
    private String singleflow;

    @ApiModelProperty(value = "累计流量")
    @TableField("totalflow")
    private String totalflow;

    @ApiModelProperty(value = "采集时间")
    @TableField("collectiontime")
    private String collectiontime;

    @ApiModelProperty(value = "电池是否低压 1 表是正常，0 表示低压")
    @TableField("batterys")
    private String batterys;

    @TableField("businesstype")
    private String businesstype;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "修改人")
    @TableField("modify_by")
    private String modifyBy;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private Date modifyTime;

    public String getSersorId() {
        return sersorId;
    }

    public void setSersorId(String sersorId) {
        this.sersorId = sersorId;
    }
    public String getHxmonitorid() {
        return hxmonitorid;
    }

    public void setHxmonitorid(String hxmonitorid) {
        this.hxmonitorid = hxmonitorid;
    }
    public String getSmokeid() {
        return smokeid;
    }

    public void setSmokeid(String smokeid) {
        this.smokeid = smokeid;
    }
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getVoltagevalue() {
        return voltagevalue;
    }

    public void setVoltagevalue(String voltagevalue) {
        this.voltagevalue = voltagevalue;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getMonitorname() {
        return monitorname;
    }

    public void setMonitorname(String monitorname) {
        this.monitorname = monitorname;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getDataunit() {
        return dataunit;
    }

    public void setDataunit(String dataunit) {
        this.dataunit = dataunit;
    }
    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getTvalue() {
        return tvalue;
    }

    public void setTvalue(String tvalue) {
        this.tvalue = tvalue;
    }
    public String getWpstatus() {
        return wpstatus;
    }

    public void setWpstatus(String wpstatus) {
        this.wpstatus = wpstatus;
    }
    public String getWrstatus() {
        return wrstatus;
    }

    public void setWrstatus(String wrstatus) {
        this.wrstatus = wrstatus;
    }
    public String getWdstatus() {
        return wdstatus;
    }

    public void setWdstatus(String wdstatus) {
        this.wdstatus = wdstatus;
    }
    public String getWkstatus() {
        return wkstatus;
    }

    public void setWkstatus(String wkstatus) {
        this.wkstatus = wkstatus;
    }
    public String getWostatus() {
        return wostatus;
    }

    public void setWostatus(String wostatus) {
        this.wostatus = wostatus;
    }
    public String getWbstatus() {
        return wbstatus;
    }

    public void setWbstatus(String wbstatus) {
        this.wbstatus = wbstatus;
    }
    public String getDsignal() {
        return dsignal;
    }

    public void setDsignal(String dsignal) {
        this.dsignal = dsignal;
    }
    public String getSingleflow() {
        return singleflow;
    }

    public void setSingleflow(String singleflow) {
        this.singleflow = singleflow;
    }
    public String getTotalflow() {
        return totalflow;
    }

    public void setTotalflow(String totalflow) {
        this.totalflow = totalflow;
    }
    public String getCollectiontime() {
        return collectiontime;
    }

    public void setCollectiontime(String collectiontime) {
        this.collectiontime = collectiontime;
    }
    public String getBatterys() {
        return batterys;
    }

    public void setBatterys(String batterys) {
        this.batterys = batterys;
    }
    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "SensorWt{" +
            "sersorId=" + sersorId +
            ", hxmonitorid=" + hxmonitorid +
            ", smokeid=" + smokeid +
            ", mac=" + mac +
            ", voltagevalue=" + voltagevalue +
            ", type=" + type +
            ", codetype=" + codetype +
            ", code=" + code +
            ", monitorname=" + monitorname +
            ", data=" + data +
            ", dataunit=" + dataunit +
            ", createtime=" + createtime +
            ", tvalue=" + tvalue +
            ", wpstatus=" + wpstatus +
            ", wrstatus=" + wrstatus +
            ", wdstatus=" + wdstatus +
            ", wkstatus=" + wkstatus +
            ", wostatus=" + wostatus +
            ", wbstatus=" + wbstatus +
            ", dsignal=" + dsignal +
            ", singleflow=" + singleflow +
            ", totalflow=" + totalflow +
            ", collectiontime=" + collectiontime +
            ", batterys=" + batterys +
            ", businesstype=" + businesstype +
            ", createBy=" + createBy +
            ", modifyBy=" + modifyBy +
            ", modifyTime=" + modifyTime +
        "}";
    }
}
