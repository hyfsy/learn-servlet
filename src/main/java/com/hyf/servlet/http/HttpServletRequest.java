package com.hyf.servlet.http;

import com.hyf.servlet.ServletContext;
import com.hyf.servlet.ServletException;
import com.hyf.servlet.ServletRequest;
import com.hyf.servlet.ServletResponse;
import com.hyf.servlet.annotation.MultipartConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

/**
 * 扩展{@link ServletRequest}接口，为HTTP servlet提供请求信息
 * <p>
 * servlet容器创建一个<code>HttpServletRequest</code>对象，
 * 并将其作为参数传递给servlet的服务方法(<code>doGet</code>， <code>doPost</code>，等等)
 */
public interface HttpServletRequest extends ServletRequest {

    /**
     * 基本身份验证的字符串标识符。值：“BASIC”
     */
    String BASIC_AUTH = "BASIC";

    /**
     * 表单身份验证的字符串标识符。值：“FORM”
     */
    String FORM_AUTH = "FORM";

    /**
     * 客户端证书身份验证的字符串标识符。值：“CLIENT_CERT”
     */
    String CLIENT_CERT_AUTH = "CLIENT_CERT";

    /**
     * 摘要身份验证的字符串标识符。值：“DIGEST”
     */
    String DIGEST_AUTH = "DIGEST";

    /**
     * 返回用于保护servlet的身份验证方案的名称。
     * 所有servlet容器都支持基本认证、表单认证和客户端认证，还可能支持摘要认证。
     * 如果servlet没有经过身份验证<code>null</code>将返回
     * <p>
     * 与CGI变量AUTH_TYPE的值相同
     *
     * @return 其中一个静态成员BASIC_AUTH、FORM_AUTH、CLIENT_CERT_AUTH、DIGEST_AUTH(适用于==比较)
     * 或指示身份验证方案的容器特定字符串，或者<code>null</code>(如果请求没有经过身份验证)
     */
    String getAuthType();

    /**
     * 返回一个数组，其中包含客户端发送的所有<code>Cookie</code>对象。
     * 如果没有发送cookie，此方法将返回<code>null</code>
     *
     * @return 此请求中包含的所有<code>Cookies</code>，或<code>null</code>(如果请求没有Cookies)的数组
     */
    Cookie[] getCookies();

    /**
     * 以<code>long</code>值的形式返回指定请求头的值，该值表示一个<code>Date</code>对象。
     * 对包含日期的标头使用此方法，例如<code>If-Modified-Since</code>。
     * <p>
     * 日期作为1970年1月1日GMT以来的毫秒数返回。标题名称不区分大小写
     * <p>
     * 如果请求没有指定名称的标头，则此方法返回-1。
     * 如果标头不能转换为日期，该方法将抛出<code>IllegalArgumentException</code>
     *
     * @param name 一个<code>字符串</code>，指定标题的名称
     * @return 一个<code>long</code>值，表示标头中指定的日期，表示自1970 GMT年1月1日起的毫秒数，
     * 如果指定的标头没有包含在请求中，则为-1
     * @throws IllegalArgumentException 如果标头值不能转换为日期
     */
    long getDateHeader(String name);

    /**
     * 以<code>String</code>的形式返回指定请求头的值。
     * 如果请求不包含指定名称的标头，则此方法返回<code>null</code>。
     * 如果有多个具有相同名称的标头，此方法将返回请求中的第一个标头。
     * 标题名称不区分大小写。您可以将此方法用于任何请求标头
     *
     * @param name 一个<code>字符串</code>，指定标题名
     * @return 一个<code>String</code>，包含请求头的值，如果请求没有该名称的头，则为<code>null</code>
     */
    String getHeader(String name);

    /**
     * 以<code>String</code>类型的<code>Enumeration</code>对象的形式返回指定请求头的所有值
     * <p>
     * 一些头文件，如<code>Accept-Language</code>可以由客户端作为多个头文件发送，
     * 每个头文件具有不同的值，而不是作为逗号分隔的列表发送头文件。
     * <p>
     * 如果请求不包含指定名称的任何标头，则此方法返回一个空的<code>Enumeration</code>。
     * 标题名称不区分大小写。您可以将此方法用于任何请求标头
     *
     * @return 一个<code>Enumeration</code>，其中包含请求头的值。如果请求没有该名称的任何标头，则返回空枚举。
     * 如果容器不允许访问头信息，则返回null
     */
    Enumeration<String> getHeaders(String name);

