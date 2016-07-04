package com.sinoservices.doppler.model;

import java.io.Serializable;

public class RequestModel implements Serializable{
	
	private static final long serialVersionUID = 4098477911931193694L;
	private String id;
	private String appName;
	private String serviceName;
	private String addr;
	private String errorLevelDisplay;
	private String reqTime;
	private String logInfo;
	private String spanId;
	private Boolean isDefineException = true;
	private Boolean isSystemException = true;
	
	public String getReqTime() {
		return this.reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getErrorLevelDisplay() {
		return this.errorLevelDisplay;
	}

	public void setErrorLevelDisplay(String errorLevelDisplay) {
		this.errorLevelDisplay = errorLevelDisplay;
	}

	public String getLogInfo() {
		return this.logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getSpanId() {
		return this.spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public Boolean getIsDefineException() {
		return isDefineException;
	}

	public void setIsDefineException(Boolean isDefineException) {
		this.isDefineException = isDefineException;
	}

	public Boolean getIsSystemException() {
		return isSystemException;
	}

	public void setIsSystemException(Boolean isSystemException) {
		this.isSystemException = isSystemException;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
