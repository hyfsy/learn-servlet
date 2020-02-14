package com.hyf.test.listener.session;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;

/**
 * HttpSession绑定解绑监听器 | HttpSession活化钝化监听器
 * <p>
 * 对象必须实现序列化接口，不需要在web.xml中注册监听器
 */
public class User implements Serializable, HttpSessionBindingListener, HttpSessionActivationListener {

    private static final long serialVersionUID = 1167862100299528814L;

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("user对象绑定了：" + event.getValue());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("user对象解绑了：" + this);
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("user对象将要和session一起钝化了.....");
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println("user对象和session一起活化了");
    }
}