    /**
     * 返回此请求包含的所有标题名称的枚举。如果请求没有标头，则此方法返回空枚举
     * <p>
     * 一些servlet容器不允许servlet使用此方法访问头信息，在这种情况下，此方法返回<code>null</code>
     *
     * @return 此请求发送的所有报头名称的枚举;如果请求没有标头，则为空枚举;
     * 如果servlet容器不允许servlet使用此方法，则返回<code>null</code>
     */
    Enumeration<String> getHeaderNames();

    /**
     * 以<code>int</code>的形式返回指定请求头的值。
     * 如果请求没有指定名称的标头，则此方法返回-1。
     * 如果标头不能转换为整数，此方法将抛出<code>NumberFormatException</code>，
     * 标题名不区分大小写
     *
     * @param name 指定一个<code>String</code>指定请求头的名称
     * @return 一个整数，表示请求报头的值;如果请求没有此名称的报头，则返回-1
     * @throws NumberFormatException 如果标头值不能转换成<code>int</code>
     */
    int getIntHeader(String name);

    /**
     * 返回发出此请求的HTTP方法的名称，例如GET、POST或PUT。
     * 与CGI变量REQUEST_METHOD的值相同
     *
     * @return <code>String</code>，指定发出此请求的方法的名称
     */
    String getMethod();

    /**
     * 返回与客户端发出此请求时发送的URL相关联的任何额外路径信息。
     * 额外的路径信息位于servlet路径之后，但位于查询字符串之前，并以“/”字符开始。
     * 如果没有额外的路径信息，这个方法返回空的
     * <p>
     * 与CGI变量PATH_INFO的值相同
     *
     * @return 一个由web容器解码的<code>字符串</code>，
     * 指定servlet路径之后但在请求URL中的查询字符串之前的额外路径信息，
     * 或者<code>null</code>(如果URL没有任何额外的路径信息)
     */
    String getPathInfo();

    /**
     * 返回servlet名称之后但查询字符串之前的任何额外路径信息，并将其转换为实际路径。
     * 与CGI变量PATH_TRANSLATED的值相同
     * <p>
     * 如果URL没有任何额外的路径信息，
     * 这个方法返回<code>null</code>或者servlet容器由于任何原因不能将虚拟路径转换为实际路径
     * (例如当从存档中执行web应用程序时)
     * <p>
     * web容器不解码这个字符串
     *
     * @return 指定实际路径的<code>字符串</code>，如果URL没有任何额外的路径信息，则<code>null</code>
     */
    String getPathTranslated();

    /**
     * 返回请求URI中指示请求上下文的部分。上下文路径总是首先出现在请求URI中。
     * 路径以“/”字符开始，但不以“/”字符结束。对于默认(根)上下文中的servlet，此方法返回空。容器不解码这个字符串
     * <p>
     * servlet容器可能通过多个上下文路径匹配上下文。
     * 在这种情况下，此方法将返回请求使用的实际上下文路径，它可能与{@link ServletContext#getContextPath()}
     * 方法返回的路径不同。
     * 应该将{@link ServletContext#getContextPath()}返回的上下文路径视为应用程序的主要或首选上下文路径
     *
     * @return 指定请求URI中表示请求上下文的部分(项目的根路径, 从 ” / “ 根开始)
     */
    String getContextPath();

    /**
     * 返回路径后请求URL中包含的查询字符串。
     * 如果URL没有查询字符串，此方法将返回<code>null</code>。与CGI变量QUERY_STRING的值相同
     *
     * @return 一个包含查询字符串的<code>String</code>，或者<code>null</code>(如果URL不包含查询字符串)。该值没有被容器解码
     */
    String getQueryString();

    /**
     * 如果用户已通过身份验证，则返回发出此请求的用户的登录名;如果用户未通过身份验证，则返回<code>null</code>。
     * 每个后续请求是否发送用户名取决于浏览器和身份验证类型。
     * 与CGI变量REMOTE_USER的值相同
     *
     * @return 一个<code>String</code>指定用户的登录名，如果用户登录名未知，返回<code>null</code>
     */
    String getRemoteUser();

