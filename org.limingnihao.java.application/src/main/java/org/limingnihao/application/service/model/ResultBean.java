package org.limingnihao.application.service.model;

import org.limingnihao.util.GsonUtil;

public class ResultBean {

	private boolean success;
	private Integer id = 0;
	private String message;
	private String msg;

	public static ResultBean fromJson(String jsonString) {
		return GsonUtil.fromJson(jsonString, ResultBean.class);
	}

	public String toJson() {
		return GsonUtil.toJson(this);
	}

	public ResultBean() {
	}

	public ResultBean(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.msg = message;
	}

	public Integer getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public String getMsg() {
		return msg;
	}

	public boolean getSuccess() {
		return success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
		this.msg = message;
	}

	public void setMsg(String msg) {
		this.msg = msg;
		this.message = msg;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "ResultBean [success=" + success + ", id=" + id + ", message=" + message + ", msg=" + msg + "]";
	}

}
