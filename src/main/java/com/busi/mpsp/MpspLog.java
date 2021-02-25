package com.busi.mpsp;



import com.busi.constant.Constants;
import com.busi.data.Response;
import com.busi.message.DynamicMessagesUtil;
import com.busi.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.Map;


/**
 * @Description：打印简要日志
 * <p>创建日期：2018/11/12 </p>
 * @version V1.0
 * @author junjie.cao
 * @see
 */
public class MpspLog {

    private static Logger logger;
    private static String separator = ",";
	
    private static Logger logger(){
        if(logger == null){
            logger = LoggerFactory.getLogger(Constants.MPSP);
        }
        return logger;
    }

    /**
     * @Description：打印简要日志(不含业务字段)
     * <p>创建人：junjie.cao,  2018/11/12  13:42</p>
     * <p>修改人：junjie.cao,  2018/11/12  13:42</p>
     *
     * @Param: responseData
     * @Param: url
     * @Param: period
     *
     */
    public static void logMPSP(Response response, String url, long period){
        if(response == null){
        	return;
        }
        StringBuilder sb = new StringBuilder();


        sb.append(StringUtils.trimWhitespace((String) MDC.get(Constants.SYS_RPID))).append(separator)
                .append(StringUtils.trimWhitespace(DateTimeUtil.getDateString())).append(separator)
                .append(StringUtils.trimWhitespace(DateTimeUtil.getTimeString())).append(separator)
                .append(separator)
                .append(StringUtils.trimWhitespace(url)).append(separator);
        //响应信息以设置的响应报文为准，
        // 若为设置根据功能码去配置文件中取值，
        // 否则统一0000当作成功，其他当作失败处理

        sb.append(period);
        logMPSP(sb.toString());
    }

    /**
     * 根据业务报文打印简要日志
     * @param resMap 响应报文转化的map
     * @param url
     * @param period
     */
    public static void logMPSP(Map<String,String> resMap, String url, long period){
        if(resMap.size() < 1){
            return;
        }
        String busi_rpid = resMap.get(Constants.M_RPID);
        String busi_funCode = resMap.get(Constants.M_FUNCODE);

        StringBuilder sb = new StringBuilder();
        sb.append(DateTimeUtil.getDateTimeMilliSecondString()).append(separator)
            .append(StringUtils.trimWhitespace((String) MDC.get(Constants.SYS_RPID))).append(separator)
            .append(StringUtils.trimWhitespace(url)).append(separator)
            .append(StringUtils.trimWhitespace(busi_rpid == null ? "":busi_rpid)).append(separator);

        if(StringUtils.isEmpty(busi_funCode)){
            busi_funCode = url.substring(url.lastIndexOf("/") + 1);
        }
        //简要日志打印业务报文字段
        String busiMpsp = DynamicMessagesUtil.getMsg(busi_funCode+".mpsp","");
        if(StringUtils.isEmpty(busiMpsp)){
            busiMpsp = DynamicMessagesUtil.getMsg("default.mpsp","");
        }
        if(!StringUtils.isEmpty(busiMpsp)){
            String[] mpspField = busiMpsp.split(",");
            for(String field :mpspField){
                sb.append(resMap.get(field)).append(separator);
            }
        }
        sb.append(period);
        logMPSP(sb.toString());
    }

    private static synchronized void logMPSP(String info){
        logger().info(info);
    }



    /**
     * @Description：去除分隔符
     * <p>创建人：junjie.cao,  2018/11/12  13:42</p>
     * <p>修改人：junjie.cao,  2018/11/12  13:42</p>
     *
      * @Param: str
     *
     */
    private static String replaceSign(String str) {
	
		if(str.contains(separator)){//将逗号转为竖线，以区分分隔符
			str = str.replaceAll(separator, "|");
		}
		if(str.contains("，")){//将逗号转为竖线，以区分分隔符
			str = str.replaceAll("，", "|");
		}
		return str;
    }

}
