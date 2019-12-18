package com.hyf.servlet.http;

import com.hyf.servlet.ServletResponse;

import java.io.IOException;
import java.util.Collection;

/**
 * 扩展{@link ServletResponse}接口，在发送响应时提供http特有的功能。例如，它有访问HTTP头文件和cookie的方法
 * <p>
 * servlet容器创建一个<code>HttpServletResponse</code>对象，
 * 并将其作为参数传递给servlet的服务方法(<code>doGet</code>，<code>doPost</code>，等等)
 * <p>
 * 2XX系列是指成功；
 * 3XX系列是指重定向；
 * 4XX系列是指客户端错误；
 * 5XX系列是指服务器错误
 *
 * @see com.hyf.servlet.ServletResponse
 */
public interface HttpServletResponse extends ServletResponse {

    /*
     * Server status codes; see RFC 2068.
     */

    /**
     * 状态码(100)，指示客户端可以继续
     */
    int SC_CONTINUE = 100;

    /**
     * 状态码(101)，指示服务器正在根据升级报头切换协议
     */
    int SC_SWITCHING_PROTOCOLS = 101;

    /**
     * 状态码(200)，指示请求正常成功
     */
    int SC_OK = 200;

    /**
     * 状态码(201)，表示请求成功并在服务器上创建了一个新资源
     */
    int SC_CREATED = 201;

    /**
     * 状态码(202)，表示一个请求已被接受处理，但尚未完成
     */
    int SC_ACCEPTED = 202;

    /**
     * 状态码(203)，表示客户机提供的元信息不是来自服务器
     */
    int SC_NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * 状态码(204)，指示请求成功，但没有新信息返回
     */
    int SC_NO_CONTENT = 204;

    /**
     * 状态码(205)，指示代理<em>SHOULD</em>导致发送请求的文档视图重置
     */
    int SC_RESET_CONTENT = 205;

    /**
     * 状态码(206)，表示服务器已经完成了对资源的部分GET请求
     */
    int SC_PARTIAL_CONTENT = 206;

    /**
     * 状态码(300)，表示请求的资源对应于一组表示中的任意一个，每个表示都有自己的特定位置
     */
    int SC_MULTIPLE_CHOICES = 300;

    /**
     * 状态码(301)，表示资源已经永久地移动到一个新位置，并且未来的引用应该使用一个新的URI来处理它们的请求
     */
    int SC_MOVED_PERMANENTLY = 301;

    /**
     * 状态码(302)，表示资源已经临时移动到另一个位置，但是未来的引用仍然应该使用原始URI来访问资源
     * <p>
     * 为了向后兼容，保留了这个定义。SC_FOUND现在是首选的定义
     */
    int SC_MOVED_TEMPORARILY = 302;

    /**
     * 状态码(302)，表示资源暂时驻留在一个不同的URI下。
     * 因为重定向有时可能会改变，所以客户端应该继续为将来的请求使用Request-URI (HTTP/1.1)来表示状态码(302)，建议使用这个变量
     *
     * @see #sendRedirect(String)
     */
    int SC_FOUND = 302;

    /**
     * 状态码(303)，表示可以在不同的URI下找到对请求的响应
     */
    int SC_SEE_OTHER = 303;

    /**
     * 状态码(304)，指示条件GET操作发现资源可用且未修改
     */
    int SC_NOT_MODIFIED = 304;

    /**
     * 状态码(305)，指示所请求的资源<em>SHOULD</em>可通过<code><em>Location</em></code>字段提供的代理访问
     */
    int SC_USE_PROXY = 305;

    /**
     * 状态码(307)，表示请求的资源暂时驻留在一个不同的URI下。
     * 临时URI <em>SHOULD</em>由响应中的<code><em>位置</em></code>字段提供
     */
    int SC_TEMPORARY_REDIRECT = 307;

    /**
     * 状态码(400)，表示客户端发送的请求在语法上不正确
     */
    int SC_BAD_REQUEST = 400;

    /**
     * 状态码(401)，指示请求需要HTTP身份验证
     */
    int SC_UNAUTHORIZED = 401;

    /**
     * 状态码(402)，保留以后使用
     */
    int SC_PAYMENT_REQUIRED = 402;

    /**
     * 状态码(403)，表示服务器理解请求，但拒绝执行请求
     */
    int SC_FORBIDDEN = 403;

    /**
     * 状态码(404)，指示所请求的资源不可用
     */
    int SC_NOT_FOUND = 404;

    /**
     * 状态码(405)，表示<code><em>Request-Line</em></code>中指定的方法对于<code><em>Request-URI</em></code>所标识的资源是不允许的
     */
    int SC_METHOD_NOT_ALLOWED = 405;

