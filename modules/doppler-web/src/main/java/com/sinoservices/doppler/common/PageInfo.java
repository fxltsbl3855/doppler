package com.sinoservices.doppler.common;


public class PageInfo {

	protected Long total;

	protected int limit = 0; 

	protected int offset = 0; //偏移量 从哪条开始

	protected String order = "asc";

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

//	public RowBounds buildBounds() {
//		int os = offset;
//		int pageSize = this.limit;
//		int pageNumber = 1;
//		if (os != 0 && os/pageSize > 1){
//			pageNumber = os/pageSize;
//		}
//		
//		return new RowBounds(pageNumber, pageSize);
//	}
}
