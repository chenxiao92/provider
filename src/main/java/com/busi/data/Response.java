package com.busi.data;


import com.busi.constant.RetCode;
import com.busi.message.RetMsg;

public class Response{

	private String rpid;

	private String retCode;

	private String retMsg;

	public Response() { }

	public Response(Request request){
		this.setRequest(request);
	}

	public String getRpid() {
		return rpid;
	}

	public void setRpid(String rpid) {
		this.rpid = rpid;
	}

	public String getRetCode() {
		return retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
		setRetMsg(RetMsg.getMsg(retCode));
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	

	public void setRequest(Request request) {
		if (request != null){
			this.setRpid(request.getRpid());
		}
	}

	public Response success(){
		setRetCode(RetCode.SUCCESS);
		setRetMsg(RetMsg.getMsg(RetCode.SUCCESS));
		return this;
	}

	public Response error(){
    	this.setRetCode(RetCode.FAIL);
    	this.setRetMsg(RetMsg.getMsg(RetCode.FAIL));
    	return this;
	}

	public Response error(String retCode){
    	this.setRetCode(retCode);
    	this.setRetMsg(RetMsg.getMsg(retCode));
    	return this;
	}

	public Response error(String retCode,String msg){
		this.setRetCode(retCode);
		this.setRetMsg(msg);
		return this;
	}

	@Override
	public String toString() {
		return "Response: " +
				"rpid='" + rpid + '\'' +
				", retCode='" + retCode + '\'' +
				", retMsg='" + retMsg + '\'';
	}
}
