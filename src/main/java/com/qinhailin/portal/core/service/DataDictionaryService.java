package com.qinhailin.portal.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.DataDictionary;
import com.qinhailin.common.vo.TreeNode;

/**
 * 数据字典
 * @author qinhailin
 *
 */
public class DataDictionaryService extends BaseService {

	private DataDictionary dao=new DataDictionary().dao();
	private final String table="data_dictionary";
	@Override
	public Model<?> getDao() {
		return dao;
	}

	@Override
	public String getTable() {
		return table;
	}
	

	/**
	 * 数据字典树
	 * @return
	 */
	public Collection<TreeNode> getDictionaryTree() {
		List<Record> list = queryAllList("order by order_num asc");
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Record record : list) {
			TreeNode node = new TreeNode();		
			node.setId(record.getStr("code"));
			node.setText(record.getStr("name"));
			Collection<TreeNode> children =new ArrayList<TreeNode>();
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}
}
