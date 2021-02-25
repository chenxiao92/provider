package com.busi.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述说明:实现将Object转化为map
 *
 * @author daqing.zheng
 * @version 1.0
 * @className ObjectToMap
 * @date 2019/8/13 17:04
 */
public class ObjectToMap  {

    /**
     * 响应对象转化为Map
     * <p>支持子类继承的对象转化</p>
     * @param o
     * @return
     */
    public static Map<String,Object> toMap(Object o) {
        Map<String,Object> map=new HashMap<String, Object>();
        Class<?> clazz = o.getClass() ;
        Field[] fields;
        try {
          for(; clazz != Object.class ; clazz = clazz.getSuperclass()){
                fields=clazz.getDeclaredFields();
                for (int i=0;i<fields.length;i++) {
                    String name=fields[i].getName();
                    Field field=clazz.getDeclaredField(name);
                    field.setAccessible(true);
                    Object obj = field.get(o);
                    if(StringUtils.isEmpty(obj)){
                        map.put(name, "");
                    }else{
                        map.put(name, obj.toString());
                    }

                }
                fields = null;
            }
        }catch(Exception e){
            throw new RuntimeException("mpsp print error：object convert to map failed");
        }
        return map;
    }
}
