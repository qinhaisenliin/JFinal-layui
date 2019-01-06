/**
 * @author hhs
 *  @date 2015年7月20日
 */
package com.qinhailin.common.entity;
/**
 * 保存登录帐号用户ID，帐号名
 *
 * @author qinhailin
 */
public interface ILoginUser extends IUser {

	/**
	 * 用户ID
	 *
	 * @return the id
	 */
	@Override
	public String getId();

	/**
	 * 登陆账号
	 *
	 * @return
	 */
	@Override
	public String getUserCode();

	/**
	 * @return the password
	 */
	@Override
	public String getPassword();

	/**
	 * 用户名称
	 *
	 * @return
	 */
	@Override
	public String getUserName();

	@Override
	public String getOrgId();

	/**
	 * 获取所在企业ID
	 * 
	 * @return
	 */
	public String getCompany();

	/**
	 *
	 * <p>
	 * 登录用户IP
	 * </p>
	 *
	 * @return
	 */
	public String getIp();

}
