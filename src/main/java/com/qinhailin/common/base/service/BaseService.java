package com.qinhailin.common.base.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.qinhailin.common.kit.IdKit;
import com.qinhailin.common.vo.Grid;

/**
 * 基于JFinal的通用service接口
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
		Db.batch(sql, paras, 100);
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
		Db.batch(sql, paras, 100);
	}
	
	/**
	 * 是否存在对象数据
	 * @param pk
	 * @param value
	 * @return
	 */
	public boolean isExit(String pk,String value){
		List<?> list=Db.find(getQuerySql()+"where "+pk+"=? limit 1", value);
		if(list.size()==1){
			return true;
		}
		return false;
	}
	
	public List<Record> queryAllList() {
		return Db.find(getQuerySql());
	}
	
	public List<Record> queryAllList(String groupOrderBy) {
		return Db.find(getQuerySql()+groupOrderBy);
	}
	
	public List<Record> queryForList(String sql) {
		return Db.find(sql);
	}
	
	public List<Record> queryForList(String sql,Object...object) {
		return Db.find(sql,object);
	}
	public List<Record> queryForList(String sql,Record record) {
		return queryForList(sql,record,null);
	}
	
	public List<Record> queryForList(String sql,Record record,String groupOrderBy){
		List<Object> paras = new ArrayList<>();
		sql = this.createQuerySql(sql, groupOrderBy, record, paras, "like");
		List<Record> list = Db.find(sql, paras.toArray());
		return list;
	}
	
	public List<Record> queryForListEq(String sql,Record record,String groupOrderBy){
		List<Object> paras = new ArrayList<>();
		sql = this.createQuerySql(sql, groupOrderBy, record, paras, "=");
		List<Record> list = Db.find(sql, paras.toArray());
		return list;
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
		int startIndex = (pageNumber - 1) * pageSize;
		List<Record> list = Db.find(sql + " limit " + startIndex + "," + pageSize, paras);
		List<Record> count = Db.find(sql, paras);
		return new Grid(list, pageNumber, pageSize, count.size());
	}
	
	private Grid getGrid(int pageNumber,int pageSize,String sql){
		int startIndex = (pageNumber - 1) * pageSize;
		List<Record> list = Db.find(sql + " limit " + startIndex + "," + pageSize);
		List<Record> count = Db.find(sql);
		return new Grid(list, pageNumber, pageSize, count.size());
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
        		if(column.endsWith("=")){
        			whereSql.append(column).append("? ");
					paras.add(value);
        		}else if(column.endsWith("like")){
        			whereSql.append(column).append(" ? ");
					paras.add("%" + value + "%");
        		}else if("=".equals(queryType)) {
					whereSql.append(column).append("=? ");
					paras.add(value);
				} else {
					whereSql.append(column).append(" like ? ");
					paras.add("%" + value + "%");
				}
        	}
        }

        if(whereSql.length()>0){
        	if(sql.contains("where")){
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
}