    /**
     * 状态码(406)，表示由请求标识的资源仅能够根据在请求中发送的accept报头生成 具有不可接受的内容特征的响应实体
     */
    int SC_NOT_ACCEPTABLE = 406;

    /**
     * 状态码(407)，指示客户端<em>SHOULD</em>首先使用代理进行身份验证
     */
    int SC_PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * 状态码(408)，表明客户端在服务器准备等待的时间内没有产生请求
     */
    int SC_REQUEST_TIMEOUT = 408;

    /**
     * 状态码(409)，指示由于与资源的当前状态发生冲突，请求无法完成
     */
    int SC_CONFLICT = 409;

    /**
     * 状态码(410)，表示资源在服务器上不再可用，且没有已知的转发地址。这种情况<em>SHOULD</em>被认为是永久性的
     */
    int SC_GONE = 410;

    /**
     * 状态码(411)，表示没有定义<code><em>Content-Length</em></code>，不能处理请求
     */
    int SC_LENGTH_REQUIRED = 411;

    /**
     * 状态码(412)，表示在服务器上测试时，一个或多个请求头字段中给定的前提条件被赋值为false
     */
    int SC_PRECONDITION_FAILED = 412;

    /**
     * 状态码(413)，表示服务器拒绝处理请求，因为请求实体比服务器愿意或能够处理的大
     */
    int SC_REQUEST_URI_TOO_LARGE = 413;

    /**
     * 状态码(414)，表示服务器拒绝服务请求，因为<code><em>Request-URI</em></code>比服务器愿意解释的长
     */
    int SC_REQUEST_URI_TOO_LONG = 414;

    /**
     * 状态码(415)，表示服务器拒绝为请求提供服务，因为请求的实体的格式不被请求的资源所支持
     */
    int SC_UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * 状态码(416)，表示服务器不能满足请求的字节范围
     */
    int SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * 状态码(417)，表示服务器不能满足Expect请求头中给出的期望
     */
    int SC_EXPECTATION_FAILED = 417;

    /**
     * 状态码(500)，指示HTTP服务器内部的一个错误，阻止它完成请求
     */
    int SC_INTERNAL_SERVER_ERROR = 500;

    /**
     * 状态码(501)，表示HTTP服务器不支持完成请求所需的功能
     */
    int SC_NOT_IMPLEMENTED = 501;

    /**
     * 状态码(502)，表示HTTP服务器在充当代理或网关时从其咨询的服务器接收到无效响应
     */
    int SC_BAD_GATEWAY = 502;

    /**
     * 状态码(503)，表示HTTP服务器暂时超载，无法处理请求
     */
    int SC_SERVICE_UNAVAILABLE = 503;

    /**
     * 状态码(504)，表示服务器在充当网关或代理时没有及时收到来自上游服务器的响应
     */
    int SC_GATEWAY_TIMEOUT = 504;

    /**
     * 状态码(505)，表示服务器不支持或拒绝支持请求消息中使用的HTTP协议版本
     */
    int SC_HTTP_VERSION_NOT_SUPPORTED = 505;

    /**
     * 将指定的cookie添加到响应中。可以多次调用此方法来设置多个cookie
     *
     * @param cookie 将cookie返回给客户端
     */
    void addCookie(Cookie cookie);

    /**
     * 返回一个布尔值，指示是否已设置命名的响应标头
     *
     * @param name 指定标题名
     * @return 如果响应头已经被设置，则返回<code>true</code>，否则返回<code>false</code>
     */
    boolean containsHeader(String name);

    /**
     * 通过包含会话ID来对指定的URL进行编码，或者，如果不需要编码，则返回未更改的URL。
     * 此方法的实现包括确定是否需要在URL中对会话ID进行编码的逻辑。
     * 例如，如果浏览器支持cookie，或者关闭了会话跟踪，URL编码就没有必要了
     * <p>
     * 为了健壮的会话跟踪，servlet发出的所有url都应该通过这个方法运行。否则，URL重写不能用于不支持cookie的浏览器
     * <p>
     * 如果URL是相对的，它总是相对于当前HttpServletRequest
     *
     * @param url 要编码的url
     * @return 如果需要编码，返回编码后的URL，否则不做任何更改
     * @throws IllegalArgumentException 如果url无效
     */
    String encodeURL(String url);

