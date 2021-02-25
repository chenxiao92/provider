package com.busi.aop;


import com.busi.constant.Constants;
import com.busi.constant.RetCode;
import com.busi.excption.UmfException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hp
 * @version V1.0
 * @Description：打印controller执行方法和处理时间日志 <p>创建日期：2018年6月26日 </p>
 * @see
 */
@Aspect
@Component
@Order(-9999)
public class GolbalControllerLogs {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String START_BOUNDARY = "Start processing the [{}] method of the [{}] class";
    private final static String END_BOUNDARY = "The end of the [{}] method of the [{}] class";
    private final static String PARAMS = "Params:";

    ThreadLocal<Long> startTime = new ThreadLocal<>();

//    @Pointcut("execution(* com.umf.busi.controller.*.*(..))")
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) && within(com.busi.controller..*)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        String method = request.getMethod();
        if(!"POST".equals(method)){
            throw new UmfException(RetCode.REQUEST_METHOD_EXCEPTION);
        }
        Object[] args = joinPoint.getArgs();
        if(args.length == 0){
            throw new UmfException(RetCode.REQUEST_PARAM_EXCEPTION);
        }
//        if (!ContentType.APPLICATION_JSON.getMimeType().equals(request.getContentType())) {
//            throw new UmfException(RetCode.REQUEST_CONTENTTYPE_EXCEPTION);
//        }
//        if ((joinPoint.getArgs()[0] instanceof Request)){
//            request.setAttribute(Constants.REQDATA, joinPoint.getArgs()[0]);
//            if (logger.isDebugEnabled()) {
//                logger.debug("请求方式：{}",joinPoint.getArgs()[0].toString());
//            }
//        }
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        logger.info(START_BOUNDARY, joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        request.setAttribute(Constants.RESDATA, ret);
//        if (logger.isDebugEnabled()) {
//            if(!StringUtils.isEmpty(ret)){
//                logger.debug(ret.toString());
//            }
//        }
        logger.info("Method deal time : " + (System.currentTimeMillis() - startTime.get()) + "ms");
    }


    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint joinPoint) {
        logger.info(END_BOUNDARY, joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());

    }
}
