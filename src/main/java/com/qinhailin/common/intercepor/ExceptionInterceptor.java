package com.qinhailin.common.intercepor;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.qinhailin.common.vo.Feedback;

/**
 * 异常处理
 * 
 * @author QinHaiLin
 * @date 2018-07-26
 */
public class ExceptionInterceptor implements Interceptor {
	private static final Logger LOG = Logger.getLogger(ExceptionInterceptor.class);
	private static final String MSG="msg";//提示信息
	private static final String E="e";//警告信息
	

	@Override
	public void intercept(Invocation inv) {
		Controller ctrl = inv.getController();
		String methodNAme=inv.getMethodName();
		
		try {
			inv.invoke();
			if(methodNAme.equals("save")){
				ctrl.setAttr(MSG, "数据保存成功");
			}else if(methodNAme.equals("update")){
				ctrl.setAttr(MSG, "数据修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage(), e);
			boolean b="XMLHttpRequest".equalsIgnoreCase(ctrl.getHeader("X-Requested-With"));
			if (b) {
				ctrl.renderJson(Feedback.error(e.getMessage()==null?"系统异常":e.getMessage()));
			} else  if(ctrl.getResponse().getStatus()==403){
				ctrl.setAttr(E, e);
				ctrl.renderError(403);
			}else{
				ctrl.getResponse().setStatus(500);
				ctrl.setAttr(E, e);
				ctrl.renderError(500);
			}
		}
	}

}
