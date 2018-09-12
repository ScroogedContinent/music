package cn.org.continent.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description Json转String工具类
 * @date 2018/7/25 19:57
 */
public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    public JsonUtils() {
    }

    public static String toString(Object obj){
        return toJson(obj);
    }

    public static String toJson(Object obj){
        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, obj);
            return writer.toString();
        } catch (Exception var2) {
            //e.printStackTrace();
            throw new RuntimeException("序列化对象【"+ obj +"】时出错", var2);
        }
    }

    public static <T1, T2>Map<T1, T2> toMap(String json, Class<T1> t1, Class<T2> t2){
        JavaType type = mapper.getTypeFactory().constructParametricType(Map.class, new Class[]{t1, t2});

        try {
            return (Map)mapper.readValue(json, type);
        } catch (Exception var5) {
            //e.printStackTrace();
            throw new RuntimeException("反序列化对象【"+ json +"】时出错", var5);
        }
    }

    public static <T>T toBean(Class<T> entityClass, String jsonString){
        try {
            return mapper.readValue(jsonString, entityClass);
        } catch (Exception var3) {
            //e.printStackTrace();
            throw new RuntimeException("JSON【"+ jsonString +"】转对象时出错", var3);
        }
    }

    public static String getJsonSuccess(String obj, boolean isObject){
        String jsonString = null;
        if (obj == null){
            jsonString = "{\"success\":true}";
        }else{
            jsonString = "{\"success\":true,\"data\":"+ obj +"}";
        }

        return jsonString;
    }

    public static String getJsonSuccess(Object obj){
        return getJsonSuccess(obj, (String)null);
    }

    public static String getJsonSuccess(Object obj, String message){
        if(obj == null){
            return "{\"success\":true,\"message\":"+ message +"\"}";
        }else{
            try {
                Map<String, Object> map = new HashMap<>();
                map.put("success", true);
                return "{\"success\":true,"+ toString(obj) + ",\"message\":"+ message +"\"}";
            } catch (Exception var2) {
                //e.printStackTrace();
                throw new RuntimeException("序列化对象【"+ obj +"】时出错", var2);
            }
        }
    }

    public static String getJsonError(Object obj){
        return getJsonError(obj, (String)null);
    }

    public static String getJsonError(Object obj, String message){
        if(obj == null){
            return "{\"success\":false,\"message\":"+ message +"\"}";
        }else{
            try {
                obj = parseIfException(obj);
                return "{\"success\":false,"+ toString(obj) + ",\"message\":"+ message +"\"}";
            } catch (Exception var2) {
                //e.printStackTrace();
                throw new RuntimeException("序列化对象【"+ obj +"】时出错", var2);
            }
        }
    }

    public static Object parseIfException(Object obj){
        return obj instanceof Exception ? getErrorMessage((Exception)obj, (String)null) : obj;
    }

    public static String getErrorMessage(Exception e, String defaultMessage){
        return defaultMessage != null ? defaultMessage : null;
    }

    public static ObjectMapper getMapper(){
        return mapper;
    }
}
