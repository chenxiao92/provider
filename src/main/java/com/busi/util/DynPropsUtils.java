package com.busi.util;

import org.springframework.context.MessageSource;


public class DynPropsUtils {

	private static MessageSource messageSource = (MessageSource) SpringUtils.getApplicationContext().getBean("myMessageSource");

	/**
	 * @Description：无占位符
	 * <p>创建人：junjie.cao,  2020/5/28  11:39</p>
	 * <p>修改人：junjie.cao,  2020/5/28  11:39</p>
	 *
	 * @param key

	 * @return java.lang.String
	 */
	public static String get(String key){
		return get(key,null,null);
	}
	/**
	 * @Description：无占位符，有默认值
	 * <p>创建人：junjie.cao,  2020/5/28  11:39</p>
	 * <p>修改人：junjie.cao,  2020/5/28  11:39</p>
	 *
	 * @param key
	 * @param defaultMessage
	 * @return java.lang.String
	 */
	public static String get(String key,String defaultMessage){
		return get(key,null,defaultMessage);
	}
	/**
	 * @Description：有占位符，无默认值
	 * <p>创建人：junjie.cao,  2020/5/28  11:38</p>
	 * <p>修改人：junjie.cao,  2020/5/28  11:38</p>
	 *
	 * @param key
	 * @param args
	 * @return java.lang.String
	 */
	public static String get(String key,Object[] args){
		return get(key,args,null);
	}
	public static String get(String key,Object[] args,String defaultMessage){
		return key.equals(messageSource.getMessage(key,args,null)) ? defaultMessage: messageSource.getMessage(key,args,null);
	}
}