    /**
     * 在<code>sendRedirect</code>方法中对指定的URL进行编码，如果不需要编码，则返回URL不变。
     * 此方法的实现包括确定是否需要在URL中对会话ID进行编码的逻辑。
     * 例如，如果浏览器支持cookie，或者关闭了会话跟踪，URL编码就没有必要了。
     * 由于进行此判断的规则与用于决定是否对正常链接进行编码的规则不同，因此此方法与<code>encodeURL</code>方法相分离
     * <p>
     * 所有发送到<code>HttpServletResponse.sendRedirect</code>方法应该通过这个方法运行。
     * 否则，URL重写不能用于不支持cookie的浏览器。
     * <p>
     * 如果URL是相对的，它总是相对于当前HttpServletRequest
     *
     * @param url 要编码的url
     * @return 如果需要编码，返回编码后的URL，否则不做任何更改
     * @throws IllegalArgumentException 如果url无效
     * @see #sendRedirect(String)
     * @see #encodeURL(String)
     */
    String encodeRedirectURL(String url);

    /**
     * @param url 要编码的url
     * @return 如果需要编码，返回编码后的URL，否则不做任何更改
     * @throws IllegalArgumentException 如果url无效
     * @deprecated 2.1 用encodeURL(String url) 代替
     */
    String encodeUrl(String url);

    /**
     * @param url 要编码的url
     * @return 如果需要编码，返回编码后的URL，否则不做任何更改
     * @throws IllegalArgumentException 如果url无
     * @deprecated 2.1 用encodeRedirectURL(String url) 代替
     */
    String encodeRedirectUrl(String url);

    /**
     * 使用指定的状态向客户端发送错误响应并清除缓冲区。
     * 服务器默认创建响应，使其看起来像包含指定消息的html格式的服务器错误页面，并将内容类型设置为“text/html”。
     * 服务器将保留cookie，并可能清除或更新将错误页面作为有效响应提供的任何标头。
     * <p>
     * 如果为传递的状态代码对应的web应用程序进行了错误页面声明，那么它将优先返回建议的msg参数，而msg参数将被忽略
     * <p>
     * 如果响应已经提交，这个方法抛出一个IllegalStateException。使用此方法后，应将响应视为已提交，而不应将其写入
     *
     * @param sc  错误状态码
     * @param msg 发送描述性消息
     * @throws IOException           如果发生输入或输出异常
     * @throws IllegalStateException 如果响应已提交
     */
    void sendError(int sc, String msg) throws IOException;

    /**
     * 使用指定的状态码向客户端发送错误响应并清除缓冲区
     * <p>
     * 服务器将保留cookie，并可能清除或更新将错误页面作为有效响应提供的任何标头。
     * <p>
     * 如果对传入的状态代码对应的web应用程序进行了错误页面声明，则将返回错误页面
     * <p>
     * 如果响应已经提交，这个方法抛出一个IllegalStateException。使用此方法后，应将响应视为已提交，而不应将其写入
     *
     * @param sc 错误状态码
     * @throws IOException           如果发生输入或输出异常
     * @throws IllegalStateException 如果响应已提交
     */
    void sendError(int sc) throws IOException;

    /**
     * 使用指定的重定向位置URL向客户端发送临时重定向响应并清除缓冲区。
     * 该缓冲区将由该方法的数据集替换。
     * 调用此方法将状态代码设置为{@link #SC_FOUND} 302 (Found) Move Temporarily 暂时移动。
     * 此方法可以接受相对URL; servlet容器必须在将响应发送到客户机之前将相对URL转换为绝对URL。
     * 如果位置是相对的，没有前导'/'，则容器将其解释为相对于当前请求URI。
     * 如果位置相对于前导'/'，则容器将其解释为相对于servlet容器根。
     * 如果位置相对于两个前边的'/'，则容器将其解释为网络路径引用(参见<a href="http://www.ietf.org/rfc/rfc3986.txt")
     * <p>
     * 如果响应已经提交，这个方法抛出一个IllegalStateException。
     * 使用此方法后，应将响应视为已提交，而不应将其写入
     *
     * @param location 重定向位置URL
     * @throws IOException              如果发生输入或输出异常
     * @throws IllegalArgumentException 如果提交了响应，或者提供了部分URL，并且不能转换为有效的URL
     */
    void sendRedirect(String location) throws IOException;

    /**
     * 使用给定的名称和日期值设置响应标头。
     * 日期是自纪元以来以毫秒为单位指定的。
     * 如果已经设置了标头，则新值将覆盖先前的值。
     * 可以使用<code>containsHeader</code>方法在设置报头的值之前测试报头是否存在
     *
     * @param name 要设置的标题的名称
     * @param date 指定的日期值
     * @see #addDateHeader(String, long)
     * @see #containsHeader(String)
     */
    void setDateHeader(String name, long date);

    /**
     * 添加具有给定名称和日期值的响应标头。日期是自纪元以来以毫秒为单位指定的。
     * 此方法允许响应标头具有<tt>多个值</tt>
     *
     * @param name 指定要设置的标题的名称
     * @param date 附加的日期值
     * @see #setDateHeader(String, long)
     */
    void addDateHeader(String name, long date);

