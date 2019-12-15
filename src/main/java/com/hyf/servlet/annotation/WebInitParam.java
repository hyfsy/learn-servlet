package com.hyf.servlet.annotation;

import java.lang.annotation.*;

/**
 * 此注释用于Servlet或过滤器实现类来指定初始化参数
 *
 * @since 3.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebInitParam {

    /**
     * 初始化参数的名称
     */
    String name();

    /**
     * 初始化参数的值
     */
    String value();

    /**
     * 初始化参数说明
     */
    String description() default "";
}
