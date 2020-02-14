package com.hyf.test.listener.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * HttpSession生命周期监听器
 * <p>
 * 调用#{@link javax.servlet.http.HttpServletRequest#getSession()}才会创建session对象
 * <p>
 * session连接超时或者调用#{@link HttpSession#invalidate()}才会销毁session对象
 */
public class MyHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("session创建了：" + session.getId());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session销毁......");
    }
}
