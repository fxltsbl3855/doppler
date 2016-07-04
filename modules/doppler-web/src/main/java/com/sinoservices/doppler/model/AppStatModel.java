package com.sinoservices.doppler.model;

import java.io.Serializable;

public class AppStatModel implements Serializable{

	private static final long serialVersionUID = -6357432911911836551L;
	private int appId;
	private String appName;
	private int reqNum;
	private int timeoutNum;
	private double timeoutPercent;
	private int errorNum;
	private double errorPercent;
	private Boolean isDefineException = true;
	private Boolean isSystemException = true;
	
	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getReqNum() {
		return this.reqNum;
	}

	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}

	public int getTimeoutNum() {
		return this.timeoutNum;
	}

	public void setTimeoutNum(int timeoutNum) {
		this.timeoutNum = timeoutNum;
	}

	public double getTimeoutPercent() {
		return this.timeoutPercent;
	}

	public void setTimeoutPercent(double timeoutPercent) {
		this.timeoutPercent = timeoutPercent;
	}

	public int getErrorNum() {
		return this.errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	public double getErrorPercent() {
		return this.errorPercent;
	}

	public void setErrorPercent(double errorPercent) {
		this.errorPercent = errorPercent;
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

}
