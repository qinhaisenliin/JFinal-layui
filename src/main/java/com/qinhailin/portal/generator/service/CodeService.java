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
package com.qinhailin.portal.generator.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JavaKeyword;
import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.qinhailin.common.config.MainConfig;
import com.qinhailin.common.vo.Grid;
import com.qinhailin.portal.generator.kit.GeneratorKit;

/**
 * 代码生成器接口
 * @author QinHaiLin
 * @date 2020-02-21
 */
public class CodeService{
	
	
	/**
	 * 针对 Model 中七种可以自动转换类型的 getter 方法，调用其具有确定类型返回值的 getter 方法
	 * 享用自动类型转换的便利性，例如 getInt(String)、getStr(String)
	 * 其它方法使用泛型返回值方法： get(String)
	 * 注意：jfinal 3.2 及以上版本 Model 中的六种 getter 方法才具有类型转换功能
	 */
	@SuppressWarnings("serial")
	protected static Map<String, String> getterTypeMap = new HashMap<String, String>() {{
		put("java.lang.String", "getStr");
		put("java.lang.Integer", "getInt");
		put("java.lang.Long", "getLong");
		put("java.lang.Double", "getDouble");
		put("java.lang.Float", "getFloat");
		put("java.lang.Short", "getShort");
		put("java.lang.Byte", "getByte");
	}};
	
	static Engine engine;
	static DruidPlugin druidPlugin;
	static {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addSharedMethod(new StrKit());
		engine.addSharedObject("getterTypeMap", getterTypeMap);
		engine.addSharedObject("javaKeyword", JavaKeyword.me);
		druidPlugin=MainConfig.createDruidPlugin();
		druidPlugin.start();
	}
	
	/**
	 * 查询数据库表
	 * @param record
	 * @return
	 */
	public Grid queryTablesList(Record record){	
		MetaBuilder metaBuilder = new MetaBuilder(druidPlugin.getDataSource());
		// 添加不需要获取的数据表
		String[] excTable={"sys_user","sys_org","sys_role","sys_function","sys_role_function","sys_user_role","sys_log","sys_tree","data_dictionary","data_dictionary_value","file_uploaded"};
		metaBuilder.addExcludedTable(excTable);
		// 实体名称去掉前缀
		metaBuilder.setRemovedTableNamePrefixes("w_","t_");
		metaBuilder.setGenerateRemarks(true);
        // TableMeta 数据库的表
        List<TableMeta> tableMetas = metaBuilder.build();
        List<TableMeta> resultList=new ArrayList<>();
        
        String name=record.get("name");
        int pageNumber=Integer.valueOf(record.get("pageNumber","1"));
        int pageSize=Integer.valueOf(record.get("pageSize","10"));
        int startIndex=(pageNumber - 1) * pageSize;
        
        for (int i=0;i<tableMetas.size();i++) {
        	//搜索
           if(name!=null){
        	   if(tableMetas.get(i).name.indexOf(name)!=-1)
        		   resultList.add(tableMetas.get(i));	   
           }else{
        	   if(i>=startIndex)
        		   resultList.add(tableMetas.get(i));      		   
           }
           //分页
           if(resultList.size()==pageSize)
        	   break;
        }
        
        return new Grid(buildTableRemarks(resultList));
	}
	
	/**
	 * 查询表备注信息
	 * @param tableMetaList
	 * @return
	 */
	private List<TableMeta> buildTableRemarks(List<TableMeta> tableMetaList){
		String sql="SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE TABLE_NAME=?";
		for(TableMeta t:tableMetaList){
			Record rd=Db.find(sql,t.name).get(0);
			t.remarks=rd.getStr("TABLE_COMMENT");
		}
		return tableMetaList;
	}
	
	/**
	 * 生成模板代码
	 * @param record
	 * @param formItem
	 * @param tableItem
	 * @return
	 */
	public Record createCodeTemplete(Record record,String formItem,String tableItem){
		Record result=new Record();
		List<String> codeJava=new ArrayList<>();
		List<String> codeHtml=new ArrayList<>();
		String modelName=record.getStr("modelName");
		String authorName=record.get("authorName");
		String basePackage=record.getStr("packageName");
		String tableComment=record.getStr("tableComment");
		String primaryKey=record.getStr("primaryKey");
		
		//模板变量
		GeneratorKit codeKit=GeneratorKit.getInstance();
		@SuppressWarnings("static-access")
		Kv kv=codeKit.setBasePackage(basePackage).setModular(basePackage.substring(basePackage.lastIndexOf(".")+1)).getJavaKv(modelName);
		kv.set("author", authorName).set("tableComment", tableComment).set("primaryKey", primaryKey);
		
		//项目模板
		String[] java={"Controller","Service"};
		String[] html={"index","add","edit","_form"};
		
		//java模板内容
		for(String str:java){
			String content=Db.getSql("code."+str);
			@SuppressWarnings("unchecked")
			Iterator<Object> iter=kv.keySet().iterator();			
			while(iter.hasNext()){
				Object obj=iter.next();
				content=content.replace("${"+obj+"}", kv.get(obj).toString());
			}
			result.set(str+".java", content);			
			codeJava.add(str+".java");
		}
		
		//html模板内容
		for(String str:html){
			String content=Db.getSql("code."+str);	
			@SuppressWarnings("unchecked")
			Iterator<Object> iter=kv.keySet().iterator();
			while(iter.hasNext()){
				Object obj=iter.next();
				content=content.replace("${"+obj+"}", kv.get(obj).toString());
			}
			
			content=content.replace("${formCols}", formItem);
			content=content.replace("${tableCols}", tableItem);

			result.set(str+".html", content);			
			codeHtml.add(str+".html");
		}
		
		result.set("Model.java", createModelCode(record.getStr("name"),modelName));		
		codeJava.add("Model.java");
		
		result.set("BaseModel.java", createBaseModelCode(record.getStr("name"),modelName));		
		codeJava.add("BaseModel.java");
		
		result.set("_MappingKit.java", createMappingKitCode((String[])record.get("tableNames"),modelName));
		codeJava.add("_MappingKit.java");
		
		result.set("codeJava", codeJava);
		result.set("codeHtml", codeHtml);
		
		return result;
	}
	
