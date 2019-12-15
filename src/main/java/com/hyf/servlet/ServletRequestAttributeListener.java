package com.hyf.servlet;

/**
 * 用于接收有关ServletRequest属性更改的通知事件
 * <p>
 * 当请求在web应用程序范围内时，将生成通知。
 * ServletRequest被定义为当它即将进入web应用程序的第一个servlet或过滤器时进入web应用程序的范围，
 * 当它退出链中的最后一个servlet或第一个过滤器时超出范围
 * <p>
 * 为了接收这些通知事件，实现类必须在web应用程序的部署描述符中声明，
 * 并使用{@link com.hyf.servlet.annotation.WebListener}注解注释或通过定义在{@link ServletContext}上
 * 的addListener方法之一注册
 * <p>
 * 此接口实现的调用顺序未指定
 *
 * @see com.hyf.servlet.ServletContext#addListener
 */
public interface ServletRequestAttributeListener {

    /**
     * 接收属性已被添加到ServletRequest的通知
     *
     * @param event 这个ServletRequestAttributeEvent包含了ServletRequest和添加的属性的名称和值
     */
    void attributeAdded(ServletRequestAttributeEvent event);

    /**
     * 接收属性已从ServletRequest中删除的通知
     *
     * @param event 这个ServletRequestAttributeEvent包含了ServletRequest和删除的属性的名称和值
     */
    void attributeRemoved(ServletRequestAttributeEvent event);

    /**
     * 接收ServletRequest上某个属性已被替换的通知
     *
     * @param event 这个ServletRequestAttributeEvent包含了ServletRequest和被替换属性的名称和(旧的)值
     */
    void attributeReplaced(ServletRequestAttributeEvent event);
}
