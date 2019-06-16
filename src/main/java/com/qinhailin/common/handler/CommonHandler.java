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

package com.qinhailin.common.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * 全局路由处理
 * 
 * @author qinhailin
 *
 */
public class CommonHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {		

		if (target.startsWith("/portal/download/")) {
			
			// 下载上传的文件
			request.setAttribute("url", target.substring(17));
			target = target.substring(0, 16);
		} else if (target.startsWith("/portal/delete/")) {
			
			// 删除上传文件
			request.setAttribute("url", target.substring(15));
			target = target.substring(0, 14);
		} else if (target.startsWith("/portal/go/")) {
			// 公共/portal/go路由
			request.setAttribute("view", target.substring(11));
			target = target.substring(0, 10);
		} else if (target.startsWith("/portal/temp/")) {	
			
			// 下载模板文件
			try {
				request.setAttribute("url", URLDecoder.decode(target.substring(12), "UTF-8"));
				System.out.println(URLDecoder.decode(target.substring(12), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			target = target.substring(0, 12);
		}else if(target.startsWith("/portal/view/")){
			
			// 图片预览
			request.setAttribute("url", target.substring(13));
			target = target.substring(0, 13);
		}
		
		next.handle(target, request, response, isHandled);
	}

}
