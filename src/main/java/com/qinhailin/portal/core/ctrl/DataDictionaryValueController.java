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

package com.qinhailin.portal.core.ctrl;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.model.DataDictionaryValue;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.portal.core.service.DataDictionaryValueService;

/**
 * 数据字典
 * @author QinHaiLin
 */
@ControllerBind(path="/portal/core/dictionary/value")
public class DataDictionaryValueController extends BaseController {

	@Inject
	DataDictionaryValueService service;
		
	public void list(){
		Record record=new Record();
		record.set("dictionary_code", getPara("code"));
		renderJson(service.queryForListEq(getParaToInt("pageNumber",1), getParaToInt("pageSize",10),record));
	}
	
	public void add(){
		setAttr("code",getPara("code"));
		render("add.html");
	}
	
	@Before(EvictInterceptor.class)
	@CacheName("dictionary")
	public void save(DataDictionaryValue dataDictionaryValue){
		String isExitSql="dictionary_code='"+dataDictionaryValue.getDictionaryCode()+"' and value";
		if(service.isExit(isExitSql, dataDictionaryValue.getValue())){
			setException("字典的数据值不能重复,请重新输入");
			setAttr("dataDictionaryValue",dataDictionaryValue);
		}else{		
			dataDictionaryValue.setId(createUUID()).save();
		}
		setAttr("code",dataDictionaryValue.getDictionaryCode());
		render("add.html");
	}
	
	public void edit(){
		setAttr("dataDictionaryValue",service.findById(getPara()));
		render("edit.html");
	}
	
	@Before(EvictInterceptor.class)
	@CacheName("dictionary")
	public void update(DataDictionaryValue dataDictionaryValue){
		dataDictionaryValue.update();
		setAttr("dataDictionaryValue",dataDictionaryValue);
		render("edit.html");
	}
	
	@Before(EvictInterceptor.class)
	@CacheName("dictionary")
	public void delete(){
		service.deleteByIds(getIds());  
		renderJson(suc());
	}
	
	/**
	 * 下拉选择数据接口
	 */
	public void getOption(){
		List<Record> list=CacheKit.get("dictionary", getPara("code"), new IDataLoader() {
			
			@Override
			public Object load() {
				Record record=new Record();
				record.set("dictionary_code", getPara("code"));
				return service.queryForListEq(getParaToInt("pageNumber",1), getParaToInt("pageSize",20),record).getList();
			}
		});
		renderJson(list);
	}
}
