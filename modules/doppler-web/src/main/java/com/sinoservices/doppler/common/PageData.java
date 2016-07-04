package com.sinoservices.doppler.common;

import java.util.List;

public class PageData {

	protected int total;

	protected List rows;

	public PageData(int total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
