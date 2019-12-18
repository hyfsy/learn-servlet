package com.hyf.servlet.http;

import org.w3c.dom.events.EventListener;

/**
 * 绑定到会话的对象可以侦听容器事件，通知它们会话将被钝化，该会话将被激活。
 * 需要在 Vms 之间迁移会话或持久会话的容器来通知绑定到实现HttpSessionActivationListener的会话的所有属性
 * <p>
 * 需要编写配置文件，需要通过Attribute将Session和监听器绑定
 */
public interface HttpSessionActivationListener extends EventListener {

    /**
     * 通知会话即将被挂起
     */
    void sessionWillPassivate(HttpSessionEvent se);

    /**
     * 会话刚刚被激活的通知
     */
    void sessionWillActivate(HttpSessionEvent se);
}
