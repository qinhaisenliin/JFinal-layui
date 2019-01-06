package com.qinhailin.common.base.service;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.qinhailin.common.model.FileUploaded;
import com.qinhailin.common.kit.ExcelKit;
import com.qinhailin.common.kit.IdKit;

/**
 * 附件管理
 * 
 * @author QinHaiLin
 *
 */
public class FileService  {

	private FileUploaded dao = new FileUploaded().dao();
	
	public boolean save(FileUploaded entity) {
		return entity.save();
	}

	public FileUploaded queryFileUploadedByUrl(String url){
		List<FileUploaded> list = dao.find("select * from file_uploaded where url=?", url);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public FileUploaded queryFileUploadedByObjectId(String objectId) {
		List<FileUploaded> list = dao.find("select * from file_uploaded where objectId=?", objectId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<FileUploaded> queryFileUploadedListByObjectId(String objectId) {
		return dao.find("select * from file_uploaded where objectId=?", objectId);
	}

	public void delete(FileUploaded entity) {
		File file = new File(entity.getSavePath());
		if (file.exists()) {
			file.delete();
		}
		entity.delete();
	}
	
	/**
	 * 批量删除文件及记录
	 * @param modelList
	 */
	public void delete(List<FileUploaded> modelList){
		Object[][] paras=new Object[modelList.size()][1];
		int i=0;
		for(FileUploaded entity:modelList){
			File file = new File(entity.getSavePath());
			if (file.exists()) {
				file.delete();
			}
			paras[i][0]=entity.getId();
			i++;
		}
		String sql="delete from file_uploaded where id=?";
		Db.batch(sql, paras, 100);
	}

	public void deleteFile(String url) {
		List<FileUploaded> list = dao.find("select * from file_uploaded where url=?", url);
		delete(list.get(0));
	}

	public void deleteFiles(List<String> urls) {
		StringBuffer sbf = new StringBuffer();
		for (String url : urls) {
			if (sbf.length() > 0) {
				sbf.append(",");
			}
			sbf.append("'").append(url).append("'");
		}
		List<FileUploaded> list = dao.find("select * from file_uploaded where url in (" + sbf.toString() + ")");
		delete(list);
	}

	public void deleteFileByObjectId(String objectId) {
		List<FileUploaded> list = dao.find("select * from file_uploaded where objectId=?", objectId);
		this.delete(list);	
	}

	/**
	 * 删除导入数据的文件
	 * @param uf
	 */
	public void deleteFile(UploadFile uf) {
		File file = new File(uf.getUploadPath() + "/" + uf.getFileName());
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 保存文件记录
	 * @param uploadFile
	 * @param objectId
	 *            附件关联对象Id
	 * @return
	 */
	public String saveFile(UploadFile uploadFile, String objectId) {
		FileUploaded entity=this.createFileUploaded(uploadFile,objectId);
		if(entity==null) {
			return "";
		}
		entity.save();
		return entity.getUrl();
	}

	public String saveFile(UploadFile uf) {
		return this.saveFile(uf, null);
	}

	/**
	 * 保存文件记录
	 * 
	 * @param list
	 * @return
	 */
	public List<String> saveFiles(List<UploadFile> list) {
		return this.saveFiles(list,null);
	}

	/**
	 * 保存文件记录
	 * 
	 * @param list
	 * @param objectId
	 *            附件关联对象Id
	 * @return
	 */
	public List<String> saveFiles(List<UploadFile> list, String objectId) {
		List<String> results = new ArrayList<String>();
		List<FileUploaded> modelList=new ArrayList<>();
		for (UploadFile uf : list) {
			FileUploaded entity=createFileUploaded(uf, objectId);
			if(entity!=null){
				modelList.add(entity);
				results.add(entity.getUrl());			
			}
		}
		Db.batchSave(modelList, 50);
		return results;
	}

	/**
	 * 导入xls数据
	 * 
	 * @param uf
	 * @param sql
	 *            insert into game_theme (id, state, title) values(?,?,?)
	 * @return
	 */
	public boolean importExcel(UploadFile uf, String sql) {
		return Db.tx(new IAtom() {
			boolean save_flag = true;
			@Override
			public boolean run() throws SQLException {
				try {
					UploadFile up = uf;
					List<Object[]> list = ExcelKit.getExcelData(up.getFile());
					Object[][] objs=new Object[list.size()][list.get(0).length+1];
					for(int i=0;i<list.size();i++){
						for(int j=0;j<list.get(0).length;j++){
							if(j==0){
								objs[i][0]=IdKit.createUUID();
								objs[i][j+1]=list.get(i)[j];
							}else{								
								objs[i][j+1]=list.get(i)[j];
							}
						}
					}
					//批量导入
					Db.batch(sql, objs, 100);
				} catch (Exception e) {
					save_flag = false;
					e.printStackTrace();
				}
				return save_flag;
			}
		});
	}

	/**
	 * 导出excel.xlsx
	 * 
	 * @param response
	 * @param title
	 *            标题，如：String[] title={"姓名","性别","年龄",...}
	 * @param fileName
	 *            文件名，如：用户信息
	 * @param list
	 *            map集合，key=0,1,2,3...,用数字作为key避免数据错乱
	 * @return
	 * @author qinhailin
	 * @date 2018年8月6日
	 */
	public void exportExcelxlsx(HttpServletResponse response, String[] title, String fileName, List<Record> list) {
		try {
			ExcelKit.exportExcelxlsx(response, title, fileName, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void exportExcelxlsx(HttpServletResponse response, String[] title, List<Record> list) {
		try {
			ExcelKit.exportExcelxlsx(response, title, null, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param response
	 * @param title
	 *            标题如：String[] title={"姓名","性别","年龄",...}
	 * @param fileName
	 *            文件名，如：用户信息
	 * @param sql
	 *            如：select name as '0',sex as '1',age as '2' from user
	 * @author qinhailin
	 * @date 2018年8月14日
	 */
	public void exportExcelxlsx(HttpServletResponse response, String[] title, String fileName, String sql) {
		try {
			List<Record> list = Db.find(sql);
			ExcelKit.exportExcelxlsx(response, title, fileName, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void exportExcelxlsx(HttpServletResponse response, String[] title, String sql) {
		try {
			List<Record> list = Db.find(sql);
			ExcelKit.exportExcelxlsx(response, title, null, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 导出excel数据
	 * @param response
	 * @param title
	 *            标题如：String[] title={"姓名","性别","年龄",...}
	 * @param fileName
	 *            文件名，如：用户信息
	 * @param sql
	 *            如：select name as '0',sex as '1',age as '2' from user where
	 *            name=?
	 * @param paras
	 *            sql查询参数
	 */
	public void exportExcelxls(HttpServletResponse response, String[] title, String fileName, String sql,
			Object... paras) {
		try {
			List<Record> list = Db.find(sql, paras);
			ExcelKit.exportExcelxlsx(response, title, fileName, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void exportExcelxlsx(HttpServletResponse response, String[] title, String sql, Object... paras) {
		try {
			List<Record> list = Db.find(sql, paras);
			ExcelKit.exportExcelxlsx(response, title, null, list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 创建文件上传文件对象信息
	 * @param uploadFile
	 * @param objectId
	 * @return
	 */
	private FileUploaded createFileUploaded(UploadFile uploadFile, String objectId){
		if(uploadFile==null) {
			return null;
		}
		// 获取已上传的文件
		File file = uploadFile.getFile();
		String filePath = uploadFile.getUploadPath();
		String fileName = uploadFile.getFileName();
		String subfix = fileName.substring(fileName.lastIndexOf("."));
		long fileSize = file.length();

		// 创建目录
		String rename = IdKit.createFileId();
		String date = rename.substring(0, 8);
		String newFilePath = filePath;
		File f = new File(newFilePath, date);
		if (!f.exists()) {
			f.mkdirs();
		}

		// 文件重命名
		boolean b = file.renameTo(new File(f, rename + subfix));
		if (b) {
			filePath = newFilePath + "/" + date + "/" + rename + subfix;
		} else {
			filePath = filePath + "/" + fileName;
			date = "";
			rename = fileName.substring(0, fileName.indexOf("."));
		}

		// 保存记录
		FileUploaded fileUpload = new FileUploaded();
		fileUpload.setId(IdKit.createIdWorker());
		fileUpload.setFileName(fileName);
		fileUpload.setSavePath(filePath);
		fileUpload.setFileSize(fileSize);
		fileUpload.setCreateTime(new Date());
		fileUpload.setObjectId(objectId);
		fileUpload.setUrl(date + "/" + rename);
		return fileUpload;
	}

}
