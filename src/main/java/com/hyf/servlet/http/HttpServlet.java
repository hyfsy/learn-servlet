package com.hyf.servlet.http;

import com.hyf.servlet.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 提供要子类化的抽象类，以创建适合于Web站点的HTTP servlet。
 * 一个子类<code>HttpServlet</code>必须覆盖至少一个方法，通常是其中之一:
 * <ul>
 * <li> <code>doGet</code>，如果servlet支持HTTP GET请求
 * <li> <code>doPost</code>，用于HTTP POST请求
 * <li> <code>doPut</code>，用于HTTP PUT请求
 * <li> <code>doDelete</code>，用于HTTP删除请求
 * <li> <code>init</code> and <code>destroy</code>，来管理servlet生命周期中所持有的资源
 * <li><code>getServletInfo</code>， servlet使用它来提供关于自身的信息
 * </ul>
 * <p>
 * 几乎没有理由重写<code>service</code> method。<code>service</code>通过将
 * 标准HTTP请求分配给每个HTTP请求类型的处理程序方法来处理它们(<code>do</code><i>XXX</i>方法列示于上)
 * <p>
 * 同样，几乎没有理由重写<code>doOptions</code>和<code>doTrace</code>方法
 * <p>
 * servlet通常运行在多线程服务器上，所以servlet必须处理并发请求，并小心同步对共享资源的访问。
 * 共享资源包括内存中的数据(如实例或类变量)和外部对象(如文件、数据库连接和网络连接)
 */
public abstract class HttpServlet extends GenericServlet {

    private static final long serialVersionUID = 8652991704815658385L;

    private static final String LSTRING_FILE = "com.hyf.servlet.http.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    private static final String METHOD_GET = "GET";
    private static final String METHOD_HEAD = "HEAD";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";
    private static final String METHOD_OPTIONS = "OPTIONS";
    private static final String METHOD_TRACE = "TRACE";

    private static final String METHOD_IFMODSINCE = "If-Modified-Since";
    private static final String METHOD_LASTMOD = "Last-Modified";


    /**
     * 什么都不做，因为这是一个抽象类
     */
    HttpServlet() {
    }

    /**
     * 由服务器调用(通过<code>service</code>方法)来允许servlet处理GET请求
     * <p>
     * 覆盖此方法来支持GET请求，也自动支持HTTP头请求。
     * HEAD请求是一个GET请求，它在响应中不返回任何主体，只返回请求头字段
     * <p>
     * 当覆盖此方法时，读取请求数据，写入响应标头，获取响应的写入器或输出流对象，最后写入响应数据。
     * 最好包括内容类型和编码。使用<code>PrintWriter</code>对象返回响应时，
     * 请在访问<code>PrintWriter</code>对象之前设置内容类型
     * <p>
     * servlet容器必须在提交响应之前写报头，因为在HTTP中报头必须在响应体之前发送。
     * <p>
     * 在可能的情况下，设置Content-Length头(使用{@link HttpServletResponse#setContentLength(int)}方法)，
     * 以允许servlet容器使用持久连接将其响应返回给客户机，从而提高性能。
     * 如果整个响应符合响应缓冲区，则自动设置内容长度
     * <p>
     * 当使用HTTP 1.1块编码(这意味着响应有一个传输编码头)时，不要设置内容长度头。
     * <p>
     * GET方法应该是安全的，也就是说，没有任何副作用，由用户负责。例如，大多数表单查询没有副作用。
     * 如果客户机请求打算更改存储的数据，则该请求应该使用其他一些HTTP方法
     * <p>
     * GET方法也应该是幂等的，这意味着它可以安全地重复。
     * 有时，使一种方法安全也使它具有幂等性。
     * 例如，重复查询既安全又有效，但是在线购买产品或修改数据既不安全也不有效
     * <p>
     * 如果请求的格式不正确，<code>doGet</code>返回一个HTTP“Bad request”消息
     *
     * @param req  一个{@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp 一个{@link HttpServletResponse}对象，其中包含servlet发送给客户端的响应
     * @throws IOException      如果servlet处理GET请求时检测到输入或输出错误
     * @throws ServletException 如果无法处理GET请求
     * @see ServletResponse#setContentType(String)
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_get_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }

    /**
     * 返回<code>HttpServletRequest</code>对象最后一次修改的时间，从格林威治时间1970年1月1日午夜开始，以毫秒为单位。
     * 如果时间未知，则此方法返回一个负数(默认值)
     * <p>
     * servlet支持HTTP GET请求，可以快速确定他们的最后修改时间应该覆盖这个方法。
     * 这使得浏览器和代理缓存更有效地工作，减少了服务器和网络资源的负载
     *
     * @param req 对象被发送到servlet的<code>HttpServletRequest</code>对象
     * @return 一个整型<code>long</code>指定<code>HttpServletRequest</code>对象最后修改的时间，
     * 从1970年1月1日格林威治时间午夜开始，以毫秒为单位，如果时间未知，则为-1
     */
    protected long getLastModified(HttpServletRequest req) {
        return -1;
    }

