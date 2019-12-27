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

package com.qinhailin.portal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.render.JsonRender;
import com.jfinal.upload.UploadFile;
import com.qinhailin.common.model.FileUploaded;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.config.WebContant;

/**
 * 附件、公共方法类
 * 特别说明:该类的的viewPath="/"
 * @author QinHaiLin
 *
 */
@ControllerBind(path="/portal")
public class PortalController extends BaseController {

	/**
	 * 
	 * go后面带视图目录参数 如：/portal/go/common/upload/upload，<br/>
	 * 将会访问/common/upload/upload.html文件
	 * 
	 * @author QinHaiLin
	 * @date 2018年8月2日
	 */
	public void go() {
		render(getAttr("view") + ".html");
	}

	/**
	 * 独立上传页面
	 * 
	 * @author QinHaiLin
	 * @date 2018年8月13日
	 */
	public void toUpload() {
		String objectId = getPara(0);
		if (objectId == null) {
			objectId = getVisitor().getCode();
		}
		setAttr("objectId", objectId);
		render("common/upload/upload.html");
	}

	/**
	 * 用于嵌套在表单中上传<br/>
	 */
	public void toFormUpload() {
		String objectId = getPara(0);
		if (objectId == null) {
			objectId = getVisitor().getCode();
		}
		setAttr("objectId", objectId);
		render("common/upload/formUpload.html");
	}
	
	/**
	 * 上传文件,可多附件上传
	 * 
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	public void upload() {
		List<UploadFile> uploadList = getFiles();
		String objectId = getPara() == null ? getPara("objectId") : getPara();
		List<String> url = saveFiles(uploadList, objectId);		
		renderJson(Ret.ok("url", url));	
	}

	/**
	 * 下载文件
	 * 
	 * @params url格式：18080615/18080615025800002
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	public void download() {
		String url = getAttr("url", "");
		FileUploaded fu;
		if (url.contains("/")) {
			fu = getFileUploaded(getAttr("url"));
		} else {
			fu = getFileUploadedByObjectId(getAttr("url"));
		}
		
		//实现服务器资源共享
		String fileTypeName = fu.getFileName().substring(fu.getFileName().lastIndexOf("."));
		fu.setSavePath(PathKit.getWebRootPath() +  "/" +
				WebContant.baseUloadPath + "/" + fu.getUrl() + fileTypeName);
		
		renderFile(new File(fu.getSavePath()), fu.getFileName());
	}

	/**
	 * 删除文件
	 * 
	 * @params url格式：18080615/18080615025800002
	 * @author QinHaiLin
	 * @date 2018年8月1日
	 */
	public void delete() {
		String url = getAttr("url", "");
		if (url.contains("/")) {
			deleteFileByUrl(url);
		} else {
			deleteFileByObjectId(url);
		}
		renderJson(Ret.ok());
	}

	/**
	 * 显示上传文件列表
	 * 
	 * @author QinHaiLin
	 * @date 2018年8月13日
	 */
	public void getFileList() {
		String objectId = getPara(0,getVisitor().getCode());
		setAttr("fileList", getFileUploadListByObjectId(objectId));
		setAttr("objectId", getPara());
		render("common/upload/fileList.html");
	}

	/**
	 * 下载模板文件
	 * 
	 * @params
	 * @author QinHaiLin
	 * @date 2018年8月2日
	 */
	public void temp() {
		renderFile(new File(PathKit.getWebRootPath() + "/" + WebContant.baseDownloadPath + getAttr("url")));
	}

	private static Map<String, String> imageContentType = new HashMap<String, String>();
	static {
		imageContentType.put("jpg","image/jpeg");
		imageContentType.put("jpeg","image/jpeg");
		imageContentType.put("png","image/png");
		imageContentType.put("tif","image/tiff");
		imageContentType.put("tiff","image/tiff");
		imageContentType.put("ico","image/x-icon");
		imageContentType.put("bmp","image/bmp");
		imageContentType.put("gif","image/gif");
	}
	
	/**
	 * 图片预览
	 * 
	 * @params url格式：18080615/18080615025800002
	 * @author QinHaiLin
	 * @date 2019年6月16日
	 */
	public void view() {
		String url = getAttr("url", "");
		FileUploaded fu;
		if (url.contains("/")) {
			fu = getFileUploaded(getAttr("url"));
		} else {
			fu = getFileUploadedByObjectId(getAttr("url"));
		}
		
		if(fu==null){
			renderHtml("<img src=\"/static/img/error/error.png\">");
			return;
		}
		
		try {
			String fileTypeName = fu.getFileName().substring(fu.getFileName().lastIndexOf("."));
			fu.setSavePath(PathKit.getWebRootPath() +  "/" +
					WebContant.baseUloadPath + "/" + fu.getUrl() + fileTypeName);
			File image = new File(fu.getSavePath());
			@SuppressWarnings("resource")
			FileInputStream inputStream = new FileInputStream(image);
			int length = inputStream.available();
			byte data[] = new byte[length];
			getResponse().setContentLength(length);
			String fileName = image.getName();
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			getResponse().setContentType(imageContentType.get(fileType));
			inputStream.read(data);
			OutputStream toClient = getResponse().getOutputStream();
			toClient.write(data);
			toClient.flush();
			toClient.close();
		} catch (IOException e) {
			handerException(e);
			renderHtml("<img src=\"/static/img/error/error.png\">");
			return;
		}
		renderNull();
	}
	
	/**
	 * ueditor上传接口
	 * @author QinHaiLin
	 * @date 2019年11月17日
	 */
	public void ueditor(){
		if ("config".equals(getPara("action"))) {
			render("/static/libs/ueditor/jsp/config.json");
			return;
		}
		/**
		 * 对应 config.json 配置的 imageActionName: "uploadimage"
		 */
		if ( ! "uploadimage".equals(getPara("action"))) {
			renderJson("state", "UploadController 只支持图片类型的文件上传");
			return ;
		}
		
		UploadFile uploadFile = getFile();
		String url=saveFile(uploadFile, getVisitor().getCode());
		
		Ret ret=Ret.create("state", "SUCCESS")
		.set("url", JFinal.me().getContextPath()+"/portal/download/"+url)
		.set("title", uploadFile.getFileName())
		.set("original", uploadFile.getOriginalFileName())
		.set("type", uploadFile.getContentType())
		.set("size", uploadFile.getFile().length());
		
		render(new JsonRender(ret).forIE());	// 防止 IE 下出现文件下载现象
	}
}
