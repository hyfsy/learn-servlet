package com.hyf.servlet;

import java.util.EventObject;

/**
 * 这是一个事件类，用于通知web应用程序servlet上下文的更改
 *
 * @see ServletContextListener
 */
public class ServletContextEvent extends EventObject {

    private static final long serialVersionUID = -5774907584971347984L;

    /**
     * 从给定的上下文构造一个ServletContextEvent
     *
     * @param source 正在发送事件的ServletContext
     */
    public ServletContextEvent(ServletContext source) {
        super(source);
    }

    /**
     * 返回已更改的ServletContext
     *
     * @return 发送事件的ServletContext
     */
    public ServletContext getServletContext() {
        return (ServletContext) super.getSource();
    }
}
