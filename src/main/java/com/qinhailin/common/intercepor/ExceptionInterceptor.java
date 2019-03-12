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
