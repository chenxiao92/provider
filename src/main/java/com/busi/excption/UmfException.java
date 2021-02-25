package com.busi.excption;


import com.busi.message.RetMsg;

/**
 * @Description：自定义异常
 * <p>创建日期：2018/11/12 </p>
 * @version V1.0
 * @author junjie.cao
 * @see
 */
public class UmfException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	
	public UmfException(String code) {
		this.code = code;
		this.msg = RetMsg.getMsg(code);
	}

	public UmfException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
