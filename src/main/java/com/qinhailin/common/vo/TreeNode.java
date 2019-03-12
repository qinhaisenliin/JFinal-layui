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

package com.qinhailin.common.vo;

import java.util.Collection;

import com.jfinal.core.JFinal;

public class TreeNode {
	private String id;
	private String text;
	private String icon;
	private String url;
	private Integer levelNo;// 是否菜单
	private String FontIcon;// 字体图标
	private Collection<TreeNode> children;
	private String pid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public String getHref() {
		return JFinal.me().getContextPath() + url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Collection<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Collection<TreeNode> children) {
		this.children = children;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getLevelNo() {
		return levelNo;
	}

	public String getFontIcon() {
		return FontIcon;
	}

	public void setLevelNo(Integer levelNo) {
		this.levelNo = levelNo;
	}

	public void setFontIcon(String fontIcon) {
		FontIcon = fontIcon;
	}

}
