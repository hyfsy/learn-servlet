package com.hyf.servlet.http;

import com.hyf.servlet.ServletContext;

/**
 * 用于接收关于HttpSession属性更改的通知事件
 * <p>
 * 为了接收这些通知事件，实现类必须在web应用程序的部署描述符中声明，
 * 并使用{@link com.hyf.servlet.annotation.WebListener}注解注释或通过定义在{@link ServletContext}上
 * 的addListener方法之一注册
 * <p>
 * 此接口实现的调用顺序未指定
 *
 * @see com.hyf.servlet.ServletContext#addListener
 */
public interface HttpSessionAttributeListener extends HttpSessionListener {

    /**
     * 接收已将属性添加到会话的通知
     *
     * @param event HttpSessionBindingEvent对象，包含会话和添加的属性的名称和值
     */
    void attributeAdded(HttpSessionBindingEvent event);

    /**
     * 接收已将属性从会话删除的通知
     *
     * @param event HttpSessionBindingEvent对象，包含会话和删除的属性的名称和值
     */
    void attributeRemoved(HttpSessionBindingEvent event);

    /**
     * 接收会话中某个属性已被替换的通知
     *
     * @param event HttpSessionBindingEvent对象，包含会话和被替换属性的名称和(旧的)值
     */
    void attributeReplaced(HttpSessionBindingEvent event);
}
