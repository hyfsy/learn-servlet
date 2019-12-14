package com.hyf.servlet.annotation;

import java.lang.annotation.*;

/**
 * 此注释用于声明WebListener
 * <p>
 * 任何使用WebListener注释的类都必须实现一个或多个接口：
 * <ul>
 * <li>{@link com.hyf.servlet.ServletContextListener}</li>
 * <li>{@link com.hyf.servlet.ServletContextAttributeListener}</li>
 * <li>{@link com.hyf.servlet.ServletRequestListener}</li>
 * <li>{@link com.hyf.servlet.ServletRequestAttributeListener}</li>
 * <li>{@link com.hyf.servlet.http.HttpSessionListener}</li>
 * <li>{@link com.hyf.servlet.http.HttpSessionIdListener}</li>
 * <li>{@link com.hyf.servlet.http.HttpSessionAttributeListener}</li>
 * </ul>
 *
 * @see com.hyf.servlet.ServletContext
 * @since 3.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebListener {
    /**
     * 监听对象的描述
     */
    String value() default "";
}
