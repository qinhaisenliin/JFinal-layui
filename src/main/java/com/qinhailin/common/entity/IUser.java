package com.qinhailin.common.entity;

import java.io.Serializable;

/**
 * 用户实体类接口
 */
public interface IUser extends Serializable{

	/**
	 * 获取用户ID
	 */
	public String getId();

	/**
	 * 获取用户编号
	 */
	public String getUserCode();

	/**
	 * 获取用户名
	 */
	public String getUserName();

	/**
	 * 获取机构ID
	 */
	public String getOrgId();

	/**
	 * 获取密码
	 */
	public String getPassword();

}
