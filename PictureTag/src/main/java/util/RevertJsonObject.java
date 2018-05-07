package util;


import com.google.gson.Gson;

public class RevertJsonObject {
           public static final String toJsonObject(Object o) {
               Gson gson = new Gson();
               return gson.toJson(o);                       //将对象转化成json对象
           }
           public static final <T> T getBean(String jsonstr,Class<T> t){
               return null;
               //return JSON.parseObject(jsonstr,t);                 //反序列化
           }

}
