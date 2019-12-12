package com.hyf.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 此注释用于声明{@link com.hyf.servlet.ServletContainerInitializer}可以处理的类类型
 *
 * @see com.hyf.servlet.ServletContainerInitializer
 * @since 3.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlesTypes {
    /**
     * 包含{@link com.hyf.servlet.ServletContainerInitializer}感兴趣的类
     * 如果<tt>ServletContainerInitializer</tt>的实现指定了这个注释，
     * Servlet容器必须将这些应用程序类扩展、实现或已经用这个注释的类类型集合传递
     * 给{@link com.hyf.servlet.ServletContainerInitializer#onStartup}方法
     * (如果没有找到匹配的类，则必须传递<tt>null</tt>)
     */
    Class<?>[] value();
}


