/**
 * @author qinhailin
 * @date 2018年8月17日
 */
package com.qinhailin.portal.core.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.SysRole;
import com.qinhailin.common.model.SysUserRole;
import com.qinhailin.common.visit.Visitor;
import com.qinhailin.common.vo.TreeNode;

/**
 * 角色
 * 
 * @author qinhailin
 *
 */
public class SysRoleService extends BaseService {

	private SysRole dao = new SysRole().dao();
	private final String table="sys_role";
	
	@Override
	public SysRole getDao(){
		return dao;
	}
	
	@Override
	public String getTable(){
		return table;
	}

	public boolean saveEntity(SysRole entity,String userCode) {
		if(isExist(entity.getRoleCode())) {
			return false;
		}
		//添加改角色的人拥有该角色
		SysUserRole userRole=new SysUserRole();
		userRole.setId(entity.getRoleCode()+"-"+userCode);
		userRole.setRoleCode(entity.getRoleCode());
		userRole.setUserCode(userCode);
		userRole.save();
		return entity.save();
	}

	public void deleteRoleByRoleCode(String roleCode) {
		if(roleCode.equals("superadmin")||roleCode.equals("admin")) {
			return ;
		}
		String sql = "delete from sys_role where role_code=? or parent_id=?";
		Db.update(sql, roleCode, roleCode);
		//删除角色权限
		sql="delete from sys_role_function where role_code=?";
		Db.update(sql, roleCode);
	}

	public boolean isExist(String roleCode) {
		String sql = "select * from sys_role where role_code=?";
		List<Record> list = Db.find(sql, roleCode);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询节点下的所有角色
	 * @param treeNodeId
	 * @return
	 */
	public Collection<TreeNode> getRoleTree(String treeNodeId) {
		List<Record> list = null;
		String sql = "";
		if (StrKit.notBlank(treeNodeId)) {
			sql = "select * from sys_role where parent_id=?";
			list = Db.find(sql, treeNodeId);
		} else {
			sql = "select * from sys_role where parent_id is null";
			list = Db.find(sql);
		}
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();

		for (Record role : list) {
			TreeNode node = new TreeNode();
			node.setId(role.getStr("role_code"));
			node.setPid(role.getStr("parent_id"));
			node.setText(role.getStr("role_name"));
			Collection<TreeNode> children = this.getRoleTree(role.getStr("role_code"));
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}
	
	public SysRole findByRoleName(String roleName) {
		List<SysRole> list = dao.find("select * from sys_role where role_name=?", roleName);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public SysRole findByRoleCode(String roleCode) {
		List<SysRole> list = dao.find("select * from sys_role where role_code=?", roleCode);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 查询用户角色树
	 * @param vs
	 * @param treeNodeId
	 * @return
	 */
	public Collection<TreeNode> getUserRoleTree(Visitor vs, String treeNodeId) {
		if ("superadmin".equals(vs.getCode())) {
			return this.getRoleTree("sys");
		}
		List<Record> list = null;
		String sql = "";
		if (StrKit.notBlank(treeNodeId)) {
			sql = Db.getSql("core.getUserRoleTree");
			list = Db.find(sql, vs.getCode(), treeNodeId);
		} else {
			sql = Db.getSql("core.getLoginUserRoleTree");
			list = Db.find(sql, vs.getCode(), vs.getCode());
		}
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		for (int i = 0; i < list.size(); i++) {
			Record record = list.get(i);
			TreeNode node = new TreeNode();

			node.setId(record.getStr("role_code"));
			node.setPid(record.getStr("parent_id"));
			node.setText(record.getStr("role_name"));

			Collection<TreeNode> children = this.getUserRoleTree(vs, record.getStr("role_code"));
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}

	public List<SysRole> querySysRoleAndChildren(String roleCode) {
		return dao.find("select * from sys_role where parend_id=? or role_code=?", roleCode, roleCode);
	}

	/**
	 * 配置角色权限
	 */
	public boolean saveRoleFunc(String roleCode, String[] funcs) {
		try {
			// return false 或者抛异常，事务回滚
			return Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {
					String sql = "delete from sys_role_function where role_code=?";
					Db.update(sql, roleCode);
					Object[][] paras=new Object[funcs.length][3];
					for (int i = 0; i < funcs.length; i++) {						
						paras[i][0]=roleCode + "-" + funcs[i];
						paras[i][1]=roleCode;
						paras[i][2]=funcs[i];
					}
					sql = "insert into sys_role_function (id,role_code,function_id) values (?,?,?)";
					Db.batch(sql, paras, 50);
					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
