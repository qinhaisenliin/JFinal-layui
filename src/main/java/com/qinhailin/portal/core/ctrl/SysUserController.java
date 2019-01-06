/**
 * @author qinhailin
 * @date 2018年8月17日
 */
package com.qinhailin.portal.core.ctrl;

import java.util.List;

import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.kit.RSAKit;
import com.qinhailin.common.model.SysOrg;
import com.qinhailin.common.model.SysUser;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.visit.Visitor;
import com.qinhailin.common.vo.Feedback;
import com.qinhailin.portal.core.service.SysOrgService;
import com.qinhailin.portal.core.service.SysUserRoleService;
import com.qinhailin.portal.core.service.SysUserService;

/**
 * 用户
 * 
 * @author QinHaiLIn
 *
 */
@ControllerBind(path="/portal/core/sysUser")
public class SysUserController extends BaseController {
	@Inject
	SysUserService service;
	@Inject
	SysOrgService sysOrgService;
	@Inject
	SysUserRoleService sysUserRoleService;
	public void index() {
		setAttr("orgList", service.queryOrgIdAndNameRecord());
		render("index.html");
	}


	public void list() {
		Record record = new Record();
		record.set("userName", getPara("userName"));
		record.set("orgId", getPara("orgId"));
		record.set("sex", getPara("sex"));
		renderJson(service.page(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), record));
	}

	public void add() {
		setAttr("orgList", service.queryOrgIdAndNameRecord());
		render("add.html");
	}

	public void save() {
		SysUser sysUser=getBean(SysUser.class);
		sysUser.setId(sysUser.getUserCode());
		if(sysUser.getPasswd()==null) {
			setAttr("sysUser", getBean(SysUser.class));
			sysUser.setPasswd(sysUser.getUserCode()+"123");
		}else {
			setAttr("sysUser", sysUser);
		}
		if(!service.saveEntity(sysUser)) {
			setException("用户编号已存在，请重新输入");
		}
		setAttr("orgList", service.queryOrgIdAndNameRecord());
		render("add.html");
	}

	public void edit() {
		SysUser entity =(SysUser) service.findById(getPara());
		entity.setUserName(entity.getUserName().trim());
		entity.setPasswd("******");
		setAttr("sysUser", entity);
		setAttr("orgList", service.queryOrgIdAndNameRecord());
		render("edit.html");
	}

	public void update(SysUser sysUser) {
		sysUser.update();
		sysUser.setPasswd("******");
		setAttr("sysUser", sysUser);
		setAttr("orgList", service.queryOrgIdAndNameRecord());
		render("edit.html");
	}

	public void delete() {
		service.deleteByIds(getIds());
		renderJson(Feedback.success());
	}

	public void isAllowLogin() {
		SysUser entity = (SysUser) service.findById(getPara("id"));
		entity.setAllowLogin(getParaToInt("state"));
		service.update(entity);
		renderJson(suc());
	}
	
	/**
	 * 重置密码
	 * 
	 * @author QinHaiLin
	 * @date 2018年12月5日
	 */
	public void resetPassword() {
		service.resetPassword(getIds());
		renderJson(suc());
	}
	
	public void my(){
		SysUser entity=service.findByUserCode(getVisitor().getCode());
		setAttr("sysUser", entity);
		SysOrg org=(SysOrg) sysOrgService.findById(entity.getOrgId());
		setAttr("orgName",org.getOrgName());
		render("my/index.html");
	}
	
	/**
	 * 修改个人信息
	 */
	public void updateMy(){
		SysUser sysUser=getBean(SysUser.class);
		sysUser.update();
		setAttr("sysUser", sysUser);
		SysOrg org=(SysOrg) sysOrgService.findById(sysUser.getOrgId());
		setAttr("orgName",org.getOrgName());
		setAttr("msg","数据修改成功");
		render("my/index.html");
	}
	
	public void myPassword(){		
		setAttr("userCode",getVisitor().getCode());
		render("my/password.html");
	}
	
	/**
	 * 修改个人密码
	 */
	public void updateMypassword(){
		Visitor vs=getVisitor();
		if(!vs.getCode().equals(getPara("userCode"))){		
			getResponse().setStatus(403);
			renderError(403);
		}
		String oldPassword=getPara("oldPassword");
		String newPassword=getPara("newPassword");
		try {
			 oldPassword=RSAKit.decryptionToString(oldPassword);
			 newPassword=RSAKit.decryptionToString(newPassword);
		} catch (Exception e) {
			handerException(e);
		}
		boolean b=service.updatePassword(vs.getCode(), newPassword, oldPassword);
		if(b){
			setAttr("msg", "密码修改成功");
		}else{
			setException("原密码错误,密码修改失败");
		}

		setAttr("userCode", vs.getCode());
		render("my/password.html");
	}
	
	/**
	 * 用户角色
	 */
	public void userRole(){
		setAttr("userCode", getPara("userCode"));
		setAttr("userName",getPara("userName"));
		render("userRole.html");
	}
	
	public void saveUserRole(){
		//用户原有的角色(已经排除和登录者的一样的角色)
		List<Record> userRoleList=sysUserRoleService.queryUserRoleList(getPara("userCode"),getVisitor().getCode());
		StringBuffer sbf=new StringBuffer();
		for(Record rd:userRoleList){
			sbf.append(rd.getStr("role_code")).append(",");
		}
		//可配置的角色
		sbf.append(getPara("role"));
		
		String[] roles=sbf.toString().split(",");
		String userCode=getPara("userCode");				
		boolean b=sysUserRoleService.saveUserRole(userCode, roles);
		if(!b){
			renderJson(err("保存失败"));
			return;
		}
		renderJson(suc("保存成功"));
	}
	
	/**
	 * 查询用户角色
	 */
	public void queryUserRole(){
		renderJson(sysUserRoleService.queryUserRoleList(getPara("userCode")));
	}
}
