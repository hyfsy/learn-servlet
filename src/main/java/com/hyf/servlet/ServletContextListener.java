package com.hyf.servlet;

import java.util.EventListener;

/**
 * 用于接收关于ServletContext生命周期更改的通知事件
 * <p>
 * 为了接收这些通知事件，实现类必须在web应用程序的部署描述符中声明，
 * 并通过{@link com.hyf.servlet.annotation.WebListener}注解注释或使用定义在{@link ServletContext}上的addListener方法之一注册
 * <p>
 * 这个接口的实现是在{@link #contextInitialized}方法中按声明的顺序调用的，
 * 在{@link #contextDestroyed}方法中按相反的顺序调用的
 *
 * @see ServletContext#addListener
 * @see ServletContextEvent
 */
public interface ServletContextListener extends EventListener {

    /**
     * 接收正在启动web应用程序初始化过程的通知
     * <p>
     * 在初始化web应用程序中的任何过滤器或servlet之前，所有ServletContextListener都会收到上下文初始化的通知
     *
     * @param sce 包含正在初始化的ServletContext的ServletContextEvent对象
     */
    void contextInitialized(ServletContextEvent sce);

    /**
     * 接收servlet上下文即将关闭的通知
     * <p>
     * 所有的servlet和过滤器都将在任何ServletContextListener被通知上下文销毁之前被销毁
     *
     * @param sce 包含被销毁的ServletContext的ServletContextEvent对象
     */
    void contextDestroyed(ServletContextEvent sce);
}
