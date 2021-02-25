package com.busi.aop;

import com.alibaba.fastjson.JSON;
import com.busi.constant.Constants;
import com.busi.constant.RetCode;
import com.busi.data.Request;
import com.busi.data.Response;
import com.busi.excption.UmfException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/** 
 * @Description：全局异常处理
 * <p>创建日期：2019-11-19 </p>
 * @version V1.0  
 * @author zhengdaqing
 */
@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 全局自定义异常捕捉处理
     * @param ex
     * @return
     * @throws IOException 
     */
    @ResponseBody
    @ExceptionHandler(value = UmfException.class)
    public void umfExceptionHandler(UmfException ex,HttpServletRequest request ,HttpServletResponse response) throws IOException {
        String errorCode = ex.getCode();
        String msg = ex.getMsg();
        logger.info("Busi error ,error code:[{}],error msg:[{}]",errorCode,msg);
        returnException(request,response,errorCode,msg);
    }


    /**
     * 字段鉴权
     * @param exception
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public void bindExceptionHandler(BindException exception,HttpServletRequest request ,HttpServletResponse response) throws IOException {
        String errorCode = RetCode.MESSAGE_AUTH_INCORRECT;
        String msg = exception.getBindingResult().getFieldError().getDefaultMessage();
        logger.info("字段鉴权异常, error code:[{}],error msg:[{}]",errorCode,msg);
        returnException(request,response,errorCode,msg);
    }

    /**
     * 全局异常处理实现参数非法性检查
     * @param exception
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public void methodArgumentValidFailHandler(MethodArgumentNotValidException exception,HttpServletRequest request ,HttpServletResponse response) throws IOException {
        String errorCode = RetCode.MESSAGE_PARAM_NOT_RIGHT;
        List<ObjectError> fieldErrorList = exception.getBindingResult().getAllErrors();
        String errorMessage = "参数校验失败:";
        for(ObjectError fieldError:fieldErrorList){
            errorMessage += ", " + fieldError.getDefaultMessage();
        }
        logger.info("error code:[{}],error msg:[{}]",errorCode,errorMessage);
        returnException(request,response,errorCode,errorMessage);
    }


    /**
     * 系统异常通用处理模块，捕获所有的Exception
     * @param ex
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public void errorHandler(Exception ex,HttpServletRequest request ,HttpServletResponse response) throws IOException {
    	logger.error("处理异常：",ex);
        String errorCode = "9999";
        logger.error("系统异常, error code:[{}],error msg:[{}]",errorCode,ex.fillInStackTrace());
        returnException(request,response,errorCode,"");
    }

    /**
     * 异常处理通用内部方法
     * @param request
     * @param response
     * @param errorCode
     * @param msg
     * @throws IOException
     */
    private void returnException(HttpServletRequest request ,HttpServletResponse response,String errorCode,String msg)throws IOException{
        Request requestData = (Request) request.getAttribute(Constants.REQDATA);
        Response responseData = new Response(requestData);
        if(StringUtils.isEmpty(msg)){
            responseData.error(errorCode);
        }else{
            responseData.error(errorCode,msg);
        }
        request.setAttribute(Constants.RESDATA, responseData);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json");
        ServletOutputStream os = response.getOutputStream();
        os.write(JSON.toJSONString(responseData).getBytes("utf-8"));
        os.flush();
    }

}
