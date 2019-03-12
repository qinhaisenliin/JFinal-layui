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

package com.qinhailin.common.kit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * 导入数据
 * 
 * @author qinhailin
 *
 */
public class ExcelKit {

	/**
	 * 读取xecell数据
	 * 
	 * @param file
	 * @return
	 * @author qinhailin
	 * @date 2018年8月6日
	 */
	public static List<Object[]> getExcelData(File file) {
		String name = file.getName();
		String type = name.substring(name.lastIndexOf("."));
		if ((".xls").equals(type)) {
			try {
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
				return readOldExcel(hssfWorkbook);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (".xlsx".equals(type)) {
			try {
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
				return readExcel(xssfWorkbook);
			} catch (InvalidFormatException | IOException e) {
				e.printStackTrace();
			}
		}

		return getData(file).get(0);
	}

	@SuppressWarnings("deprecation")
	private static List<List<Object[]>> getData(File file) {
		HSSFWorkbook workbook;
		List<List<Object[]>> data = new ArrayList<List<Object[]>>();
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
			HSSFSheet sheet = null;
			// 循环sheet
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				sheet = workbook.getSheetAt(i);
				List<Object[]> rows = new ArrayList<Object[]>();
				int colsnum = 0;
				// 循环每一行
				for (int j = 0; j <= sheet.getLastRowNum(); j++) {
					HSSFRow row = sheet.getRow(j);
					if (null != row) {
						// 列数以excel第二行为准，第二行为标题，第一行为excel导入提示信息
						colsnum = sheet.getRow(1).getPhysicalNumberOfCells();
						Object[] cols = new Object[colsnum];
						// 循环每一个单元格，以一行为单位，组成一个数组
						for (int k = 0; k < colsnum; k++) {
							// 判断单元格是否为null，若为null，则置空
							if (null != row.getCell(k)) {
								int type = row.getCell(k).getCellType();
								// 判断单元格数据是否为数字
								if (type == HSSFCell.CELL_TYPE_NUMERIC) {
									// 判断该数字的计数方法是否为科学计数法，若是，则转化为普通计数法
									if (String.valueOf(row.getCell(k).getNumericCellValue()).matches(".*[E|e].*")) {
										DecimalFormat df = new DecimalFormat("#.#");
										// 指定最长的小数点位为10
										df.setMaximumFractionDigits(10);
										cols[k] = df.format(row.getCell(k).getNumericCellValue());
										// 判断该数字是否是日期,若是则转成字符串
									} else if (HSSFDateUtil.isCellDateFormatted(row.getCell(k))) {
										Date d = row.getCell(k).getDateCellValue();
										DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
										cols[k] = formater.format(d);
									} else {
										cols[k] = (row.getCell(k) + "").trim();
									}
								} else {
									cols[k] = (row.getCell(k) + "").trim();
								}
							} else {
								cols[k] = "";
							}
						}
						// 以一行为单位，加入list
						rows.add(cols);
					}
				}
				// 返回所有数据，第一个list表示sheet，第二个list表示sheet内所有行数据，第三个string[]表示单元格数据
				data.add(rows);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 处理2007之前的excel
	 * 
	 * @param hssfWorkbook
	 * @return
	 * @author qinhailin
	 * @date 2018年8月6日
	 */
	private static List<Object[]> readOldExcel(HSSFWorkbook hssfWorkbook) {
		List<Object[]> data = new ArrayList<Object[]>();
		HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
		HSSFCell cell = null;
		HSSFRow row = null;
		for (int i = sheetAt.getFirstRowNum(); i < sheetAt.getPhysicalNumberOfRows(); i++) {
			row = sheetAt.getRow(i + 1);
			if (row == null) {
				continue;
			}
			System.out.print(i + 1 + "-->: ");
			Object[] objects = new Object[row.getLastCellNum()];
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				switch (cell.getCellTypeEnum()) {
				case STRING:
					objects[j] = cell.getStringCellValue();
					System.out.print(cell.getStringCellValue());
					System.out.print(", ");
					break;
				case _NONE:
					objects[j] = "";
					break;
				case BOOLEAN:
					objects[j] = cell.getBooleanCellValue();
					System.out.print(cell.getBooleanCellValue());
					System.out.print(", ");
					break;
				case NUMERIC:
					// 处理double类型的 1.0===》1
					DecimalFormat df = new DecimalFormat("0");
					String s = df.format(cell.getNumericCellValue());
					objects[j] = s;
					System.out.print(s);
					System.out.print(", ");
					break;
				default:
					objects[j] = cell.toString();
				}
			}
			data.add(objects);
			System.out.println();
		}
		return data;
	}

	/**
	 * 处理2007之后的excel
	 * 
	 * @param xssfWorkbook
	 * @return
	 * @author qinhailin
	 * @date 2018年8月6日
	 */
	private static List<Object[]> readExcel(XSSFWorkbook xssfWorkbook) {
		List<Object[]> data = new ArrayList<Object[]>();
		// 获得excel第一个工作薄
		XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
		// 行
		XSSFRow row = null;
		// 列
		XSSFCell cell = null;
		for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
			// 获取每一行
			row = sheet.getRow(i + 1);
			// 判断是否出现空行
			if (row == null) {
				continue;
			}
			System.out.print(i + 1 + "-->: ");
			Object[] objects = new Object[row.getLastCellNum()];
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				cell = row.getCell(j);

				if (cell == null) {
					continue;
				}
				// 第一行数据
				switch (cell.getCellTypeEnum()) {
				case STRING:
					objects[j] = cell.getStringCellValue();
					System.out.print(cell.getStringCellValue());
					System.out.print(", ");
					break;
				case _NONE:
					objects[j] = "";
					break;
				case BOOLEAN:
					objects[j] = cell.getBooleanCellValue();
					System.out.print(cell.getBooleanCellValue());
					System.out.print(", ");
					break;
				case NUMERIC:
					// 处理double类型的 1.0===》1
					DecimalFormat df = new DecimalFormat("0");
					String s = df.format(cell.getNumericCellValue());
					objects[j] = s;
					System.out.print(s);
					System.out.print(", ");
					break;
				default:
					objects[j] = cell.toString();
				}

			}
			data.add(objects);
			System.out.println();
		}
		return data;
	}

