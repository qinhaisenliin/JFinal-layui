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

package com.qinhailin.portal.form.ctrl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Inject;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.config.WebContant;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.TreeNode;
import com.qinhailin.portal.form.service.SysTreeService;


/**
 * 系统树
 * @author QinHaiLin
 * @date 2019年4月24日  
 */
@ControllerBind(path="/portal/form/sysTree")
public class SysTreeController extends BaseController {

	@Inject
	SysTreeService service;
	
	/**
	 * 表单分类树
	 * 
	 * @author QinHaiLin
	 * @date 2019年3月6日
	 */
	public void tree() {
		Collection<TreeNode> nodeList = service.getSysTree(getPara("type"),null);
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setId("");
		node.setText(WebContant.projectName);
		node.setChildren(nodeList);
		nodes.add(node);
		renderJson(nodes);
	}
	
	public void add() {
		render("add.html");
	}
	
	/**
	 * json方式保存数据
	 * 
	 * @author QinHaiLin
	 * @date 2019年3月13日
	 */
	@SuppressWarnings("unchecked")
	public void save() {
		Record record=new Record().setColumns(getKv());
		record.set(service.getPrimaryKey(), createUUID());
		record.set("create_time",new Date());
		boolean b=service.save(service.getTable(),service.getPrimaryKey(), record);
		if(!b) {
			renderJson(fail());
			return;
		}
		renderJson(ok());
	}
	
	public void edit() {
		render("edit.html");
	}
	
	public void getModel() {
		List<Record> list=service.find(service.getTable(), Kv.by(service.getPrimaryKey()+"=", getPara(0)));
		if(list.size()==0) {
			renderJson(fail());
			return;
		}
		renderJson(ok(list.get(0)));
	}
	
	/**
	 * json方式修改数据
	 * 
	 * @author QinHaiLin
	 * @date 2019年3月13日
	 */
	@SuppressWarnings("unchecked")
	public void update() {
		Record record=new Record().setColumns(getKv());
		boolean b=service.update(service.getTable(), service.getPrimaryKey(), record);
		if(!b) {
			renderJson(fail());
			return;
		}
		renderJson(ok());
	}
	
	public void delete() {
		service.deleteByIds(service.getTable(), service.getPrimaryKey(), getIds());
		renderJson(suc());
	}
	
}