    /**
     * 返回一个布尔值，指示已验证的用户是否包含在指定的逻辑“角色”中。
     * 可以使用部署描述符定义角色和角色成员关系。
     * 如果用户没有经过身份验证，该方法将返回<code>false</code>
     * <p>
     * 在调用<code>isUserInRole</code>时，不应该使用角色名"*"作为参数。
     * 任何调用<code>isUserInRole</code>并且角色名为"*"必须返回false。
     * <p>
     * 如果要测试的安全角色的角色名是“**”，并且应用程序没有使用角色名“**”声明一个应用程序安全角色，
     * <code>isUserInRole</code>必须在用户经过身份验证时返回true;
     * 也就是说，只有当{@link #getRemoteUser}和{@link #getUserPrincipal}都返回非空值时才会这样。
     * 否则，容器必须检查用户在应用程序角色中的成员资格。
     *
     * @param role 指定角色的名称
     * @return 一个<code>boolean</code>，指示发出此请求的用户是否属于给定角色;
     * 如果用户没有被认证，返回<code>false</code>
     */
    boolean isUserInRole(String role);

    /**
     * 返回一个<code>Principal</code>对象，其中包含当前经过身份验证的用户的名称。
     * 如果用户没有通过身份验证，该方法将返回<code>null</code>。
     *
     * @return 返回一个<code>Principal</code>对象，其中包含当前经过身份验证的用户的名称。
     * 如果用户没有通过身份验证，该方法将返回<code>null</code>。
     */
    java.security.Principal getUserPrincipal();

    /**
     * 返回客户端指定的会话ID。这可能与此请求的当前有效会话的ID不同。
     * 如果客户端没有指定会话ID，该方法将返回<code>null</code>
     *
     * @return 一个<code>String</code>指定会话ID，如果请求没有指定会话ID，则返回<code>null</code>
     * @see #isRequestedSessionIdValid()
     */
    String getRequestedSessionId();

    /**
     * 在HTTP请求的第一行中，从协议名到查询字符串返回此请求的URL的一部分。web容器不解码这个字符串。
     * 例如:
     * <table summary="返回值示例">
     * <tr align=left><th>HTTP请求的第一行</th>
     * <th>返回值</th>
     * <tr><td> POST /some/path.html HTTP/1.1 <td><td> /some/path.html
     * <tr><td> GET http://foo.bar/a.html HTTP/1.0 <td><td> /a.html
     * <tr><td> HEAD /xyz?a=b HTTP/1.1 <td><td> /xyz
     * </table>
     * 使用{@link HttpUtils#getRequestURL}来使用方案和主机重构URL
     *
     * @return 一个<code>String</code>，包含从协议名到查询字符串的部分URL
     * @see #getRequestURL()
     */
    String getRequestURI();

    /**
     * 重新构造客户端用于发出请求的URL。返回的URL包含协议、服务器名、端口号和服务器路径，但不包含查询字符串参数
     * <p>
     * 如果这个请求已经被转发使用，通过{@link com.hyf.servlet.RequestDispatcher#forward(ServletRequest, ServletResponse)}，
     * 则重构URL中的服务器路径必须反映用于获取RequestDispatcher的路径，而不是客户端指定的服务器路径
     * <p>
     * 因为这个方法返回一个<code>StringBuffer</code>，而不是一个字符串，所以您可以很容易地修改URL，例如，添加查询参数
     * <p>
     * 此方法用于创建重定向消息和报告错误
     *
     * @return 一个包含重建URL的<code>StringBuffer</code>对象
     */
    StringBuffer getRequestURL();

    /**
     * 返回请求URL中调用servlet的部分。此路径以“/”字符开头，
     * 包含servlet名称或servlet路径，但不包含任何额外的路径信息或查询字符串。
     * 与CGI变量SCRIPT_NAME的值相同
     * <p>
     * 如果用于处理此请求的servlet是使用"/*"模式匹配的，此方法将返回一个空字符串("")
     *
     * @return 一个<code>String</code>，其中包含正在调用的servlet的名称或路径，如请求URL中指定的那样，已解码;
     * 如果用于处理请求的servlet使用“/*”模式匹配，则返回一个空字符串
     */
    String getServletPath();

