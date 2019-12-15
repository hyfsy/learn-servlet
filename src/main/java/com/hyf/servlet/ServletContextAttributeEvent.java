package com.hyf.servlet;

/**
 * 事件类，用于通知web应用程序的ServletContext属性的更改
 *
 * @see ServletContextAttributeListener
 */
public class ServletContextAttributeEvent extends ServletContextEvent {

    private static final long serialVersionUID = -7126871836573579199L;

    private String name;
    private Object value;

    /**
     * 从给定的ServletContext、属性名和属性值构造一个ServletContextAttributeEvent
     *
     * @param source 获取属性改变的ServletContext
     * @param name   指定更改的ServletContext属性的名称
     * @param value  值更改的ServletContext属性的值
     */
    public ServletContextAttributeEvent(ServletContext source, String name, Object value) {
        super(source);
        this.name = name;
        this.value = value;
    }

    /**
     * 获取已更改的ServletContext属性的名称
     *
     * @return 已更改的ServletContext属性的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取已更改的ServletContext属性的值
     * <p>
     * 如果属性被添加，这是属性的值。如果属性被删除，这是被删除属性的值。如果属性被替换，这是属性的旧值
     *
     * @return 已更改的ServletContext属性的值
     */
    public Object getValue() {
        return value;
    }
}
