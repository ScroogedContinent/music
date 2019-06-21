package cn.org.scrooged.annotation;

import cn.org.scrooged.constant.MusicApiConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description
 * @date 2019/6/13 21:34
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyNameFiled {

    /**
     * @return 键值对的编号
     */
    String code();

    /**
     * 如果查询键值对时，没有遍历到这个结果，将使用指定的值
     * @return 默认为null
     */
    String defaultValue() default "";

    /**
     * 默认的key分割符，当该值是一个多选的键值对时，需要指定此分隔符
     * 可以自定义分割符
     * @return 返回的分割符
     * @see MusicApiConstant#DEFAULT_WORDS_SEPARATOR
     */
    String delimiter() default MusicApiConstant.DEFAULT_WORDS_SEPARATOR;
}
