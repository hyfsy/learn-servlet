package com.hyf.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 定义向servlet提供客户端请求信息的对象
 * servlet容器创建一个<code>ServletRequest</code>对象并传递它作为servlet的<code>service</code>方法的参数
 * <p>
 * 扩展<code>ServletRequest</code>可以提供的接口,附加协议特定的数据
 * 例如，HTTP协议:由{@link com.hyf.servlet.http.HttpServletRequest}提供
 */
public interface ServletRequest {

    /**
     * 可通过两种方法设置：
     * 1.servlet容器可以设置属性，以提供有关请求的自定义信息
     * 2.{@link ServletRequest#setAttribute},这允许信息嵌入到一个请求在调用${ServletDispatcher}之前
     *
     * @param name 属性名
     * @return 返回名称对应的属性，如果不存在返回null
     */
    Object getAttribute(String name);

    /**
     * 返回一个<code>Enumeration</code>，其中包含此请求可用属性的名称
     *
     * @return 返回包含属性的<code>Enumeration</code>，如果没有属性返回一个空的<code>Enumeration</code>
     */
    Enumeration<Object> getAttributeNames();

    /**
     * 返回在正文中使用的字符集编码名称
     *
     * @return 返回一个字符串编码，如果没有指定编码，返回<code>null</code>
     */
    String getCharacterEncoding();

    /**
     * 重写此正文中使用的字符编码的名称请求
     * 必须在读取请求参数之前调用此方法或者使用getReader()读取输入。否则，就没有效果
     *
     * @param env 字符编码名称
     * @throws UnsupportedEncodingException 在设置字符编码的状态时，指定的编码无效
     */
    void setCharacterEncoding(String env) throws UnsupportedEncodingException;

    /**
     * 返回请求体的长度，以字节为单位
     * 如果请求体的长度未知或大于 Integer.MAX_VALUE ,则返回 -1
     * 对于HTTP servlet，与CGI变量CONTENT_LENGTH的值相同
     *
     * @return 请求体的长度
     */
    int getContentLength();

    /**
     * 返回请求体的长度，以字节为单位
     * 如果请求体的长度未知或大于 Integer.MAX_VALUE ,则返回 -1L
     * 对于HTTP servlet，与CGI变量CONTENT_LENGTH的值相同
     *
     * @return 请求体的长度
     * @since 3.1
     */
    long getContentLengthLong();

    /**
     * 返回请求体的MIME类型,如果类型未知，返回 <code>null</code>
     * 对于HTTP servlet，与CGI变量CONTENT_TYPE的值相同
     *
     * @return 返回MIME类型，如果类型未知则为null
     */
    String getContentType();

    /**
     * 通过{@link ServletInputStream}将请求体作为二进制数据检索
     * 可以通过 {@link #getReader}来读取正文，不能同时使用
     *
     * @return 返回一个在当前请求中的 {@link ServletInputStream} 对象
     * @throws IllegalStateException 如果{@link #getReader}在这个请求中已经被调用了
     * @throws IOException           如果发生输入或输出异常
     */
    ServletInputStream getInputStream() throws IOException;

    /**
     * 返回请求参数值，如果不存在，返回null
     * 请求参数是随请求一起发送的额外信息。对于HTTP servlet，参数包含在查询字符串或提交的表单数据中
     * 1.如果只有一个值时使用此方法，如果可能存在多个值，使用{@link #getParameterValues}
     * 2.如果使用此方法并且存在多个值，返回 <code>getParameterValues</code>返回的数组中的第一个值
     * 3.如果数据是在请求体中的，例如POST请求，通过{@link #getInputStream}或{@link #getReader()}可以干扰执行此方法
     *
     * @param name 指定参数的名称
     * @return 返回一个 <code>String</code> 表示的单个值参数
     * @see #getParameterValues
     */
    String getParameter(String name);

    /**
     * 返回 在本请求中 包含所包含参数的名称的<code>Enumeration</code>
     * 如果请求没有参数，方法返回一个空的<code>Enumeration</code>
     *
     * @return 包含请求参数名称的<code>Enumeration</code>
     */
    Enumeration<String> getParameterNames();

