package com.rzyc.fulongapi.bean.sensor;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author dong
 * @date 2023-06-06 16:19
 * @Version V1.0
 */
public class SensorDataDto {

    @ApiModelProperty("消息ID")
    private Long xdsmokelogid;

    @ApiModelProperty("设备ID")
    private Long smokeid;

    @ApiModelProperty("事件类型")
    private Integer messagetype;

    @ApiModelProperty("设备类型")
    private String devicetype;

    @ApiModelProperty("设备号唯一编码")
    private String mac;

    @ApiModelProperty("设备卡号ICCID")
    private String ccid;

    @ApiModelProperty("设备卡IMSI")
    private String imsi;

    @ApiModelProperty("信号强度")
    private Integer dsignal;

    @ApiModelProperty("电池是否低压 1 表是正常，0 表示低压")
    private Integer batterys;

    @ApiModelProperty("电压具体值 电压值mV")
    private Integer voltagevalue;

    @ApiModelProperty("监测温度，单位°C")
    private Integer tvalue;

    @ApiModelProperty("设备IMEI号")
    private String Imei;

    @ApiModelProperty("防区号")
    private String ccc;

    @ApiModelProperty("防拆状态     0 闭合 1打开")
    private Integer tamperstatus;

    @ApiModelProperty("精确到毫秒")
    private Long createtime;

    @ApiModelProperty("保留")
    private Integer cstatus;



    @ApiModelProperty("监控数据")
    private String monitordata;

    @ApiModelProperty("监控数据单位")
    private String dataunit;

    @ApiModelProperty("水压/水位状态0：水压正常   1:欠压警告    2:过压警告")
    private Integer wpstatus;

    @ApiModelProperty("变化率状态  0：正常状态  1：设备状态报警")
    private Integer wrstatus;

    @ApiModelProperty("倾斜状态  0：正常状态  1：设备状态报警")
    private Integer wdstatus;

    @ApiModelProperty("震动报警  0：正常状态  1：设备状态报警")
    private Integer wkstatus;

    @ApiModelProperty("漏水状态  0：正常状态  1：设备状态报警")
    private Integer wostatus;

    @ApiModelProperty("本次流量")
    private String singleflow;

    @ApiModelProperty("累计流量")
    private String totalflow;

    @ApiModelProperty("采集时间-时间戳")
    private long collectiontime;

    public Long getXdsmokelogid() {
        return xdsmokelogid;
    }

    public void setXdsmokelogid(Long xdsmokelogid) {
        this.xdsmokelogid = xdsmokelogid;
    }

    public Long getSmokeid() {
        return smokeid;
    }

    public void setSmokeid(Long smokeid) {
        this.smokeid = smokeid;
    }

    public Integer getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(Integer messagetype) {
        this.messagetype = messagetype;
    }

    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCcid() {
        return ccid;
    }

    public void setCcid(String ccid) {
        this.ccid = ccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Integer getDsignal() {
        return dsignal;
    }

    public void setDsignal(Integer dsignal) {
        this.dsignal = dsignal;
    }

    public Integer getBatterys() {
        return batterys;
    }

    public void setBatterys(Integer batterys) {
        this.batterys = batterys;
    }

    public Integer getVoltagevalue() {
        return voltagevalue;
    }

    public void setVoltagevalue(Integer voltagevalue) {
        this.voltagevalue = voltagevalue;
    }

    public Integer getTvalue() {
        return tvalue;
    }

    public void setTvalue(Integer tvalue) {
        this.tvalue = tvalue;
    }

    public String getImei() {
        return Imei;
    }

    public void setImei(String imei) {
        Imei = imei;
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public Integer getTamperstatus() {
        return tamperstatus;
    }

    public void setTamperstatus(Integer tamperstatus) {
        this.tamperstatus = tamperstatus;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Integer getCstatus() {
        return cstatus;
    }

    public void setCstatus(Integer cstatus) {
        this.cstatus = cstatus;
    }

    public String getMonitordata() {
        return monitordata;
    }

    public void setMonitordata(String monitordata) {
        this.monitordata = monitordata;
    }

    public String getDataunit() {
        return dataunit;
    }

    public void setDataunit(String dataunit) {
        this.dataunit = dataunit;
    }

    public Integer getWpstatus() {
        return wpstatus;
    }

    public void setWpstatus(Integer wpstatus) {
        this.wpstatus = wpstatus;
    }

    public Integer getWrstatus() {
        return wrstatus;
    }

    public void setWrstatus(Integer wrstatus) {
        this.wrstatus = wrstatus;
    }

    public Integer getWdstatus() {
        return wdstatus;
    }

    public void setWdstatus(Integer wdstatus) {
        this.wdstatus = wdstatus;
    }

    public Integer getWkstatus() {
        return wkstatus;
    }

    public void setWkstatus(Integer wkstatus) {
        this.wkstatus = wkstatus;
    }

    public Integer getWostatus() {
        return wostatus;
    }

    public void setWostatus(Integer wostatus) {
        this.wostatus = wostatus;
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

    public long getCollectiontime() {
        return collectiontime;
    }

    public void setCollectiontime(long collectiontime) {
        this.collectiontime = collectiontime;
    }
}
