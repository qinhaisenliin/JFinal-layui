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

package com.qinhailin.pub.login.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.aop.Inject;
import com.qinhailin.pub.login.exception.LoginException;
import com.qinhailin.common.entity.impl.LoginUserImpl;
import com.qinhailin.common.kit.IpKit;
import com.qinhailin.common.kit.Md5Kit;
import com.qinhailin.common.model.SysUser;
import com.qinhailin.common.visit.Visitor;
import com.qinhailin.common.visit.VisitorUtil;
import com.qinhailin.common.visit.impl.VisitorImpl;
import com.qinhailin.common.config.WebContant;
import com.qinhailin.portal.core.service.SysUserService;

/**
 * @author QinHaiLin
 *
 */
public class LoginService {

	@Inject
	SysUserService sysUserService;

	/**
	 * 登陆验证
	 * 
	 * @param userCode
	 * @param password
	 * @param req
	 * @return
	 * @throws LoginException
	 * @author QinHaiLin
	 * @date 2018年10月15日
	 */
	public Visitor aopLogin(String userCode, String password, HttpServletRequest req) throws LoginException {
		//验证数据
		if ((userCode == null) || (userCode.trim().length() == 0) || (password == null)
				|| (password.trim().length() == 0)) {
			throw new LoginException("请输入用户名和密码");
		}
		//验证用户
		SysUser user = sysUserService.findByUserCode(userCode.toLowerCase());
		if (user == null) {
			throw new LoginException("用户不存在");
		}		
		//验证权限
		if ((user.getAllowLogin() != null) && (user.getAllowLogin() != WebContant.allowLogin)) {
			throw new LoginException("没有登录权限，请联系管理员");
		}		
		//验证码
		checkVerifyCode(user, req);		
		// 验证密码
		if (user.getPasswd().equals(Md5Kit.md5(password))) {
			return returnVistor(user, req);
		}
		//验证失败
		loginFail(user);
			
		return null;
	}

	/**
	 * 检查验证码
	 * 
	 * @param user
	 * @param req
	 * @throws LoginException
	 * @author QinHaiLin
	 * @date 2018年10月22日
	 */
	private void checkVerifyCode(SysUser user, HttpServletRequest req) throws LoginException {
		Date allowLogTime = user.getAllowLoginTime();
		if (allowLogTime != null) {
			Date nowDate = new Date();
			Calendar cal = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal.setTime(allowLogTime);
			cal2.setTime(nowDate);
			float f = cal.getTimeInMillis() - cal2.getTimeInMillis();
			// 验证码验证
			if (f > 0) {
				user.setFailureNumber(0);
				sysUserService.update(user);
				String verifyCode = req.getParameter("verifyCode");
				String code = (String) req.getSession()
						.getAttribute("verifyCode");
				// 更换验证码！防止暴力破解
				Random random = new Random();
				String sRand = "";
				for (int i = 0; i < 4; i++) {
					String rand = String.valueOf(random.nextInt(10));
					sRand += rand;
				}
				req.getSession().setAttribute("verifyCode", sRand);
				if ((verifyCode == null) || !verifyCode.equalsIgnoreCase(code)) {
					throw new LoginException("验证码错误，请重新输入");
				}
			}
		}
	}

	/**
	 * 返回登录者信息
	 * 
	 * @param user
	 * @param req
	 * @return
	 * @throws LoginException
	 * @author QinHaiLin
	 * @date 2018年10月22日
	 */
	private Visitor returnVistor(SysUser user, HttpServletRequest req) throws LoginException {
		LoginUserImpl loginUser = new LoginUserImpl();
		loginUser.setId(user.getId());
		loginUser.setUserCode(user.getUserCode());
		loginUser.setUserName(user.getUserName());
		loginUser.setIp(IpKit.getRealIp(req));
		VisitorImpl vistor = new VisitorImpl(loginUser);
		
		// 权限
		Map<String, Boolean> funcMap = sysUserService.getUserFuncMap(user.getUserCode());
		vistor.setFuncMap(funcMap);

		// 记录最后一次登录时间
		user.setAllowLoginTime(new Date());
		user.setFailureNumber(0);
		sysUserService.update(user);
		VisitorUtil.setVisitor(vistor, req.getSession());
		return vistor;
	}

	/**
	 * 登陆失败
	 * 
	 * @param user
	 * @throws LoginException
	 * @author QinHaiLin
	 * @date 2018年10月22日
	 */
	private void loginFail(SysUser user) throws LoginException {
		int failureNumber = user.getFailureNumber() == null ? 0
				: user.getFailureNumber();
		user.setFailureNumber(failureNumber + 1);
		String msg = "帐号或密码错误";
//		if (failureNumber >= 2) {//错误3次数  
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			// 密码错误三次后要验证码
			cal.add(Calendar.DATE, 1);
			user.setAllowLoginTime(cal.getTime());
//			msg = "密码错误3次,请输入验证码！";
//		}
		sysUserService.update(user);
		throw new LoginException(msg);
	}
	
}