    /**
     * 返回一个包含<code>String</code>对象的数组，存放给定请求参数的所有值
     * 如果参数不存在则返回<code>null</code>
     * <p>
     * 数组表示为前端相同name传递的参数
     *
     * @param name 请求参数名称
     * @return 包含参数值的对象数组
     * @see #getParameter
     */
    String[] getParameterValues(String name);

    /**
     * 获取对应参数名称的值<code>Map</code>对象
     * key为参数名称，value为参数值数组
     * key为字符串类型，value是字符串数组类型
     * <p>
     * 数组表示为前端相同name传递的参数
     *
     * @param name 属性名称
     * @return 存放对应名称的属性值的不可变的Map对象
     */
    Map<String, String[]> getParameterMap(String name);

    /**
     * 返回请求使用的协议的名称和版本
     * 例如：HTTP / 1.1
     * 对于HTTP servlet，与CGI变量SERVER_PROTOCOL的值相同
     *
     * @return 返回一个包含协议的<code>String</code>名称和版本号
     */
    String getProtocol();

    /**
     * 返回用于发出此请求的方案的名称，例如：
     * <code>http</code>，<code>https</code>，<code>ftp</code>
     * 不同的方案有不同的url构造规则
     *
     * @return 返回一个用于提出该请求的方案(Scheme)名称的<code>String</code>
     */
    String getScheme();

    /**
     * 返回发送该请求的服务器主机名（在：前的主机头值）
     * 或者解析后的服务器名称、服务器IP地址
     *
     * @return 服务名
     */
    String getServerName();

    /**
     * 获取发送请求的端口号，是主机中：后的部分
     *
     * @return 返回一个指定端口号的整数
     */
    int getServerPort();

    /**
     * 通过{@link BufferedReader }使用字符数据检索请求体
     * 可以通过 {@link #getInputStream}来读取正文，不能同时使用
     *
     * @return 返回一个包含请求体的<code>BufferedReader</code>对象
     * @throws UnsupportedEncodingException 如果设置的编码不支持，并且文本不能被解码
     * @throws IllegalStateException        如果 {@link #getInputStream}在这个请求中已经被调用了
     * @throws IOException                  如果发生输入或输出异常
     */
    BufferedReader getReader() throws IOException;

    /**
     * 返回客户端的Internet协议(IP)地址
     * 对于HTTP servlet，与CGI的变量REMOTE_ADDR的值相同
     *
     * @return 返回一个包含请求的IP地址, 并非URL上的IP
     */
    String getRemoteAddr();

    /**
     * 返回客户端的完全限定名或发送请求的最后一个代理
     * 如果引擎不能或选择不解析主机名(为了提高性能)，此方法返回点字符串形式的IP地址
     * 对于HTTP servlet，与CGI变量REMOTE_HOST的值相同
     *
     * @return 包含完整的客户的限定名称
     */
    String getRemoteHost();

    /**
     * 在当前请求中存储属性，在请求之间重置属性，通常与{@link RequestDispatcher}一起使用
     * 如果传入的对象为空，则效果与调用{@link #removeAttribute}一样
     * 当请求通过{@link RequestDispatcher}被分发到其他的Web应用程序,调用servlet中可能无法正确检索此方法设置的对象
     *
     * @param name 指定属性的名称
     * @param o    <code>o</code>被存放起来
     */
    void setAttribute(String name, Object o);

    /**
     * 此方法并不是很需要使用，因为属性只持续到 请求被处理结束
     *
     * @param name 指定要删除的属性的名称
     */
    void removeAttribute(String name);

    /**
     * 返回客户端首选的<code>Locale</code>
     * 根据请求头中的 Accept-Language 获取
     * 如果客户端请求没有提供 Accept-Language 头信息，此方法返回服务器的默认语言环境
     *
     * @return 返回客户端首选的<code>Locale</code>
     */
    Locale getLocale();

