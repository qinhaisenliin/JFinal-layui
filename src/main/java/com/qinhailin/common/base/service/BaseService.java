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

package com.qinhailin.common.base.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbPro;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.qinhailin.common.kit.IdKit;
import com.qinhailin.common.vo.Grid;

/**
 * 基于JFinal的通用service接口，支持多数据源
 * @author QinHaiLin
 */
public abstract class BaseService {
	protected Logger logger = Logger.getLogger(getClass());
		
	/**
	 * 获取model dao
	 * @return 业务Model
	 */
	public abstract Model<?> getDao();

	/**
	 * 指定数据源,多数据源情况下使用<br/>
	 * 列如oracle数据源别名为oracle，在service重写该方法:
	 * <pre>
	 * @Override
	 * public String getDb(){
	 *	return "oracle";
	 * }
	 * </pre>
	 * @return 若return null，则使用主数据源
	 * @author QinHaiLin
	 * @date 2019年3月13日
	 */
	public String getDb(){
		return null;
	}
	
	/**
	 * 获取DBPro数据源
	 * @return
	 */
	private DbPro getDbPro(){
		if(getDb()!=null){
			return Db.use(getDb());
		}
		return Db.use();
	}
	
	/**
	 * 获取table名称
	 * @return tableName
	 */
	public String getTable() {
		return _getTable().getName();
	};
	
	/**
	  *  获取表主键（单键表）
	 * @return
	 */
	public String getPK() {
		return _getTable().getPrimaryKey()[0];		
	}
	
	protected Table _getTable() {
		if(getDao()==null){
			logger.error("请实现getDao()方法,且不能返回null");
		}
		return TableMapping.me().getTable(getDao().getClass());
	}
	
	
	/**
	 * 通用findById
	 * @param id
	 * @return
	 */
	public Model<?> findById(String id){
		return getDao().findById(id);
	}
	
	/**
	 * 通过字段查找对象数据
	 * @param pk 字段名
	 * @param value 字段值
	 * @return
	 */
	public Model<?> findByPk(String pk,String value){
		List<?> list=getDao().find(getQuerySql()+" where "+pk+"=?", value);
		if(list.size()>0){
			return (Model<?>) list.get(0);
		}
		return null;
	}
	
	/**
	 * 通用save
	 * @param entity
	 * @return
	 */
	public boolean save(Model<?> entity){
		//主键赋值uuid
		if(entity.get(getPK())==null){
			entity.set(getPK(), IdKit.createUUID());		
		};
		return entity.save();
	}
	
	/**
	 * 通用update
	 * @param entity
	 * @return
	 */
	public boolean update(Model<?> entity){
		return entity.update();
	}
	
	/**
	 * 通用delete
	 * @param entity
	 * @return
	 */
	public boolean delete(Model<?> entity){
		return entity.delete();
	}
	
	/**
	 * 通用deleteById
	 * @param id
	 * @return
	 */
	public boolean deleteById(String id){
		return getDao().deleteById(id);
	}
	
	/**
	 * 通用deleteByIds
	 * @param ids
	 */
	public void deleteByIds(List<String> ids){
		Object[][] paras=new Object[ids.size()][1];
		for(int i=0;i<ids.size();i++) {
			paras[i][0]=ids.get(i);
		}
		String sql="delete from "+getTable()+" where id=?";
		getDbPro().batch(sql, paras, 100);
	}
	
	/**
	 * 根据字段删除数据
	 * @param ids
	 * @param pk
	 */
	public void deleteByPk(List<String> ids,String pk){
		Object[][] paras=new Object[ids.size()][1];
		for(int i=0;i<ids.size();i++) {
			paras[i][0]=ids.get(i);
		}
		String sql="delete from "+getTable()+" where "+pk+"=?";	
		getDbPro().batch(sql, paras, 100);			
	}
	
	/**
	 * 是否存在对象数据
	 * @param pk
	 * @param value
	 * @return
	 */
	public boolean isExit(String pk,String value){
		List<?> list=getDbPro().find(getQuerySql()+"where "+pk+"=?", value);
		return list.size()>0;
	}
	
	public List<Record> queryAllList() {
		return getDbPro().find(getQuerySql());
	}
	
	public List<Record> queryAllList(String groupOrderBy) {
		return getDbPro().find(getQuerySql()+groupOrderBy);
	}
	
