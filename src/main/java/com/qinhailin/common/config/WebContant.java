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
public interface WebContant {
	
	/** ============================系统常量=========================== **/
	/** 视图基础目录 **/
	String baseViewPath="/WEB-INF/views";
	/** 项目名称 **/
	String projectName=PropKit.get("projectName","JFinal极速开发世界");
	/** 上传目录 **/
	String baseUloadPath=PropKit.get("baseUploadPath","WEB-INF/temp/upload");
	/** 下载目录 **/
	String baseDownloadPath=PropKit.get("baseDownloadPath","WEB-INF/temp/download");

	/** 上传最大限制 **/
	Integer maxPostSize=1024*1024*100;
	/** 允许登录 **/
	Integer allowLogin=0;	
	/** 错误页面 **/
	String error403View=baseViewPath+"/common/error/403.html";
	String error404View=baseViewPath+"/common/error/404.html";
	String error500View=baseViewPath+"/common/error/500.html";
	/** 前端函数模板 **/
	String functionTemp=baseViewPath+"/common/templete/_layout.html";
	/** sql模板 **/
	String sqlTemplate="/sql/all_sqls.sql";
	
	
}
