package com.hyf.servlet;

import java.util.Enumeration;

/**
 * servlet容器使用的过滤器配置对象,在初始化过程中将信息传递给筛选器
 *
 * @see Filter
 */
public interface FilterConfig {

    /**
     * 返回在部署中定义的此过滤器的名称
     */
    String getFilterName();

    /**
     * 返回调用者所在的{@link ServletContext}引用执行
     *
     * @return 返回调用者使用的 {@link ServletContext}对象与它的servlet容器交互
     * @see ServletContext
     */
    ServletContext getServletContext();

    /**
     * 根据名称返回指定初始化的参数，如果不存在返回<code>null</code>
     *
     * @param name 一个初始化参数的名称
     * @return 返回<code>String</code>类型的初始化的参数，如果不存在返回<code>null</code>
     */
    String getInitParameter(String name);

    /**
     * 返回筛选器的初始化参数的名称<code>String</code>类型的<code>Enumeration</code>
     * 没有初始化参数则返回一个空的<code>Enumeration</code>
     *
     * @return 返回一个<code>Enumeration</code> 容器存放 <code>String</code> 对象
     * 包含过滤器初始化参数的名称
     */
    Enumeration<String> getInitParameterNames();
}
