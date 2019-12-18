package com.hyf.servlet.http;

import com.hyf.servlet.ServletContext;

import java.util.Enumeration;

/**
 * JSESSIONID问题：
 * <p>
 * 在访问tomcat服务器HttpServletRequest的getSession(true)的时候创建
 * tomcat的ManagerBase类提供创建sessionid的方法：随机数+时间+jvmid
 * （jvm的id值会根据服务器的硬件信息计算得来，因此不同jvm的id值都是唯一的）
 * <p>
 * tomcat的StandardManager类将session存储在内存中，也可以持久化到file，数据库，memcache，redis等
 * 客户端只保存sessionid到cookie中
 * <p>
 * session销毁只能通过invalidate或超时，关掉浏览器并不会关闭session
 * <p>
 * <p>
 * 提供一种方法来跨多个页面请求或访问Web站点来标识用户，并存储有关该用户的信息。
 * <p>
 * servlet容器使用这个接口在HTTP客户端和HTTP服务器之间创建会话。
 * 会话在来自用户的多个连接或页面请求之间持续存在指定的时间段。
 * 一个会话通常对应一个用户，该用户可能多次访问一个站点。
 * 服务器可以以多种方式维护会话，比如使用cookie或重写url。
 * <p>
 * 此接口允许servlet：
 * <ul>
 * <li>查看和操作有关会话的信息，如会话标识符、创建时间和最后访问时间
 * <li>将对象绑定到会话，允许用户信息跨多个用户连接持久存储
 * </ul>
 * <p>
 * 当应用程序在会话中存储对象或从会话中删除对象时，会话检查该对象是否实现{@link HttpSessionBindingListener}。
 * 如果它这样做了，servlet将通知对象它已被绑定到会话或从会话解除绑定。
 * 在绑定方法完成后发送通知。对于无效或过期的会话，将在会话无效或过期后发送通知。
 * <p>
 * 当容器在分布式容器设置的 Vms 之间迁移一个会话时（先序列化，再反序列化，大概就是这个意思，要写配置文件），
 * 实现{@link HttpSessionActivationListener}接口的所有会话属性都会得到通知
 * <p>
 * servlet应该能够处理客户端不选择加入会话的情况，例如当有意关闭cookie时。
 * 在客户端加入会话之前，<code>isNew</code>返回<code>true</code>。
 * 如果客户端选择不加入会话，<code>getSession</code>将对每个请求返回不同的会话，
 * <code>isNew</code>将始终返回<code>true</code>
 * <p>
 * 会话信息的作用域仅限于当前的web应用程序(<code>ServletContext</code>)，
 * 因此存储在一个上下文中的信息在另一个上下文中不会直接可见
 *
 * @see HttpSessionBindingListener
 * @see HttpSessionActivationListener
 * @see HttpSessionContext
 */
public interface HttpSession {

    /**
     * 返回创建此会话的时间，以毫秒为单位，从格林威治时间1970年1月1日午夜开始计算
     *
     * @return 一个<code>long</code>指定这个会话是何时创建的，从格林威治时间1970年1月1日开始以毫秒为单位表示
     * @throws IllegalStateException 如果在无效会话上调用此方法
     */
    long getCreationTime();

    /**
     * 返回一个包含分配给此会话的唯一标识符的字符串。标识符由servlet容器分配，并且依赖于实现
     *
     * @return 指定分配给该会话的标识符的字符串
     */
    String getId();

    /**
     * 返回客户端发送与此会话关联的请求的最后一次时间，即从1970年1月1日格林威治时间午夜开始的毫秒数，并由容器接收请求的时间标记
     * <p>
     * 您的应用程序采取的行动，如获取或设置与会话相关联的值，不影响访问时间
     *
     * @return 一个<code>long</code>表示客户端发送与此会话相关的请求的最后一次时间，以毫秒为单位表示，从1/1/1970 GMT开始
     * @throws IllegalStateException 如果在无效会话上调用此方法
     */
    long getLastAccessedTime();

    /**
     * 返回此会话所属的ServletContext
     *
     * @return 返回web应用程序的ServletContext对象
     */
    ServletContext getServletContext();

    /**
     * 返回servlet容器在客户端访问之间保持会话打开的最大时间间隔(以秒为单位)。
     * 在此间隔之后，servlet容器将使会话无效。
     * 可以使用<code>setMaxInactiveInterval</code>方法设置最大时间间隔
     * <p>
     * 返回值为0或更少，表示该会话永远不会超时
     *
     * @return 一个整数，指定此会话在客户端请求之间保持打开的秒数
     */
    int getMaxInactiveInternal();

