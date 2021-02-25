package com.busi.message;

import com.busi.util.SpringUtils;
import org.springframework.context.MessageSource;

public class RetMsg {

	private static MessageSource messageSource = (MessageSource) SpringUtils.getApplicationContext().getBean("myMessageSource");

	public static String getMsg(String code){
		return code.equals(messageSource.getMessage(code,null,null)) ? "请求失败" : messageSource.getMessage(code,null,null);
	}
	public static String getMsg(String code,Object[] args){
		return code.equals(messageSource.getMessage(code,args,null)) ? "请求失败" : messageSource.getMessage(code,null,null);
	}

}