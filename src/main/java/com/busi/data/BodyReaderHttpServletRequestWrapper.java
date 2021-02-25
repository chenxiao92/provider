package com.busi.data;

import com.busi.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/** 
 * request重新包装工具，用于日志打印
 * <p>创建日期：2019年3月20日 </p>
 * @version V1.0  
 * @author luqian
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(BodyReaderHttpServletRequestWrapper.class);
	/**
	 * request请求报文
	 */
	private final byte[] body;
    /** 
     * <p>Title:重新包装request，请求body放入body数组 </p> 
     * <p>Description: 重新包装request，解决body一次读取问题 </p> 
     * @param request 原始request
     * @throws IOException io异常
     */
    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String bodyString = getBodyString(request);
        body = bodyString.getBytes(Constants.SYS_CHARSET);
    }

    /**
     * <p>Title: getInputStream</p> 
     * <p>Description:重写 getInputStream，包装后的request可以读取body</p> 
     * @return ServletInputStream 输入流
     * @throws IOException io异常
     * @see javax.servlet.ServletRequestWrapper#getInputStream() 
     */ 
    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }
    
    /**
     * 获取request对象中的body字符串
     * @return 
     * String 请求报文
     */
    public String getBody() {
    	if(null!=body && body.length!=0) {
            try {
                return new String(body,Constants.SYS_CHARSET);
            } catch (UnsupportedEncodingException e) {
                LOG.error("字符集有误:"+Constants.SYS_CHARSET,e);
            }
        }
    	return null;
    }

    /**
	 * request报文读取方法
	 * @param request 包装后的request对象
	 * @return String 请求报文
	 * @throws IOException 流未正常操作时，抛出该异常
	 */
	private String getBodyString(HttpServletRequest request) throws IOException {
		//报文拼装字符流
		StringBuilder sb = new StringBuilder();
		//读取request请求的字符流
		try (InputStream inputStream = request.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Constants.SYS_CHARSET));) {
			char[] bodyCharBuffer = new char[1024];
			int len = 0;
			while ((len = reader.read(bodyCharBuffer)) != -1) {
				sb.append(new String(bodyCharBuffer, 0, len));
			}
		}
		return sb.toString();
	}
}
