package com.busi.data;


import com.busi.constant.Constants;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/** 
 * response重新包装工具，用于日志打印
 * <p>创建日期：2019年3月20日 </p>
 * @version V1.0  
 * @author luqian
 */
public class BodyWriterHttpServletResposeWrapper extends HttpServletResponseWrapper {

	/**
	 * response响应报文流备份
	 */
	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	/**
	 * 备份response
	 */
	private HttpServletResponse response;
	
	public BodyWriterHttpServletResposeWrapper(HttpServletResponse response) {
		super(response);
		this.response = response;
	}

    /**
     * 获取响应字符串
     * <p>创建人：luqian ,  2019年3月21日  上午9:30:45</p>
     * <p>修改人：luqian ,  2019年3月21日  上午9:30:45</p>
     *
     * @return
     * String 
     */
    public String getBody() {
        return new String(byteArrayOutputStream.toByteArray(),Charset.forName(Constants.SYS_CHARSET));
    }

    /**
     * <p>Title: getOutputStream</p> 
     * <p>Description:重写 getOutputStream，绕过一次读取问题 </p> 
     * @return 包装后的response流
     * @see javax.servlet.ServletResponseWrapper#getOutputStream() 
     */ 
    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStreamWrapper(this.byteArrayOutputStream , this.response);
    }

    /**
     * <p>Title: getWriter</p> 
     * <p>Description:重写 getWriter，绕过一次读取问题 </p> 
     * @return PrintWriter 打印流
     * @throws IOException 未正常操作流，则抛出该异常
     * @see javax.servlet.ServletResponseWrapper#getWriter() 
     */ 
    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(new OutputStreamWriter(this.byteArrayOutputStream , this.response.getCharacterEncoding()));
    }
    
    /** 
     * ServletOutputStream重新包装，用于打印响应报文
     * <p>创建日期：2019年3月21日 </p>
     * @version V1.0  
     * @author luqian
     */
    private static class ServletOutputStreamWrapper extends ServletOutputStream {

        private ByteArrayOutputStream outputStream;
        private HttpServletResponse response;

        public ServletOutputStreamWrapper(ByteArrayOutputStream outputStream, HttpServletResponse response) {
			this.outputStream = outputStream;
			this.response = response;
		}

		@Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        @Override
        public void write(int b) throws IOException {
            this.outputStream.write(b);
        }

        @Override
        public void flush() throws IOException {
            if (! this.response.isCommitted()) {
                byte[] body = this.outputStream.toByteArray();
                ServletOutputStream outputStream = this.response.getOutputStream();
                outputStream.write(body);
                outputStream.flush();
            }
        }
    }
}
