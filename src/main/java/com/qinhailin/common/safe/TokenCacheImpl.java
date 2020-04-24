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

import java.util.ArrayList;
import java.util.List;

import com.jfinal.token.ITokenCache;
import com.jfinal.token.Token;

/**
 * token缓存实现接口
 * @author qinhailin
 * @date 2020-04-24
 */
public class TokenCacheImpl implements ITokenCache{

	private List<Token> tokenList=new ArrayList<>();
	
	@Override
	public void put(Token token) {
		tokenList.add(token);	
	}

	@Override
	public void remove(Token token) {
		tokenList.remove(token);
	}

	@Override
	public boolean contains(Token token) {
		return tokenList.contains(token);
	}

	@Override
	public List<Token> getAll() {
		return tokenList;
	}

}
