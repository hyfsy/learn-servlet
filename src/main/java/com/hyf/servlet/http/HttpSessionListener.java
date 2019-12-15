package com.hyf.servlet.http;

import com.hyf.servlet.ServletContext;

/**
 * 用于接收关于HttpSession生命周期更改的通知事件
 * <p>
 * 为了接收这些通知事件，实现类必须在web应用程序的部署描述符中声明，
 * 并使用{@link com.hyf.servlet.annotation.WebListener}注解注释或通过定义在{@link ServletContext}上
 * 的addListener方法之一注册
 * <p>
 * 此接口的实现在其{@link #sessionCreated}方法中按声明的顺序调用，
 * 在其{@link #sessionDestroyed}方法中按相反的顺序调用
 *
 * @see com.hyf.servlet.ServletContext#addListener
 * @see HttpSessionEvent
 */
public interface HttpSessionListener {

    /**
     * 接收创建会话的通知
     *
     * @param event 设置包含会话的HttpSessionEvent
     */
    void sessionCreated(HttpSessionEvent event);

    /**
     * 接收会话即将失效的通知
     *
     * @param event 设置包含会话的HttpSessionEvent
     */
    void sessionDestroyed(HttpSessionEvent event);
}
