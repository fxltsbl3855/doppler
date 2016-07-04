package com.sinoservices.doppler.view;



public class UserQueryCondition{

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -3642267567274510487L;
	
	/**
	 * 用户账号
	 */
	private String account;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 激活状态
	 */
	private String activeStatus;
	
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the activeStatus
	 */
	public String getActiveStatus() {
		return activeStatus;
	}

	/**
	 * @param activeStatus the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
}