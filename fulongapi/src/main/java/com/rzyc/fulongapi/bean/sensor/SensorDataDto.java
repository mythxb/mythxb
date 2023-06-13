package com.rzyc.fulongapi.bean.sensor;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dong
 * @date 2023-06-06 16:19
 * @Version V1.0
 */
public class SensorDataDto {


    //
    private String hxmonitorid;

    //设备ID
    private String smokeid;

    //mac地址
    private String mac;

    //电压具体值 电压值mV
    private String voltagevalue;

    //
    private String type;

    //
    private String codetype;

    //
    private String code;

    //传感器名
    private String monitorname;

    //传感器值
    private String data;

    //传感器单位
    private String dataunit;

    //创建时间
    private String createtime;

    //监测温度，单位°C
    private String tvalue;

    //水压/水位状态0：水压正常   1:欠压警告    2:过压警告
    private String wpstatus;

    //变化率状态  0：正常状态  1：设备状态报警
    private String wrstatus;

    //倾斜状态  0：正常状态  1：设备状态报警
    private String wdstatus;

    //震动报警  0：正常状态  1：设备状态报警
    private String wkstatus;

    //漏水状态  0：正常状态  1：设备状态报警
    private String wostatus;


    private String wbstatus;

    //信号强度
    private String dsignal;

    //本次流量
    private String singleflow;

    //累计流量
    private String totalflow;

    //采集时间
    private String collectiontime;

    //电池是否低压 1 表是正常，0 表示低压
    private String batterys;


    private String businesstype;

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
}
