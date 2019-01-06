package com.qinhailin.portal.echart.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Echart图表数据源对象
 * 
 * @author QinHaiLin
 * @date 2018年3月7日
 */
public class EchartData {
	/**统计图标题**/
	private String title;
	/**统计图小标题**/
	private String subtitle;	
	/**x轴名称**/
	private String xName;	
	/**y轴名称**/
	private String yName;	
	/**x轴坐标 **/
	private String[] xAxis;	
	/**y轴坐标**/
	private Double[] yAxis;
	private Map<String,Double> xyAxisMap=new HashMap<>();
	/** 用于多组数据统计**/
	private List<Map<String,Object>> series=new ArrayList<Map<String,Object>>();
	/**图例名称**/
	private String seriesName;
	//饼图图例
	private List<String> seriesNameList=new ArrayList<String>();
	/**柱状、折线数据**/
	private Double[] seriesData;
	/**饼图数据**/
	private List<Map<String,Object>> seriesPieData=new ArrayList<Map<String,Object>>();
	private List<List<Map<String,Object>>> seriesPieDataList=new ArrayList<List<Map<String,Object>>>();
	/**图表类型**/
	private String type;
	/**单位**/
	private String tooltipText;
	/**附属类**/
	private String stack;
	/**附属类子名称**/
	private String stackName;
	/**附属类子数据**/
	private Double stackData;
	/**是否坐标轴刻度与标签对齐**/
	private Boolean isAlignWithLabel;
	/**标题位置:left,center,right**/
	private String title_x;
	/**图例位置:left,center,right**/
	private String legend_x;
	/** 图例名称**/
	private String[] legend;	
	
	public EchartData() {
		super();
	}
	
	
	
	/**
	 * 构造基础参数
	 * @param title
	 * @param subtitle
	 * @param xName
	 * @param yName
	 * @param type
	 * @param tooltipText
	 * @param seriesName
	 */
	public EchartData(String title, String subtitle, String xName, String yName, String type, String tooltipText,String seriesName) {
		super();
		this.title = title;
		this.subtitle = subtitle;
		this.xName = xName;
		this.yName = yName;
		this.type = type;
		this.tooltipText = tooltipText;
		this.seriesName=seriesName;
	}



	public String getTitle() {
		return title;
	}
	
	/**统计图标题**/
	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	/**统计图副标题**/
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getxName() {
		return xName;
	}

	/**x轴名称**/
	public void setxName(String xName) {
		this.xName = xName;
	}

	public String getyName() {
		return yName;
	}

	/**y轴名称**/
	public void setyName(String yName) {
		this.yName = yName;
	}

	public String[] getxAxis() {
		return xAxis;
	}

	/**x轴坐标**/
	public void setxAxis(String[] xAxis) {
		this.xAxis = xAxis;
	}

	public Double[] getyAxis() {
		return yAxis;
	}

	/**y轴坐标**/
	public void setyAxis(Double[] yAxis) {
		this.yAxis = yAxis;
	}

	public Map<String, Double> getXyAxisMap() {
		return xyAxisMap;
	}

	public void setXyAxisMap(Map<String, Double> xyAxisMap) {
		this.xyAxisMap = xyAxisMap;
	}

	public List<Map<String,Object>> getSeries() {
		return series;
	}

	/**多组统计数据**/
	public void setSeries(List<Map<String,Object>> series) {
		this.series = series;
	}

	public String getSeriesName() {
		return seriesName;
	}

	/**图例名称**/
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
	public List<String> getSeriesNameList() {
		return seriesNameList;
	}
	
	/**图例名称**/
	public void setSeriesNameList(List<String> seriesNameList) {
		this.seriesNameList = seriesNameList;
	}
	
	public Double[] getSeriesData() {
		return seriesData;
	}

	/**柱状、折线数据**/
	public void setSeriesData(Double[] seriesData) {
		this.seriesData = seriesData;
	}

	public List<Map<String,Object>> getSeriesPieData() {
		return seriesPieData;
	}

	/**饼图数据**/
	public void setSeriesPieData(List<Map<String,Object>> seriesPieData) {
		this.seriesPieData = seriesPieData;
	}
	
