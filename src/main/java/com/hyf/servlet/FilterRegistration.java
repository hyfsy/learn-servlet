package com.hyf.servlet;

import java.util.Collection;
import java.util.EnumSet;

/**
 * 通过该接口可以进一步配置{@link Filter}
 *
 * @see ServletContext#getFilterRegistrations()
 * @since 3.0
 */
public interface FilterRegistration extends Registration {

    /**
     * 通过此FilterRegistration表示的过滤器的类型，通过给定的servlet名称和dispatcher给其添加过滤器映射
     * <p>
     * 过滤映射是按照它们增加的顺序匹配的
     * <p>
     * 根据<tt>isMatchAfter</tt>参数的值，给定的过滤器映射将在servlet上下文的任何<i>声明</i>过滤器映射之后或之前进行考虑，
     * 该过滤器映射是从ServletContext获得此过滤器<code>FilterRegistration</code>对象的
     * <p>
     * 如果这个方法被多次调用，每个连续的调用都会增加前一个方法的效果
     *
     * @param dispatcherTypes 过滤器映射的dispatcher类型，如果是默认的<tt>DispatcherType.REQUEST</tt>，则为null
     * @param isMatchAfter    如果给定的过滤器映射应该在任何声明的过滤器映射之后匹配，则isMatchAfter为<tt>true</tt>，
     *                        如果在ServletContext的任何声明的过滤器映射之前匹配，则为<tt>false</tt>，
     *                        从ServletContext获得这个过滤器<code>FilterRegistration</code>对象
     *                        （false表明在代码设置的filter先于web.xml中设置的filter）
     * @param servletNames    过滤器映射的servlet名称
     */
    void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames);

    /**
     * 通过<code>FilterRegistration</code>获取当前可用的过滤器映射的servlet名称集合
     * <p>
     * 如果允许，对返回的<code>Collection</code>对象的任何修改都不影响这个<code>FilterRegistration</code>
     *
     * @return 一个(可能是空的)通过<code>FilterRegistration</code>获取当前可用的过滤器映射的servlet名称集合
     */
    Collection<String> getServletNameMappings();

    /**
     * 使用给定的url模式和dispatcher的类型 为FilterRegistration所表示的过滤器 添加过滤器映射
     * <p>
     * 过滤映射是按照它们增加的顺序匹配的
     * <p>
     * 根据<tt>isMatchAfter</tt>参数的值，给定的过滤器映射将在servlet上下文的任何<i>声明</i>过滤器映射之后或之前进行考虑，
     * 该过滤器映射是从ServletContext获得此过滤器<code>FilterRegistration</code>对象的
     * <p>
     * 如果这个方法被多次调用，每个连续的调用都会增加前一个方法的效果
     *
     * @param dispatcherTypes 过滤器映射的dispatcher类型，如果是默认的<tt>DispatcherType.REQUEST</tt>，则为null
     * @param isMatchAfter    如果给定的过滤器映射应该在任何声明的过滤器映射之后匹配，则isMatchAfter为<tt>true</tt>，
     *                        如果在ServletContext的任何声明的过滤器映射之前匹配，则为<tt>false</tt>，
     *                        从ServletContext获得这个过滤器<code>FilterRegistration</code>对象
     *                        （false表明在代码设置的filter先于web.xml中设置的filter）
     * @param urlPatterns     过滤器映射的url模式
     */
    void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns);

    /**
     * 获取由<code>FilterRegistration</code>表示的过滤器当前可用的URL模式映射
     * <p>
     * 如果允许，对返回的<code>Collection</code>对象的任何修改都不影响这个<code>FilterRegistration</code>
     *
     * @return 一个(可能是空的)通过<code>FilterRegistration</code>获取当前可用的过滤器映射的URL模式集合
     */
    Collection<String> getUrlPatternMappings();

    /**
     * 通过该接口可以进一步配置通过{@link ServletContext}上的<tt>addFilter</tt>方法之一注册的{@link Filter}
     */
    interface Dynamic extends FilterRegistration, Registration.Dynamic {
    }
}
