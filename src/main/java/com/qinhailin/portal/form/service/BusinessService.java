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
 * @author QinHaiLin
 * @date 2019年3月14日  
 */
public class BusinessService extends DbService{


	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.DbService#getTable()
	 */
	@Override
	public String getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.DbService#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Grid queryPage(int pageNumber,int pageSize,String table,Kv columns){
		
		return page(pageNumber,pageSize,table,getKv(columns));
	}

	/**
	 * 模糊查询
	 * @param columns
	 * @return
	 */
	private Kv getKv(Kv columns) {
		Kv kv=new Kv();
		for(Object key:columns.keySet()) {
			if(columns.get(key)!=null&&!columns.get(key).equals("")) {				 
				kv.set(key+" like",columns.get(key));						
			}
		}
		return kv;
	}
}
