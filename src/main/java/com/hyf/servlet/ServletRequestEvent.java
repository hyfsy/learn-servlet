package com.hyf.servlet;

import java.util.EventObject;

/**
 * 此类事件指示ServletRequest的生命周期事件。
 * 事件的来源是这个web应用程序的ServletContext
 *
 * @see ServletRequestListener
 */
public class ServletRequestEvent extends EventObject {

    private static final long serialVersionUID = -7823415006311616190L;

    private ServletRequest request;

    /**
     * 为给定的ServletContext和ServletRequest构造一个ServletRequestEvent
     *
     * @param context web应用程序的ServletContext
     * @param request 正在发送事件的ServletRequest
     */
    public ServletRequestEvent(ServletContext context, ServletRequest request) {
        super(context);
        this.request = request;
    }

    /**
     * 返回正在更改的ServletRequest
     */
    public ServletRequest getRequest() {
        return request;
    }

    /**
     * 返回此web应用程序的ServletContext
     */
    public ServletContext getServletContext() {
        return (ServletContext) super.getSource();
    }
}
