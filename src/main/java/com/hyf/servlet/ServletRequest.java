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
}
