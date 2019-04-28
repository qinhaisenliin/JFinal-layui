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

package com.qinhailin.portal.form.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.service.DbService;
import com.qinhailin.common.vo.TreeNode;

/**
 * 系统树
 * @author QinHaiLin
 * @date 2019年4月24日  
 */
public class SysTreeService extends DbService{
	
	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.DbService#getTable()
	 */
	@Override
	public String getTable() {
		return "sys_tree";
	}
	
	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.DbService#getPrimaryKey()
	 */
	@Override
	public String getPrimaryKey() {
		return "id";
	}

	/**
	 * 查询系统树
	 * @param treeNodeId
	 * @return
	 */
	public Collection<TreeNode> getSysTree(String type,String parendId) {
		List<Record> list = null;
		String sql = "";
		if(parendId==null) {
			sql = "select * from sys_tree where type=? and parent_id is null order by order_no asc";		
			list = find(sql, type);
		}else {
			sql = "select * from sys_tree where type=? and parent_id=? order by order_no asc";		
			list = find(sql, type,parendId);
		}
		
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();

		for (Record rd : list) {
			TreeNode node = new TreeNode();
			node.setId(rd.getStr("id"));
			node.setPid(rd.getStr("parent_id"));
			node.setText(rd.getStr("name"));
			Collection<TreeNode> children = this.getSysTree(type,rd.getStr("id"));
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}

	
}
