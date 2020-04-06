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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.intercepor.TokenInterceptor;
import com.qinhailin.common.kit.RSAKit;
import com.qinhailin.common.model.SysOrg;
import com.qinhailin.common.model.SysUser;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.safe.TokenValidator;
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
		record.set("userCode", getPara("userCode"));
		record.set("userName", getPara("userName"));
		record.set("orgId", getPara("orgId"));
		record.set("sex", getPara("sex"));
		record.set("type", getPara("type","user"));
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

	public void edit() throws UnsupportedEncodingException {
		SysUser entity =(SysUser) service.findById(URLDecoder.decode(getPara(),"utf-8"));
		entity.setUserName(entity.getUserName().trim());
		entity.setPasswd("******");
		setAttr("sysUser", entity);
		setAttr("orgList", service.queryOrgIdAndNameRecord());
		render("edit.html");
	}

	public void update() {
		SysUser sysUser=getBean(SysUser.class);
		if(sysUser.getOrgId()==null){
			sysUser.setOrgId("sys");
		}
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
		createToken();
		SysUser entity=service.findByUserCode(getVisitor().getCode());
		setAttr("sysUser", entity);
		SysOrg org=(SysOrg) sysOrgService.findById(entity.getOrgId());
		setAttr("orgName",org.getOrgName());
		render("my/index.html");
	}
	
	/**
	 * 修改个人信息
	 */
	@Before(TokenValidator.class)
	public void updateMy(){
		SysUser sysUser=getBean(SysUser.class);
		Visitor vs=getVisitor();
		if(!vs.getCode().equals(sysUser.getUserCode())){		
			getResponse().setStatus(403);
			renderError(403);
		}
		sysUser.update();
		setAttr("sysUser", sysUser);
		SysOrg org=(SysOrg) sysOrgService.findById(sysUser.getOrgId());
		setAttr("orgName",org.getOrgName());
		setAttr("msg","数据修改成功");
		render("my/index.html");
	}
	
	public void myPassword(){	
		createToken();
		setAttr("userCode",getVisitor().getCode());
		render("my/password.html");
	}
	
	/**
	 * 修改个人密码
	 */
	@Before(TokenValidator.class)
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
			createToken();
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
		//用户原有的角色(登录者却没有)
		StringBuffer sbf=new StringBuffer();
		if(!getVisitor().getCode().equals("superadmin")){//超级管理员登录不查询
			List<Record> userRoleList=sysUserRoleService.queryUserRoleList(getPara("userCode"),getVisitor().getCode());
			for(Record rd:userRoleList){
				sbf.append(rd.getStr("role_code")).append(",");
			}			
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
