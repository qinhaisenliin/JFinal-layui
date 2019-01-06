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
	private final String table="sys_log";
	@Override
	public Model<?> getDao() {
		return dao;
	}

	@Override
	public String getTable() {
		return table;
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