	public List<List<Map<String,Object>>> getSeriesPieDataList(){
		return seriesPieDataList;
	}
	
	public void setSeriesPieDataList(List<List<Map<String,Object>>> seriesPieDataList) {
		this.seriesPieDataList=seriesPieDataList;
	}

	public String getType() {
		return type;
	}

	/**图标类型**/
	public void setType(String type) {
		this.type = type;
	}

	public String getTooltipText() {
		return tooltipText;
	}

	/**单位**/
	public void setTooltipText(String tooltipText) {
		this.tooltipText = tooltipText;
	}

	public String getStack() {
		return stack;
	}

	/**附属类**/
	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getStackName() {
		return stackName;
	}

	/**附属子类名称**/
	public void setStackName(String stackName) {
		this.stackName = stackName;
	}

	public Double getStackData() {
		return stackData;
	}

	/**附属子类数据**/
	public void setStackData(Double stackData) {
		this.stackData = stackData;
	}

	public Boolean getIsAlignWithLabel() {
		return isAlignWithLabel;
	}

	/**是否坐标轴刻度与标签对齐**/
	public void setIsAlignWithLabel(Boolean isAlignWithLabel) {
		this.isAlignWithLabel = isAlignWithLabel;
	}

	public String getTitle_x() {
		return title_x;
	}

	/**标题位置:left,center,right**/
	public void setTitle_x(String title_x) {
		this.title_x = title_x;
	}

	public String getLegend_x() {
		return legend_x;
	}

	/**图例位置:left,center,right**/
	public void setLegend_x(String legend_x) {
		this.legend_x = legend_x;
	}

	public String[] getLegend() {
		return legend;
	}

	public void setLegend(String[] legend) {
		this.legend = legend;
	}

	/**
	 * x轴,y轴map对象
	 * @param xAixs
	 * @param yAxis
	 * @return
	 * @author QinHaiLin
	 * @date 2018年12月4日
	 */
	public Map<String,Double> putXyAxisMap(String xAixs,Double yAxis){
		xyAxisMap.put(xAixs, yAxis);
		return xyAxisMap;
	}

	/**
	 * 构造柱形图、曲线图数据格式<br/>
	 * {
			name: '销量',
			type: 'bar',
			data: [15,36,51,62,75,110]
		}
	 * @return
	 * @author QinHaiLin
	 * @date 2018年3月10日
	 */
	public Map<String,Object> createSeries() {
		Map<String,Object> map=new HashMap<>();
		map.put("name", seriesName);
		map.put("type", type);
		map.put("data", seriesData);
		series.add(map);
		return map;	
	}
	
	/**
	 * 构造饼图数据格式:<br/>
	 * [{value:10,name:'衬衫'},{value:30,name:'羊毛衫'},{value:50,name:'雪纺衫'}]
	 * @return
	 * @author QinHaiLin
	 * @date 2018年3月10日
	 */
	public List<Map<String,Object>> createSeriesPieData() {
		
		if(xAxis!=null&&seriesData!=null) {
			
			for(int i=0;i<xAxis.length;i++) {
				Map<String,Object> map=new HashMap<>();
				map.put("value", seriesData[i]);
				map.put("name", xAxis[i]);
				seriesPieData.add(map);			
			}
		}
		seriesPieDataList.add(seriesPieData);
		return seriesPieData;
	}


	@Override
	public String toString() {
		return "EchartData [title=" + title + ", subtitle=" + subtitle + ", xName=" + xName + ", yName=" + yName
				+ ", xAxis=" + Arrays.toString(xAxis) + ", yAxis=" + Arrays.toString(yAxis) + ", series=" + series
				+ ", seriesName=" + seriesName + ", seriesData=" + Arrays.toString(seriesData) + ", seriesPieData="
				+ seriesPieData + ", type=" + type + ", tooltipText=" + tooltipText + ", stack=" + stack
				+ ", stackName=" + stackName + ", stackData=" + stackData + ", isAlignWithLabel=" + isAlignWithLabel
				+ ", title_x=" + title_x + ", legend_x=" + legend_x + ", legend=" + Arrays.toString(legend) + "]";
	}

	
}
