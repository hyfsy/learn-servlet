package com.hyf.servlet;

import com.hyf.servlet.http.Cookie;

/**
 * 可用于配置用于会话跟踪目的的cookie的各种属性
 * <p>
 * 这个类的一个实例是通过调用{@link ServletContext#getSessionCookieConfig}获得的
 *
 * @see ServletContext#getSessionCookieConfig()
 * @since 3.0
 */
public interface SessionCookieConfig {

//    String    name;
//    String    domain;
//    String    path;
//    String    comment;
//    boolean   httpOnly;
//    boolean   secure;
//    int       maxAge;

    /**
     * 获取将分配给代表应用程序创建的任何会话跟踪cookie的名称，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     * <p>
     * 默认情况下，<tt>JSESSIONID</tt>将被用作cookie名
     *
     * @return 通过{@link #setName}设置的cookie名，如果{@link #setName}从未被调用，则<tt>null</tt>
     */
    String getName();

    /**
     * 设置将分配给任何代表应用程序创建的会话跟踪cookie的名称，
     * 该应用程序由获取此SessionCookieConfig的<tt>ServletContext</tt>表示
     * <p>
     * 注意:
     * 更改会话跟踪cookie的名称可能会破坏其他层(例如，负载均衡前端)，
     * 这些层假设cookie名称等于默认的<tt>JSESSIONID</tt>，因此应该谨慎执行
     *
     * @param name 指定要使用的cookie名
     * @throws IllegalStateException 如果从中获取此SessionCookieConfig的ServletContext已经初始化
     * @see Cookie#getName()
     */
    void setName(String name);

    /**
     * 获取将分配给代表应用程序创建的任何会话跟踪cookie的域名，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     *
     * @return 通过{@link #setDomain}设置cookie域，如果{@link #setDomain}从未被调用，则<tt>null</tt>
     * @see Cookie#getDomain()
     */
    String getDomain();

    /**
     * 设置将分配给代表应用程序创建的任何会话跟踪cookie的域名，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     *
     * @param domain 要使用的cookie域
     * @throws IllegalStateException 如果从中获取此SessionCookieConfig的ServletContext已经初始化
     * @see Cookie#setDomain(String)
     */
    void setDomain(String domain);

    /**
     * 获取将分配给代表应用程序创建的任何会话跟踪cookie的路径，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     * <p>
     * 默认情况下，将使用获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>的上下文路径
     *
     * @return 通过{@link #setPath}设置cookie路径，如果从未调用{@link #setPath}，则<tt>null</tt>
     * @see Cookie#getPath()
     */
    String getPath();

    /**
     * 获取将分配给代表应用程序创建的任何会话跟踪cookie的路径，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     *
     * @param path 要使用的cookie路径
     * @throws IllegalStateException 如果从中获取此SessionCookieConfig的ServletContext已经初始化
     * @see Cookie#setPath(String)
     */
    void setPath(String path);

    /**
     * 获取将分配给代表应用程序创建的任何会话跟踪cookie的路径，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     *
     * @return 通过{@link #setComment}返回cookie评论集，如果{@link #setComment}从未被调用，则<tt>null</tt>
     * @see Cookie#getComment()
     */
    String getComment();

    /**
     * 获得设置将分配给代表应用程序创建的任何会话跟踪cookie的注释，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     * <p>
     * 作为该调用的一个副作用，会话跟踪cookie将被标记一个<code>Version</code> = <code>1</code> 的属性
     *
     * @param comment 要使用的cookie注释
     * @throws IllegalStateException 如果从中获取此SessionCookieConfig的ServletContext已经初始化
     * @see Cookie#setComment(String)
     * @see Cookie#getVersion()
     */
    void setComment(String comment);

    /**
     * 获得检查代表应用程序创建的会话跟踪cookie是否被标记为<i>HttpOnly</i>，
     * 这些cookie由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     *
     * @return 如果代表应用程序创建的会话跟踪cookie是由<tt>ServletContext</tt>表示的，
     * 则<tt>SessionCookieConfig</tt>是由<tt>SessionCookieConfig</tt>获取的，则标记为<i>HttpOnly</i>，
     * ，则标记为<code>true</code>，否则标记为<code>false</code>
     * @see Cookie#isHttpOnly()
     */
    boolean isHttpOnly();

