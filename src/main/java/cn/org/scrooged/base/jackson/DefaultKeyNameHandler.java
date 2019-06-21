package cn.org.scrooged.base.jackson;

import cn.org.scrooged.annotation.KeyNameFiled;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Function;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2019/6/13 21:44
 */
public class DefaultKeyNameHandler implements FieldMethodHandle<String> {

    /**
     * 键值对生产者
     */
    private KeyNameProducer keyNameProducer;

    public DefaultKeyNameHandler(KeyNameProducer keyNameProducer) {
        this.keyNameProducer = keyNameProducer;
    }

    /**
     * 是否支持处理当前字段，该方法会首先检测成员方法
     * @param field 注解字段
     * @param module getter 方法
     * @return 如果支持，返回是
     */
    @Override
    public boolean support(Field field, Method module) {
        return hasKeyNameAnnotation(field) || hasKeyNameAnnotation(module);
    }

    /**
     * 判断是否 键值对 的注解
     * @param ae 可以接受注解的元素
     * @return 如果有 返回 true
     */
    private boolean hasKeyNameAnnotation(AnnotatedElement ae) {
        return null != ae && 0 != ae.getAnnotationsByType(KeyNameFiled.class).length;
    }

    /**
     * 获取修改后字段的值
     * @param definition bean属性定义
     * @param bean 将会被序列化的对象
     * @return
     */
    @Override
    public String value(BeanPropertyDefinition definition, Object bean) {
        KeyNameFiled keyNameFiled = keyNameFiled(definition);
        String code = keyNameFiled.code();

        //原始的值，可能的情况是：effect，draft，invalid
        String delimiter = keyNameFiled.delimiter();
        Object oValue = originValue(definition, bean);
        if (null == oValue) {
            return keyNameFiled.defaultValue();
        }else {
            String[] values = oValue.toString().split("\\Q" + delimiter + "\\E");

            if (values.length != 0) {
                Function<String, String> coverter = convertFunction(keyNameProducer.keyNames(code), keyNameFiled.defaultValue());
                if (values.length == 1) {
                    return coverter.apply(values[0]);
                } else {
                    StringJoiner joiner = new StringJoiner(delimiter);
                    for (String value : values) {
                        joiner.add(coverter.apply(value));
                    }
                    return joiner.toString();
                }
            }
        }
        return keyNameFiled.defaultValue();
    }

    /**
     * 构造一个转换函数，该函数负责将键值对的key转换为对应的名称（name）
     * @param keyNames 键值对列表
     * @param dv 默认值
     * @return 返回处理函数
     */
    private Function<String, String> convertFunction(IKeyName[] keyNames, String dv) {
        return s -> {
            for (IKeyName kn : keyNames) {
                if (equalsAsString(kn.getCode(),s)) {
                    return kn.getName();
                }
            }
            return dv;
        };
    }

    /**
     * 将两个对象作为字符串去比较
     * @param code code
     * @param value 值
     * @return
     */
    private boolean equalsAsString(Serializable code, String value) {
        return null != code && String.valueOf(code).equals(value);
    }

    /**
     * 获取字段的值
     * @param definition 定义
     * @param bean 调用对象
     * @return
     */
    private Object originValue(BeanPropertyDefinition definition, Object bean) {
        return Optional.ofNullable(definition.getField())
                .map(AnnotatedField::getAnnotated)
                .map(f -> fieldValue(f, bean))
                .orElseGet(() -> methodValue(definition.getGetter().getAnnotated(), bean));
    }

    /**
     * 获取keyName 字段 字段
     * @param definition
     * @return
     */
    private KeyNameFiled keyNameFiled(BeanPropertyDefinition definition) {
        return Optional.ofNullable(definition.getField())
                .map(f -> f.getAnnotated().getAnnotation(KeyNameFiled.class))
                .orElseGet(() -> definition.getGetter().getAnnotation(KeyNameFiled.class));
    }

    /**
     * 方法返回错误
     * @param method
     * @param bean bean
     * @return
     */
    private Object methodValue(Method method, Object bean) {
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        try {
            return method.invoke(bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * 从field的取值
     * @param field
     * @param bean
     * @return
     */
    private Object fieldValue(Field field, Object bean) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            return field.get(bean);
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
