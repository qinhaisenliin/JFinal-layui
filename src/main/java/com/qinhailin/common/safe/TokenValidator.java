package com.qinhailin.common.safe;
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
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;

/**
 * token验证
 * @author QinHaiLin
 * @date 2020-02-13
 */
public class TokenValidator extends Validator {
	@Inject
	TokenService tokenService;
	
	@Override
	protected void validate(Controller c) {
		boolean b=tokenService.validateToken(c);
		if(!b){
			boolean isAjax="XMLHttpRequest".equalsIgnoreCase(c.getHeader("X-Requested-With"));
			if(isAjax){
				c.renderJson(Ret.fail("msg", "token验证失败"));
			}else{
				c.setAttr("msg", "token验证失败");
				c.renderError(403);
			}
			return;
		}
	}

	@Override
	protected void handleError(Controller c) {
		
	}

}
