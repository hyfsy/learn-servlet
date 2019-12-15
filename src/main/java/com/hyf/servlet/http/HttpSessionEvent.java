package com.hyf.servlet.http;

import java.util.EventObject;

/**
 * 这个类表示web应用程序中会话更改的事件通知
 */
public class HttpSessionEvent extends EventObject {

    private static final long serialVersionUID = 7412813738415131162L;

    /**
     * 从给定会话构造会话事件
     */
    public HttpSessionEvent(HttpSession session) {
        super(session);
    }

    /**
     * 返回已更改的会话
     */
    public HttpSession getSession() {
        return (HttpSession) super.getSource();
    }

}
