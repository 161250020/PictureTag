package util;

import com.alibaba.fastjson.JSON;

public class RevertJsonObject {
           public static final String toJsonObject(Object o) {
               return JSON.toJSONString(o);                       //将对象转化成json对象
           }
           public static final <T> T getBean(String jsonstr,Class<T> t){
               return JSON.parseObject(jsonstr,t);                 //反序列化
           }

}