    /**
     * 返回<code>Enumeration</code> <code>Locale</code>对象
     * 表示从首选语言环境开始依次递减，基于接受语言的客户端可接受的语言环境头
     * 如果客户端请求没有提供 Accept-Language 头信息，这个方法返回一个<code>枚举</code>，包含一个<code>Locale</code>，为服务器的默认语言环境
     *
     * @return 返回一个首选的服务器语言环境
     */
    Enumeration<Locale> getLocales();

    /**
     * 返回一个布尔值，指示是否使用安全通道，例如HTTPS
     *
     * @return 返回一个布尔值，指示请求是否使用安全通道
     */
    boolean isSecure();

    /**
     * 返回一个充当包装器的{@link RequestDispatcher}对象位于给定路径上的资源
     * 一个<code>RequestDispatcher</code> 对象可以用来转发对资源的请求或在响应中包含资源
     * 资源可以是动态的，也可以是静态的
     * 指定的路径名可能是相对的，但它不能扩展当前servlet上下文之外。如果这条路始于 "/" 被解释为相对于当前上下文根
     * 返回 <code>RequestDispatcher</code> 或 <code>null</code>
     * 此方法与{@link ServletContext #getRequestDispatcher}是这个方法可以取一个相对路径
     *
     * @param path 指定路径名到资源。如果它是相对的，那它一定是相对于当前servlet
     * @return 返回一个<code>RequestDispatcher</code> 对象资源在指定路径上的包装，或返回一个<code>null</code>
     */
    RequestDispatcher getRequestDispatcher(String path);

    /**
     * @deprecated Java Servlet API 2.1后废弃
     * 使用{@link ServletContext#getRealPath}代替
     */
    String getRealPath();

    /**
     * 返回客户端的Internet协议(IP)源端口或发送请求的最后一个代理
     *
     * @return 返回一个指定端口号的整数
     */
    int getRemotePort();

    /**
     * 返回收到请求的Internet协议(IP)接口的主机名
     *
     * @return 返回一个包含主机的<code>String</code>接收请求的IP的名称
     */
    String getLocalName();

    /**
     * 返回收到请求的Internet协议(IP)接口地址
     *
     * @return 接收请求的IP的地址
     */
    String getLocalAddr();

    /**
     * 返回收到请求的Internet协议(IP)接口的端口号
     *
     * @return 指定端口号的整数
     */
    int getLocalPort();

    /**
     * 获取该请求最后一次分发后的{@link ServletContext}对象
     *
     * @return 请求最后一次分发后的{@link ServletContext}对象
     * @since 3.0
     */
    ServletContext getServletContext();

    /**
     * 将此请求放入异步模式，并初始化其{@link AsyncContext}与原始的(未包装的)ServletRequest和ServletResponse对象
     * 调用此方法将导致关联的提交响应被延迟到{@link AsyncContext#complete}，否则，异步调用操作超时
     * 用返回的AsyncContext调用{@link AsyncContext#hasOriginalRequestAndResponse()}将返回<code>true</code>
     * 此方法清除在AsyncContext注册的{@link AsyncListener}实例的列表
     * 在调用每个startAsync前调用AsyncListener的{@link AsyncListener#onStartAsync onStartAsync}方法
     * 此方法的后续调用，或其重载，将返回相同的AsyncContext实例，重新初始化
     *
     * @return 返回(重新)初始化的AsyncContext
     * @throws IllegalStateException 如果这个请求在一个过滤器的范围内
     *                               servlet不支持异步，即调用{@link #isAsyncSupported}返回false
     *                               在没有任何异步分发的情况下再次调用此方法，结果由{@link AsyncContext#dispatch}方法产生
     *                               在任何此类分派的范围之外调用，或在相同的分发范围内
     *                               如果响应已经关闭了
     * @since 3.0
     */
    AsyncContext startAsync() throws IllegalStateException;

