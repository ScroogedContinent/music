package cn.org.continent.annotation;

import cn.org.continent.constant.DbConstant;

import java.lang.annotation.*;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 默认排序字段标示
 * @date 2018/8/24 13:02
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderField {

    //排序方式
    String value() default DbConstant.ORDER_DESC;

    //唯一的排序字段
    boolean primary() default false;
}
