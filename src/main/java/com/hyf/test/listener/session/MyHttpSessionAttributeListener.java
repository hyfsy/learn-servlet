package com.hyf.test.listener.session;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * HttpSession属性变化监听器
 * <p>
 * 注意#{@link #attributeReplaced(HttpSessionBindingEvent)}方法
 */
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("session属性增加：[" + event.getName() + ", " + event.getValue() + "]");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("session属性移除：" + event.getName());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        // 为移除前的值
        Object value = event.getValue();
        // 为移除后的值
        Object attribute = event.getSession().getAttribute(event.getName());

        System.out.println("session属性改变：[" + event.getName() + ", " + value + " -> " + attribute + "]");
    }
}
