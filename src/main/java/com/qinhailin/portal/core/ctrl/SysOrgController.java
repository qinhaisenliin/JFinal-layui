/**
 * @author qinhailin
 * @date 2018年8月17日
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
		if (entity != null) {
			setAttr("parentid", entity.getId());
			setAttr("parentIdName", entity.getOrgName());
		} else {
			setAttr("parentid", "sys");
			setAttr("parentIdName", "组织机构");
		}
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