	public List<Record> queryForList(String sql) {
		return getDbPro().find(sql);
	}
	
	public List<Record> queryForList(String sql,Object...object) {
		return getDbPro().find(sql,object);
	}
	public List<Record> queryForList(String sql,Record record) {
		return queryForList(sql,record,null);
	}
	
	public List<Record> queryForList(String sql,Record record,String groupOrderBy){
		List<Object> paras = new ArrayList<>();
		sql = this.createQuerySql(sql, groupOrderBy, record, paras, "like");
		return getDbPro().find(sql, paras.toArray());	
	}
	
	public List<Record> queryForListEq(String sql,Record record,String groupOrderBy){
		List<Object> paras = new ArrayList<>();
		sql = this.createQuerySql(sql, groupOrderBy, record, paras, "=");
		return getDbPro().find(sql, paras.toArray());
	}
	
	/**
	 * 自定义分页查询
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param record
	 * @param groupOrderBy
	 * @return
	 */
	public Grid queryForList(String sql,int pageNumber,int pageSize,Record record,String groupOrderBy){
		List<Object> paras = new ArrayList<>();
		sql = this.createQuerySql(sql, groupOrderBy, record, paras, "like");
		return getGrid(pageNumber, pageSize, sql, paras.toArray());
	}
	
	public Grid queryForListEq(String sql,int pageNumber,int pageSize,Record record,String groupOrderBy){
		List<Object> paras = new ArrayList<>();
		sql = this.createQuerySql(sql, groupOrderBy, record, paras, "=");
		return getGrid(pageNumber, pageSize, sql, paras.toArray());
	}
	
	public Grid queryForList(int pageNumber,int pageSize,String sql){
		return getGrid(pageNumber, pageSize, sql);
	}
	
	public Grid queryForList(int pageNumber,int pageSize,String sql,Object... object){
		return getGrid(pageNumber, pageSize, sql, object);	
	}
	
	public Grid queryForList(int pageNumber,int pageSize){
		return getGrid( pageNumber, pageSize,getQuerySql());
	}
	
	public Grid queryForList(int pageNumber,int pageSize,Record record){
		List<Object> paras=new ArrayList<>();
		String sql=createQuerySql(getQuerySql(), null, record, paras, "like");
		return getGrid( pageNumber, pageSize,sql,paras.toArray());
	}
	
	public Grid queryForList(int pageNumber,int pageSize,Record record,String orderBygroupBySql){
		List<Object> paras=new ArrayList<>();
		String sql=createQuerySql(getQuerySql(), orderBygroupBySql, record, paras, "like");
		return getGrid( pageNumber, pageSize,sql,paras.toArray());
	}
	
	/**
	 * 全等查询
	 * @param pageNumber
	 * @param pageSize
	 * @param record
	 * @return
	 */
	public Grid queryForListEq(int pageNumber,int pageSize,Record record){
		List<Object> paras=new ArrayList<>();
		String sql=createQuerySql(getQuerySql(), null, record, paras, "=");
		return getGrid( pageNumber, pageSize,sql,paras.toArray());
	}
	
	/**
	 * 全等查询
	 * @param pageNumber
	 * @param pageSize
	 * @param record
	 * @param orderBygroupBySql
	 * @return
	 */
	public Grid queryForListEq(int pageNumber,int pageSize,Record record,String orderBygroupBySql){
		List<Object> paras=new ArrayList<>();
		String sql=createQuerySql(getQuerySql(), orderBygroupBySql, record, paras, "=");
		return getGrid( pageNumber, pageSize,sql,paras.toArray());
	}
				
	/**
	 * 分页,模糊查询
	 * @param grid
	 * @param record columns查询元素集合  
	 * @return
	 */
	public Grid queryForList(Grid grid, Record record) {
		List<Object> paras=new ArrayList<>();
		String sql = this.createQuerySql(getQuerySql(), null, record, paras, "like");
		return getGrid(grid.getPageNumber(),grid.getPageSize(),sql,paras.toArray());
	}
	
	/**
	 * 分页查询,分组排序
	 * @param grid
	 * @param orderBygroupBySql
	 * @return
	 */
	public Grid queryForList(Grid grid, String orderBygroupBySql) {
		List<Object> paras=new ArrayList<>();
		String sql = this.createQuerySql(getQuerySql(), orderBygroupBySql, null, paras, "like");
		return getGrid(grid.getPageNumber(),grid.getPageSize(),sql,paras.toArray());
	}

