package com.busi.message;

import com.busi.util.SpringUtils;
import org.springframework.context.MessageSource;

/**
 * 描述说明:
 *
 * @author daqing.zheng
 * @version 1.0
 * @className DynamicMessagesUtil
 * @date 2019/8/13 17:49
 */
public class DynamicMessagesUtil {
    private static MessageSource messageSource = (MessageSource) SpringUtils.getApplicationContext().getBean("myDynamicSource");

    public static String getMsg(String code){
        String message = messageSource.getMessage(code,null,null);
        return message;
    }

    public static String getMsg(String code,String defaultValue){
        String message = messageSource.getMessage(code,null,defaultValue,null);
        return message;
    }


}
