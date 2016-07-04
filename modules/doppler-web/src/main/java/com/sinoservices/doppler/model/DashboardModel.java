package com.sinoservices.doppler.model;

import java.io.Serializable;
import java.util.List;

import com.sinoservices.doppler.bo.AppReq;

public class DashboardModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8509524525290022274L;
	private int webNum;
	private int serverNum;
	private int webReqNum;
	private int serverReqNum;
	List<AppReq> webReqList;
	List<AppReq> serverReqList;

	public DashboardModel() {
	}

	public DashboardModel(int webNum, int serverNum) {
	}

	public int getWebNum() {
		return this.webNum;
	}

	public void setWebNum(int webNum) {
		this.webNum = webNum;
	}

	public int getServerNum() {
		return this.serverNum;
	}

	public void setServerNum(int serverNum) {
		this.serverNum = serverNum;
	}

	public int getWebReqNum() {
		return this.webReqNum;
	}

	public void setWebReqNum(int webReqNum) {
		this.webReqNum = webReqNum;
	}

	public int getServerReqNum() {
		return this.serverReqNum;
	}

	public void setServerReqNum(int serverReqNum) {
		this.serverReqNum = serverReqNum;
	}

	public List<AppReq> getWebReqList() {
		return this.webReqList;
	}

	public void setWebReqList(List<AppReq> webReqList) {
		this.webReqList = webReqList;
	}

	public List<AppReq> getServerReqList() {
		return this.serverReqList;
	}

	public void setServerReqList(List<AppReq> serverReqList) {
		this.serverReqList = serverReqList;
	}
}
