/**Controller.java代码模板*/
#sql("Controller")
#[[
package ${controllerPackage};

import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;

import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.routes.ControllerBind;
import ${importModel};
import ${servicePackage}.${modelName}Service;

/**
 * ${tableComment}
 * @author ${author}
 * @date ${date}
 */
@ControllerBind(path="/portal${actionKey}")
public class ${modelName}Controller extends BaseController {

   @Inject
   ${modelName}Service service;

  	public void index(){
    	render("index.html");
  	}
    
   public void list() {
      Record record=getAllParamsToRecord();
      renderJson(service.page(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10),record));
	}
    
   public void add() {
    	render("add.html");
   }

   public void save() {
    	${modelName} entity=getBean(${modelName}.class);
    	entity.setId(createUUID()).save();
    	setAttr("${lowercaseModelName}", entity);
    	render("add.html");
   }

   public void edit() {
      setAttr("${lowercaseModelName}", service.findById(getPara(0)));
      render("edit.html");
   }

   public void update() {
      ${modelName} entity=getBean(${modelName}.class);
      entity.update();
      setAttr("${lowercaseModelName}", entity);
      render("edit.html");
   }

   public void delete() {
      service.deleteByIds(getIds());
      renderJson(suc());
   }

}
]]#
#end