    /**
     * 设置具有给定名称和值的响应标头。如果已经设置了标头，则新值将覆盖先前的值。
     * 可以使用<code>containsHeader</code>方法在设置报头的值之前测试报头是否存在
     *
     * @param name  指定标题的名称
     * @param value 附加头值如果包含八位元字符串，则按照RFC 2047进行编码(http://www.ietf.org/rfc/rfc2047.txt)
     * @see #containsHeader(String)
     * @see #addHeader(String, String)
     */
    void setHeader(String name, String value);

    /**
     * 添加具有给定名称和值的响应标头。此方法允许响应标头具有多个值
     *
     * @param name  指定标题的名称
     * @param value 附加头值如果包含八位元字符串，则按照RFC 2047进行编码(http://www.ietf.org/rfc/rfc2047.txt)
     * @see #setHeader(String, String)
     */
    void addHeader(String name, String value);

    /**
     * 设置具有给定名称和整数值的响应标头。如果已经设置了标头，则新值将覆盖先前的值。
     * 可以使用<code>containsHeader</code>方法在设置报头的值之前测试报头是否存在
     *
     * @param name  指定标题的名称
     * @param value 指定的整数值
     * @see #containsHeader(String)
     * @see #addIntHeader(String, int)
     */
    void setIntHeader(String name, int value);

    /**
     * 添加具有给定名称和整数值的响应标头。此方法允许响应标头具有多个值
     *
     * @param name  指定标题的名称
     * @param value 指定的整数值
     * @see #setIntHeader(String, int)
     */
    void addIntHeader(String name, int value);

    /**
     * @param sc 状态码
     * @param sm 状态消息
     * @deprecated 2.1 由于消息参数的含义不明确。
     * 若要设置状态码，请使用<code>setStatus(int)</code>，
     * 若要发送带有描述的错误，请使用<code>sendError(int, String)</code>。
     * 设置此响应的状态代码和消息
     */
    void setStatus(int sc, String sm);

    /**
     * 获取此响应的当前状态码
     *
     * @return 返回此响应的当前状态码
     * @since 3.0
     */
    int getStatus();

    /**
     * 设置此响应的状态码
     * <p>
     * 此方法用于在没有错误时设置返回状态码(例如，SC_OK或sc_moved_transient状态码)
     * <p>
     * 如果此方法用于设置错误代码，则不会触发容器的错误页机制。
     * 如果出现错误，调用方希望调用web应用程序中定义的错误页面，则必须使用{@link #sendError}
     * <p>
     * 此方法保留所有cookie和其他响应标头
     * <p>
     * 有效状态码是2XX、3XX、4XX和5XX范围内的状态码。其他状态码被视为容器特定的
     *
     * @param sc 状态码
     * @see #sendError(int)
     */
    void setStatus(int sc);

    /**
     * 获取具有给定名称的响应标头的值
     * <p>
     * 如果一个具有给定名称的响应标头存在并且包含多个值，那么首先添加的值将被返回。
     * 该方法只考虑通过{@link #setHeader}、{@link #addHeader}、{@link #setDateHeader}、
     * {@link #addDateHeader}、{@link #setIntHeader}或{@link #addIntHeader}设置或添加的响应头信息
     *
     * @param name 指定要返回其值的响应标头的名称
     * @return 具有给定名称的响应标头的值，或者<tt>null</tt>(如果没有在此响应上设置具有给定名称的标头)
     * @since 3.0
     */
    String getHeader(String name);

    /**
     * 获取具有给定名称的响应标头的值
     * <p>
     * 此方法只考虑通过{@link #setHeader}、{@link #addHeader}、{@link #setDateHeader}、
     * {@link #addDateHeader}、{@link #setIntHeader}或{@link #addIntHeader}设置或添加的响应头信息
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>HttpServletResponse</code>
     *
     * @param name 指定要返回其值的响应标头的名称
     * @return 一个(可能是空的)<code>集合</code>，包含给定名称的响应头的值
     * @since 3.0
     */
    Collection<String> getHeaders(String name);

    /**
     * 获取此响应的标题的名称
     * <p>
     * 此方法只考虑通过{@link #setHeader}、{@link #addHeader}、{@link #setDateHeader}、
     * {@link #addDateHeader}、{@link #setIntHeader}或{@link #addIntHeader}设置或添加的响应头信息
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>HttpServletResponse</code>
     *
     * @return 一个(可能是空的)<code>集合</code>，包含响应头的所有名称
     */
    Collection<String> getHeaderNames();

}
