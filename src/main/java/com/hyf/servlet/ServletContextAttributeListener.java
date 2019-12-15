package com.hyf.servlet;

/**
 * 用于接收关于ServletContext属性更改的通知事件
 * <p>
 * 为了接收这些通知事件，实现类必须在web应用程序的部署描述符中声明，
 * 并通过{@link com.hyf.servlet.annotation.WebListener}注解注释或使用定义在{@link ServletContext}上的addListener方法之一注册
 * <p>
 * 此接口实现的调用顺序未指定
 *
 * @see com.hyf.servlet.ServletContext#addListener
 * @see ServletContextAttributeEvent
 */
public interface ServletContextAttributeListener {

    /**
     * 接收通知，当属性已被添加到ServletContext
     *
     * @param event ServletContextAttributeEvent对象，其中包含添加属性后的ServletContext以及属性名和值
     */
    void attributeAdded(ServletContextAttributeEvent event);

    /**
     * 接收属性已从ServletContext中删除的通知
     *
     * @param event ServletContextAttributeEvent对象，其中包含删除属性后的ServletContext以及属性名和值
     */
    void attributeRemoved(ServletContextAttributeEvent event);

    /**
     * 接收servlet上下文中某个属性已被替换的通知
     *
     * @param event ServletContextAttributeEvent对象，其中包含替换了属性后的ServletContext，以及属性名及其旧值
     */
    void attributeReplaced(ServletContextAttributeEvent event);
}
