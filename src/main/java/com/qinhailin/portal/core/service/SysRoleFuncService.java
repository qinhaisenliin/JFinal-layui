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

package com.qinhailin.portal.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.SysRoleFunction;

/**
 * 角色权限关系
 * 
 * @author qinhailin
 *
 */
public class SysRoleFuncService extends BaseService {

	private SysRoleFunction dao = new SysRoleFunction().dao();

	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.BaseService#getDao()
	 */
	@Override
	public Model<?> getDao() {
		return dao;
	}

	/**
	 * 用户权限
	 * @param userCode
	 * @return
	 * @author qinhailin
	 * @date 2018年11月14日
	 */
    public Map<String,Boolean> getUserPermissions(String userCode) {	
    	return CacheKit.get("userFunc", userCode, new IDataLoader() {		
			@Override
			public Object load() {							
				String sql = Db.getSql("core.getUserPermissions");	
				List<Record> list=Db.find(sql, userCode);
				
				Map<String,Boolean> map=new HashMap<>();
				for(Record r:list) {
					map.put(r.getStr("link_page"), true);
				}
				return map;
			}
		});
	}
    
    /**
     * 系统权限
     * @return
     * @author qinhailin
     * @date 2018年11月14日
     */
    public Map<String,Boolean> getSysPermissions() {   	
    	return CacheKit.get("userFunc", "allPermissions", new IDataLoader() {  		
			@Override
			public Object load() {			
				List<Record> list=Db.find(Db.getSql("core.getAllPermissions"));
				Map<String,Boolean> map=new HashMap<>();		
				for(Record r:list) {					
					map.put(r.getStr("link_page"), true);
				}
				return map;
			}
		});
    }
}
