/**
 * @author qinhailin
 * @date 2018年8月17日
 */
package com.qinhailin.portal.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
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
	private final String table="sys_role_function";

	@Override
	public SysRoleFunction getDao(){
		return dao;
	}
	
	@Override
	public String getTable(){
		return table;
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
					map.put(r.getStr("url"), true);
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
					map.put(r.getStr("url"), true);
				}
				return map;
			}
		});
    }
}
