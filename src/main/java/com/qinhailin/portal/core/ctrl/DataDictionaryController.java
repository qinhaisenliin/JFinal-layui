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

import java.util.ArrayList;
import java.util.Collection;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.model.DataDictionary;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.TreeNode;
import com.qinhailin.portal.core.service.DataDictionaryService;
import com.qinhailin.portal.core.service.DataDictionaryValueService;

/**
 * 数据字典
 * @author QinHaiLin
 */
@ControllerBind(path="/portal/core/dictionary")
public class DataDictionaryController extends BaseController {

	@Inject
	DataDictionaryService service;
	@Inject
	DataDictionaryValueService dataDictionaryValueService;
	
	public void index(){
		render("index.html");
	}
		
	public void add(){
		render("add.html");
	}
	
	public void save(){
		DataDictionary dataDictionary=getBean(DataDictionary.class);
		if(service.isExit("code", dataDictionary.getCode())){
			setException("字典编号已存在,请重新输入");
			setAttr("dataDictionary",dataDictionary);
		}else{
			dataDictionary.setId(createUUID()).save();		
		}
		render("add.html");
	}
	
	public void edit(){
		setAttr("dataDictionary",service.findByPk("code",getPara()));
		render("edit.html");
	}
	
	@Before(EvictInterceptor.class)
	@CacheName("dictionary")
	public void update(){
		getBean(DataDictionary.class).update();
		setAttr("dataDictionary",getBean(DataDictionary.class));
		render("edit.html");
	}
	
	@Before(EvictInterceptor.class)
	@CacheName("dictionary")
	public void delete(){
		service.deleteByPk(getIds(),"code");
		dataDictionaryValueService.deleteByPk(getIds(), "dictionary_code");
		renderJson(suc());
	}
	
	public void tree(){
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setId("frame");
		node.setText("业务字典");
		node.setChildren(service.getDictionaryTree());
		nodes.add(node);
		renderJson(nodes);
	}
}