	/**
	 * 导出数据.xlsx
	 * 
	 * @param response
	 * @param title
	 *            标题
	 * @param fileName
	 *            文件名
	 * @param list
	 *            导出数据
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author qinhailin
	 * @date 2018年8月13日
	 */
	public static void exportExcelxlsx(HttpServletResponse response, String[] title, String fileName, List<Record> list)
			throws FileNotFoundException, IOException {
		// 获取模板的输入流
		File f = new File(PathKit.getWebRootPath() + "/WEB-INF/temp/download/templete.xlsx");
		InputStream inputStream = new FileInputStream(f);

		// 获取模板的工作薄
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

		XSSFSheet sheetAt = workbook.getSheetAt(0);
		XSSFRow row = sheetAt.getRow(0);
		short rowHeight = row.getHeight();
		XSSFRow row1 = null;
		XSSFCell cell = null;

		// 标题
		row1 = sheetAt.createRow(0);
		row1.setHeight(rowHeight);
		for (int i = 0; i < title.length; i++) {
			cell = row1.createCell(i);
			cell.setCellValue(title[i] == null ? "" : title[i].toString());
		}

		// 内容
		for (int i = 0; i < list.size(); i++) {
			Record record = list.get(i);
			int length = record.getColumns().size();
			row1 = sheetAt.createRow(i + 1);// 内容从第二行开始
			row1.setHeight(rowHeight);
			for (int j = 0; j < length; j++) {
				cell = row1.createCell(j);
				cell.setCellValue(record.getStr(j + "") == null ? "" : record.getStr(j + ""));
			}
		}

		if (fileName != null) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else {
			fileName = "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");
		String date = sdf.format(new Date());

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + date + ".xlsx");

		workbook.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		workbook.close();
	}
	
	/**
	 * 导出数据.xls
	 * 
	 * @param response
	 * @param title
	 *            标题
	 * @param fileName
	 *            文件名
	 * @param list
	 *            导出数据
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void exportExcelxls(HttpServletResponse response, String[] title, String fileName, List<Record> list)
			throws FileNotFoundException, IOException {
		// 获取模板的输入流
		File f = new File(PathKit.getWebRootPath() + "/WEB-INF/temp/download/templete.xls");
		InputStream inputStream = new FileInputStream(f);

		// 获取模板的工作薄
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

		HSSFSheet sheetAt = workbook.getSheetAt(0);
		HSSFRow row = sheetAt.getRow(0);
		short rowHeight = row.getHeight();
		HSSFRow row1 = null;
		HSSFCell cell = null;

		// 标题
		row1 = sheetAt.createRow(0);
		row1.setHeight(rowHeight);
		for (int i = 0; i < title.length; i++) {
			cell = row1.createCell(i);
			cell.setCellValue(title[i] == null ? "" : title[i].toString());
		}

		// 内容
		for (int i = 0; i < list.size(); i++) {
			Record record = list.get(i);
			int length = record.getColumns().size();
			row1 = sheetAt.createRow(i + 1);// 内容从第二行开始
			row1.setHeight(rowHeight);
			for (int j = 0; j < length; j++) {
				cell = row1.createCell(j);
				cell.setCellValue(record.getStr(j + "") == null ? "" : record.getStr(j + ""));
			}
		}

		if (fileName != null) {
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} else {
			fileName = "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddmmss");
		String date = sdf.format(new Date());

		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + date + ".xlsx");

		workbook.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		workbook.close();
	}
}