    /**
     * 此方法表示当前session在指定时间内若没有与服务器发生任何交互后失效(以秒为单位)
     * <p>
     * 期间用户的任何活动都将刷新session的失效时间，例如在10秒内用户刷新页面将重新计算失效时间
     * <p>
     * <tt>interval</tt>值为0或更少表示该会话不应该超时
     *
     * @param internal 指定秒数的整数
     */
    void setMaxInactiveInternal(int internal);

    /**
     * @deprecated 2.1 无替代方法，它将在Java Servlet API的未来版本中被删除
     */
    HttpSessionContext getHttpSessionContext();

    /**
     * 在此会话中返回与指定名称绑定的对象，或<code>null</code>(如果没有在该名称下绑定对象)
     *
     * @param name 一个指定对象名称的字符串
     * @return 返回指定名称的对象
     * @throws IllegalStateException 如果在无效会话上调用此方法
     */
    Object getAttribute(String name);

    /**
     * @param name 一个指定对象名称的字符串
     * @return 返回指定名称的对象
     * @throws IllegalStateException 如果在无效会话上调用此方法
     * @deprecated 2.2 用{@link #getAttribute(String)} 代替
     */
    Object getValue(String name);

    /**
     * 返回一个<code>String</code>类型的<code>Enumeration</code>对象，其中包含绑定到此会话的所有对象的名称
     *
     * @return <code>String</code>类型的<code>Enumeration</code>对象，其中包含绑定到此会话的所有对象的名称
     * @throws IllegalStateException 如果在无效会话上调用此方法
     */
    Enumeration<String> getAttributeNames();

    /**
     * @return 指定绑定到此会话的所有对象的名称的<code>String</code>对象数组
     * @throws IllegalStateException 如果在无效会话上调用此方法
     * @deprecated 2.1 用{@link #getAttributeNames()} 代替
     */
    String[] getValueNames();

    /**
     * 使用指定的名称将对象绑定到此会话。如果已经将同名对象绑定到会话，则将替换该对象
     * <p>
     * 执行此方法后，如果新对象实现了<code>HttpSessionBindingListener</code>，
     * 则容器调用{@link HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)}。
     * 然后容器通知web应用程序中的所有<code>HttpSessionAttributeListener</code>
     * <p>
     * 如果一个实现了<code>HttpSessionBindingListener</code>的对象绑定到这个名称的会话，
     * 则调用其<code>HttpSessionBindingListener.valueUnbound</code>方法
     * <p>
     * 如果传入的值是null，这与调用<code>removeAttribute()<code>具有相同的效果
     *
     * @param name  对象绑定到的名称;不能为空
     * @param value 指定要绑定的对象的值
     * @throws IllegalStateException 如果在无效会话上调用此方法
     */
    void setAttribute(String name, Object value);

    /**
     * @param name  对象绑定到的名称;不能为空
     * @param value 要绑定的对象;不能为空
     * @throws IllegalStateException 如果在无效会话上调用此方法
     * @deprecated 2.2 用{@link #setAttribute(String, Object)} 代替
     */
    void putValue(String name, Object value);

    /**
     * 从此会话中移除与指定名称绑定的对象。如果会话没有与指定名称绑定的对象，则此方法不执行任何操作
     * <p>
     * 执行此方法后，如果新对象实现了<code>HttpSessionBindingListener</code>，
     * 则容器调用{@link HttpSessionBindingListener#valueBound(HttpSessionBindingEvent)}。
     * 然后容器通知web应用程序中的所有<code>HttpSessionAttributeListener</code>
     *
     * @param name 指定要从会话中删除的对象的名称
     * @throws IllegalStateException 如果在无效会话上调用此方法
     */
    void removeAttribute(String name);

    /**
     * @param name 指定要从会话中删除的对象的名称
     * @throws IllegalStateException 如果在无效会话上调用此方法
     * @deprecated 2.2 用{@link #removeAttribute(String)} 代替
     */
    void removeValue(String name);

    /**
     * 使此会话无效，然后解绑它所有绑定的对象
     *
     * @throws IllegalStateException 如果在无效会话上调用此方法
     */
    void invalidate();

    /**
     * 返回<code>true</code>,如果客户端还不知道会话或者客户端选择不加入会话。
     * 例如，如果服务器只使用基于cookie的会话，而客户端已经禁用了cookie的使用，那么每个请求上的会话都是新的
     *
     * @return 如果服务器已经创建了一个会话，但是客户端还没有加入，返回<code>true</code>，否则返回<code>false</code>
     */
    boolean isNew();
}
