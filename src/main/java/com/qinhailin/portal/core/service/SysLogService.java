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

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.SysLog;

/**
 * 系统日志
 * @author qinhailin
 *
 */
public class SysLogService extends BaseService {

	private SysLog dao=new SysLog().dao();

	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.BaseService#getDao()
	 */
	@Override
	public Model<?> getDao() {
		return dao;
	}
	
	public Map<String,String> getFuncMapForLog(){
		return CacheKit.get("userFunc", "getFuncMapForLog", new IDataLoader() {	
			@Override
			public Object load() {
				Map<String,String> map=new HashMap<>();
				List<Record> list=queryForList("select link_page,func_name,parent_name  from sys_function where link_page is not null");
				for(Record record:list){
					map.put(record.getStr("link_page"), record.getStr("func_name"));
					map.put(record.getStr("link_page")+record.getStr("func_name"), record.getStr("parent_name"));
				}
				return map;
			}
		});
	}
	
}
