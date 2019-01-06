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
		
		SysLog sysLog=new SysLog(IdKit.createUUID(),actionKey,new Date(),controller.getParaMap().toString(),ip);
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
					}
					sysLog.save();
				}		
			}
		}		
	}

}
