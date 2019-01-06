/**
 * @author qinhailin
 * @date 2018年10月15日
 */
package com.qinhailin.pub.login.ctrl;

import com.jfinal.core.Controller;
import com.qinhailin.common.kit.VerifyCodeKit;
import com.qinhailin.common.routes.ControllerBind;

/**
 * 验证码
 * 
 * @author QinHaiLin
 *
 */
@ControllerBind(path="/pub/verify")
public class VerifyController extends Controller {

	public void index() {
		VerifyCodeKit.createImage(getResponse(), 2);
		getSession().setAttribute("verifyCode", VerifyCodeKit.getVerityCode(2));
		renderNull();
	}
}
