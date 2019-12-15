package com.hyf.servlet.annotation;

import com.hyf.servlet.ServletRequest;
import com.hyf.servlet.ServletResponse;

import java.lang.annotation.*;

/**
 * 用于声明servlet的注释。
 * <p>
 * 此注释在部署时由容器处理，相应的servlet在指定的URL模式下可用
 *
 * @see com.hyf.servlet.Servlet
 * @since 3.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServlet {

    /**
     * servlet的名称
     */
    String name() default "";

    /**
     * servlet的URL模式
     */
    String value() default "";

    /**
     * servlet的URL模式
     */
    String[] urlPatterns() default {};

    /**
     * servlet的加载-启动顺序
     */
    int loadOnStartup() default -1;

    /**
     * servlet的初始化参数
     */
    WebInitParam[] initParams() default {};

    /**
     * 声明servlet是否支持异步操作模式
     *
     * @see com.hyf.servlet.ServletRequest#startAsync()
     * @see com.hyf.servlet.ServletRequest#startAsync(ServletRequest, ServletResponse)
     */
    boolean asyncSupported() default false;

    /**
     * servlet的小图标
     */
    String smallIcon() default "";

    /**
     * servlet的大图标
     */
    String largeIcon() default "";

    /**
     * servlet的描述
     */
    String description() default "";

    /**
     * servlet的显示名
     */
    String displayName() default "";
}
