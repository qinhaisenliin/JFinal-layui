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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.qinhailin.common.visit.Visitor;
import com.qinhailin.common.visit.VisitorUtil;
import com.qinhailin.portal.core.service.SysRoleFuncService;

/**
 * 登陆session、权限认证
 * 
 * @author QinHaiLin
 * @date 2018-07-13
 */
public class SessionInterceptor implements Interceptor {

	@Inject
	SysRoleFuncService sysRoleFuncService;
	
	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		String actionKey = inv.getActionKey();
		HttpSession session = controller.getSession();
		Visitor vs = VisitorUtil.getVisitor(session);
		
		//需要身份认证的地址
		if (actionKey.startsWith("/portal")||actionKey.equals("/")) {
			if (vs == null) {
				//地址参数
				String para=controller.getPara()==null?"":"/"+controller.getPara();
				Map<String,String[]> paramMap=controller.getParaMap();
				boolean first=true;
				for(String key:paramMap.keySet()){
					para+=first?"?":"&";					
					para+=key+"="+paramMap.get(key)[0];
					first=false;
				}				
				//参数转码
				try {
					para=URLEncoder.encode(para, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				//部门详情
				if(actionKey.equals("/portal/core/sysOrg/detail")){
					actionKey="/portal/core/sysOrg";
				}
				//重新登录，携带重定向地址
				controller.redirect("/pub/login?returnUrl="+actionKey+para);
				return;
			}
			
			//验证用户权限
			if(sysRoleFuncService.getSysPermissions().get(actionKey)!=null
					&&sysRoleFuncService.getUserPermissions(vs.getCode()).get(actionKey)==null) {								
				controller.getResponse().setStatus(403);
				controller.renderError(403);
				return;
			}
			
		}
		
		//用于页面按钮权限控制
		controller.setAttr("vs", vs);
		inv.invoke();
	}

}
