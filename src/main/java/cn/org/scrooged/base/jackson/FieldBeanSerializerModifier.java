package cn.org.scrooged.base.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2019/6/21 10:45
 */
public class FieldBeanSerializerModifier extends BeanSerializerModifier {

    /**
     * 字段处理器
     */
    private List<FieldMethodHandle<?>> handles;

    public FieldBeanSerializerModifier() {
        this.handles = new ArrayList<>(5);
    }

    /**
     * 添加处理器
     * @param handle 处理器
     */
    public void addHandle(FieldMethodHandle<?> handle) { this.handles.add(handle); }

    /**
     * 改变生成的属性，本方法所有返回的集合必须是可修改的
     * @param config 配置信息
     * @param beanDesc bean简介
     * @param beanProperties 属性
     * @return 更改后的属性列表
     */
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        List<BeanPropertyWriter> nativeWriters = super.changeProperties(config, beanDesc, beanProperties);

        List<BeanPropertyWriter> handleWriters = handles.stream().map(h -> covertToWriter(h, beanDesc))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        handleWriters.addAll(nativeWriters);
        return handleWriters;
    }

    /**
     * 转换为属性写入器
     * @param handle
     * @param desc bean
     * @return
     */
    private List<BeanPropertyWriter> covertToWriter(FieldMethodHandle<?> handle, BeanDescription desc) {
        BeanPropertyDefinition[] definitions = desc.findProperties()
                .stream()
                .filter(p -> handle.support(
                        Optional.ofNullable(p.getField()).map(AnnotatedField::getAnnotated).orElse(null),
                        Optional.ofNullable(p.getGetter()).map(AnnotatedMethod::getAnnotated).orElse(null)
                ))
                .toArray(BeanPropertyDefinition[]::new);
        return Arrays.stream(definitions)
                .map(d -> new MusicVirtualBeanPropertyWriter<>(handle.name(d.getName()), o -> handle.value(d, o)))
                .collect(Collectors.toList());
    }
}
