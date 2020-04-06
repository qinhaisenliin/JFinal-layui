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
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.jsoup.helper.StringUtil;

import com.jfinal.handler.Handler;
import com.qinhailin.common.safe.XssHttpServletRequestWrapper;

/**
 * xss拦截器
 * @author QinHaiLin
 *
 */
public class XssHandler extends Handler {


	// 排除的url，使用的target.startsWith匹配的
   private String excludePattern;
    
   /**
    * 忽略列表，使用正则排除url
    * @param exclude
    */
   public XssHandler(String excludePattern) {
       this.excludePattern = excludePattern;
   }

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

		java.util.regex.Pattern pattern = Pattern.compile(excludePattern);
		//带.表示非action请求，忽略（其实不太严谨，如果是伪静态，比如.html会被错误地排除）；匹配excludePattern的，忽略
       if (target.indexOf(".") == -1 && !(!StringUtil.isBlank(excludePattern) && pattern.matcher(target).find() ) ){
           request = new XssHttpServletRequestWrapper(request);
       }
       
       next.handle(target, request, response, isHandled);
       
	}

}
