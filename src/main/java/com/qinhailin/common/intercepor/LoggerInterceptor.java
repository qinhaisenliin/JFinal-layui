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

import java.net.SocketException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.qinhailin.common.kit.IdKit;
import com.qinhailin.common.kit.IpKit;
import com.qinhailin.common.model.SysLog;
import com.qinhailin.common.visit.Visitor;
import com.qinhailin.common.visit.VisitorUtil;
import com.qinhailin.portal.core.service.SysLogService;

/**
 * 系统日志
 * @author QinHaiLin
 *
 */
public class LoggerInterceptor implements Interceptor {
	@Inject
	SysLogService sysLogService;
	
	@Override
	public void intercept(Invocation inv) {
		inv.invoke();
		Controller controller = inv.getController();
		String actionKey = inv.getActionKey();
		String methodName=inv.getMethodName();
		HttpSession session = controller.getSession();
		Visitor vs = VisitorUtil.getVisitor(session);
		
		String ip=IpKit.getRealIp(controller.getRequest());
		//本机ip
		if("0:0:0:0:0:0:0:1".equals(ip)){
			try {
				ip=IpKit.getLocalIp();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		String data=JsonKit.toJson(controller.getParaMap()).toString();
		SysLog sysLog=new SysLog(IdKit.createUUID(),actionKey,new Date(),data,ip);
		if(vs!=null){
			sysLog.setUserCode(vs.getCode()+"("+vs.getName()+")");							
			Map<String,String> funcMap=sysLogService.getFuncMapForLog();
			String actionName=funcMap.get(actionKey);
			if(actionName!=null){		
				sysLog.setMethodName(actionName);
				sysLog.setRemark("操作日志("+funcMap.get(actionKey+actionName)+")");
				sysLog.save();
			}else{
				String method="save.*|update.*|delete.*|submit";
				Pattern pattern=Pattern.compile(method);
				boolean b=pattern.matcher(methodName).matches();
				if(b){	
					String name=methodName.contains("save")?"添加":methodName.contains("update")?"修改":methodName.contains("submit")?"登录":"删除";
					sysLog.setMethodName(name);
					sysLog.setRemark("数据日志");
					if(name.equals("登录")){
						sysLog.setRemark("登录日志");
						sysLog.setData("{}");
					}
					sysLog.save();
				}		
			}
		}		
	}

}
