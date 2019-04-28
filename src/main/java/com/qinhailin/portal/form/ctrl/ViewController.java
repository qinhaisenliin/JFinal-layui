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

import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.config.WebContant;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.TreeNode;
import com.qinhailin.portal.form.service.FormViewService;
import com.qinhailin.portal.form.service.SysTreeService;


/**
 * 在线表单设计
 * @author QinHaiLin
 * @date 2019年4月24日  
 */
@ControllerBind(path="/portal/form/view")
public class ViewController extends BaseController {

	@Inject
	FormViewService service;
	@Inject
	SysTreeService sysTreeService;
	
	/**
	 * 表单首页
	 * 
	 * @author QinHaiLin
	 * @date 2019年4月24日
	 */
	public void index() {
		render("index.html");
	}
	
	/**
	 * 表单分类树
	 * 
	 * @author QinHaiLin
	 * @date 2019年4月24日
	 */
	public void tree() {
		Collection<TreeNode> nodeList = sysTreeService.getSysTree(getPara("type"),null);
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setId("");
		node.setText(WebContant.projectName);
		node.setChildren(nodeList);
		nodes.add(node);
		renderJson(nodes);
	}
	
	/**
	 * 表单查询列表
	 * 
	 * @author QinHaiLin
	 * @date 2019年4月24日
	 */
	public void list() {
		renderJson(service.queryPage(getParaToInt("pageNumber"),getParaToInt("pageSize"),getKv()));
	}
	
	public void add() {
		setAttr("treeId", getPara("treeId"));
		setAttr("treeName", getPara("treeName"));
		render("add.html");
	}
		
	@SuppressWarnings("unchecked")
	public void save() {	
		Record record=new Record().setColumns(getKv());
		if(service.isExit("code", record.get("code"))){
			renderJson(fail("编号已存在，请重新输入！"));
			return;	
		}
		
		record.set(service.getPrimaryKey(), createUUID());		
		record.set("create_time", new Date()).set("status", "INIT");		
		boolean b=service.save(record);
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
		Record formView=service.findById(getPara(0));
		if(formView==null) {
			renderJson(fail("对象数据不存在"));
			return;
		}
		
		//查询所属分类树名称
		Record sysTree=sysTreeService.findById(formView.get("tree_id"));
		if(sysTree!=null) {
			String treeName=sysTree.get("name");
			formView.set("tree_name", treeName);
		}
		renderJson(ok(formView));
	}
	
	@SuppressWarnings("unchecked")
	public void update() {
		Record record=new Record().setColumns(getKv());
		record.set("update_time", new Date());
		boolean b=service.update(record);
		if(!b) {
			renderJson(fail());
			return;
		}
		renderJson(ok());
	}
	
	public void delete() {
		boolean b=service.deleteByIds(getIds());
		renderJson(b?suc():err());
	}
}
