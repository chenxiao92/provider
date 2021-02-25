package com.busi.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * 指定自定义过滤器执行顺序配置类
 * <p>创建日期：2019年8月23日 </p>
 * @version V1.0
 * @author zhengdaqing
 */
@Configuration
public class FilterOrderConfig {
	/**
	 * 打印请求相关信息
	 * @return
	 * Filter 
	 */
	@Bean
	public Filter requestFilter(){
		return new RequestFilter();
	}
	/**
	 * 注册uriFilter过滤器，并设置优先级
	 * @return
	 * FilterRegistrationBean 
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean1(){
		FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
		filterRegistrationBean.setFilter(requestFilter());
		//order的数值越小 则优先级越高
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}
}
