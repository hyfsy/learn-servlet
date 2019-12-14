package com.hyf.servlet;

import java.io.IOException;

/**
 * 定义一个对象，该对象接收来自客户机的请求并将其发送到服务器上的任何资源(如servlet、HTML文件或JSP文件)。
 * servlet容器创建<code>RequestDispatcher</code>对象，该对象用作位于特定路径或由特定名称提供的服务器资源的包装器。
 * <p>
 * 此接口用于封装servlet，但是servlet容器可以创建<code>RequestDispatcher</code>对象来包装任何类型的资源
 *
 * @see ServletRequest#getRequestDispatcher(String)
 * @see ServletContext#getRequestDispatcher(String)
 * @see ServletContext#getNamedDispatcher(String)
 */
public interface RequestDispatcher {

    /**
     * 请求属性的名称，在该属性下，原始 request_uri 可用于{@link #forward(ServletRequest, ServletResponse)}的目标
     */
    public static final String FORWARD_REQUEST_URI = "javax.servlet.forward.request_uri";

    /**
     * 请求属性的名称，在该属性下，原始 context_path 可用于{@link #forward(ServletRequest, ServletResponse)}的目标
     */
    String FORWARD_CONTEXT_PATH = "javax.servlet.forward.context_path";

    /**
     * 请求属性的名称，在该属性下，原始 path_info 可用于{@link #forward(ServletRequest, ServletResponse)}的目标
     */
    String FORWARD_PATH_INFO = "javax.servlet.forward.path_info";

    /**
     * 请求属性的名称，在该属性下，原始 servlet_path 可用于{@link #forward(ServletRequest, ServletResponse)}的目标
     */
    String FORWARD_SERVLET_PATH = "javax.servlet.forward.servlet_path";

    /**
     * 请求属性的名称，在该属性下，原始 query_string 可用于{@link #forward(ServletRequest, ServletResponse)}的目标
     */
    String FORWARD_QUERY_STRING = "javax.servlet.forward.query_string";


    /**
     * 用于存储{@link #include(ServletRequest, ServletResponse) include}的目标 request_uri 的请求属性的名称
     */
    String INCLUDE_REQUEST_RUI = "javax.servlet.include.request_uri";

    /**
     * 用于存储{@link #include(ServletRequest, ServletResponse) include}的目标 context_path 的请求属性的名称
     */
    String INCLUDE_CONTEXT_PATH = "javax.servlet.include.context_path";

    /**
     * 用于存储{@link #include(ServletRequest, ServletResponse) include}的目标 path_info 的请求属性的名称
     */
    String INCLUDE_PATH_INFO = "javax.servlet.include.path_info";

    /**
     * 用于存储{@link #include(ServletRequest, ServletResponse) include}的目标 servlet_path 的请求属性的名称
     */
    String INCLUDE_SERVLET_PATH = "javax.servlet.include.servlet_path";

    /**
     * 用于存储{@link #include(ServletRequest, ServletResponse) include}的目标 query_string 的请求属性的名称
     */
    String INCLUDE_QUERY_STRING = "javax.servlet.include.query_string";

    /**
     * 在错误分派期间传播异常对象的请求属性的名称
     */
    String ERROR_EXCEPTION = "javax.servlet.error.exception";

    /**
     * 在错误分派过程中传播异常对象类型的请求属性的名称
     */
    String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    /**
     * 在错误分派期间传播异常消息的请求属性的名称
     */
    String ERROR_MESSAGE = "javax.servlet.error.message";

    /**
     * 在错误分派过程中传播处理导致错误的请求URI的请求属性的名称
     */
    String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";

    /**
     * 在错误分派过程中，发生错误的servlet的名称将在该属性下传播的请求属性的名称
     */
    String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";

    /**
     * 在错误调度期间传播响应状态的请求属性的名称
     */
    String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    /**
     * 将请求从一个servlet转发到在服务器上的另一个资源(servlet、JSP文件或Html文件)。
     * 这种方法允许一个servlet对一个请求做初步的处理和另一个要生成的资源响应
     * <p>
     * 通过<code>ServletRequest<code>的<code>getRequestDispatcher()</code>方法
     * 获取一个<code>RequestDispatcher</code>对象，
     * 通过对对象的路径元素和参数进行调整以匹配目标资源的路径
     *
     * <code>forward</code>应该在响应提交给客户端(在响应体输出被刷新之前)之前被调用。
     * 如果已经提交了响应，则抛出<code>IllegalStateException</code>异常，响应缓冲区中未提交的输出将被自动清除
     * <p>
     * 传递给调用servlet的服务方法的请求和响应参数必须相同，
     * 或者是包装它们的{@link ServletRequestWrapper}或{@link ServletResponseWrapper}类的子类
     * <p>
     * 此方法将给定请求的dispatcher类型设置为<code>DispatcherType.FORWARD</code>
     *
     * @param req  一个{@link ServletRequest}对象，表示客户端对servlet的请求
     * @param resp 一个{@link ServletResponse}对象，表示servlet返回给客户端的响应
     * @throws ServletException      如果目标资源抛出此异常
     * @throws IOException           如果目标资源抛出此异常
     * @throws IllegalStateException 如果响应已经提交
     * @see ServletRequest#getDispatcherType()
     */
    void forward(ServletRequest req, ServletResponse resp) throws ServletException, IOException;

    /**
     * 在响应中包含资源的内容(servlet、JSP页面、HTML文件)。本质上，该方法支持可编程的服务器端include
     * <p>
     * {@link ServletResponse}对象的路径元素和参数与调用者保持不变。所包含的servlet不能更改响应状态代码或设置报头;任何改变的尝试都会被忽略
     * <p>
     * 此方法将给定请求的dispatcher类型设置为<code>DispatcherType.INCLUDE</code
     *
     * @param req  一个包含客户端请求的{@link ServletRequest}对象
     * @param resp 包含servlet响应的{@link ServletResponse}对象
     * @throws ServletException 如果目标资源抛出此异常
     * @throws IOException      如果目标资源抛出此异常
     * @see ServletRequest#getDispatcherType()
     */
    void include(ServletRequest req, ServletResponse resp) throws ServletException, IOException;
}
