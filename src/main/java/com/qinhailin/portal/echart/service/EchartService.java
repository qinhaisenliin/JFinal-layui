/**
* @author QinHaiLin
* @date 2018年11月21日
*/
package com.qinhailin.portal.echart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.portal.echart.vo.Chart;
import com.qinhailin.portal.echart.vo.EchartData;

/**
 * Echart图表统计基础接口
 * @author QinHaiLin
 * @date 2018年11月21日  
 */
public class EchartService {

	public static final EchartService me=new EchartService();
	
	/**
	 * 封装Echart数据
	 * @param data
	 * @return
	 * @author QinHaiLin
	 * @date 2018年11月28日
	 */
	public EchartData getEchartData(Chart data){
		EchartData echartData=new EchartData(data.getTitle(), data.getSubtitle(), data.getxName(), data.getyName(), data.getChartType(), data.getTooltipText(),data.getSeriesName());		
		List<Object> xAxis=new ArrayList<>();
		List<Double> yAxis=new ArrayList<>();
		
		if(data.getSql()!=null) {				
			//查询统计数据
			List<Record> queryList=Db.find(data.getSql(), data.getParams());							
			for(Record map:queryList) {				
				xAxis.add(map.get(data.getxAxis()));
				yAxis.add(map.getDouble(data.getyAxis()));
				echartData.putXyAxisMap(map.get(data.getxAxis()), map.getDouble(data.getyAxis()));
			}						
		}
		
		if(yAxis.size()==0) {
			xAxis.add(data.getxName());
			yAxis.add(0.0);
			echartData.putXyAxisMap(data.getxName(),0.0);
		}
		
		echartData.setxAxis(xAxis.toArray(new String[xAxis.size()]));
		echartData.setSeriesData(yAxis.toArray(new Double[yAxis.size()]));
		
		//柱状,折现图数据
		List<Map<String,Object>> seriesList=new ArrayList<>();
		seriesList.add(echartData.createSeries());
		echartData.setSeries(seriesList);			
		//饼图数据
		echartData.createSeriesPieData();
		
		return echartData;
	}
}
