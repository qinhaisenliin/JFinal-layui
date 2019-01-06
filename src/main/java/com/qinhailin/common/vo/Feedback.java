package com.qinhailin.common.vo;

import java.util.Map;

/**
 * 返回json数据信息
 * "{\"code\":\"error\",\"success\":true,\"error\":false,\"msg\":\"\"}";
 *
 * @author qinhailin
 *
 */
public class Feedback {
	public static final String KEY = "feedback";
	public static final String MSG_UPDATE_SUCCESS = "数据保存成功.";
	public static final String MSG_UPDATE_ERROR = "修改发生错误.";
	public static final String MSG_SAVE_SUCCESS = "数据提交完成.";
	public static final String MSG_SAVE_ERROR = "保存发生错误.";
	public static final String MSG_DEL_ERROR = "数据已删除.";
	private Object vo = null;
	String code;
	boolean success;
	boolean error;
	String msg;
	private int systemSwitch;
	private Map<String, Boolean> funcMap;
	private Throwable throwable;
	private String requestUrl;
	private static Feedback successFeedback = new Feedback(true, "");
	private static Feedback saveSuccessFeedback = new Feedback(true, MSG_SAVE_SUCCESS);
//	private static Feedback saveErrorFeedback = new Feedback(false, MSG_SAVE_ERROR);
	private static Feedback updateSuccessFeedback = new Feedback(true,
			MSG_UPDATE_SUCCESS);
//	private static Feedback updateErrorFeedback = new Feedback(false, MSG_UPDATE_ERROR);

	public static Feedback success() {
		return successFeedback;
	}

	public static Feedback success(String msg) {
		Feedback successFeedback = new Feedback(true, msg);
		return successFeedback;
	}


	public static Feedback saveSuccess() {
		return saveSuccessFeedback;
	}


	public static Feedback updateSuccess() {
		return updateSuccessFeedback;
	}

	public static Feedback error(String msg) {
		Feedback jv = new Feedback();
		jv.setCode("error");
		jv.setSuccess(false);
		jv.setError(true);
		jv.setMsg(msg);
		return jv;
	}

	public static Feedback error(Throwable throwable) {
		Feedback jv = new Feedback();
		jv.setCode("error");
		jv.setSuccess(false);
		jv.setError(true);
		jv.setThrowable(throwable);
		return jv;
	}

	public Feedback() {
		super();
	}

	public Feedback(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
		if (success) {
			this.code = "success";
		} else {
			this.code = "error";
			error = true;
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsg() {
		if (msg != null) {
			return msg;
		}
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getVo() {
		return vo;
	}

	public void setVo(Object vo) {
		this.vo = vo;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public Map<String, Boolean> getFuncMap() {
		return funcMap;
	}

	public void setFuncMap(Map<String, Boolean> funcMap) {
		this.funcMap = funcMap;
	}

	public int getSystemSwitch() {
		return systemSwitch;
	}

	public void setSystemSwitch(int systemSwitch) {
		this.systemSwitch = systemSwitch;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

}
