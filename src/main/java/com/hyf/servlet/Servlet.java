package com.hyf.servlet;

import java.io.IOException;

/**
 * 定义每个servlet必须实现的方法
 * servlet接收和响应来自Web客户端的请求
 * 实现这个接口，你可以写一个通用的servlet扩展
 *
 * 这个接口定义了初始化servlet的方法，服务请求，并从服务器删除servlet
 * 这些方法称为生命周期方法，在以下序列:
 *  1.构造servlet,使用<code>init</code>方法初始化
 *  2.处理客户端对<code>service</code>方法的调用
 *  3.servlet退出服务，然后销毁，销毁前执行<code>destroy</code>方法，然后垃圾回收并最终完成
 *
 *  除了生命周期方法，这个接口提供了：
 *  <code>getServletConfig</code>方法，servlet可以用来获取任何启动信息
 *  <code>getServletInfo</code>方法，它允许servlet返回自身的基本信息，例如作者、版本和版权
 *
 */
public interface Servlet {

    /**
     * 初始化servlet
     * @param config servlet配置对象
     * @throws ServletException 可能发生干扰servlet执行的异常
     */
    void init(ServletConfig config) throws ServletException;

    /**
     * 获取servlet的配置信息对象
     * @return servlet配置对象
     */
    ServletConfig getServletConfig();

    /**
     * 调用servlet执行的动作
     * @param req 请求对象
     * @param resp 响应对象
     * @throws ServletException 可能发生干扰servlet执行的异常
     * @throws IOException 如果发送输入输出异常
     */
    void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException;

    /**
     * 获取servlet的信息
     * @return servlet信息
     */
    String getServletInfo();

    /**
     * 销毁servlet执行的动作，可用来释放资源
     */
    void destroy();

}
