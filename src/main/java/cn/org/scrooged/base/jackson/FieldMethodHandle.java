package cn.org.scrooged.base.jackson;

import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 注解处理器
 * @date 2019/6/21 10:13
 */
public interface FieldMethodHandle<R> {

    /**
     * 是否支持处理当前注解
     * @param field 注解字段
     * @param module getter 方法
     * @return 返回true 表示支持该注解
     */
    boolean support(Field field, Method module);

    /**
     * 转换处理器
     * @param definition bean属性定义
     * @param bean 将会被序列化的对象
     * @return
     */
    R value(BeanPropertyDefinition definition, Object bean);

    /**
     * 返回名称
     * @param name 名称
     * @return
     */
    default String name(String name) { return "__" + name; }
}
