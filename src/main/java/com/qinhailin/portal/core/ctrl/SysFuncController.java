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
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.qinhailin.common.model.SysFunction;
import com.qinhailin.common.model.SysRole;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.Feedback;
import com.qinhailin.common.vo.TreeNode;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.config.WebContant;
import com.qinhailin.portal.core.service.SysFuncService;
import com.qinhailin.portal.core.service.SysRoleService;

/**
 * 功能
 * 
 * @author QinHaiLin
 *
 */
@ControllerBind(path="/portal/core/sysFunc")
public class SysFuncController extends BaseController {

	@Inject
	SysFuncService service;
	@Inject
	SysRoleService roleService;
	
	public void index() {
		render("index.html");
	}

	public void list() {
		Record record = new Record();
		record.set("parent_code ='"+getPara("id")+"' or id", getPara("id"));
		renderJson(service.page(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), record));
	}

	public void add() {
		setAttr("parentId", getPara("parentId"));
		SysFunction parent = (SysFunction) service.findById(getPara("parentId"));
		if (parent == null) {
			setAttr("parentName", WebContant.projectName);
		} else {
			setAttr("parentName", parent.getFuncName());
		}
		render("add.html");
	}

	public void save() {
		SysFunction entity=getBean(SysFunction.class);
		SysFunction parent = (SysFunction) service.findById(entity.getParentCode());
		if (parent == null) {
			entity.setParentName(WebContant.projectName);
		} else {
			entity.setParentName(parent.getFuncName());
		}
		if(!service.saveEntity(entity)) {
			setException("功能编号已存在，请重新输入");
		}
		setAttr("sysFunction", entity);
		CacheKit.removeAll("funcManager");
		render("add.html");
	}

	public void edit() {
		setAttr("sysFunction", service.findById(getPara()));
		render("edit.html");
	}

	@Before(EvictInterceptor.class)
	@CacheName("userFunc")
	public void update() {
		SysFunction entity=getBean(SysFunction.class);
		SysFunction parent = (SysFunction) service.findById(entity.getParentCode());
		if (parent == null) {
			entity.setParentName(WebContant.projectName);
		} else {
			entity.setParentName(parent.getFuncName());
		}
		entity.update();
		setAttr("sysFunction", entity);
		CacheKit.removeAll("funcManager");
		render("edit.html");
	}

	@Before(EvictInterceptor.class)
	@CacheName("userFunc")
	public void delete() {
		service.deleteByIds(getIds());
		CacheKit.removeAll("funcManager");
		renderJson(Feedback.success());
	}

	/**
	 * 功能树
	 * 
	 * @author qinhailin
	 * @date 2018年10月8日
	 */
	public void tree() {
		Collection<TreeNode> nodeList = CacheKit.get("funcManager", "tree", new IDataLoader() {		
			@Override
			public Object load() {
				return service.getFunctionTree("frame");
			}
		});
				
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setId("frame");
		node.setText(WebContant.projectName);
		node.setChildren(nodeList);
		nodes.add(node);

		renderJson(nodes);
	}

	/**
	 * 角色功能树
	 *
	 * @param req
	 * @param roleCode
	 * @param g
	 * @return
	 * @author QinHaiLin
	 * @date 2018年3月6日上午10:21:49
	 */
	public void roleFuncTree() {
		try {
			String roleCode = getPara("roleCode");
			if (StrKit.notBlank(getPara("type"))) {
				if ("parentRoleCode".equals(getPara("type")) && !"sys".equals(getPara("roleCode"))) {
					SysRole sysRole = roleService.findByRoleCode(getPara("roleCode"));
					roleCode = sysRole.getParentId();
				}
			}
			
			Collection<TreeNode> nodeList = CacheKit.get("funcManager", roleCode);
			if(nodeList==null){				
				nodeList=service.getRoleFunctionTree(roleCode, "frame");
				CacheKit.put("funcManager", roleCode, nodeList);
			}
	
			Collection<TreeNode> nodes = new ArrayList<TreeNode>();
			TreeNode node = new TreeNode();
			node.setId("frame");
			node.setText(WebContant.projectName);
			node.setChildren(nodeList);
			nodes.add(node);
			renderJson(nodes);
		} catch (Exception e) {
			handerException(e);
			renderJson(err());
		}
	}

	@Before(EvictInterceptor.class)
	@CacheName("userFunc")
	public void isStop() {
		SysFunction entity = (SysFunction) service.findById(getPara("id"));
		entity.setIsStop(getParaToInt("state")).update();
		CacheKit.removeAll("funcManager");
		renderJson(suc());
	}
	
	/**
	 * 表单修改字段值
	 */
	@Before(EvictInterceptor.class)
	@CacheName("userFunc")
	public void updateFieldValue(){
		getModel(SysFunction.class).update();
		CacheKit.removeAll("funcManager");
		renderJson(suc("修改成功"));
	}
}
