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
