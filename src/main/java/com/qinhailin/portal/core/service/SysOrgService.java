/**
 * @author qinhailin
 * @date 2018年8月17日
 */
package com.qinhailin.portal.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.SysOrg;
import com.qinhailin.common.vo.TreeNode;

/**
 * 部门
 * 
 * @author qinhailin
 *
 */
public class SysOrgService extends BaseService {

	private SysOrg dao = new SysOrg().dao();
	private final String table="sys_org";
	
	@Override
	public SysOrg getDao(){
		return dao;
	}
	
	@Override
	public String getTable(){
		return table;
	}
	
	/**
	 * 删除部门及子部门
	 * @param id
	 * @author QinHaiLin
	 * @date 2018年12月5日
	 */
	public void deleteOrgById(String id) {
		String hql = "delete from sys_org where id=? or parentid=?";
		Db.update(hql, id, id);
	}

	public Collection<TreeNode> getOrgTree(String treeNodeId) {
		List<SysOrg> list = null;

		if (StrKit.notBlank(treeNodeId)) {

			list = dao.find("select * from sys_org where parentid=? order by id asc", treeNodeId);
		} else {
			list = dao.find("select * from sys_org where parentid is null");
		}
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		for (SysOrg org : list) {
			TreeNode node = new TreeNode();
			node.setId(org.getId());
			node.setPid(org.getParentid());
			node.setText(org.getOrgName());
			Collection<TreeNode> children = this.getOrgTree(org.getId());
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}

}
