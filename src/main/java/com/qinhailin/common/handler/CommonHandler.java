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
			// 公共/pub/go路由
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
		}
		
		next.handle(target, request, response, isHandled);
	}

}
