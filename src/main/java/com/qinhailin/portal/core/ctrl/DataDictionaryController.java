package com.qinhailin.portal.core.ctrl;

import java.util.ArrayList;
import java.util.Collection;

import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.model.DataDictionary;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.TreeNode;
import com.qinhailin.portal.core.service.DataDictionaryService;
import com.qinhailin.portal.core.service.DataDictionaryValueService;

/**
 * 数据字典
 * @author QinHaiLin
 */
@ControllerBind(path="/portal/core/dictionary")
public class DataDictionaryController extends BaseController {

	@Inject
	DataDictionaryService service;
	@Inject
	DataDictionaryValueService dataDictionaryValueService;
	
	public void index(){
		render("index.html");
	}
		
	public void add(){
		render("add.html");
	}
	
	public void save(DataDictionary dataDictionary){
		if(service.isExit("code", dataDictionary.getCode())){
			setException("字典编号已存在,请重新输入");
			setAttr("dataDictionary",dataDictionary);
		}else{
			dataDictionary.setId(createUUID()).save();		
		}
		render("add.html");
	}
	
	public void edit(){
		setAttr("dataDictionary",service.findByPk("code",getPara()));
		render("edit.html");
	}
	
	@Before(EvictInterceptor.class)
	@CacheName("dictionary")
	public void update(){
		getBean(DataDictionary.class).update();
		setAttr("dataDictionary",getBean(DataDictionary.class));
		render("edit.html");
	}
	
	@Before(EvictInterceptor.class)
	@CacheName("dictionary")
	public void delete(){
		service.deleteByPk(getIds(),"code");
		dataDictionaryValueService.deleteByPk(getIds(), "dictionary_code");
		renderJson(suc());
	}
	
	public void tree(){
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		node.setId("frame");
		node.setText("业务字典");
		node.setChildren(service.getDictionaryTree());
		nodes.add(node);
		renderJson(nodes);
	}
}
