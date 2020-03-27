/**service.java代码模板*/
#sql("Service")
#[[
package ${servicePackage};

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import ${importModel};
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.vo.Grid;

/**
 * ${tableComment}
 * @author ${author}
 * @date ${date}
 */
public class ${modelName}Service extends BaseService {
	private ${modelName} dao = new ${modelName}().dao();

    @Override
   public Model<${modelName}> getDao(){
    	return dao;
   }

   public Grid page(int pageNumber, int pageSize,Record record) {
      Record rd = new Record();
      //rd.set("user_name like", record.getStr("searchName"));
      return queryForList(pageNumber, pageSize,rd);
   }
}
]]#
#end