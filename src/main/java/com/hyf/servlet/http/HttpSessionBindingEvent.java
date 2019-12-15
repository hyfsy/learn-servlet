package com.hyf.servlet.http;

/**
 * 这种类型的事件都是发送到一个实现了{@link HttpSessionBindingListener}接口的对象，从会话绑定或取消绑定时,
 * 或实现了{@link HttpSessionAttributeListener}接口,该接口在部署描述符中配置，在会话中绑定、取消绑定或替换任何属性时使用
 * <p>
 * <code>HttpSession</code>绑定对象通过调用<code>HttpSession.setAttribute</code>，
 * 并通过调用<code>HttpSession.removeAttribute</code>解除绑定对象
 *
 * @see HttpSession
 * @see HttpSessionBindingListener
 * @see HttpSessionAttributeListener
 */
public class HttpSessionBindingEvent extends HttpSessionEvent {

    private static final long serialVersionUID = 3293238926842850540L;

    /**
     * 被绑定或未绑定的对象名称
     */
    private String name;
    /**
     * 被绑定或未绑定的对象
     */
    private Object value;

    /**
     * 构造一个事件，该事件通知对象它已被绑定到会话或从会话解除绑定。
     * 要接收事件，对象必须实现{@link HttpSessionBindingListener}
     *
     * @param session 绑定或解除绑定的对象会话
     * @param name    被绑定或未绑定的对象名称
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name) {
        super(session);
        this.name = name;
    }

    /**
     * 构造一个事件来通知一个对象，该对象已被绑定到会话或从会话解除绑定
     * <p>
     * 要接收事件，对象必须实现{@link HttpSessionBindingListener}
     *
     * @param session 绑定或解除绑定的对象会话
     * @param name    被绑定或未绑定的对象名称
     * @param value   被绑定或未绑定的对象
     * @see #getName
     * @see #getSession
     */
    public HttpSessionBindingEvent(HttpSession session, String name, Object value) {
        super(session);
        this.name = name;
        this.value = value;
    }

    /**
     * 返回属性绑定到会话或从会话解除绑定的名称
     *
     * @return 一个字符串，指定将对象绑定到会话或从会话解除绑定的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 返回已添加、删除或替换的属性的值。
     * 如果属性被添加(或绑定)，这就是属性的值。
     * 如果属性被删除(或未绑定)，这是被删除属性的值。
     * 如果属性被替换，这是属性的旧值
     */
    public Object getValue() {
        return value;
    }
}
