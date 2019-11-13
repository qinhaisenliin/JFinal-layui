package com.qinhailin.common.visit.impl;

import java.util.Map;

import com.qinhailin.common.entity.ILoginUser;
import com.qinhailin.common.model.SysOrg;
import com.qinhailin.common.visit.Visitor;


/**
 * @author hhs
 * @date 2018年1月23日
 */
public class VisitorImpl implements Visitor {
	private static final long serialVersionUID = 1L;
	ILoginUser user;
	private long loginTime;
	private String orgName;
	private Map<String,Boolean> funcMap;
	private static final SysOrg orgDao=new SysOrg().dao();
	
	public VisitorImpl(ILoginUser user) {
		super();
		loginTime = System.currentTimeMillis();
		this.user = user;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.xunyijia.auth.visit.Visitor#getLoginTime()
	 */
	@Override
	public long getLoginTime() {
		return loginTime;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.xunyijia.auth.visit.Visitor#getUserData()
	 */
	@Override
	public ILoginUser getUserData() {
		return user;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.xunyijia.auth.visit.Visitor#getName()
	 */
	@Override
	public String getName() {
		return user.getUserName();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.xunyijia.auth.visit.Visitor#getCode()
	 */
	@Override
	public String getCode() {
		return user.getUserCode();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.xunyijia.auth.visit.Visitor#getOrgName()
	 */
	@Override
	public String getOrgName() {
		SysOrg sysOrg=orgDao.findById(user.getOrgId());
		if(sysOrg!=null){
			orgName=sysOrg.getOrgName();
		}
		return orgName;
	}

	@Override
	public Map<String, Boolean> getFuncMap() {
		return funcMap;
	}

	public void setFuncMap(Map<String, Boolean> funcMap) {
		this.funcMap = funcMap;
	}

	@Override
	public Integer getType() {
		return null;
	}


}
