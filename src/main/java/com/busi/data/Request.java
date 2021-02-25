package com.busi.data;


import org.slf4j.MDC;


public class Request {


	private String rpid;

	private String reqDate;

	private String reqTime;

	public String getRpid() {
		return rpid;
	}

	public void setRpid(String rpid) {
		this.rpid = rpid;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	@Override
	public String toString() {
		String sys_rpid = "";
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(sys_rpid).append("]-Request:")
				.append("rpid='").append(rpid).append("\'")
				.append(",reqDate='").append(reqDate).append("\'")
				.append(",reqTime='").append(reqTime).append("\'");
		return sb.toString() ;
	}
}
