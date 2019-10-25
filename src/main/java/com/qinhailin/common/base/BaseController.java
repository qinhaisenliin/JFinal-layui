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

package com.qinhailin.common.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.qinhailin.common.base.service.FileService;
import com.qinhailin.common.kit.IdKit;
import com.qinhailin.common.model.FileUploaded;
import com.qinhailin.common.visit.Visitor;
import com.qinhailin.common.visit.VisitorUtil;
import com.qinhailin.common.vo.Feedback;

public class BaseController extends Controller {
	private static final Logger LOG = Logger.getLogger(BaseController.class);

	FileService fileService =Aop.get(FileService.class);

	/** 访问者信息 **/
	@NotAction
	public Visitor getVisitor(){
		return  VisitorUtil.getVisitor(getSession());
	}
	
	/**
	 * 捕获异常
	 * 
	 * @param e
	 * @author QinHaiLin
	 * @date 2018年10月15日
	 */
	@NotAction
	public void handerException(Exception e) {
		LOG.info(e.getMessage(), e);
		e.printStackTrace();
	}
	
	/**
	 * 记录日志信息
	 * @param message
	 * @author QinHaiLin
	 * @date 2018年12月11日
	 */
	@NotAction
	public void logInfo(String message) {
		LOG.info(message);
	}
	
	/**
	 * 输出异常信息到页面
	 * @param message
	 * @author QinHaiLin
	 * @date 2018年12月4日
	 */
	@NotAction
	public void setException(String message) {
		setAttr("e", new Exception(message));
	}
	
	/**
	 * 输出提示信息到页面
	 * @param message
	 */
	@NotAction
	public void setMsg(String message){
		setAttr("msg",message);
	}
	
	/**
	 * 创建32位字符串
	 * 
	 * @return
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	@NotAction
	public String createUUID() {
		return IdKit.createUUID();
	}
	
	/**
	 * {
	 *   "code": "success",
	 *   "success": true,
	 *   "error": false,
	 *   "msg": "成功"
	 *  }
	 * @return
	 */
	@NotAction
	public Feedback suc(){
		return Feedback.success("成功");
	}
	
	/**
	 * {
	 *   "code": "success",
	 *   "success": true,
	 *   "error": false,
	 *   "msg": ""
	 *  }
	 * @param msg 提示信息
	 * @return
	 */
	@NotAction
	public Feedback suc(String msg){
		return Feedback.success(msg);
	}
	/**
	 * {
	 *   "code": "error",
	 *   "success": false,
	 *   "error": true,
	 *   "msg": "失败"
	 *  }
	 * @return
	 */
	@NotAction
	public Feedback err(){
		return Feedback.error("失败");
	}
	
	/**
	 * {
	 *   "code": "error",
	 *   "success": false,
	 *   "error": true,
	 *   "msg": ""
	 *  }
	 * @param msg 提示信息
	 * @return
	 */
	@NotAction
	public Feedback err(String msg){
		return Feedback.error(msg);
	}
	
