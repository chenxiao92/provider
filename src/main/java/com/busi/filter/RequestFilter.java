package com.busi.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.busi.constant.Constants;
import com.busi.data.BodyReaderHttpServletRequestWrapper;
import com.busi.data.BodyWriterHttpServletResposeWrapper;
import com.busi.mpsp.FilterFields;
import com.busi.util.DynPropsUtils;
import com.busi.util.KeyWorker;
import com.busi.util.RequestAddressUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述说明:
 *
 * @author daqing.zheng
 * @version 1.0
 * @className RequestFilter
 * @date 2019/8/23 11:23
 */
public class RequestFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String  REQUESTURI = "Request url:";
    private final static String  REOMOTEIP = "Remote IP:";
    private final static String  CONTENTTYPE = "ContentType:";
    private final static String  START_FILTER_BOUNDARY = "===================Filter processing start===================";
    private final static String  END_FILTER_BOUNDARY = "====================Filter processing end====================";

    /**
     * 请求 和 响应报文打印
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        logger.debug(START_FILTER_BOUNDARY);
        long startTime = System.currentTimeMillis();
        MDC.put(Constants.SYS_STARTTIME, String.valueOf(startTime));
        MDC.put(Constants.SYS_RPID, String.valueOf(KeyWorker.nextId()));
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String remoteAddress = RequestAddressUtil.getRemoteAddress(httpServletRequest);
        logger.info(REOMOTEIP + remoteAddress);
        logger.info(CONTENTTYPE + httpServletRequest.getContentType());
        // 请求URL
        String uri = httpServletRequest.getRequestURI();
        logger.info(REQUESTURI + uri);
        BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(
                httpServletRequest);
        logger.info("# 请求报文body:{}", filterLog(requestWrapper.getBody()));
        BodyWriterHttpServletResposeWrapper responseWrapper = new BodyWriterHttpServletResposeWrapper(
                (HttpServletResponse) response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        MDC.clear();
        logger.info("# 响应报文body:{}", filterLog(responseWrapper.getBody()));
        logger.debug(END_FILTER_BOUNDARY);
    }

    /**
     * 屏蔽部分字段，如长字节字段，敏感字段
     *
     * @param body 原请求报文
     * @return String 过滤后的报文
     * @throws IOException
     * @throws JsonProcessingException
     */
    private String filterLog(String body) throws JsonProcessingException, IOException {
        // 最终打印的日志字符串
        String logStr = body;
        // 需要屏蔽的字段列表
        String fieldStr = DynPropsUtils.get(Constants.LOG_MASK_FIELDS, StringUtils.EMPTY);
        // 如果配置文件中配置了屏蔽字段列表，并且请求报文是xml或是json报文，则执行屏蔽字段步骤
        if (StringUtils.isNotBlank(fieldStr)) {
            String[] fields = fieldStr.split(Constants.SYMBOL_COMMA);
            for (String field : fields) {
                logStr = FilterFields.filterJsonField(logStr, field);
            }
        }
        return logStr;
    }
}