    /**
     * 从受保护的<code>service</code>方法接收HTTP头请求并处理请求。
     * 当客户机只希望查看响应的头信息(如内容类型或内容长度)时，就发送HEAD请求。
     * HTTP HEAD方法对响应中的输出字节进行计数，以准确地设置内容长度报头
     * <p>
     * 如果你重写这个方法，你可以避免计算响应体，只是设置响应头直接提高性能。
     * 确保您编写的<code>doHead</code>方法是安全的，并且是幂等的(也就是说，保护自己不被一个HTTP HEAD请求多次调用)。
     * 如果HTTP头请求的格式不正确，<code>doHead</code>返回一个HTTP“Bad Request”消息
     *
     * @param req  传递给servlet的请求对象
     * @param resp servlet用于返回 Head 给client的响应对象
     * @throws ServletException 如果无法处理 Head 的请求
     * @throws IOException      如果发生输入或输出错误
     */
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoBodyServletResponse response = new NoBodyServletResponse(resp);
        doGet(req, response);
        response.setContentLength();
    }

    /**
     * 由服务器调用(通过<code>service</code>方法)来允许servlet处理POST请求。HTTP POST方法允许客户端一次性向Web服务器发送无限长度的数据，在发布信用卡号等信息时非常有用。
     * <p>
     * 当覆盖此方法时，读取请求数据，写入响应标头，获取响应的写入器或输出流对象，最后写入响应数据。
     * 最好包括内容类型和编码。使用<code>PrintWriter</code>对象返回响应时，
     * 请在访问<code>PrintWriter</code>对象之前设置内容类型
     * <p>
     * servlet容器必须在提交响应之前写报头，因为在HTTP中报头必须在响应体之前发送
     * <p>
     * 在可能的情况下，设置Content-Length头(使用{@link HttpServletResponse#setContentLength(int)}方法)，
     * 以允许servlet容器使用持久连接将其响应返回给客户机，从而提高性能。
     * 如果整个响应符合响应缓冲区，则自动设置内容长度
     * <p>
     * 当使用HTTP 1.1块编码(这意味着响应有一个传输编码头)时，不要设置内容长度头。
     * <p>
     * 此方法既不需要是安全的，也不需要是幂等的。
     * 通过POST请求的操作可能会产生副作用，用户需要对此负责，
     * 例如，更新存储的数据或在线购买商品。
     * 如果HTTP POST请求的格式不正确，<code>doPost</code>返回一个HTTP“Bad Request”消息
     *
     * @param req  一个{@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp 一个{@link HttpServletResponse}对象，其中包含servlet发送给客户端的响应
     * @throws IOException      如果servlet处理请求时检测到输入或输出错误
     * @throws ServletException 如果 POST 请求不能被处理
     * @see ServletOutputStream
     * @see HttpServletResponse#setContentType(String)
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_post_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }

    /**
     * 由服务器调用(通过<code>service</code>方法)来允许servlet处理PUT请求
     * <p>
     * PUT操作允许客户端将文件放在服务器上，类似于通过FTP发送文件
     * <p>
     * 当覆盖此方法时，保留随请求发送的任何内容头信息
     * (包括内容长度、内容类型、内容传输编码、内容编码、内容基础、内容语言、内容位置、内容md5和内容范围)。
     * 如果您的方法不能处理内容头，它必须发出错误消息(HTTP 501 -未实现)并放弃请求。
     * 有关HTTP 1.1的更多信息，请参见RFC 2616 <a href="http://www.ietf.org/rfc/rfc2616.txt"></a>
     * <p>
     * 此方法既不需要是安全的，也不需要是幂等的。
     * 执行<code>doPut</code>的操作可能会产生副作用，用户要对此负责。
     * 在使用此方法时，将受影响的URL的副本保存在临时存储中可能很有用
     * <p>
     * 如果HTTP PUT请求的格式不正确，<code>doPut</code>返回一个HTTP“Bad Request”消息
     *
     * @param req  {@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp {@link HttpServletResponse}对象，该对象包含servlet返回给客户机的响应
     * @throws ServletException 如果 PUT 请求不能被处理
     * @throws IOException      如果servlet处理PUT请求时发生输入或输出错误
     */
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_put_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }

    /**
     * 由服务器调用(通过<code>service</code>方法)来允许servlet处理DELETE请求。
     * DELETE操作允许客户端删除文档或来自服务器的Web页面
     * <p>
     * 此方法既不需要是安全的，也不需要是幂等的。
     * 通过DELETE请求的操作可能会产生副作用，用户可能要为此负责。
     * 在使用此方法时，将受影响的URL的副本保存在临时存储中可能很有用
     * <p>
     * 如果HTTP DELETE请求的格式不正确，<code>doDelete</code>返回一个HTTP“Bad Request”消息
     *
     * @param req  {@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp {@link HttpServletResponse}对象，该对象包含servlet返回给客户机的响应
     * @throws ServletException 如果无法处理 DELETE 请求
     * @throws IOException      如果servlet处理 DELETE 请求时发生输入或输出错误
     */
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        String msg = lStrings.getString("http.method_delete_not_supported");
        if (protocol.endsWith("1.1")) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, msg);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        }
    }

    private Method[] getDeclaredMethod(Class<? extends HttpServlet> c) {

        Class<?> clazz = c;
        Method[] allMethods = null;

        while (!clazz.equals(HttpServlet.class)) {
            Method[] thisMethods = allMethods;
            Method[] subMethods = clazz.getDeclaredMethods();
            if (allMethods != null && allMethods.length > 0) {
                allMethods = new Method[thisMethods.length + subMethods.length];
                System.arraycopy(thisMethods, 0, allMethods, 0, thisMethods.length);
                System.arraycopy(subMethods, 0, allMethods, 0, subMethods.length);
            } else {
                allMethods = new Method[subMethods.length];
            }
            clazz = clazz.getSuperclass();
        }
        return allMethods == null ? new Method[0] : allMethods;
    }

    /**
     * 由服务器调用(通过<code>service</code>方法)，以允许servlet处理选项请求。
     * OPTIONS请求确定服务器支持哪些HTTP方法并返回适当的标头。
     * 例如，如果servlet重写<code>doGet</code>，这个方法将返回以下头信息:
     * <p>
     * <code>Allow: GET, HEAD, TRACE, OPTIONS</code>
     * <p>
     * 没有必要重写这个方法，除非servlet实现了新的HTTP方法，除了那些由HTTP 1.1实现的
     *
     * @param req  {@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp {@link HttpServletResponse}对象，该对象包含servlet返回给客户机的响应
     * @throws ServletException 如果无法处理 OPTIONS 请求
     * @throws IOException      如果servlet处理 OPTIONS 请求时发生输入或输出错误
     */
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean ALLOW_GET = false;
        boolean ALLOW_HEAD = false;
        boolean ALLOW_POST = false;
        boolean ALLOW_PUT = false;
        boolean ALLOW_DELETE = false;
        boolean ALLOW_OPTIONS = true;
        boolean ALLOW_TRACE = true;

        Method[] methods = getDeclaredMethod(this.getClass());

        for (Method method : methods) {
            String name = method.getName();
            switch (name) {
                case "doGet":
                    ALLOW_GET = true;
                    ALLOW_HEAD = true;
                    break;
                case "doPost":
                    ALLOW_POST = true;
                    break;
                case "doPut":
                    ALLOW_PUT = true;
                    break;
                case "doDelete":
                    ALLOW_DELETE = true;
                    break;
                default:
                    break;
            }
        }

        StringBuilder sb = new StringBuilder();
        if (ALLOW_GET) {
            sb.append(METHOD_GET);
        }
        if (ALLOW_HEAD) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(METHOD_HEAD);
        }
        if (ALLOW_POST) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(METHOD_POST);
        }
        if (ALLOW_PUT) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(METHOD_PUT);
        }
        if (ALLOW_DELETE) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(METHOD_DELETE);
        }
        if (ALLOW_OPTIONS) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(METHOD_OPTIONS);
        }
        if (ALLOW_TRACE) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(METHOD_TRACE);
        }
        resp.addHeader("Allow", sb.toString());
    }

    /**
     * 由服务器调用(通过<code>service</code>方法)以允许servlet处理跟踪请求
     * <p>
     * 跟踪将跟踪请求发送给客户机的头信息返回，以便在调试时使用。没有必要重写这个方法
     *
     * @param req  {@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp {@link HttpServletResponse}对象，该对象包含servlet返回给客户机的响应
     * @throws ServletException 如果无法处理 TRACE 请求
     * @throws IOException      如果servlet处理 TRACE 请求时发生输入或输出错误
     */
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String CRLF = "\r\n";

        StringBuilder buffer = new StringBuilder();
        buffer.append("TRACE ").append(req.getRequestURI()).append(' ').append(req.getProtocol());

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            buffer.append(CRLF).append(headerName).append(": ").append(req.getHeader(headerName));
        }

        buffer.append(CRLF);

        resp.setContentType("message/http");
        resp.setContentLength(buffer.length());
        ServletOutputStream out = resp.getOutputStream();

        out.print(buffer.toString());
    }

    /**
     * 将客户端请求分派到受保护的<code>service</code>方法。没有必要重写这个方法
     *
     * @param req  {@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp {@link HttpServletResponse}对象，该对象包含servlet返回给客户机的响应
     * @throws ServletException 如果HTTP请求无法处理
     * @throws IOException      如果servlet处理HTTP请求时发生输入或输出错误
     * @see Servlet#service(ServletRequest, ServletResponse)
     */
    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;

        if (!(req instanceof HttpServletRequest && resp instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        request = (HttpServletRequest) req;
        response = (HttpServletResponse) resp;

        service(request, response);
    }

    /*
     * 如果还没有设置Last-Modified实体标头字段，并且该值有意义，则设置该字段。
     * 在doGet之前调用，以确保在写入响应数据之前设置了标头。子类可能已经设置了这个头，所以我们进行检查
     */
    private void maybeSetLastModified(HttpServletResponse resp, long lastModified) {
        if (resp.containsHeader(METHOD_LASTMOD)) {
            return;
        }
        if (lastModified > 0) {
            resp.setDateHeader(METHOD_LASTMOD, lastModified);
        }
    }

    /**
     * 从公共的<code>service</code> method >接收标准的HTTP请求，
     * 并将它们分派给在这个类中定义的<code>do</code><i>XXX</i> methods
     * <p>
     * 此方法是{@link Servlet#service(ServletRequest, ServletResponse)} 的http特定版本。
     * 没有必要重写这个方法
     *
     * @param req  {@link HttpServletRequest}对象，它包含客户端对servlet发出的请求
     * @param resp {@link HttpServletResponse}对象，该对象包含servlet返回给客户机的响应
     * @throws ServletException 如果HTTP请求无法处理
     * @throws IOException      如果servlet处理HTTP请求时发生输入或输出错误
     * @see Servlet#service(ServletRequest, ServletResponse)
     */
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        long lastModified;
        switch (method) {
            case METHOD_GET:
                lastModified = getLastModified(req);
                if (lastModified == -1) {
                    // 因为servlet不支持if-modified-since，所以没有理由进一步使用昂贵的逻辑
                    doGet(req, resp);
                } else {
                    long ifModifiedSince = req.getDateHeader(METHOD_IFMODSINCE);
                    if (ifModifiedSince < lastModified) {
                        // 如果servlet mod时间较晚，则调用doGet()
                        // 四舍五入到最近的秒，以便进行适当的比较
                        // 一个ifModifiedSince的值总是小于-1
                        maybeSetLastModified(resp, lastModified);
                        doGet(req, resp);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                    }
                }
                break;
            case METHOD_HEAD:
                lastModified = getLastModified(req);
                maybeSetLastModified(resp, lastModified);
                doHead(req, resp);
                break;
            case METHOD_POST:
                doPost(req, resp);
                break;
            case METHOD_PUT:
                doPut(req, resp);
                break;
            case METHOD_DELETE:
                doDelete(req, resp);
                break;
            case METHOD_OPTIONS:
                doOptions(req, resp);
                break;
            case METHOD_TRACE:
                doTrace(req, resp);
                break;
            default:
                String msg = lStrings.getString("http.method_not_implemented");
                Object[] errArgs = new Object[1];
                errArgs[0] = method;
                msg = MessageFormat.format(msg, errArgs);

                // 注意，这意味着servlet不支持请求的任何方法，无论在这个服务器的任何地方
                resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, msg);
        }
    }
}

