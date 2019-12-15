package com.hyf.servlet.annotation;

import com.hyf.servlet.DispatcherType;
import com.hyf.servlet.ServletRequest;
import com.hyf.servlet.ServletResponse;

import java.lang.annotation.*;

/**
 * 用于声明servlet过滤器的注解
 * <p>
 * 此注解在部署时由容器处理，相应的过滤器应用于指定的URL模式、servlet和dispatcher类型
 *
 * @see com.hyf.servlet.Filter
 * @since 3.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebFilter {

    /**
     * 过滤器的描述
     */
    String description() default "";

    /**
     * 过滤器的显示名称
     */
    String displayName() default "";

    /**
     * 过滤器的初始化参数
     */
    WebInitParam[] initParams() default {};

    /**
     * 过滤器的名称
     */
    String filterName() default "";

    /**
     * 过滤器的小图标
     */
    String smallIcon() default "";

    /**
     * 过滤器的大图标
     */
    String largeIcon() default "";

    /**
     * 过滤器应用到的servlet的名称
     */
    String[] servletNames() default {};

    /**
     * 过滤器应用到的URL模式
     */
    String value() default "";

    /**
     * 过滤器应用到的URL模式
     */
    String[] urlPatterns() default {};

    /**
     * 过滤器应用到的调度程序类型
     */
    DispatcherType[] dispatcherTypes() default {DispatcherType.REQUEST};

    /**
     * 声明过滤器是否支持异步操作模式
     *
     * @see ServletRequest#startAsync()
     * @see ServletRequest#startAsync(ServletRequest, ServletResponse)
     */
    boolean asyncSupported() default false;
}
