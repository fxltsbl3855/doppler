package com.sinoservices.doppler.model;

import java.io.Serializable;

public class ServiceStatModel implements Serializable{
	
	private static final long serialVersionUID = -1909486248097837851L;
	private String spanViewId;
	private String spanView;
	private int reqNum;
	private double qps;
	private int maxTime;
	private int timeoutNum;
	private double timeoutPercent;
	private int errorNum;
	private double errorPercent;
	private Boolean isDefineException = true;
	private Boolean isSystemException = true;
	
	public String getSpanViewId() {
		return this.spanViewId;
	}

	public void setSpanViewId(String spanViewId) {
		this.spanViewId = spanViewId;
	}

	public String getSpanView() {
		return this.spanView;
	}

	public void setSpanView(String spanView) {
		this.spanView = spanView;
	}

	public int getReqNum() {
		return this.reqNum;
	}

	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}

	public double getQps() {
		return this.qps;
	}

	public void setQps(double qps) {
		this.qps = qps;
	}

	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
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
