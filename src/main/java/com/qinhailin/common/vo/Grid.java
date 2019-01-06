package com.qinhailin.common.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 列表分页对象数据
 * @author qinhailin
 *
 */
public class Grid extends Pager{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private int code=0;
	private String msg="获取成功";

	private Collection<?> list;
	private String name;
	private String id;
	private String error;
	private List<String> ids;
	
	public Grid(){
		super();
	}
	public Grid(Collection<?> list){
		this.list=list;
		this.setTotalRow(getTotal()==0?list.size():getTotal());
	}
	public Grid(Collection<?> list,int totalRow){
		this.list=list;
		this.setTotalRow(totalRow);
	}
	
	public Grid(Collection<?> list,int pageNumber,int pageSize){
		this.list=list;
		this.setPageNumber(pageNumber);
		this.setPageSize(pageSize);
	}
	
	public Grid(Collection<?> list,int pageNumber,int pageSize,int totalRow){
		this.list=list;
		this.setPageNumber(pageNumber);
		this.setPageSize(pageSize);
		this.setTotalRow(totalRow);
	}
	
	public List<Integer> getIntIds() {
		List<Integer> intIds = new ArrayList<Integer>();
		if (getIds() != null) {
			for (String s : getIds()) {
				if (s != null && s.trim().length() > 0) {
					intIds.add(Integer.valueOf(s));
				}
			}
		}
		return intIds;
	}

	public List<Long> getLongIds() {
		List<Long> longIds = new ArrayList<Long>();
		if (getIds() != null) {
			for (String s : getIds()) {
				if (s != null && s.trim().length() > 0) {
					longIds.add(Long.valueOf(s));
				}
			}
		}
		return longIds;
	}

	public long getTotal() {
		return this.getTotalRow();
	}
	
	public int getCode() {
		return code;
	}

	public Grid setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public Grid setMsg(String msg) {
		this.msg = msg;
		return this;
	}


	public Collection<?> getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public Grid setList(Collection list) {
		this.list = list;
		return this;
	}

	public String getName() {
		return name;
	}

	public Grid setName(String name) {
		this.name = name;
		return this;
	}

	public String getId() {
		return id;
	}

	public Grid setId(String id) {
		this.id = id;
		return this;
	}

	public String getError() {
		return error;
	}

	public Grid setError(String error) {
		this.error = error;
		return this;
	}

	public List<String> getIds() {
		return ids;
	}

	public Grid setIds(List<String> ids) {
		this.ids = ids;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Grid [code=");
		builder.append(code);
		builder.append(", msg=");
		builder.append(msg);
		builder.append(", list=");
		builder.append(list);
		builder.append(", name=");
		builder.append(name);
		builder.append(", id=");
		builder.append(id);
		builder.append(", error=");
		builder.append(error);
		builder.append(", ids=");
		builder.append(ids);
		builder.append("]");
		return builder.toString();
	}

}
