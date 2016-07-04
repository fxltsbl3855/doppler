
package com.sinoservices.doppler.model;

import java.util.Date;

/**
 * <p>联系人表实体类</p>
 * 表名：CD_CONTACT
 * <pre>
 * 修改记录:
 * 修改后版本				修改人			修改内容
 * 2013-12-16.1	Carter.zheng	create
 * </pre>
 */
public class CdContactModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 联系人表主键
	 */
	private Long cdcoId;
	
	/**
	 * 创建人
	 */
	private String creator;
	
	/**
	 * 联系人名称
	 */
	private String cdcoContactName;
	
	/**
	 * 联系人邮箱
	 */
	private String cdcoEmail;
	
	/**
	 * 联系人头衔
	 */
	private String cdcoViscountship;
	
	/**
	 * 时间戳
	 */
	private Long recVer;
	
	/**
	 * 修改人
	 */
	private String modifier;
	
	/**
	 * 代码
	 */
	private String pmCode;
	
	/**
	 * 联系人电话
	 */
	private String cdcoMobile;
	
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	
	/**
	 * 联系人传真
	 */
	private String cdcoFax;
	
	/**
	 * 联系人手机
	 */
	private String cdcoTel;
	
	/**
	 * 是否默认联系人:N否,Y是
	 */
	private String cdcoIsDefault;
	
	/**
	 * 分公司
	 */
	private String orgId;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 备注
	 */
	private String cdcoRemark;
	

	/**
	 * 设置 联系人表主键 
	 * @param cdcoId
	 *            the cdcoId to set
	 */
	public void setCdcoId(Long cdcoId){
		this.cdcoId = cdcoId;
	}
	
	/**
	 * 设置 创建人 
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator){
		this.creator = creator;
	}
	
	/**
	 * 设置 联系人名称 
	 * @param cdcoContactName
	 *            the cdcoContactName to set
	 */
	public void setCdcoContactName(String cdcoContactName){
		this.cdcoContactName = cdcoContactName;
	}
	
	/**
	 * 设置 联系人邮箱 
	 * @param cdcoEmail
	 *            the cdcoEmail to set
	 */
	public void setCdcoEmail(String cdcoEmail){
		this.cdcoEmail = cdcoEmail;
	}
	
	/**
	 * 设置 联系人头衔 
	 * @param cdcoViscountship
	 *            the cdcoViscountship to set
	 */
	public void setCdcoViscountship(String cdcoViscountship){
		this.cdcoViscountship = cdcoViscountship;
	}
	
	/**
	 * 设置 时间戳 
	 * @param recVer
	 *            the recVer to set
	 */
	public void setRecVer(Long recVer){
		this.recVer = recVer;
	}
	
	/**
	 * 设置 修改人 
	 * @param modifier
	 *            the modifier to set
	 */
	public void setModifier(String modifier){
		this.modifier = modifier;
	}
	
	/**
	 * 设置 代码 
	 * @param pmCode
	 *            the pmCode to set
	 */
	public void setPmCode(String pmCode){
		this.pmCode = pmCode;
	}
	
	/**
	 * 设置 联系人电话 
	 * @param cdcoMobile
	 *            the cdcoMobile to set
	 */
	public void setCdcoMobile(String cdcoMobile){
		this.cdcoMobile = cdcoMobile;
	}
	
	/**
	 * 设置 修改时间 
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Date modifyTime){
		this.modifyTime = modifyTime;
	}
	
	/**
	 * 设置 联系人传真 
	 * @param cdcoFax
	 *            the cdcoFax to set
	 */
	public void setCdcoFax(String cdcoFax){
		this.cdcoFax = cdcoFax;
	}
	
	/**
	 * 设置 联系人手机 
	 * @param cdcoTel
	 *            the cdcoTel to set
	 */
	public void setCdcoTel(String cdcoTel){
		this.cdcoTel = cdcoTel;
	}
	
	/**
	 * 设置 是否默认联系人:N否,Y是 
	 * @param cdcoIsDefault
	 *            the cdcoIsDefault to set
	 */
	public void setCdcoIsDefault(String cdcoIsDefault){
		this.cdcoIsDefault = cdcoIsDefault;
	}
	
	/**
	 * 设置 分公司 
	 * @param orgId
	 *            the orgId to set
	 */
	public void setOrgId(String orgId){
		this.orgId = orgId;
	}
	
	/**
	 * 设置 创建时间 
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	/**
	 * 设置 备注 
	 * @param cdcoRemark
	 *            the cdcoRemark to set
	 */
	public void setCdcoRemark(String cdcoRemark){
		this.cdcoRemark = cdcoRemark;
	}
	

	/**
	 * @return   联系人表主键 
	 */
	public Long getCdcoId(){
		return cdcoId;
	}
	
	/**
	 * @return   创建人 
	 */
	public String getCreator(){
		return creator;
	}
	
	/**
	 * @return   联系人名称 
	 */
	public String getCdcoContactName(){
		return cdcoContactName;
	}
	
	/**
	 * @return   联系人邮箱 
	 */
	public String getCdcoEmail(){
		return cdcoEmail;
	}
	
	/**
	 * @return   联系人头衔 
	 */
	public String getCdcoViscountship(){
		return cdcoViscountship;
	}
	
	/**
	 * @return   时间戳 
	 */
	public Long getRecVer(){
		return recVer;
	}
	
	/**
	 * @return   修改人 
	 */
	public String getModifier(){
		return modifier;
	}
	
	/**
	 * @return   代码 
	 */
	public String getPmCode(){
		return pmCode;
	}
	
	/**
	 * @return   联系人电话 
	 */
	public String getCdcoMobile(){
		return cdcoMobile;
	}
	
	/**
	 * @return   修改时间 
	 */
	public Date getModifyTime(){
		return modifyTime;
	}
	
	/**
	 * @return   联系人传真 
	 */
	public String getCdcoFax(){
		return cdcoFax;
	}
	
	/**
	 * @return   联系人手机 
	 */
	public String getCdcoTel(){
		return cdcoTel;
	}
	
	/**
	 * @return   是否默认联系人:N否,Y是 
	 */
	public String getCdcoIsDefault(){
		return cdcoIsDefault;
	}
	
	/**
	 * @return   分公司 
	 */
	public String getOrgId(){
		return orgId;
	}
	
	/**
	 * @return   创建时间 
	 */
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	 * @return   备注 
	 */
	public String getCdcoRemark(){
		return cdcoRemark;
	}
	

}
