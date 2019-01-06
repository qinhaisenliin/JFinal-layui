package com.qinhailin.index;

import java.util.Collection;


import com.jfinal.aop.Inject;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.kit.Md5Kit;
import com.qinhailin.common.model.SysFunction;
import com.qinhailin.common.model.SysUser;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.visit.Visitor;
import com.qinhailin.common.visit.VisitorUtil;
import com.qinhailin.common.vo.TreeNode;
import com.qinhailin.portal.core.service.SysFuncService;
import com.qinhailin.portal.core.service.SysUserService;

/**
 * 系统首页
 * 
 * @author qinhailin
 * @date 2018年7月19日
 */
@ControllerBind(path="/")
public class IndexController extends BaseController {

	@Inject
	SysFuncService sysFuncService;
	@Inject
	SysUserService sysUserService;

	/**
	 * 主页方法
	 * 
	 * @author qinhailin
	 * @date 2018年11月15日
	 */
	public void index() {
		Visitor vs = VisitorUtil.getVisitor(getSession());
		// 锁屏未解锁,刷新页面强制移除登录身份信息
		String userName = (String) getSession().getAttribute(vs.getName());

		if (userName != null) {
			getSession().removeAttribute(userName);
			VisitorUtil.removeVisitor(getSession());
			this.logout();
			return;
		}

		// 缓存
		Collection<TreeNode> funcList = CacheKit.get("userFunc", "funcList", new IDataLoader() {
			@Override
			public Object load() {
				return sysFuncService.getUserFunctionTree(vs.getCode(), "sys");
			}
		});
		
		SysFunction sf = CacheKit.get("userFunc", "frameMainView", new IDataLoader() {
			@Override
			public Object load() {
				return sysFuncService.findById("frame_main_view");
			}
		});

		if (sf != null) {
			setAttr("frameMainView", sf.getLinkPage());
		}
		
		setAttr("funcList", JsonKit.toJson(funcList));
		setAttr("vs", vs);
		
		render("index.html");
	}


	/**
	   * 退出登录
	 * 
	 * @author qinhailin
	 * @date 2018年11月15日
	 */
	public void logout() {    
		VisitorUtil.removeVisitor(getSession());
		redirect("/pub/login");
	}

	/**
	 * 锁屏
	 *
	 * @param userName
	 * @param session
	 * @return
	 * @author QinHaiLin
	 * @date 2018年3月6日下午11:48:34
	 */
	public void lock() {
		getSession().setAttribute(getPara("userName"), getPara("userName"));
		renderJson(suc());
	}

	/**
	 * 解屏
	 *
	 * @param userName
	 * @param userPasswd
	 * @param session
	 * @return
	 * @author QinHaiLin
	 * @date 2018年3月6日下午11:48:34
	 */
	public void unLock() {
		String passwd = Md5Kit.md5(getPara("password"));
		Visitor vs = VisitorUtil.getVisitor(getSession());
		SysUser user = sysUserService.findByUserCode(vs.getCode());
		if (passwd.equals(user.getPasswd())) {
			getSession().removeAttribute(getPara("userName"));
			renderJson(suc());
		} else {
			renderJson(err("密码错误，请重新输入..."));
		}
	}
}
