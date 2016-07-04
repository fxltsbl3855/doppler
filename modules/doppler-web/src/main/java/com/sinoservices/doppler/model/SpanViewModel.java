package com.sinoservices.doppler.model;

import java.io.Serializable;

public class SpanViewModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4252516288177332350L;
	private int reqNum;
	private String date;
	private Boolean isDefineException = true;
	private Boolean isSystemException = true;

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

	public int getReqNum() {
		return this.reqNum;
	}

	public void setReqNum(int reqNum) {
		this.reqNum = reqNum;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