/*
 * 不包含正文的响应，用于(哑)“头”的支持。
 * 这只是吞下主体，计算字节以便适当地设置内容长度。
 * 所有其他方法都直接委托给包装好的HTTP Servlet响应对象
 */
class NoBodyServletResponse extends HttpServletResponseWrapper {

    private static final String LSTRING_FILE = "com.hyf.servlet.http.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    private NoBodyOutputStream noBody;
    private PrintWriter writer;
    private boolean didSetContentLength;
    private boolean usingOutputStream;

    public NoBodyServletResponse(HttpServletResponse response) {
        super(response);
        noBody = new NoBodyOutputStream();
    }

    void setContentLength() {
        if (!didSetContentLength) {
            if (writer != null) {
                writer.flush();
            }
            setContentLength(noBody.getContentLength());
        }
    }

    @Override
    public void setContentLength(int len) {
        super.setContentLength(len);
        didSetContentLength = true;
    }

    @Override
    public void setContentLengthLong(long len) {
        super.setContentLengthLong(len);
        didSetContentLength = true;
    }


    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void addHeader(String name, String value) {
        super.addHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void setIntHeader(String name, int value) {
        super.setIntHeader(name, value);
        checkHeader(name);
    }

    @Override
    public void addIntHeader(String name, int value) {
        super.addIntHeader(name, value);
        checkHeader(name);
    }

    private void checkHeader(String name) {
        if ("content-length".equalsIgnoreCase(name)) {
            didSetContentLength = true;
        }
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {

        if (writer != null) {
            throw new IllegalStateException(lStrings.getString("err.ise.getOutputStream"));
        }
        usingOutputStream = true;

        return noBody;
    }

    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException {
        if (usingOutputStream) {
            throw new IllegalStateException(lStrings.getString("err.ise.getWriter"));
        }

        if (writer == null) {
            OutputStreamWriter outputStream = new OutputStreamWriter(noBody, getCharacterEncoding());
            writer = new PrintWriter(outputStream);
        }
        return writer;
    }
}

/*
 * Servlet输出流会吞噬所有数据
 */

class NoBodyOutputStream extends ServletOutputStream {

    private static final String LSTRING_FILE = "com.hyf.servlet.http.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    private int contentLength = 0;

    NoBodyOutputStream() {
    }

    int getContentLength() {
        return contentLength;
    }

    @Override
    public void write(int b) throws IOException {
        contentLength++;
    }

    @Override
    public void write(byte[] buf, int offset, int len) throws IOException {

        if (buf == null) {
            throw new IllegalArgumentException(lStrings.getString("err.io.nullArray"));
        }

        if (offset < 0 || len < 0 || offset + len > buf.length) {
            String msg = lStrings.getString("err.io.indexOutOfBounds=");
            Object[] errArgs = new Object[3];
            errArgs[0] = offset;
            errArgs[1] = len;
            errArgs[2] = buf.length;
            msg = MessageFormat.format(msg, errArgs);
            throw new IndexOutOfBoundsException(msg);
        }
        contentLength += len;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
    }

}
