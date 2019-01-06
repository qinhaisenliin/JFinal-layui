package com.qinhailin.common.visit;

import javax.servlet.http.HttpSession;

import com.qinhailin.common.entity.ILoginUser;

/**
 * 访问者工具
 * 
 * @author hhs
 * @date 2015年7月20日
 */
public class VisitorUtil {
	public static final String VISITOR_KEY = "visitor";
	public static final ThreadLocal<ILoginUser> LOGIN_USER = new ThreadLocal<ILoginUser>();

	/**
	 *
	 * <p>
	 * 获取线程变量：登录用户
	 * </p>
	 * 
	 * @return
	 *         <p>
	 * 		2017年9月7日
	 *         </p>
	 * @author hhs
	 */
	public static ILoginUser getLoginUser() {
		return LOGIN_USER.get();
	}

	public static void setLoginUser(ILoginUser loginUser) {
		if (LOGIN_USER.get() == null) {
			LOGIN_USER.set(loginUser);
		}
	}

	public static void setVisitor(Visitor v, HttpSession session) {
		if (getVisitor(session) != null) {
			removeVisitor(session);
		}
		session.setAttribute(VISITOR_KEY, v);
	}

	/**
	 * 获取访问者
	 * 
	 * @param session
	 * @return
	 */
	public static Visitor getVisitor(HttpSession session) {
		if (session == null) {
			return null;
		}
		Visitor v = (Visitor) session.getAttribute(VISITOR_KEY);
		return v;
	}

	public static void removeVisitor(HttpSession session) {
		if (session != null) {
			session.removeAttribute(VISITOR_KEY);
		}
	}

	/**
	 * 获取访问者的数据
	 * 
	 * @param session
	 * @return ComUser
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getVisitorUserData(HttpSession session) {
		if (session == null) {
			return null;
		}
		try {
			if (session.getAttribute(VISITOR_KEY) == null) {
				return null;
			} else {
				Visitor v = (Visitor) session.getAttribute(VISITOR_KEY);
				T t = (T) v.getUserData();
				return t;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取登录用户
	 * 
	 * @author hhs
	 * @date 2016年7月19日
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends ILoginUser> T getUser(HttpSession session) {
		if (session == null) {
			return null;
		}
		try {
			if (session.getAttribute(VISITOR_KEY) == null) {
				return null;
			} else {
				Visitor v = (Visitor) session.getAttribute(VISITOR_KEY);
				T t = (T) v.getUserData();
				return t;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
