package com.hyf.servlet;

/**
 * 这是一个事件类，用于通知应用程序中servlet请求属性的更改
 *
 * @see ServletRequestAttributeListener
 */
public class ServletRequestAttributeEvent extends ServletRequestEvent {

    private static final long serialVersionUID = 4189199903931260756L;

    private String name;
    private Object value;

    /**
     * 构造一个ServletRequestAttributeEvent，
     * 提供此web应用程序的servlet上下文、属性正在更改的ServletRequest以及属性的名称和值
     *
     * @param context 发送事件的ServletContext
     * @param request 正在发送事件的ServletRequest
     * @param name    指定请求属性的名称
     * @param value   请求属性的值
     */
    public ServletRequestAttributeEvent(ServletContext context, ServletRequest request, String name, Object value) {
        super(context, request);
        this.name = name;
        this.value = value;
    }

    /**
     * 返回在ServletRequest上更改的属性的名称F
     *
     * @return 返回更改后的请求属性的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 返回已添加、删除或替换的属性的值。
     * 如果添加了属性，这就是属性的值。如果删除了属性，这是删除的属性的值。如果属性被替换，这是属性的旧值
     *
     * @return 返回更改后的请求属性的值
     */
    public Object getValue() {
        return value;
    }
}
