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

import com.jfinal.aop.Inject;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.model.SysOrg;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.Feedback;
import com.qinhailin.common.vo.TreeNode;
import com.qinhailin.portal.core.service.SysOrgService;

/**
 * 部门
 * 
 * @author QinHaiLin
 *
 */
@ControllerBind(path="/portal/core/sysOrg")
public class SysOrgController extends BaseController {

	@Inject
	SysOrgService service;

	public void index() {
		render("index.html");
	}

	public void add() {
		SysOrg entity = (SysOrg) service.findById(getPara("orgCode"));	
		setAttr("parentid", entity!=null?entity.getId():"sys");
		setAttr("parentIdName", entity!=null?entity.getOrgName():"组织机构");
		render("add.html");
	}

	public void save() {
		SysOrg entity=getBean(SysOrg.class);
		entity.setId(createUUID()).setOrgCode(entity.getId()).save();
		setAttr("sysOrg", entity);
		CacheKit.removeAll("orgManager");
		render("add.html");
	}

	public void edit() {
		setAttr("sysOrg", service.findById(getPara("orgCode")));
		render("edit.html");
	}

	public void update() {
		SysOrg entity=getBean(SysOrg.class);
		entity.update();
		setAttr("sysOrg", entity);
		service.updateOrgParentName(entity.getId());
		CacheKit.removeAll("orgManager");
		render("edit.html");
	}

	public void detail() {
		setAttr("vo", service.findById(getPara("orgCode")));
		render("detail.html");
	}

	
	public void delete() {
		service.deleteOrgById(getPara("orgCode"));
		CacheKit.removeAll("orgManager");
		renderJson(Feedback.success());
	}

	/**
	 * 部门树
	 * 
	 * @author qinhailin
	 * @date 2018年10月8日
	 */
	public void tree() {
		Collection<TreeNode> nodeList = CacheKit.get("orgManager", "tree", new IDataLoader() {
			
			@Override
			public Object load() {
				return service.getOrgTree("sys");
			}
		});
		
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setId("sys");
		node.setText("组织机构");
		node.setChildren(nodeList);
		nodes.add(node);

		renderJson(nodes);
	}

}
