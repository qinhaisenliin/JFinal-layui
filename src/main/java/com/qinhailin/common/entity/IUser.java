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