	/**
	 * 创建Model代码
	 * @param tableName
	 * @param className
	 * @return
	 */
	public String createModelCode(String tableName,String className){
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = className.substring(0, className.lastIndexOf("."));
		// base model 所使用的包名
		String baseModelPackageName = modelPackageName+".base";

		Record record=new Record();
		record.set("pageNumber", "1").set("pageSize", "1").set("name",tableName);
		@SuppressWarnings("unchecked")
		List<TableMeta> tableMetas=(List<TableMeta>) queryTablesList(record).getList();
		
		String template = "/com/jfinal/plugin/activerecord/generator/model_template.jf";
		Kv data = Kv.by("modelPackageName", modelPackageName);
		data.set("baseModelPackageName", baseModelPackageName);
		data.set("generateDaoInModel", false);
		data.set("tableMeta", tableMetas.get(0));
		String content=engine.getTemplate(template).renderToString(data);
		return content;
	}
	
	/**
	 * 创建BaseModel代码
	 * @param tableName
	 * @param className
	 * @return
	 */
	public String createBaseModelCode(String tableName,String className){
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = className.substring(0, className.lastIndexOf("."));
		// base model 所使用的包名
		String baseModelPackageName = modelPackageName+".base";

		Record record=new Record();
		record.set("pageNumber", "1").set("pageSize", "1").set("name",tableName);
		@SuppressWarnings("unchecked")
		List<TableMeta> tableMetas=(List<TableMeta>) queryTablesList(record).getList();
		
		String template = "/com/jfinal/plugin/activerecord/generator/base_model_template.jf";
		Kv data = Kv.by("baseModelPackageName", baseModelPackageName);
		data.set("generateChainSetter", true);
		data.set("tableMeta", tableMetas.get(0));
		String content=engine.getTemplate(template).renderToString(data);
		return content;
	}
	
	/**
	 * 创建数据表映射文件_MappingKit.java代码
	 * @param tableNames
	 * @param className
	 * @author QinHaiLin
	 * @date 2020-02-21
	 */
	@SuppressWarnings("unchecked")
	public String createMappingKitCode(String[] tableNames,String className){
		// MappingKit 所使用的包名
		String mappingKitPackageName = className.substring(0, className.lastIndexOf("."));
		Record record=new Record();
		record.set("pageNumber", "1").set("pageSize", "1");
		List<TableMeta> tableMetas=new ArrayList<>();
		
		for(String tableName:tableNames){
			record.set("name",tableName);
			List<TableMeta> tableMeta=(List<TableMeta>) queryTablesList(record).getList();
			tableMetas.addAll(tableMeta);
		}		
		
		String template = "/com/jfinal/plugin/activerecord/generator/mapping_kit_template.jf";
		Kv data = Kv.by("mappingKitPackageName", mappingKitPackageName);
		data.set("mappingKitClassName", "_MappingKit");
		data.set("tableMetas", tableMetas);
		
		return engine.getTemplate(template).renderToString(data);
	}
		
	/**
	 * 下载文件
	 * @param type
	 * @param className
	 * @param packageName
	 * @param content
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public void downloadFile(String type,String className,String packageName,Record record) throws IOException{
		GeneratorKit genratorKit=GeneratorKit.getInstance();
		//设置package
		genratorKit.setBasePackage(packageName.substring(0, packageName.lastIndexOf(".")));
		//设置模块
		genratorKit.setModular(packageName.substring(packageName.lastIndexOf(".")+1,packageName.length()));
		List<String> listCode=record.get(type);

		//后端代码
		if("codeJava".equals(type)){
			for(String str:listCode){
				genratorKit.createJavaFile(className, str,record.getStr(str));				
			}
		}
		//前端代码
		else if("codeHtml".equals(type)){
			for(String str:listCode){
				genratorKit.createHtmlFile(className, str,record.getStr(str));
			}
		}
		//下载所有代码
		else{
			listCode=record.get("codeJava");
			for(String str:listCode){
				genratorKit.createJavaFile(className, str,record.getStr(str));
			}
			listCode=record.get("codeHtml");
			for(String str:listCode){
				genratorKit.createHtmlFile(className, str,record.getStr(str));				
			}
		}

	}
	
}
