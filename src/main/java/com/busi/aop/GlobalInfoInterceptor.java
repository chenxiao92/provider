package com.busi.aop;

import com.busi.constant.Constants;
import com.busi.data.Response;
import com.busi.mpsp.MpspLog;
import com.busi.util.ObjectToMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/** 
 * @Description：全局处理请求前和响应后的处理
 * <p>创建日期：2018年6月26日 </p>
 * @version V1.0  
 * @author junjie.cao
 * @see
 */
public class GlobalInfoInterceptor extends HandlerInterceptorAdapter{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static String  START_BOUNDARY = "===================Business processing start===================";
	private final static String  END_BOUNDARY = "====================Business processing end====================";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info(START_BOUNDARY);
		return super.preHandle(request, response, handler);
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long starTime = Long.valueOf((String) MDC.get(Constants.SYS_STARTTIME));
		long endTime = System.currentTimeMillis();
		Object responseData = request.getAttribute(Constants.RESDATA);
		if(responseData instanceof Response){
			Map resMap = ObjectToMap.toMap(responseData);
			MpspLog.logMPSP(resMap, request.getServletPath(),endTime - starTime);
		}
		if(ex != null){
			logger.error("处理异常", ex);
		}
		MDC.remove(Constants.SYS_RPID);
		super.afterCompletion(request, response, handler, ex);
		logger.info(END_BOUNDARY);
	}

}
