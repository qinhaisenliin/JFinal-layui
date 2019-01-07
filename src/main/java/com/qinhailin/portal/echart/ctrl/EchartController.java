/**
* @author QinHaiLin
* @date 2018年11月28日
*/
package com.qinhailin.portal.echart.ctrl;

import com.jfinal.core.Controller;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.portal.echart.service.EchartService;
import com.qinhailin.portal.echart.vo.Chart;

/**
 * Echart图表统计
 * @author QinHaiLin
 * @date 2018年11月28日  
 */
@ControllerBind(path="/portal/echart")
public class EchartController extends Controller {

	private EchartService service=EchartService.me;
	
	/**
	 * echart图表统计实例
	 */
	public void index() {
		render("index.html");
	}
	
	/**
	 * 图表统计业务数据接口
	 */
	public void queryData() {
		//TODO 根据具体业务给下面的变量赋值即可
		String title="";
		String subtitle="";
		String xAxis="";		//sql中的字段名
		String yAxis="";		//sql中的字段名
		String chartType="";	//bar,line,pie
		String xName="";		
		String yName="";		
		String seriesName="";
		String tooltipText="";
		String sql="";			//统计数据的sql
		Object[] params=new Object[0];
		Chart data =new Chart(title, subtitle, xAxis, yAxis, chartType, xName, yName, seriesName, tooltipText, sql, params);
		renderJson(service.getEchartData(data));
	}
	
}
