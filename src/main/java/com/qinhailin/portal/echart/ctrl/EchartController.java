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

package com.qinhailin.portal.echart.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.portal.echart.service.EchartService;
import com.qinhailin.portal.echart.vo.Chart;
import com.qinhailin.portal.echart.vo.EchartData;

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
		List<EchartData> list=new ArrayList<>();
		EchartData echartDataResult =new EchartData();
		List<Map<String,Object>> series=new ArrayList<>();
		//TODO 根据具体业务给下面的变量赋值即可
		String[] legend=new String[]{"男生","女生"};//统计实例
		String title="ECharts入门示例";
		String subtitle="部门人数统计";
		String xAxis="x";		//sql中的字段名
		String yAxis="y";		//sql中的字段名
		String chartType="bar";	//bar,line,pie
		String xName="部门";		
		String yName="人数（人）";		
		String tooltipText="";
		
		for(int i=0;i<2;i++){
			String seriesName= legend[i];
			String sql="select b.org_name x ,count(*) y from sys_user a,sys_org b where a.org_id=b.id and a.sex=? group by b.org_name";//统计部门人数sql
			Object[] params=new Object[]{i};
			Chart data =new Chart(title, subtitle, xAxis, yAxis, chartType, xName, yName, seriesName, tooltipText, sql, params);
			echartDataResult=service.getEchartData(data);
			series.add(echartDataResult.getSeries().get(0));
		}

		echartDataResult.setLegend_x("left");
		echartDataResult.setLegend_y("top");
		echartDataResult.setLegend(legend);
		echartDataResult.setSeries(series);
		list.add(echartDataResult);
		renderJson(list);
	}
	
}
