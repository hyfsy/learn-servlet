package com.hyf.servlet;

/**
 * 会话跟踪模式的枚举
 * <p>
 * track-mode是在Servlet 3.0加入，表示container需要使用什么技术跟踪session id
 * <ol>
 * <li>1、URL：container将session ID嵌入到URL中，这种方式不安全</li>
 * <li>2、COOKIE：使用COOKIE，这种方式安全</li>
 * <li>3、SSL：使用SSL Session ID作为HTTP的session ID，这种最为安全，但是要求所以的请求都是HTTPS的</li>
 * </ol>
 * 可以使用其中一种或者多种，如果不提供URL，则不会在URL中嵌入Session ID，
 * 如果同时采用URL，COOKIE，则优先采用COOKIE，同时也支持URL方式，
 * 如果采用了SSL，就不能采用URL或者COOKIE
 *
 * @see ServletContext#setSessionTrackingModes
 */
public enum SessionTrackingMode {
    COOKIE,
    URL,
    SSL
}