	/**
	 * 获取数组变量ids
	 * 
	 * @return
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	@NotAction
	public List<String> getIds() {
		return getArray("ids");
	}

	/**
	 * 获取数组变量
	 * 
	 * @param arrayName
	 * @return
	 * @author QinHaiLin
	 * @date 2018年9月17日
	 */
	@NotAction
	public List<String> getArray(String arrayName) {		
		List<String> ids = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			if (getPara(arrayName + "[" + i + "]")== null) {
				break;
			} 
			ids.add(getPara(arrayName + "[" + i + "]"));
		}
		return ids;
	}

	/**
	 * 保存文件记录
	 * 
	 * @return url格式：180801/1808010001004
	 * 
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	@NotAction
	public String saveFile(UploadFile uploadFile) {
		return fileService.saveFile(uploadFile);
	}
	@NotAction
	public String saveFile(UploadFile uploadFile, String objectId) {
		return fileService.saveFile(uploadFile, objectId);
	}
	@NotAction
	public List<String> saveFiles(List<UploadFile> list) {
		return fileService.saveFiles(list);
	}
	@NotAction
	public List<String> saveFiles(List<UploadFile> list, String objectId) {
		return fileService.saveFiles(list,objectId);
	}

	/**
	 * 通过url获取文件记录对象
	 * 
	 * @param url
	 *            格式：180801/1808010001004
	 * @return
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	@NotAction
	public FileUploaded getFileUploaded(String url) {
		return fileService.queryFileUploadedByUrl(url);
	}
	@NotAction
	public FileUploaded getFileUploadedByObjectId(String objectId) {
		return fileService.queryFileUploadedByObjectId(objectId);
	}
	@NotAction
	public List<FileUploaded> getFileUploadListByObjectId(String objectId) {
		return fileService.queryFileUploadedListByObjectId(objectId);
	}

	/**
	 * 删除文件
	 * 
	 * @param url
	 *            格式：180801/1808010001004
	 * @return
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	@NotAction
	public void deleteFileByUrl(String url) {
		fileService.deleteFile(url);
	}
	@NotAction
	public void deleteFileByUrls(List<String> urls) {
		fileService.deleteFiles(urls);
	}
	@NotAction
	public void deleteFileByModel(FileUploaded entity) {
		fileService.delete(entity);
	}
	@NotAction
	public void deleteFileByObjectId(String objectId) {
		fileService.deleteFileByObjectId(objectId);
	}

	/**
	 * 导入数据
	 * 
	 * @param uf
	 * @param sql
	 *            insert into game_theme (id, state, title) values(?,?,?)
	 * @return
	 * @author QinHaiLin
	 * @date 2018年9月14日
	 */
	@NotAction
	public Boolean importExcel(UploadFile uf, String sql) {
		boolean b = fileService.importExcel(uf, sql);
		fileService.deleteFile(uf);
		return b;
	}

	/**
	 * 导出数据.xlsx
	 * 
	 * @param title
	 *            String[] title={"姓名","性别","年龄",...}
	 * @param fileName
	 * @param sql
	 *            select name as '0',sex as '1',age as '2' from user
	 * @author QinHaiLin
	 * @date 2018年8月14日
	 */
	@NotAction
	public void exportExcel(String[] title, String fileName, String sql) {
		fileService.exportExcelxlsx(getResponse(), title, fileName, sql);
	}
	@NotAction
	public void exportExcel(String[] title, String sql) {
		fileService.exportExcelxlsx(getResponse(), title, null, sql);
	}
	
	/**
	 * 文件名放前面，避免误用
	 * @param fileName
	 * @param title
	 * @param sql
	 * @param paras
	 */
	@NotAction
	public void exportExcel(String fileName, String[] title, String sql, Object... paras) {
		fileService.exportExcelxlsx(getResponse(), title, fileName, sql, paras);
	}
	@NotAction
	public void exportExcel(String[] title, String sql, Object... paras) {
		fileService.exportExcelxlsx(getResponse(), title, null, sql, paras);
	}
	@NotAction
	public void exportExcel(String[] title, String fileName, List<Record> list) {
		fileService.exportExcelxlsx(getResponse(), title, fileName, list);
	}
	@NotAction
	public void exportExcel(String[] title, List<Record> list) {
		fileService.exportExcelxlsx(getResponse(), title, null, list);
	}
	
	/**
	 * 写内容到html
	 * @param fileName 格式：index.html
	 * @param text 任意内容
	 * @author QinHaiLin
	 * @date 2019年3月21日
	 */
	@NotAction
	public void writeToHtml(String fileName,String text) {
		File file = new File(PathKit.getWebRootPath() + getViewPath()+"/"+fileName);
		try {
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));		
			writer.write(text);				
			writer.flush();
			writer.close();
		} catch (IOException e) {
			handerException(e);
		}
	}
	
	/**
	 * 返回接口成功数据
	 * @param data
	 * @return
	 * @author QinHaiLin
	 * @date 2019年3月6日
	 */
	@NotAction
	public Ret ok(Object data) {
		return Ret.ok("msg", "成功").set("data", data);
	}
	
	@NotAction
	public Ret ok() {
		return Ret.ok("msg", "成功");
	}
	
	@NotAction
	public Ret fail() {
		return Ret.fail("msg", "失败");
	}

	/**
	 * 返回接口失败数据
	 * @param data
	 * @return
	 * @author QinHaiLin
	 * @date 2019年3月6日
	 */
	@NotAction
	public Ret fail(String msg) {
		return Ret.fail("msg", msg);
	}
	
	/**
	 * 获取请求参数,转化为JSONObject 
	 * @return
	 */
	@NotAction
    public JSONObject getAllParamsToJson(){
        JSONObject result=new JSONObject();
        Map<String,String[]> map=getParaMap();
        Set<String> keySet=map.keySet();
        for(String key:keySet){
            if(map.get(key) instanceof String[]){
                String[] value=map.get(key);
                if(value.length==0){
                    result.put(key,null);
 
                }else if(value.length==1){
                    result.put(key,value[0]);
                }else{
                    result.put(key,value);
                }
            }else{
                result.put(key,map.get(key));
            }
        }
        return result;
    }
    
	/** 
	 * 获取请求参数,转化为JFinal的Record对象 
	 * @return
	 */
	@NotAction
    public Record getAllParamsToRecord(){
    	@SuppressWarnings("unchecked")
		Record result=new Record().setColumns(getKv());      
        return result;
    }	    
	
	/**
	 * 够造Kv对象
	 * @param key
	 * @param value
	 * @return
	 */
	@NotAction
	public Kv byKv(Object key,Object value){
		return Kv.by(key, value);
	}
}