	/**
	 * 分页,模糊查询,分组排序
	 * @param grid
	 * @param record columns查询元素集合
	 * @param orderBygroupBySql 分组排序
	 * 
	 */
	public Grid queryForList(Grid grid, Record record, String orderBygroupBySql) {
		List<Object> paras = new ArrayList<>();
		String sql = this.createQuerySql(getQuerySql(), orderBygroupBySql, record, paras, "like");
		return getGrid(grid.getPageNumber(),grid.getPageSize(),sql,paras.toArray());
	}

	private Grid getGrid(int pageNumber,int pageSize,String sql,Object... paras){
		SqlPara sqlPara=new SqlPara().setSql(sql);
		for(int i=0;i<paras.length;i++){
			sqlPara.addPara(paras[i]);
		}
		
		Page<Record> page=getDbPro().paginate(pageNumber, pageSize, sqlPara);
		return new Grid(page.getList(), pageNumber, pageSize, page.getTotalRow());
	}
	
	private Grid getGrid(int pageNumber,int pageSize,String sql){
		SqlPara sqlPara=new SqlPara().setSql(sql);
		Page<Record> page=getDbPro().paginate(pageNumber, pageSize, sqlPara);
		return new Grid(page.getList(), pageNumber, pageSize, page.getTotalRow());
	}
	
	/**
	 * 拼接模糊查询条件
	 * 
	 * @param sql
	 * @param orderByGroupBySql
	 * @param record
	 *            columns查询元素集合
	 * @param paras
	 * @param queryType
	 *            like or = ，模糊查询或者全等查询
	 * @return
	 */
	private String createQuerySql(String sql, String orderByGroupBySql, Record record, List<Object> paras,
			String queryType) {
		if(record==null){
			return orderByGroupBySql == null ? sql : sql + " " + orderByGroupBySql;
		}
		
		Map<String,Object> columns=record.getColumns();
        Iterator<String> iter=columns.keySet().iterator();
        StringBuffer whereSql=new StringBuffer();
        
        while(iter.hasNext()){
        	String column=iter.next();
        	Object value=columns.get(column);
        	
        	if(value!=null&&value.toString().trim().length()>0){
        		if(whereSql.length()>0){
        			whereSql.append(" and ");
        		}
        		//用法看用户管理查询功能
        		if(column.endsWith("=")){                              //column=、column<=、column>=
        			whereSql.append(column).append(" ? ");
					paras.add(value);
        		} else if(column.endsWith(">")||column.endsWith("<")){ //column<、column>
        			whereSql.append(column).append(" ? ");
        			paras.add(value);
        		}else if(column.toLowerCase().endsWith("like")){       //column like
        			whereSql.append(column).append(" ? ");
					paras.add("%" + value + "%");
        		}else if("=".equals(queryType)) {
					whereSql.append(column).append(" =? ");
					paras.add(value);
				}else{
					whereSql.append(column).append(" like ? ");
					paras.add("%" + value + "%");
				}
        	}
        }

        if(whereSql.length()>0){
        	if(sql.toLowerCase().contains("where")){
        		sql+=" and "+whereSql.toString();
        	}else{
        		sql+=" where "+whereSql.toString();
        	}        	
        }
        
		if (orderByGroupBySql != null) {
			sql += " " + orderByGroupBySql;
		}
		
        return sql;
	}
	
	/**
	 * select * from getTable()
	 * @return
	 */
	private String getQuerySql() {			
		return "select * from "+getTable()+" ";
	}
	
	/**
	 * 自定义sql查询，sql定义在sql模板文件中 
	 * @param dbName 数据库配置名称，用于切换多数据源查询，为null时查询主数据源
	 * @param params 查询参数（JSONObject）
	 * @param sqlTemplateName sql唯一名称（命名空间.sql语句名称）
	 * @param orderBygroupBySql 排序语句
	 * @return 
	 */	
	public Grid queryForListBySqlTemplate(String dbName,Integer pageNumber,Integer pageSize,JSONObject params,String sqlTemplateName,String orderBygroupBySql) {
		DbPro dbPro = Db.use();
		//切换数据源
		if(StrKit.notBlank(dbName)) {
			dbPro = Db.use(dbName);
		}
		SqlPara sqlPara = dbPro.getSqlPara(sqlTemplateName,params);
		if(StrKit.notBlank(orderBygroupBySql)) {//如果有排序语句，则追加
			sqlPara.setSql(sqlPara.getSql() + " " + orderBygroupBySql);
		}
		Page<Record> page = dbPro.paginate(pageNumber,pageSize, sqlPara);
		return new Grid(page.getList(),pageNumber,pageSize,page.getTotalRow());
	}	
	