    /**
     * 将此请求放入异步模式，并初始化其{@link AsyncContext}与给定的请求和响应对象
     * 必须有相同的ServletRequest和ServletResponse实例参数，
     * 或者{@link ServletRequestWrapper}和{@link ServletResponseWrapper}包装它们，
     * 它们被传递到{@link Servlet#service service}方法的Servlet或{@link Filter#doFilter doFilter}方法
     * <p>
     * 调用{@link AsyncContext#hasOriginalRequestAndResponse}返回<code>false</code>
     * 除非传入ServletRequest和ServletResponse参数是原装的，或不带任何应用程式提供的包装
     * 此后在<i>outbound（出站）</i>方向调用的任何过滤器，请求被放入异步模式，可以以此作为提示
     * 他们添加的一些请求和/或响应包装在其<i>inbound（入站）</i>
     * 调用期间可能需要停留在指定位置，异步操作的持续时间及其关联资源可能不会被释放
     * <p>
     * 在<i>入站</i>期间应用的ServletRequestWrapper，过滤器的调用可以由<i>outbound</i>释放
     * 只有在给定的<code>servletRequest</code>：
     * 用来初始化<code>AsyncContext</code>并且由{@link AsyncContext#getRequest()}返回的<code>servletRequest</code>对象
     * 不包含<code>ServletRequestWrapper</code>，这同样适用于<code>ServletResponseWrapper</code>实例
     *
     * @param req  servletRequest变量用来初始化<code>AsyncContext</code>
     * @param resp servletResponse变量用来初始化<code>AsyncContext</code>
     * @return 返回(重新)初始化的AsyncContext
     * @throws IllegalStateException 见上面的重载方法
     * @since 3.0
     */
    AsyncContext startAsync(ServletRequest req, ServletResponse resp) throws IllegalStateException;

    /**
     * 检查此请求是否已进入异步模式
     * 一个ServletRequest通过调用{@link #startAsync}或{@link #startAsync(ServletRequest, ServletResponse)}进入异步模式
     * <p>
     * 如果这个请求已经进入异步模式，但使用一个{@link AsyncContext#dispatch}方法或
     * 从异步模式通过调用{@link AsyncContext#complete}释放了资源，返回<code>false</code>
     *
     * @return 如果这个请求已经进入异步模式，则返回<code>true</code>，否则返回<code>false</code>
     * @since 3.0
     */
    boolean isAsyncStarted();

    /**
     * 检查此请求是否支持异步操作
     * 如果这个请求在未被注释的过滤器或servlet的范围内或在部署描述符中标记为能够支持异步处理，异步操作会禁用这个请求
     *
     * @return 如果这个请求支持异步操作，则返回<code>true</code>，否则返回<code>false</code>
     * @since 3.0
     */
    boolean isAsyncSupported();

    /**
     * 返回当前请求最近一次调用{@link #startAsync}或{@link #startAsync(ServletRequest, ServletResponse)}创建或初始化的<code>AsyncContext</code>
     *
     * @return 当前请求最近一次调用{@link #startAsync}或{@link #startAsync(ServletRequest, ServletResponse)}创建或初始化的<code>AsyncContext</code>
     * @throws IllegalStateException 如果该请求不是异步模式
     *                               如果没有用{@link #startAsync}或{@link #startAsync(ServletRequest, ServletResponse)}调用
     * @since 3.0
     */
    AsyncContext getAsyncContext();

    /**
     * 获取此请求的分发类型
     * 一个请求的dispatcher类型被容器使用，选择需要应用于该项要求的过滤器，只有匹配dispatcher类型和url模式的过滤器才会被应用
     * 允许一个过滤器配置了多个dispatcher类型时通过分发类型用于查询一个请求
     * 允许过滤器根据不同的分发类型处理请求
     * <p>
     * 一个请求的初始分发类型为：<cdoe>DispatcherType.REQUEST</cdoe>
     * 通过调用{@link RequestDispatcher#forward(ServletRequest, ServletResponse)}转变为<code>DispatcherType.FORWARD</code>
     * 通过调用{@link RequestDispatcher#include(ServletRequest, ServletResponse)}转变为<code>DispatcherType.INCLUDE</code>
     * 当一个异步请求调用其中一个{@link AsyncContext#dispatch}方法后，类型转变为<code>DispatcherType.ASYNC</code>
     * 请求的分发类型通过容器的错误处理将请求发送到错误页，转变为<code>DispatcherType.ERROR</code>
     *
     * @return 当前请求的分发类型
     * @since 3.0
     */
    DispatcherType getDispatcherType();
}
