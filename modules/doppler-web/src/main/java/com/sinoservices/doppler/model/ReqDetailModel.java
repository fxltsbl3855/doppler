package com.sinoservices.doppler.model;

import java.io.Serializable;

public class ReqDetailModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5906026380593507110L;
	private String spanId;
	private String sourceAppName;
	private String sourceServiceName;
	private String sourceAddrName;
	private String targetAppName;
	private String targetServiceName;
	private String targetAddrName;
	private String errorAddr;
	private String errorTime;
	private String errorInfo;
	private Boolean isDefineException = true;
	private Boolean isSystemException = true;
	
	public String getSpanId() {
		return this.spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public String getSourceAppName() {
		return this.sourceAppName;
	}

	public void setSourceAppName(String sourceAppName) {
		this.sourceAppName = sourceAppName;
	}

	public String getSourceServiceName() {
		return this.sourceServiceName;
	}

	public void setSourceServiceName(String sourceServiceName) {
		this.sourceServiceName = sourceServiceName;
	}

	public String getSourceAddrName() {
		return this.sourceAddrName;
	}

	public void setSourceAddrName(String sourceAddrName) {
		this.sourceAddrName = sourceAddrName;
	}

	public String getTargetAppName() {
		return this.targetAppName;
	}

	public void setTargetAppName(String targetAppName) {
		this.targetAppName = targetAppName;
	}

	public String getTargetServiceName() {
		return this.targetServiceName;
	}

	public void setTargetServiceName(String targetServiceName) {
		this.targetServiceName = targetServiceName;
	}

	public String getTargetAddrName() {
		return this.targetAddrName;
	}

	public void setTargetAddrName(String targetAddrName) {
		this.targetAddrName = targetAddrName;
	}

	public String getErrorAddr() {
		return this.errorAddr;
	}

	public void setErrorAddr(String errorAddr) {
		this.errorAddr = errorAddr;
	}

	public String getErrorTime() {
		return this.errorTime;
	}

	public void setErrorTime(String errorTime) {
		this.errorTime = errorTime;
	}

	public String getErrorInfo() {
		return this.errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
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
