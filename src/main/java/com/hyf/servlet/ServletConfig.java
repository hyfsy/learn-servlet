package com.hyf.servlet;

import java.util.Enumeration;

/**
 * servlet容器使用的对象
 * 在初始化过程中将信息传递给servlet
 */
public interface ServletConfig {

    /**
     * 获取servlet的名称
     *
     * @return servlet的名称
     */
    String getServletName();

    /**
     * 返回调用者所在的<code>servletContext</code>引用
     *
     * @return servlet上下文对象
     */
    ServletContext getServletContext();

    /**
     * 通过名称返回servlet的初始化参数
     *
     * @param name servlet参数名
     * @return servlet参数对应的值，如果没找到返回<code>null</code>
     */
    String getInitParameter(String name);

    /**
     * 返回一个存放所有servlet初始化参数名称的<code>Enumeration</code>容器
     *
     * @return 返回<code>Enumeration</code>容器，如果没有初始化参数返回一个空的<code>Enumeration</code>
     */
    Enumeration<String> getInitParameterNames();
}
