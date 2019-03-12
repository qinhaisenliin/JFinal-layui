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

package com.qinhailin.common.config;

import com.jfinal.kit.PropKit;



/**
 * 系统配置常量
 * @author QinHaiLin
 * @date 2018年11月19日  
 */
public class WebContant {
	
	/** ============================系统常量=========================== **/
	/** 视图基础目录 **/
	public static String baseViewPath="/WEB-INF/views";
	/** 项目名称 **/
	public static String projectName=PropKit.get("projectName","JFinal极速开发世界");
	/** 上传目录 **/
	public static String baseUloadPath=PropKit.get("baseUploadPath","WEB-INF/temp/upload");
	/** 下载目录 **/
	public static String baseDownloadPath=PropKit.get("baseDownloadPath","WEB-INF/temp/download");

	/** 上传最大限制 **/
	public static Integer maxPostSize=1024*1024*100;
	/** 允许登录 **/
	public static Integer allowLogin=0;	
	/** 错误页面 **/
	public static String error403View="/WEB-INF/views/common/error/403.html";
	public static String error404View="/WEB-INF/views/common/error/404.html";
	public static String error500View="/WEB-INF/views/common/error/500.html";
	/** 前端函数模板 **/
	public static String functionTemp="/WEB-INF/views/common/templete/_layout.html";
	/** sql模板 **/
	public static String sqlTemplate="/sql/all_sqls.sql";
	
	
}
