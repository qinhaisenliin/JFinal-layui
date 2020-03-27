/**
 * Copyright 2019-2020 覃海林(qinhaisenlin@163.com).
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

import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.qinhailin.common.safe.TokenService;

/**
 * token拦截器
 * @author QinHaiLin
 * @date 2020-02-13
 */
public class TokenInterceptor implements Interceptor {

	@Inject
	TokenService tokenService;
	
	@Override
	public void intercept(Invocation inv) {
		Controller c=inv.getController();
		String methName=inv.getMethod().getName();
		
		//给默认的添加、修改方法添加token
		if(methName.equals("add")||methName.equals("edit")){
			tokenService.createToken(c);		
		}
		
		//验证token
		if(methName.equals("save")||methName.equals("update")){
			boolean b=tokenService.validateToken(c);
			if(!b){
				boolean isAjax="XMLHttpRequest".equalsIgnoreCase(c.getHeader("X-Requested-With"));
				if(isAjax){
					c.renderJson(Ret.fail("msg", "token验证不通过,请刷新页面"));
				}else{
					c.setAttr("msg", "token验证不通过");
					c.renderError(403);
				}
				return;
			}
			//添加修改成功后，返回对的页面，此次是解决业务验证不通过的情况，如：添加用户时，如果存在用户编号，那么需要重新填写，此时就要重新赋值新的token
			tokenService.createToken(c);
		}
		inv.invoke();
	}

}
