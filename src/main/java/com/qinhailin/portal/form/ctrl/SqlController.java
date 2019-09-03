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

import java.util.Date;
import java.util.List;

import com.jfinal.aop.Inject;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.portal.form.service.FormSqlService;
import com.qinhailin.portal.form.service.SysTreeService;


/**
 * 表单自定义查询sql
 * @author QinHaiLin
 * @date 2019年4月24日  
 */
@ControllerBind(path="/portal/form/sql")
public class SqlController extends BaseController {

	@Inject
	FormSqlService service;
	@Inject
	SysTreeService sysTreeService;
	
	public void index() {
		render("index.html");
	}
	
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
		record.set("create_time", new Date());		
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
		boolean b=service.update(record);
		if(!b) {
			renderJson(fail());
			return;
		}
		CacheKit.remove("cusSql",record.get("code"));
		renderJson(ok());
	}
	
	public void delete() {
		boolean b=service.deleteByIds(getIds());
		CacheKit.removeAll("cusSql");
		renderJson(b?suc():err());
	}
	
	/**
	 * sql预览
	 */
	public void review(){
		String sql=getPara("sql","");
		Record sqlView=service.findById(getPara("sqlId"));
		if(sqlView!=null){//通过id预览
			sql =sqlView.getStr("content");			
		}
		if(!sql.isEmpty()){
			String s="select * from ("+sql+") a";
			setAttr("json", JsonKit.toJson(Db.find(s)));		
		}
		render("review.html");
	}
	
	/**
	 * 通过SQL编号查询,返回json
	 */
	public void query(){
		Record sqlView=service.findPk("code", getPara(0));
		if(sqlView!=null){
			renderJson(Db.find(sqlView.getStr("content")));	
			return;
		}
		renderNull();
	}
	
	/**
	 * 下拉选择数据接口
	 */
	public void getOption(){
		List<Record> list=CacheKit.get("cusSql", getPara("code"), new IDataLoader() {
			
			@Override
			public Object load() {
				Record sqlView=service.findPk("code", getPara("code"));
				if(sqlView!=null){
					return Db.find(sqlView.getStr("content"));					
				}
				return null;
			}
		});
		renderJson(list);
	}
}
