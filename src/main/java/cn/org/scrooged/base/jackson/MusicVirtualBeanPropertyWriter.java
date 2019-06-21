package cn.org.scrooged.base.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.VirtualBeanPropertyWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.function.Function;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2019/6/13 21:58
 */
public class MusicVirtualBeanPropertyWriter<T> extends VirtualBeanPropertyWriter {

    private static final long serialVersionUID = 1485771561215487105L;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 值
     */
    private Function<Object, T> func;

    private ThreadLocal<T> value = new ThreadLocal<>();

    /**
     * 只要值被使用，该值就会被重置
     */
    private ThreadLocal<Boolean> init = ThreadLocal.withInitial(() -> false);

    public MusicVirtualBeanPropertyWriter(String name, Function<Object, T> func) {
        this.name = name;
        this.func = func;
    }

    /**
     * @return 返回字段名称
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 重写方法，返回值
     * @param o 被调用的java类，抽象节点不需要这个
     * @param jsonGenerator 生成器
     * @param serializerProvider 序列化提供者
     * @return 返回值
     */
    @Override
    protected Object value(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        return initValue(o);
    }

    /**
     * 初始化结果
     * @param o
     * @return
     */
    private Object initValue(Object o) {
        if (init.get()) {
            return value.get();
        }
        value.set(func.apply(o));
        init.set(true);
        return value.get();
    }

    /**
     * 忽略配置，增加容错，不支持配置信息
     * @param mapperConfig 配置
     * @param annotatedClass 注解类型
     * @param beanPropertyDefinition 定义
     * @param javaType java类型
     * @return 返回自身
     */
    @Override
    public VirtualBeanPropertyWriter withConfig(MapperConfig<?> mapperConfig, AnnotatedClass annotatedClass, BeanPropertyDefinition beanPropertyDefinition, JavaType javaType) {
        return this;
    }

    /**
     * @param config 抽象类没有field，不需要修复
     */
    @Override
    public void fixAccess(SerializationConfig config) {
        // do nothing
    }

    @Override
    public JavaType getType() {
        return TypeFactory.defaultInstance().constructType(Object.class);
    }

    /**
     * 像字段一样序列化
     * @param bean bean
     * @param gen 生成器
     * @param prov 序列化提供者
     * @throws Exception 异常信息
     */
    @Override
    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        if (null == initValue(bean)) {
            if (_nullSerializer != null) {
                gen.writeFieldName(name);
                _nullSerializer.serialize(null, gen, prov);
            }
        } else {
            JsonSerializer<Object> ser = prov.findValueSerializer(value.get().getClass());
            gen.writeFieldName(name);
            if (_typeSerializer == null) {
                ser.serialize(initValue(bean), gen, prov);
            } else {
                ser.serializeWithType(initValue(bean), gen, prov, _typeSerializer);
            }
        }
    }

    /**
     * 清理本地线程池变量
     */
    public void removeThreadLocal() {
        init.remove();
        value.remove();
    }
}
