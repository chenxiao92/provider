package com.busi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description：SpringUtil
 * <p>创建日期：2019-01-11 </p>
 * @version V1.0  
 * @author junjie.cao
 * @see
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        if(SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
        logger.debug("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象========");
    }
    /**
     * @Description：获取applicationContext
     * <p>创建人：junjie.cao,  2019/1/11  20:26</p>
     * <p>修改人：junjie.cao,  2019/1/11  20:26</p>
     *
      * @Param: null
     *
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    /**
     * @Description：通过name获取 Bean.
     * <p>创建人：junjie.cao,  2019/1/11  20:26</p>
     * <p>修改人：junjie.cao,  2019/1/11  20:26</p>
     *
      * @Param: name
     *
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * @Description：通过class获取Bean.
     * <p>创建人：junjie.cao,  2019/1/11  20:26</p>
     * <p>修改人：junjie.cao,  2019/1/11  20:26</p>
     *
      * @Param: clazz
     *
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @Description：
     * <p>创建人：junjie.cao,  2019/1/11  20:27</p>
     * <p>修改人：junjie.cao,  2019/1/11  20:27</p>
     *
     * @Param: name
     * @Param: clazz
     *
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}