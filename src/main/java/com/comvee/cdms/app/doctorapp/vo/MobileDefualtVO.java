package com.comvee.cdms.app.doctorapp.vo;

import java.io.Serializable;

public class MobileDefualtVO implements Serializable{

	private static final long serialVersionUID = 123456789L;
	
	/**接入ID*/
	private String join_id;
	
	/**请求流水号*/
	private String req_num;
	
	/**安全认证*/
	private String valid;
	
	/**用户会话ID*/
	private String sessionId;
	
	/**设备号*/
	private String dev;
	
	/**系统版本号*/
	private String sys;
	
	/**设备型号*/
	private String dev_type;
	
	/**SMS卡编号*/
	private String imsi;
	
	/**网络方式*/
	private String net;
	
	/**分辨率*/
	private String reso;
	
	/**客户端版本号*/
	private String ver;
	
	/**推送key*/
	private String pushTokenKey;
	
	/**装载渠道*/
	private String loadFrom;
	
	private String jsessionId;
	
	/**用户ip地址*/
	private String ip;
	
	public String getJoin_id() {
		return join_id;
	}
	public void setJoin_id(String joinId) {
		join_id = joinId;
	}
	public String getReq_num() {
		return req_num;
	}
	public void setReq_num(String reqNum) {
		req_num = reqNum;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDev() {
		return dev;
	}
	public void setDev(String dev) {
		this.dev = dev;
	}
	public String getSys() {
		return sys;
	}
	public void setSys(String sys) {
		this.sys = sys;
	}
	public String getDev_type() {
		return dev_type;
	}
	public void setDev_type(String devType) {
		dev_type = devType;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getNet() {
		return net;
	}
	public void setNet(String net) {
		this.net = net;
	}
	public String getReso() {
		return reso;
	}
	public void setReso(String reso) {
		this.reso = reso;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getPushTokenKey() {
		return pushTokenKey;
	}
	public void setPushTokenKey(String pushTokenKey) {
		this.pushTokenKey = pushTokenKey;
	}
	public String getLoadFrom() {
		return loadFrom;
	}
	public void setLoadFrom(String loadFrom) {
		this.loadFrom = loadFrom;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getJsessionId() {
		return jsessionId;
	}
	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}
	
}
