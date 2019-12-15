package com.hyf.servlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * 定义一个通用的、独立于协议的servlet。
 * 要编写一个Web上使用的 HTTP servlet，请扩展{@link com.hyf.servlet.http.HttpServlet}
 * <p>
 * <code>GenericServlet</code>实现了<code>Servlet</code>和<code>ServletConfig</code>接口。
 * <code>GenericServlet</code>可以由servlet直接扩展，尽管扩展特定于协议的子类(如<code>HttpServlet</code>)更为常见
 * <p>
 * <code>GenericServlet</code>使得编写servlet更容易。
 * 它提供了生命周期方法的简单版本<code>init</code>和<code>destroy</code>和<code>ServletConfig</code>接口中的方法。
 * <code>GenericServlet</code>也实现了<code>log</code>方法，在<code>ServletContext</code>接口中声明
 * <p>
 * 要编写一个通用的servlet，您只需要重写抽象的<code>service</code>方法
 */
public abstract class GenericServlet implements Servlet, ServletConfig, Serializable {

    private static final long serialVersionUID = -319719445632817477L;

    private static final String LSTRING_FILE = "com.hyf.servlet.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    private transient ServletConfig config;

    /**
     * 什么也不做。所有servlet初始化都是通过<code>init</code>方法之一完成的
     */
    public GenericServlet() {
    }

    /**
     * 由servlet容器调用，以指示servlet正在退出服务。
     *
     * @see Servlet#destroy()
     */
    @Override
    public void destroy() {
    }

    /**
     * 返回包含指定初始化参数值的<code>字符串</code>，或者<code>null</code>(如果参数不存在)
     * <p>
     * 此方法提供方便。它从servlet的<code>ServletConfig</code>对象获取命名参数的值
     *
     * @param name 指定初始化参数的名称
     * @return 包含初始化参数的值
     * @see ServletConfig#getInitParameter(String)
     */
    @Override
    public String getInitParameter(String name) {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getInitParameter(name);
    }

    /**
     * 以<code>String</code>对象<code>Enumeration</code>的形式返回servlet的初始化参数的名称，
     * 如果servlet没有初始化参数，则返回一个空的<code>Enumeration</code>
     * <p>
     * 此方法提供方便。它从servlet的<code>ServletConfig</code>对象获取参数名
     *
     * @return <code>String</code>对象的枚举，包含servlet所有初始化参数的名称
     * @see ServletConfig#getInitParameterNames()
     */
    @Override
    public Enumeration<String> getInitParameterNames() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getInitParameterNames();
    }

    /**
     * 返回这个servlet的{@link ServletConfig}对象
     *
     * @return 初始化这个servlet的<code>ServletConfig</code>对象
     * @see Servlet#getServletConfig()
     */
    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    /**
     * 返回该servlet正在其中运行的{@link ServletContext}的引用
     * <p>
     * <code>ServletContext</code>对象通过<code>init</code>方法传递给这个servlet
     *
     * @return 此方法提供方便。它从servlet的<code>ServletConfig</code>对象获取上下文
     * @see ServletConfig#getServletContext()
     */
    @Override
    public ServletContext getServletContext() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getServletContext();
    }

    /**
     * 返回关于servlet的信息，如作者、版本和版权。
     * 默认情况下，该方法返回一个空字符串。
     * 重写此方法，使其返回有意义的值
     *
     * @return 关于此servlet的信息，默认为空字符串
     * @see Servlet#getServletInfo()
     */
    @Override
    public String getServletInfo() {
        return "";
    }

    /**
     * 由servlet容器调用，以指示servlet正在将servlet放入服务中
     * <p>
     * 此实现存储它从servlet容器接收的{@link ServletConfig}对象，以供以后使用。
     * 当覆盖方法的这种形式时，调用<code>super.init(config)</code>
     *
     * @param config 包含此servlet配置信息的<code>ServletConfig</code>对象
     * @throws ServletException 如果发生异常中断servlet的正常操作
     * @see Servlet#init(ServletConfig)
     * @see UnavailableException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    /**
     * 一个方便的方法，可以被覆盖，这样就不需要调用<code>super.init(config)</code>
     * <p>
     * 不要重写{@link #init(ServletConfig)}方法，只要重写这个方法，
     * 它就会被<code>GenericServlet.init(ServletConfig config)</code>调用。
     * <code>ServletConfig</code>对象仍然可以通过{@link #getServletConfig()}获取
     *
     * @throws ServletException 如果发生异常中断servlet的正常操作
     */
    public void init() throws ServletException {
    }

    /**
     * 将指定的消息写入servlet日志文件，并以servlet的名称作为前缀
     *
     * @param msg 一个<code>字符串</code>，指定要写入日志文件的消息
     * @see ServletContext#log(String)
     */
    public void log(String msg) {
        getServletContext().log(getServletName() + ": " + msg);
    }

    /**
     * 将给定的<code>Throwable</code>异常的解释性消息和堆栈跟踪写入servlet日志文件，以servlet的名称作为前缀
     *
     * @param msg 描述错误或异常的<code>字符串</code>
     * @param t   <code>Throwable</code>类型的错误或异常
     * @see ServletContext#log(String, Throwable)
     */
    public void log(String msg, Throwable t) {
        getServletContext().log(getServletName() + ": " + msg, t);
    }

    /**
     * 由servlet容器调用，以允许servlet响应请求
     * <p>
     * 这个方法被声明为抽象的，所以子类(如<code>HttpServlet</code>)必须覆盖它
     *
     * @param req  请求对象 <code>ServletRequest</code>对象，包含客户端的请求
     * @param resp 响应对象 <code>ServletResponse</code>对象，该对象将包含servlet的响应
     * @throws ServletException 可能发生干扰servlet执行的异常
     * @throws IOException      如果发生输入输出异常
     * @see Servlet#service(ServletRequest, ServletResponse)
     */
    @Override
    public abstract void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException;

    /**
     * 返回此servlet实例的名称
     *
     * @return 返回此servlet实例的名称
     * @see ServletConfig#getServletName()
     */
    @Override
    public String getServletName() {
        ServletConfig sc = getServletConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }
        return sc.getServletName();
    }
}
