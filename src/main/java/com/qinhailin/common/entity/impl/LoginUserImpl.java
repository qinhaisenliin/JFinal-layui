/**
 * Copyright 2019 覃海林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.qinhailin.common.entity.impl;

import com.qinhailin.common.entity.ILoginUser;

public class LoginUserImpl implements ILoginUser {
	private static final long serialVersionUID = 1L;
	private String id;
	private String userCode;
	private String userName;
	private String orgId;
	private String company;
	private String ip;
	private String password;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getUserCode() {
		return userCode;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getOrgId() {
		return orgId;
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
