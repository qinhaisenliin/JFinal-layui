/**
 * Copyright 2019-2020 覃海林(qinhaisenlin@163.com).
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
package com.qinhailin.portal.generator.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.aop.Inject;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.Grid;
import com.qinhailin.portal.generator.service.CodeService;

/**
 * 代码生成器
 * @author QinHaiLin
 * @date 2020-02-21
 */
@ControllerBind(path="/portal/generator/code")
public class CodeController extends BaseController {

	@Inject
	CodeService codeService;
	
	/**
	 * 代码器首页
	 */
	public void index(){
		render("index.html");
	}

	public void tables(){
		render("tables.html");
	}
	
	/**
	 * 数据库表
	 */
	public void tablesList(){
		renderJson(codeService.queryTablesList(getAllParamsToRecord()));
	}
	
	/**
	 * 生成代码
	 * @author QinHaiLin
	 * @date 2020-02-21
	 */
	public void createCode(){
		Record record=getAllParamsToRecord();
		String[] tableNameArray=getPara("name").split(",");
		String modelPackage=getPara("packageName")+".model.";
		String[] modelNameArray=getPara("modelName").split(",");
		List<Record> codeList=new ArrayList<>();
		List<String> modelList=new ArrayList<>();
		for(int i=0;i<tableNameArray.length;i++){
			// 查询表信息
			record.set("name", tableNameArray[i]);
			Grid grid=codeService.queryTablesList(record);
			@SuppressWarnings("unchecked")
			List<TableMeta> tableList=(List<TableMeta>) grid.getList();
			List<ColumnMeta> columnMetas=tableList.get(0).columnMetas;
			
			// 用模板引擎生成 HTML 片段 replyItem
			Ret ret=Ret.by("columnMetas", columnMetas);
			ret.set("modelName",StrKit.firstCharToLowerCase(modelNameArray[i]));
			ret.set("primaryKey",tableList.get(0).primaryKey);
			String formItem = renderToString("temp/_form.html", ret);
			String tableItem = renderToString("temp/_table.html", ret);
			
			// 创建模板内容
			record.set("modelName", modelPackage+modelNameArray[i]);
			record.set("primaryKey", tableList.get(0).primaryKey);
			record.set("tableComment", tableList.get(0).remarks);
			record.set("tableNames", tableNameArray);
			Record codeRecord=codeService.createCodeTemplete(record,formItem,tableItem);		
			codeList.add(codeRecord);
			modelList.add(modelPackage+modelNameArray[i]);
		}
		
		// 存储数据，用于创建本地文件
		setSessionAttr("downloadCode", codeList);
		setSessionAttr("modelList", modelList);
		setSessionAttr("packageName", getPara("packageName"));
		renderJson(Ret.ok("data", codeList));	
	}
	
	/**
	 * 下载代码
	 */
	public void download(){
		List<Record> codeList=getSessionAttr("downloadCode");
		String packageName=getSessionAttr("packageName");
		List<String> modelList=getSessionAttr("modelList");
		
		try {
			for(int i=0;i<codeList.size();i++){		
				codeService.downloadFile(getPara("type"),modelList.get(i), packageName,codeList.get(i));		
			}
			renderJson(ok());
		} catch (IOException e) {
			handerException(e);
			renderJson(fail());
		}
	}
	
}
