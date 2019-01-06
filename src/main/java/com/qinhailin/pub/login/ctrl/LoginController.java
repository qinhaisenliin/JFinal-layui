/**
 * @author QinHaiLin
 * @date 2018年10月15日
 */
package com.qinhailin.pub.login.ctrl;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.kit.RSAKit;
import com.qinhailin.common.model.SysUser;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.portal.core.service.SysUserService;
import com.jfinal.aop.Inject;
import com.qinhailin.pub.login.service.LoginService;

/**
 * 登陆
 * 
 * @author QinHaiLin
 * @date 2018-10-15
 */
@ControllerBind(path="/pub/login")
public class LoginController extends BaseController {

	@Inject
	LoginService loginService;
	@Inject
	SysUserService sysUserService;

	public void index() {
		try {
			setAttr("returnUrl",URLEncoder.encode(getPara("returnUrl",""),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		render("login.html");
	}

	/**
	 * 登录认证
	 */
	public void submit() {
		String userCode = getPara("userCode");
		String password = getPara("password");
		String returnUrl=getPara("returnUrl");

		//匹配中文
		Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]");
		Matcher matcher=pattern.matcher(returnUrl);		
		//中文转码
		while(matcher.find()){
			try {
				returnUrl=returnUrl.replace(matcher.group(), URLEncoder.encode(matcher.group(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		try {
			password = RSAKit.decryptionToString(password);
			loginService.aopLogin(userCode, password, getRequest());
			redirect(!returnUrl.equals("")?returnUrl:"/");
		} catch (Exception e) {
			handerException(e);
			SysUser user = sysUserService.findByUserCode(userCode);
			if (user != null) {
				isShowVerifyCode(user);
			}
			setAttr("returnUrl", returnUrl);
			setAttr("msg", e.getCause().getMessage());
			render("login.html");
		} 

	}
	
	/**
	 * 显示验证码
	 * @param user
	 */
	private void isShowVerifyCode(SysUser user){
		Date allowLogTime = user.getAllowLoginTime();
		if (allowLogTime != null) {
			Date nowDate = new Date();
			Calendar cal = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal.setTime(allowLogTime);
			cal2.setTime(nowDate);
			float f = cal.getTimeInMillis() - cal2.getTimeInMillis();
			if (f > 0) {
				setAttr("vc", "verifyCode");
			}
		}
	}

}
