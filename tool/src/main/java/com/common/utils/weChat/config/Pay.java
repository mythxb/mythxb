package com.common.utils.weChat.config;

public class Pay {
	
	private String appid;
	
	private String partnerid;
	
	private String packages;
	
	private String noncestr;
	
	private String timestamp;
	
	private String prepayid;
	
	private String key;
	
	private String sign;
	
	private String alipay;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Pay [appid=" + appid + ", partnerid=" + partnerid + ", packages=" + packages + ", noncestr=" + noncestr
				+ ", timestamp=" + timestamp + ", prepayid=" + prepayid + ", key=" + key + ", sign=" + sign
				+ ", alipay=" + alipay + "]";
	}

	

	
	
	

}