    /**
     * 返回当前<code>HttpSession</code>与此请求相关联，
     * 或者，如果没有当前会话且<code>create</code>为<code>true</code>，则返回一个新会话
     * <p>
     * 如果<code>create</code>为<code>false</code>，且请求没有有效的<code>HttpSession</code>，
     * 此方法返回<code>null</code>
     * <p>
     * 要确保会话得到正确维护，必须在提交响应之前调用此方法。
     * 如果容器使用cookie来维护会话完整性，并且在提交响应时要求创建新会话，则抛出IllegalStateException
     *
     * @param create 必要时且为<code>true</code>时 为该请求创建新会话;
     *               如果没有当前会话，且为<code>false</code>则返回<code>null</code>
     * @return 与此请求关联的<code>HttpSession</code>，
     * 如果<code>create</code>为<code>false</code>，且请求没有有效会话，则返回<code>null</code>
     * @see #getSession()
     */
    HttpSession getSession(boolean create);

    /**
     * 返回与此请求关联的当前会话，或者如果该请求没有会话，则创建一个会话
     *
     * @return 返回与此请求关联的<code>HttpSession</code>
     * @see #getSession(boolean)
     */
    HttpSession getSession();

    /**
     * 更改与此请求关联的当前会话的会话id，并返回新的会话id
     *
     * @return 返回新的会话id
     * @throws IllegalStateException 如果没有与请求关联的会话
     * @since 3.1
     */
    String changeSessionId();

    /**
     * 检查请求的会话ID是否仍然有效
     * 如果客户端没有指定任何会话ID，这个方法返回<code>false</code>
     *
     * @return 如果此请求在当前会话上下文中具有有效会话的id，则返回<code>true</code>，否则为<code>false</code>
     * @see #getRequestedSessionId()
     * @see #getSession()
     * @see ServletContext
     */
    boolean isRequestedSessionIdValid();

    /**
     * 检查请求的会话ID是否以cookie的形式传入
     *
     * @return 如果会话ID是一个cookie的话，返回<code>true</code>;否则返回<code>false</code>
     * @see #getSession()
     */
    boolean isRequestedSessionIdFromCookie();

    /**
     * 检查请求的会话ID是否作为请求URL的一部分传入
     * <p>
     * 在请求后面用;分隔，如：（/test;jsessionid=xxx?test=1234）
     *
     * @return 如果会话ID作为URL的一部分输入，则返回<code>true</code>;否则，返回<code>false</code>
     * @see #getSession()
     */
    boolean isRequestedSessionIdFromURL();

    /**
     * @deprecated 2.1 使用{@link #isRequestedSessionIdFromURL()}代替
     */
    boolean isRequestedSessionIdFromUrl();

    /**
     * 使用为<code>ServletContext</code>配置的容器登录机制对发出此请求的用户进行身份验证
     * <p>
     * 这个方法可以修改和提交<code>HttpServletResponse</code>参数
     * <p>
     *
     * @param response <code>HttpServletResponse</code>与此<code>HttpServletRequest</code>关联
     * @return 当<code>getUserPrincipal</code>，<code>getRemoteUser</code>，
     * <code>getAuthType</code>所返回的值都不为空时，返回<code>true</code>。
     * 如果认证不完整，并且底层登录机制已经提交，
     * 在响应中，消息(例如，挑战)和HTTP状态代码将返回给用户，返回<code>false</code>
     * @throws ServletException      如果身份验证失败，调用者负责处理错误(即，底层登录机制没有建立要返回给用户的消息和HTTP状态码)
     * @throws IOException           如果从该请求读取或写入给定响应时发生输入或输出错误
     * @throws IllegalStateException 如果登录机制试图修改响应，并且它已经提交了
     * @since 3.0
     */
    boolean authenticate(HttpServletResponse response) throws ServletException, IOException;

