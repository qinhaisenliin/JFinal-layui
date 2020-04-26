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
package com.qinhailin.common.safe;

import com.jfinal.aop.Inject;
import com.jfinal.core.Const;
import com.jfinal.core.Controller;
import com.jfinal.token.TokenManager;

/**
 * token
 * @author QinHaiLin
 * @date 2020-02-14
 */
public class TokenService {
	private static boolean flag=false;

	@Inject
	private TokenCacheImpl tokenCache;
	
	/**
	 * 创建token
	 * @param c
	 */
	public void createToken(Controller c){
		if(!flag){
			TokenManager.init(tokenCache);
			flag=true;
		}
		TokenManager.createToken(c, Const.DEFAULT_TOKEN_NAME, Const.DEFAULT_SECONDS_OF_TOKEN_TIME_OUT);
	}
	
	/**
	 * 验证token
	 * @param c
	 */
	public boolean validateToken(Controller c){
		return TokenManager.validateToken(c, Const.DEFAULT_TOKEN_NAME);
	}
}
