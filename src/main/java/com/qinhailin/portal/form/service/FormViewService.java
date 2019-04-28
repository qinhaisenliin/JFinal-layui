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

package com.qinhailin.portal.form.service;

import com.jfinal.kit.Kv;
import com.qinhailin.common.base.service.DbService;
import com.qinhailin.common.vo.Grid;

/**
 * 流程表单
 * @author QinHaiLin
 * @date 2019年4月24日  
 */
public class FormViewService extends DbService{
		
	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.DbService#getTable()
	 */
	@Override
	public String getTable() {
		return "form_view";
	}

	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.DbService#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		return "id";
	}
	
	public Grid queryPage(int pageNumber,int pageSize,Kv columns){
		Kv kv=new Kv();
		kv.set("name like", columns.get("name"));
		kv.set("code like",columns.get("code"));
		kv.set("status =", columns.get("status"));
		kv.set("tree_id =", columns.get("tree_id"));
		return page(pageNumber, pageSize, kv,"order by create_time desc");
	}
}
