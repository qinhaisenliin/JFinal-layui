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
package com.qinhailin.portal.generator.kit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jfinal.kit.Kv;

/**
 * 代码生成器
 * @author QinHaiLin
 * @date 2020-02-21
 */
public class GeneratorKit {
	
	private static final String point =".";
	private static final String separator ="/";		
	private static String author = "QinHaiLin";

	private static final String javaResource="src/main/java";
	private static final String htmlResource="src/main/webapp/WEB-INF/views/portal";
	
	//packager名称
	private static String basePackage = "com.qinhailin.portal";
	private static String modular="business";
	private static String ctrl="ctrl";
	private static String service="service";
	
	//文件package目录
	/** package样例：com.qinhailin.portal.business.ctrl */
	private static String ctrlPackage = "";
	/** package样例：com.qinhailin.portal.business.service */
	private static String servicePackage = "";
	
	//文件地址
	private static String ctrlPath = "";
	private static String servicePath = "";
	private static String modelPath = "";
	private static String baseModelPath = "";

	/** 模板参数集 */
	private static Kv kv=Kv.by("author", author);
	
	public GeneratorKit(){
		
	}
	
	private static GeneratorKit single = new GeneratorKit();

    public static GeneratorKit getInstance() {
        return single;
    }
	
	/**
	 * 文件基础package，默认值是com.qinhailin.portal
	 * @param basePackage
	 * @return
	 */
	public GeneratorKit setBasePackage(String basePackage){
		GeneratorKit.basePackage=basePackage;
		return this;	
	}
	
	/**
	 * 模块package，默认是business
	 * @param modular
	 * @return
	 */
	public GeneratorKit setModular(String modular){
		GeneratorKit.modular=modular;
		return this;
	}
	
	/**
	 * 业务模块
	 * @return
	 */
	public String getModular(){
		return modular;
	}
		
	/**
	 * 获取Java模板参数集
	 * @param className 如：com.qinhailin.common.model.SysUser
	 */
	public static Kv getJavaKv(String className) {
		String cName = getLastChar(className);
		ctrlPackage=basePackage+point+ctrl;
		servicePackage=basePackage+point+service;
		kv.set("controllerPackage", ctrlPackage);
		kv.set("servicePackage", servicePackage);
		kv.set("modelName", cName);
		kv.set("lowercaseModelName", getLowercaseChar(cName));
		kv.set("importModel", className);
		kv.set("actionKey", separator+modular+separator+getLowercaseChar(cName));
		kv.set("date",getDate());
		return kv;
	} 
    	
	/**
	 * 创建java文件
	 * @param modelClassName com.qinhailin.common.model.SysUser
	 * @param fileType Service,Controller
	 * @return
	 * @throws IOException 
	 */
	public static File createJavaFile(String modelClassName,String fileType,String content) throws IOException{
		String modelName = getLastChar(modelClassName);
		String modelPackage=modelClassName.substring(0, modelClassName.lastIndexOf(point));
		//controller类
		ctrlPath=basePackage.replace(point, separator)+separator+modular+separator+ctrl;
		//service类
		servicePath=basePackage.replace(point, separator)+separator+modular+separator+service;
		//model类
		modelPath=modelPackage.replace(point, separator);
		//baseModel类
		baseModelPath=modelPackage.replace(point, separator)+separator+"base";
		
		String filePath=System.getProperty("user.dir") + separator+javaResource+separator;
		String fileName = modelName+fileType;
		if("Service.java".equalsIgnoreCase(fileType)){
			filePath+=servicePath+separator;
		}else if("Controller.java".equalsIgnoreCase(fileType)){
			filePath+=ctrlPath+separator;
		}else if("Model.java".equals(fileType)){
			filePath+=modelPath+separator;
			fileName=modelName+".java";
		}else if("BaseModel.java".equals(fileType)){
			filePath+=baseModelPath+separator;
			fileName="Base"+modelName+".java";
		}else if("_MappingKit.java".equals(fileType)){
			filePath+=modelPath+separator;
			fileName=fileType;
		}else{
			filePath+=fileType+separator;			
		}
		
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		
		file=new File(filePath+fileName);
		writeContentToFile(file,content);
		return file;
	}
	
	/**
	 * 创建html文件
	 * @param className 如：com.qinhailin.common.model.SysUser
	 * @param fName
	 * @return
	 * @throws IOException 
	 */
	public static File createHtmlFile(String className,String fName,String content) throws IOException{
		String cName = getLastChar(className);
		String filePath=System.getProperty("user.dir") + separator+htmlResource
				+separator+ modular+separator+getLowercaseChar(cName)+separator;

		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		file=new File(filePath+fName);
		writeContentToFile(file,content);
		return file;
	}
	

	/**
	 * 写内容入文件
	 * @param file
	 * @param content
	 * @throws IOException
	 */
	public static void writeContentToFile(File file,String content) throws IOException{
		System.out.print("正在创建文件："+ file.getPath());
		OutputStreamWriter out=null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			out.write(content);
			System.out.println("   ：创建成功");
		} finally {
			if(out!=null){
				out.close();				
			}
		}
	}
	
	/**
	 * 获取路径的最后面字符串<br>
	 * 如：<br>
	 * str = "com.qinhailin.common.model.SysUser"<br>
	 * return "SysUser";
	 * @param str
	 * @return
	 */
	private static String getLastChar(String str) {
		if ((str != null) && (str.length() > 0)) {
			int dot = str.lastIndexOf('.');
			if ((dot > -1) && (dot < (str.length() - 1))) {
				return str.substring(dot + 1);
			}
		}
		return str;
	}
	
	/**
	 * 把第一个字母变为小写<br>
	 * 如：<br>
	 * str = "SysUser";<br>
	 * return "sysUser";
	 * @param str
	 * @return
	 */
	private static String getLowercaseChar(String str){
		return str.substring(0,1).toLowerCase()+str.substring(1);
	}
	
	/**
	 * 获取系统时间
	 * @return
	 */
	private static String getDate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}
	
}