    /**
     * 获得标记或取消标记为代表应用程序创建的会话跟踪cookie，
     * 这些应用程序由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     * <p>
     * 通过向cookie添加<tt>HttpOnly</tt>属性，将其标记为<tt>HttpOnly</tt>。
     * <i>HttpOnly</i>cookies不应该暴露在客户端脚本代码中，因此可能有助于减轻某些类型的跨站点脚本攻击
     *
     * @param httpOnly 如果代表应用程序创建的会话跟踪cookie(由<tt>ServletContext</tt>表示)被标记为<i>httpOnly</i>，
     *                 则标记为<code>true</code>，否则标记为<code>false</code>
     * @throws IllegalStateException 如果从中获取此SessionCookieConfig的ServletContext已经初始化
     * @see Cookie#setHttpOnly(boolean)
     */
    void setHttpOnly(boolean httpOnly);

    /**
     * 获得检查代表应用程序创建的会话跟踪cookie是否被标记为<i>secure</i>，
     * 即使启动相应会话的请求使用的是普通HTTP而不是HTTPS
     *
     * @return 如果代表获取此<tt>SessionCookieConfig</tt>的<tt>ServletContext</tt>
     * 所代表的应用程序创建的会话跟踪cookie将标记为<i>secure</i>，
     * 即使启动相应会话的请求使用的是纯HTTP而不是HTTPS，返回<code>true</code>
     * 如果它们将标记为<i>secure</i>仅当启动相应会话的请求也是安全的，返回<code>false</code>
     * @see Cookie#getSecure()
     * @see ServletRequest#isSecure()
     */
    boolean isSecure();

    /**
     * 获得标记或取消标记代表应用程序创建的会话跟踪cookie，
     * 这些应用程序由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     * <p>
     * 将会话跟踪cookie标记为<tt>secure</tt>的一个用例是，
     * 即使启动会话的请求来自HTTP，也要支持这样一种拓扑结构，
     * 即web容器前端是由SSL卸载负载均衡器完成的。在这种情况下，客户机和负载均衡器之间的通信将通过HTTPS，
     * 而负载均衡器和web容器之间的通信将通过HTTP
     *
     * @param secure 如果代表由<tt>ServletContext<tt>表示的应用程序创建的会话跟踪cookie
     *               （从中获取此<tt SessionCookieConfig<tt>）应标记为<i>secure<i>，
     *               即使发起相应会话的请求使用的是纯HTTP而不是HTTPS，
     *               如果它们应标记为<i>secure<i>的，则返回<code>true</code>，否则返回<code>false</code>
     * @throws IllegalStateException 如果从中获取此SessionCookieConfig的ServletContext已经初始化
     * @see Cookie#setSecure(boolean)
     * @see ServletRequest#isSecure()
     */
    void setSecure(boolean secure);

    /**
     * 获得获取代表应用程序创建的会话跟踪cookie的生存期(以秒为单位)
     * 这些应用程序由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     * <p>
     * 默认情况下，返回<tt>-1</tt>
     *
     * @return 获得代表应用程序创建的会话跟踪cookie的生存期(以秒为单位)，这些应用程序由<tt>ServletContext</tt>表示，
     * 从该<tt>SessionCookieConfig</tt>获得
     * @see Cookie#getMaxAge()
     */
    int getMaxAge();

    /**
     * 为代表应用程序创建的会话跟踪cookie设置生存期(以秒为单位)，
     * 这些应用程序由<tt>ServletContext</tt>表示，从该<tt>SessionCookieConfig</tt>获得
     *
     * @param maxAge 会话跟踪cookie的生存期(以秒为单位)，这些应用程序由<tt>ServletContext</tt>表示，
     *               从该<tt>SessionCookieConfig</tt>获得
     * @throws IllegalStateException 如果从中获取此SessionCookieConfig的ServletContext已经初始化
     * @see Cookie#setMaxAge(int)
     */
    void setMaxAge(int maxAge);

}
