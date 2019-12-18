package com.hyf.servlet.http;

import java.util.Enumeration;

/**
 * @see HttpSession
 * @see HttpSessionEvent
 * @see HttpSessionBindingListener
 * @deprecated 2.1 因为安全原因，没有替代。该接口将在该API的未来版本中删除
 */
public interface HttpSessionContext {

    /**
     * @deprecated 2.1 没有替代。该方法必须返回null，并将在该API的未来版本中删除
     */
    HttpSession getSession(String sessionId);

    /**
     * @deprecated 2.1 没有替代。该方法必须返回一个空的<code>Enumeration</code>对象，并将在该API的未来版本中删除
     */
    Enumeration<String> getIds();
}
