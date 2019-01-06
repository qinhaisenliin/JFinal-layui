package com.qinhailin.common.visit;

import java.io.Serializable;
import java.util.Map;

import com.qinhailin.common.entity.ILoginUser;


/**
 * 访问者接口
 * 
 * @author qinhailin
 * @date 2018-10-23
 */
public interface Visitor extends Serializable {
	public long getLoginTime();

	public ILoginUser getUserData();

	public String getName();

	public String getCode();

	public String getOrgName();

	public Integer getType(); // 获取用户类型
	
	public Map<String,Boolean> getFuncMap();
}