	/**
	 * 自定义sql查询，sql定义在sql模板文件中
	 * @param grid 封装的layui表格对象
	 * @param params 查询参数（JSONObject）
	 * @param sqlTemplateName sql唯一名称（命名空间.sql语句名称）
	 * @param orderBygroupBySql 排序语句
	 * @return 
	 */
	public Grid queryForListBySqlTemplate(Integer pageNumber,Integer pageSize,JSONObject params,String sqlTemplateName,String orderBygroupBySql) {
		return queryForListBySqlTemplate(null, pageNumber, pageSize, params, sqlTemplateName, orderBygroupBySql);
	}
	
	public Grid queryForListByRecord(String dbConfig,int pageNumber,int pageSize,Record params,String orderBySql,String groupBySql) {
		return getGrid(dbConfig,pageNumber,pageSize,params,orderBySql,groupBySql);
	}
	
	public Grid queryForListByRecord(int pageNumber,int pageSize,Record params,String orderBySql) {
		return getGrid(null,pageNumber,pageSize,params,orderBySql,null);
	}
	
	/**
	 * 单表分页条件查询，支持多数据源
	 * @param dbConfig 数据源名称
	 * @param pageNumber 页码 
	 * @param pageSize 分页大小
	 * @param params 查询条件
	 * @param orderBySql orderBy语句
	 * @param groupBySql groupBy语句
	 * @return
	 */
	private Grid getGrid(String dbConfig,int pageNumber,int pageSize,Record params,String orderBySql,String groupBySql) {
		//拼接sql中的from部分
		StringBuilder from =new StringBuilder("from ");
		from.append(getTable()).append(" where 1=1");
		//这个用来存值不为空的value集合
		List<Object> notNullValues = new ArrayList<>();
		if(params!=null && params.getColumnNames().length > 0) {
			//查询条件前置部分集合（字段+匹配符号，如 name like,id = ）
			//也可以直接写全查询条件，这时不需要value,如(id = 1 or parent_id = 1)
			String columnNames[] = params.getColumnNames();
			//查询条件后置部分集合（每个查询条件匹配的值，如"张三",20）
			Object[] columnValues = params.getColumnValues();
			for(int i = 0;i<columnNames.length;i++) {
				String columnName = columnNames[i];
				Object columnValue = columnValues[i];
				if(columnValue != null && StrKit.notBlank(String.valueOf(columnValue))) {
					//处理不带?号的查询条件，这类查询条件，value一律传"withoutValue"
					if("withoutValue".equals(columnValue)) {
						from.append(" and ").append(columnName);
					}else {						
						if(columnName.contains("like")) {
							columnValue = "%"+columnValue+"%";							
						}
						from.append(" and ").append(columnName).append(" ?");
						notNullValues.add(columnValue);
					}
				}
			}
		}
		Object[] notNullValueArr = new Object[notNullValues.size()];
		notNullValues.toArray(notNullValueArr);
		//计数语句
		String totalRowSql = "select count(*) " + from.toString();
		
		if(StrKit.notBlank(groupBySql)) {
			totalRowSql += " " + groupBySql;
		}
		//查询语句
		String findSql = "select * " + from.toString() ;
		if(StrKit.notBlank(orderBySql)) {
			findSql += " " + orderBySql; 
		}
		Page<Record> page = new Page<Record>();
		if(StrKit.notBlank(dbConfig)) {
			page = Db.use(dbConfig).paginateByFullSql(pageNumber, pageSize,totalRowSql,findSql,notNullValueArr);
		}else {
			page = Db.paginateByFullSql(pageNumber, pageSize,totalRowSql,findSql,notNullValueArr);
		}
		
		return new Grid(page.getList(), pageNumber, pageSize,page.getTotalRow());	
	}
}
