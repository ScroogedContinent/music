package cn.org.continent.annotation;

import java.lang.annotation.*;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 全文检索字段标示
 * @date 2018/8/26 10:45
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fulltext {

}
