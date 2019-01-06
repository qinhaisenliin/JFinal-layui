package com.qinhailin.common.entity;

/**
 *
 * 用户实体类接口
 *
 * @author hhs
 * @date 2017年11月16日
 */
public interface IUser {

	/**
	 *
	 * 获取用户ID
	 *
	 * @return
	 * @author hhs
	 * @date 2017年11月16日
	 */
	public String getId();

	/**
	 * 获取用户编号
	 *
	 * @return
	 * @author hhs
	 * @date 2017年11月16日
	 */
	public String getUserCode();

	/**
	 * 获取用户名
	 * 
	 * @return
	 * @author hhs
	 * @date 2017年11月16日
	 */
	public String getUserName();

	/**
	 * 获取机构ID
	 * 
	 * @return
	 * @author hhs
	 * @date 2017年11月16日
	 */
	public String getOrgId();

	/**
	 * 获取密码
	 * 
	 * @return
	 * @author hhs
	 * @date 2017年11月16日
	 */
	public String getPassword();

}