    /**
     * 在为<code>ServletContext</code>配置的web容器登录机制使用的密码验证域中验证提供的用户名和密码
     * <p>
     * 当为<code>ServletContext</code>配置的登录机制支持用户名密码验证时，并且在调用登录时，
     * 请求调用者的身份已被验证时，此方法返回而不抛出<code>ServletException</code>
     * <p>
     * 当为<code>ServletContext</code>配置的登录机制支持用户名密码验证，并且在调用登录时，
     * 请求的调用者的身份还没有建立(所有<code>getUserPrincipal</code>，<code>getRemoteUser</code>，
     * <code>getAuthType</code>都返回null)，验证所提供的凭证成功，
     * 否则，该方法将抛出一个<code>ServletException</code>，如下所述
     * <p>
     * 当这个方法没有抛出异常返回时，它必须建立非空值作为<code>getUserPrincipal</code>，
     * <code>getRemoteUser</code>，<code>getAuthType</code>返回的值
     *
     * @param name     <code>String</code>值对应于用户名的登录标识符
     * @param password <code>String</code>值对应于密码的登录标识符
     * @throws ServletException 如果配置的登录机制不支持用户名密码身份验证，
     *                          或者已经建立了非空调用者身份(在调用登录之前)，
     *                          或者提供的用户名和密码验证失败
     * @since 3.0
     */
    void login(String name, String password) throws ServletException;

    /**
     * 将<code>null</code>作为在请求调用<code>getUserPrincipal</code>，<code>getRemoteUser</code>，
     * <code>getAuthType</code>时返回的值
     *
     * @throws ServletException 如果注销失败
     * @since 3.0
     */
    void logout() throws ServletException;

    /**
     * 获取此请求的所有{@link Part}组件，条件是其类型为<code>multipart/form-data</code>
     * <p>
     * 如果这个请求的类型是<code>multipart/form-data</code>，但是不包含任何<code>Part</code> components，
     * 那么返回的<code>Collection</code>将是空的
     * <p>
     * 对返回的<code>Collection</code>的任何操作都不能影响这个<code>HttpServletRequest</code>
     *
     * @return 当前请求的<code>Part<code>类型的集合（可能为空）
     * @throws IOException           如果在检索此请求的{@link Part}组件期间发生I/O错误
     * @throws ServletException      如果这个请求的类型不是<code>multipart/form-data</code>
     * @throws IllegalStateException 如果请求体大于<code>maxRequestSize</code>，
     *                               或者请求中的任何<code>Part</code>对象的文件大小大于<code>maxFileSize</code>，
     *                               或者没有用<code>@MultipartConfig</code>注解，
     *                               或者部署描述符中没有<code>multipart-config</code>
     * @see MultipartConfig#maxFileSize()
     * @see MultipartConfig#maxRequestSize()
     * @since 3.0
     */
    Collection<Part> getParts() throws IOException, ServletException;

    /**
     * 获取具有给定名称的{@link Part}对象
     *
     * @param name 请求<code>Part</code>的名称（前端控件中对应的name属性）
     * @return 返回具有给定名称的<code>Part</code>对象，或者<code>null</code>，
     * 如果这个请求的类型是<code>multipart/form-data</code>，但是不包含所请求的<code>Part</code>
     * @throws IOException           如果在检索此请求的{@link Part}组件期间发生I/O错误
     * @throws ServletException      如果这个请求的类型不是<code>multipart/form-data</code>
     * @throws IllegalStateException 如果请求体大于<code>maxRequestSize</code>，
     *                               或者请求中的任何<code>Part</code>对象的文件大小大于<code>maxFileSize</code>，
     *                               或者没有用<code>@MultipartConfig</code>注解，
     *                               或者部署描述符中没有<code>multipart-config</code>
     * @see MultipartConfig#maxFileSize()
     * @see MultipartConfig#maxRequestSize()
     * @since 3.0
     */
    Part getPart(String name) throws IOException, ServletException;

    /**
     * 为给定的类创建一个<code>HttpUpgradeHandler</code>的实例，并将其用于http协议升级处理
     *
     * @param handlerClass 用于升级的<code>HttpUpgradeHandler</code>类
     * @return 返回<code>HttpUpgradeHandler</code>的一个实例
     * @throws IOException      如果在升级期间发生I/O错误
     * @throws ServletException 如果给定的<code>handlerClass</code>不能被实例化
     * @see HttpUpgradeHandler
     * @see WebConnection
     * @since 3.1
     */
    <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException;
}
