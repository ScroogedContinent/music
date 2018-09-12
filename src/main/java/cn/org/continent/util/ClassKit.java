package cn.org.continent.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 类工具包
 * @date 2018/8/26 14:07
 */
public class ClassKit {

    private ClassKit(){
        //静态类不可以实例化
    }

    /**
     * 获取指定类的所有字段
     * @param clazz
     * @return
     */
    public static List<Field> getClassField(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        if( null == clazz){
            return null;
        }
        while( null != clazz ){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取指定类的所有字段名称
     * @param clazz
     * @return
     */
    public static List<String> getClassFieldName(Class<?> clazz){
        List<Field> fields = getClassField(clazz);
        List<String> fieldNames = new ArrayList<>();
        if( null != fields ){
            for( Field field : fields ) {
                fieldNames.add(field.getName());
            }
        }
        return fieldNames;
    }

    /**
     * 获取指定类的拥有某个字段的注解
     * @param clazz
     * @param annoClass
     * @return
     */
    public static List<Field> getClassFieldByAnnotation(Class<?> clazz, Class annoClass){
        List<Field> retList = new ArrayList<>();
        List<Field> fields = getClassField(clazz);
        fields.forEach(field -> {
            Annotation annotation = field.getAnnotation(annoClass);
            if( null != annotation ){
                retList.add(field);
            }
        });
        return retList;
    }
}
