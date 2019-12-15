package com.hyf.servlet.http;

import java.util.EventListener;

/**
 * 使对象在绑定到会话或从会话解除绑定时得到通知。
 * 对象通过{@link HttpSessionBindingEvent}对象获取通知。
 * 这可能是servlet编程者显式地从会话中取消属性绑定的结果，
 * 也可能是由于会话无效，也可能是由于会话超时
 *
 * @see HttpSession
 * @see HttpSessionBindingEvent
 */
public interface HttpSessionBindingListener extends EventListener {

    /**
     * 通知对象它正被绑定到会话并标识会话
     *
     * @param event 标识会话的事件
     */
    void valueBound(HttpSessionBindingEvent event);

    /**
     * 通知对象它正在从会话中解除绑定，并标识会话
     *
     * @param event 标识会话的事件
     */
    void valueUnBound(HttpSessionBindingEvent event);
}
