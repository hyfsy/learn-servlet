package com.hyf.servlet;

/**
 * 用于接收关于进入和离开web应用程序范围的请求的通知事件
 * <p>
 * ServletRequest的定义是:当它要进入web应用程序的第一个servlet或过滤器时，
 * 它进入web应用程序的作用域;当它退出链中的最后一个servlet或第一个过滤器时，它退出作用域
 * <p>
 * 为了接收这些通知事件，实现类必须在web应用程序的部署描述符中声明，
 * 并使用{@link com.hyf.servlet.annotation.WebListener}注解注释或通过定义在{@link ServletContext}上
 * 的addListener方法之一注册
 * <p>
 * 这个接口的实现是在他们的{@link #requestInitialized}方法中以声明的顺序调用的，
 * 在他们的{@link #requestDestroyed}方法中以相反的顺序调用的
 *
 * @see com.hyf.servlet.ServletContext#addListener
 * @see ServletRequestEvent
 */
public interface ServletRequestListener {

    /**
     * 接收通知，当ServletRequest将进入web应用程序范围
     *
     * @param sre 这个ServletRequestEvent包含了ServletRequest和代表web应用程序的ServletContext
     */
    void requestInitialized(ServletRequestEvent sre);

    /**
     * 接收通知，当ServletRequest将要超出web应用程序范围
     *
     * @param sre 这个ServletRequestEvent包含了ServletRequest和代表web应用程序的ServletContext
     */
    void requestDestroyed(ServletRequestEvent sre);
}
