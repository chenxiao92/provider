package com.busi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * ip地址获取公共处理类
 * @author hejinyun@umpay.com
 * @version 1.0  
 */
public class RequestAddressUtil {
	
	private static Logger logger = LoggerFactory.getLogger(RequestAddressUtil.class);
	/**
	 * 未知
	 */
	private static final String UNKNOWN = "unknown";
	/**
	 * 获取远程请求客户端ip地址
	 * @param request 请求对象
	 * @return String 远程ip
	 */
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取远程请求客户端ip地址
	 * @return String 远程用户地址
	 */
	public static String getRemoteAddress() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("SESSION");
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 *  获取本地ip地址
	 * @param networkCard 网路卡
	 * @return
	 * String 本地ip
	 */
	public static String getHostAddress(String networkCard){
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()){
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkCard.endsWith(networkInterface.getName())){
					Enumeration<InetAddress> netAddress = networkInterface.getInetAddresses();
					while (netAddress.hasMoreElements()){
						InetAddress address = netAddress.nextElement();
						if (address instanceof Inet4Address){
							return address.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e){
			logger.info("#getHostAddress() 获取本地ip地址异常", e);
		}
		return null;
	}
	

	/**
	 * 获取远程URL
	 * @return
	 * String 远程url
	 */
	public static String getRequestURI() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		return request.getRequestURI();
	}
}
