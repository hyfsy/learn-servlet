package com.hyf.servlet;

import com.hyf.servlet.descriptor.JspConfigDescriptor;
import com.hyf.servlet.http.HttpServletRequest;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

/**
 * servlet上下文环境对象
 * 定义servlet的一组方法用于与其servlet容器通信
 * 例如，获取文件的MIME类型，分派请求，或写入日志文件
 * 在Java虚拟机中的每个Web应用程序都只有一个上下文对象
 * “web应用程序”是servlet和Content的集合，安装在服务器URL名称空间的特定子集下，如<code>/catalog</code>
 * <code>ServletContext</code>对象包含在{@link ServletConfig}对象中，该对象是Web服务器在初始化servlet时提供的servlet
 *
 * @see ServletConfig
 * @see ServletConfig#getServletContext
 */
public interface ServletContext {

    /**
     * 通过servlet容器为<tt>ServletContext</tt>)定义的存储私有的临时目录(<tt>java.io.File</tt>类型)的名称
     */
    public static final String TEMPDIR = "javax.servlet.context.tempdir";

    /**
     * ServletContext属性的名称，其值（类型为java.util.List <java.lang.String>）包含WEB-INF / lib中的JAR文件的名称列表，按其网页片段名称排序
     */
    public static final String ORDERED_LIBS = "javax.servlet.context.orderedLibs";

    /**
     * 返回web应用程序的上下文路径
     * 上下文路径是请求URI中被使用为选择请求的上下文的部分
     * 上下文路径总是在请求URI中出现，以Web服务器的URL名称空间为基础
     * 如果此上下文是“默认”上下文，将是一个空字符串
     * 如果上下文不是服务器名称空间的根，则路径以 / 开始字符，但不以 / 字符结尾
     * <p>
     * servlet容器可能匹配多个上下文路径在这种情况下：
     * 使用{@link HttpServletRequest#getContextPath}将返回请求使用的实际上下文路径
     * 它可能与此方法返回的路径不同
     * 应将此方法{@link #getContextPath}返回的上下文路径视为应用程序的主要或首选上下文路径
     *
     * @return 返回web应用程序的上下文路径，或为默认(根)的上下文""
     */
    String getContextPath();

    /**
     * 返回一个<code>ServletContext</code>对象对应于服务器上的指定URL
     * 此方法允许servlet访问服务器的各个部分的上下文，以及需要从上下文获取{@link RequestDispatcher}对象
     * 给定的路径必须以<tt>/</tt>开始，这是指向服务器的文档相对根目录，并根据此容器上托管的其他web应用程序上下文的根进行匹配
     * 在有安全意识的环境中，servlet容器可能根据给定的URL返回<code>null</code>
     *
     * @param uriPath 容器中另一个web应用程序的上下文路径
     * @return 如果有则返回对应URL的<code>ServletContext</code>对象，如果不存在，则返回空
     * 或者容器希望对这个访问进行限制
     * @see RequestDispatcher
     */
    ServletContext getContext(String uriPath);

    /**
     * 返回servlet容器支持的Servlet API的主要版本
     * 所有符合的实现在3.0版本中，这个方法必须返回整数 3
     *
     * @return 3
     */
    int getMajorVersion();

    /**
     * 返回servlet容器支持的Servlet API的小版本
     * 在3.0版本中，这个方法必须返回整数 0
     *
     * @return 0
     */
    int getMinorVersion();

    /**
     * 获取这个Servlet上下文所表示的应用程序所基于的Servlet规范的主要版本
     * 返回的值可能与{@link #getMajorVersion}不同，返回Servlet规范的版本主要由Servlet容器支持
     *
     * @return 基于这个ServletContext表示的应用程序的Servlet规范的主要版本
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    int getEffectiveMajorVersion();

    /**
     * 获取这个Servlet上下文所表示的应用程序所基于的Servlet规范的小版本
     * 返回的值可能与{@link #getMinorVersion}不同，后者返回Servlet容器支持的小版本Servlet规范
     *
     * @return 这个Servlet上下文所表示的应用程序所基于的Servlet规范的小版本
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    int getEffectiveMinorVersion();

    /**
     * 返回指定文件的MIME类型，如果MIME类型未知，则返回<code>null</code>
     * MIME类型由servlet容器的配置决定，可以在web应用程序部署描述符中指定
     * 常见的MIME类型包括：
     * <code>text/html</code>和<code>image/gif</code>
     *
     * @param file 指定文件的名称
     * @return 一个 <code>String</code> 指定文件的MIME类型
     */
    String getMimeType(String file);

    /**
     * 返回web应用程序中所有资源路径的目录式列表，其中最长的子路径与提供的路径参数相匹配
     * 路径表示子目录路径以<tt>/</tt>结束
     * 返回的路径都是相对于web应用程序的根，或相对于<tt>/META-INF/resources</tt>
     * * web应用程序内部JAR文件的目录
     * * <tt>/WEB-INF/lib</tt>目录，并有一个前导<tt>/</tt>
     * 例如，web应用程序包含以下内容:
     * <code><pre>
     * /welcome.html
     * /catalog/index.html
     * /catalog/products.html
     * /catalog/offers/books.html
     * /customer/login.jsp
     * /WEB-INF/web.xml
     * </pre></code>
     * 调用方法：
     * <tt>getResourcePaths("/")</tt>将返回<tt>{"/welcome.html", "/catalog/", "/customer/", "/WEB-INF/"}</tt>
     * <tt>getResourcePaths("/catalog/")</tt>将返回{"/index.html", "/products.html", "/offers/"}
     *
     * @param path 用于匹配资源的部分路径，必须以<tt>/</tt>开始
     * @return 如果有，则返回一个包含目录列表的集合
     * 从提供的路径开始，在web应用程序中没有其路径的资源，则为空
     */
    Set<String> getResourcePaths(String path);

    /**
     * 返回映射到给定路径的资源的URL
     * <p>
     * 路径必须以<tt>/</tt>开始，并解释关联到当前上下文的根，
     * 或者在<tt>/META-INF/resources</tt>目录下关联到web应用程序内部<tt>/WEB-INF/lib</tt>目录中的JAR文件
     * 此方法在搜索<tt>/WEB-INF/lib</tt>中的任何JAR文件之前，会首先搜索web应用程序文档根路径的请求资源
     * 搜索<tt>/WEB-INF/lib</tt>中的JAR文件的顺序是未定义的
     * <p>
     * 这个方法允许servlet容器可从 任何来源 创建一个资源给servlet
     * 资源可以位于本地，远程，文件系统，在数据库中，或在<code>.war</code>文件中
     * <p>
     * servlet容器访问资源 必须<tt>实现URL处理程序</tt>和<tt>有<code>URLConnection</code>对象</tt>
     * <p>
     * 如果没有资源映射到路径名，则此方法返回<code>null</code>
     * <p>
     * 一些容器可能允许使用URL类的方法写入此方法返回的URL
     * <p>
     * 资源内容是直接返回的，因此请注意请求<code>.jsp</code>页面返回jsp源代码
     * 使用<code>RequestDispatcher</code>来包含执行的结果
     * <p>
     * 这个方法的目的不同于{@link Class#getResource(String)}，
     * 因为Class的方法是基于类加载器查找资源的，而这方法不使用类加载器
     *
     * @param path 指定资源的路径
     * @return 返回位于指定路径的资源，如果该路径没有资源则返回<code>null</code>
     * @throws MalformedURLException 如果路径名没有给出正确的格式
     */
    URL getResource(String path) throws MalformedURLException;

    /**
     * 返回位于指定路径资源的一个<code>InputStream</code>对象
     *
     * <code>InputStream</code>中的数据可以是任何类型或长度
     * 路径必须根据 在<code>getResource</code>中给出的规则
     * 如果指定的路径没有资源存在，这个方法返回<code>null</code>
     * <p>
     * 通过<code>getResource</code>方法获得的内容长度和内容类型等元信息在使用此方法时丢失
     * <p>
     * servlet容器访问资源 必须<tt>实现URL处理程序</tt>和<tt>有<code>URLConnection</code>对象</tt>
     * <p>
     * 这个方法的目的不同于{@link Class#getResource(String)}，
     * 因为Class的方法是基于类加载器查找资源的，而这方法不使用类加载器
     *
     * @param path 指定资源的路径
     * @return 返回<code>InputStream</code>给servlet，如果指定的路径没有资源存在，返回<code>null</code>
     * @see #getResource(String)
     */
    InputStream getResourceAsStream(String path);

    /**
     * 返回一个起作用的{@link RequestDispatcher}对象作为位于给定路径的资源的包装
     * 一个<code>RequestDispatcher</code>对象可以用来转发对资源的请求或在响应中包含资源
     * 资源可以是动态的，也可以是静态的
     * <p>
     * 路径名必须以<tt>/</tt>开头，并解释为相对于当前上下文根
     * 使用<code>getContext</code>获取外部资源的<code>RequestDispatcher</code>的上下文对象
     * <p>
     * 如果<code>ServletContext</code>不能返回<code>RequestDispatcher</code>，此方法返回<code>null</code>
     *
     * @param path 返回一个<code>RequestDispatcher</code>对象用作资源的包装器
     * @return 返回一个<code>RequestDispatcher</code>对象作为在指定路径的资源的包装器，
     * 如果<code>ServletContext</code>不能返回<code>RequestDispatcher</code>，则返回<code>null</code>
     * @see RequestDispatcher
     * @see #getContext(String)
     */
    RequestDispatcher getRequestDispatcher(String path);

    /**
     * 返回一个{@link RequestDispatcher}对象包装给定名称的servlet
     * <p>
     * servlet(以及JSP页面)可以通过服务器来命名管理或通过web应用程序部署描述符，
     * servlet实例可以使用{@link ServletConfig#getServletName}
     * <p>
     * <code>ServletContext</code>由于任何原因不能返回<code>RequestDispatcher</code>，则此方法返回<code>null</code>
     *
     * @param name 一个<code>String</code>指定的名称，指定要包装的servlet
     * @return 返回一个<code>RequestDispatcher</code>对象
     * *用作命名servlet的包装器，
     * *或<code>null</code>如果<code>ServletContext</code>
     * *无法返回<code>RequestDispatcher</code>
     */
    RequestDispatcher getNamedDispatcher(String name);

    /**
     * @deprecated 2.1 在3.0版本后返回null，没有直接替代的方法
     * 可以使用{@link ServletContext}类，通过调用公共非servlet类的方法，可以执行共享业务逻辑
     */
    Servlet getServlet(String name) throws ServletException;

    /**
     * @deprecated 2.0 在3.0版本后返回null，没有直接替代的方法
     */
    Enumeration<Servlet> getServlets();

    /**
     * @deprecated 2.1 在3.0版本后返回null，没有直接替代的方法
     */
    Enumeration<String> getServletNames();

    /**
     * 通常将指定的消息写入servlet日志文件，经常是一个事件日志
     * servlet日志文件的名称和类型特定于servlet容器
     *
     * @param msg 指定要写入日志文件的消息
     */
    void log(String msg);

    /**
     * @deprecated 2.1 使用{@link #log(String, Throwable)}代替
     * 此方法最初定义为编写一个异常的堆栈跟踪和解释错误消息到servlet日志文件
     */
    void log(Exception exception, String msg);

    /**
     * 针对给定的<code>Throwable</code>异常写入说明消息和堆栈跟踪到servlet日志文件。
     * servlet日志的名称和类型文件是特定于servlet容器的，通常是一个事件日志
     *
     * @param msg       描述错误或异常的消息
     * @param throwable 错误或异常
     */
    void log(String msg, Throwable throwable);

    /**
     * 获取<code>"/"</code>在机器中的实际地址。
     * 例如，如果<tt>path</tt> = <tt>/index.html</tt>，此方法将返回服务器的绝对文件路径
     * <p>
     * 实际返回的路径将是一个表单，适用于电脑及操作系统上正在运行的servlet容器，包括正确的路径分隔符
     * <p>
     * 只有当容器将JAR文件从其包含的JAR文件中解包时，才必须考虑<tt>/META-INF/Resources</tt>应用程序对应的<tt>/WEB-INF/lib</tt>目录中的资源，
     * 在这种情况下，必须返回解包位置的路径。
     * <p>
     * 如果servlet容器无法将给定的<i>虚拟</i>路径转换为<i>物理</i>路径，此方法返回<code>null</code>
     *
     * @param path 要转换为物理路径的虚拟路径
     * @return 返回项目物理路径，如果不能执行翻译则返回<code>null</code>
     */
    String getRealPath(String path);

    /**
     * 返回正在运行的servlet容器的名称和版本
     * 返回字符串的形式：<i>serverName</i>/<i>versionNumber</i>
     * <p>
     * servlet容器可能返回其他可选信息例如，在括号中的主字符串之后，
     * 例如：<code>JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86)</code>
     *
     * @return 返回一个<code>String</code>，其中至少包含servlet容器名称和版本号
     */
    String getServletInfo();

    /**
     * 返回一个字符串，其中包含指定的值<i>上下文范围</i>的初始化参数，如果参数不存在，则返回<code>null</code>
     * <p>
     * 此方法可以使可用的配置信息在整个web应用程序被使用。
     * 例如，它可以提供一个网站管理员的电子邮件地址或持有关键数据的系统名称
     * <p>
     * 和{@link ServletConfig#getInitParameter(String)}的区别是该方法是取出web.xml的全局配置标签的属性，
     * 而后一个方法是获取每个servlet对应的初始化参数
     *
     * @param name 被请求值的参数名称
     * @return 返回一个<code>String</code>，其中至少包含servlet容器名称和版本号
     * @see ServletConfig#getInitParameter(String)
     */
    String getInitParameter(String name);

    /**
     * 返回一个<code>String</code>类型的<code>Enumeration</code>对象，包含上下文的初始化参数的名称。
     * 如果上下文没有初始化，则返回空的<code>Enumeration</code>对象
     * <p>
     * 和{@link ServletConfig#getInitParameterNames()}的区别是该方法是取出web.xml的全局配置标签的属性，
     * 而后一个方法是获取每个servlet对应的初始化参数
     *
     * @return 返回一个<code>Enumeration</code>对象，其中包含上下文的初始化参数名称
     * @see ServletConfig#getInitParameterNames()
     */
    Enumeration<String> getInitParameterNames();

    /**
     * 使用给定的名称和设置上下文初始化参数这个ServletContext上的值
     *
     * @param name  指定要设置的上下文初始化参数的名称
     * @param value 指定要设置的上下文初始化参数的值
     * @return 如果通过给定的name和value设置这个ServletContext的初始化参数成功，返回true。
     * 如果它没有被设置，因为这个ServletContext已经包含了一个具有匹配名称的上下文初始化参数，返回false
     * @throws IllegalStateException         如果这个ServletContext已经初始化
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    boolean setInitParameter(String name, String value);

    /**
     * 返回指定名称的servlet容器属性，如果没有该名称的属性则返回<code>null</code>
     * <p>
     * 可以通过{@link #getAttributeNames()}获取所有的属性名称
     * <p>
     * 该属性以<code>java.lang.Object</code>或者一些子类的形式返回
     *
     * @param name 属性名称
     * @return 返回一个包含该值的<code>Object</code>对象，如果没有匹配的属性存在返回<code>null</code>
     * @see #getAttributeNames()
     */
    Object getAttribute(String name);

    /**
     * 返回一个<code>枚举</code>，其中包含了ServletContext中可用的属性名
     * <p>
     * 使用{@link #getAttribute}方法和属性名 获取属性值
     *
     * @return 返回属性名字的<code>Enumeration</code>
     */
    Enumeration<String> getAttributeNames();

    /**
     * 将对象绑定到此ServletContext中的给定属性名。
     * 如果指定的名称已经存在，方法将把属性值替换为新属性值
     * <p>
     * 如果<code>ServletContext</code>中有配置监听器，容器也会相应地通知它们
     * <p>
     * 如果传递空值，效果与调用<code>removeAttribute()</code>相同
     *
     * @param name   指定的属性名称
     * @param object 绑定属性名称的对象
     */
    void setAttribute(String name, Object object);

    /**
     * 在ServletContext中删除具有给定名称的属性。
     * 删除后，随后调用{@link #getAttribute}来检索属性的值将返回<code>null</code>
     * <p>
     * 如果<code>ServletContext</code>中有配置监听器，容器也会相应地通知它们
     *
     * @param name 指定要删除的属性名称
     */
    void removeAttribute(String name);

    /**
     * 按照display-name元素在此web应用程序的部署描述符中指定的那样，返回与此ServletContext对应的web应用程序的名称
     *
     * @return 返回web应用程序的名称，如果没有在部署描述符中声明名称，则返回null
     */
    String getServletContextName();

    /**
     * 将具有给定名称和类名的servlet添加到此servlet的上下文中
     * <p>
     * 注册的servlet可以通过返回的{@link ServletRegistration}对象进一步配置
     * <p>
     * 指定的<tt>className</tt>将使用与此ServletContext表示的应用程序相关联的类加载器加载
     * <p>
     * 如果这个ServletContext已经包含了一个servlet的初步ServletRegistration对象，并具有给定的<tt>servletName</tt>，
     * 那么它将被完成(通过将给定的<tt>className</tt>分配给它)并返回
     * <p>
     * 这个方法用给定的<tt>className</tt>自检类：
     * <ol>
     * <li>{@link com.hyf.servlet.annotation.ServletSecurity}</li>
     * <li>{@link com.hyf.servlet.annotation.MultipartConfig}</li>
     * <li><tt>javax.annotation.security.RunAs</tt></li>
     * <li><tt>javax.annotation.security.DeclareRoles</tt></li>
     * </ol>
     * 此外，如果具有给定<tt>类名</tt>的类表示托管Bean，则此方法支持资源注入
     *
     * @param servletName servlet的名称
     * @param className   servlet的全类名
     * @return 返回一个ServletRegistration对象，该对象可以用于进一步的操作配置已注册的servlet，
     * 否则如果ServletContext已经包含了一个完整的ServletRegistration，并且该servlet具有给定的<tt>servletName</tt>，则返回<code>null</code>
     * @throws IllegalStateException         如果这个ServletContext已经初始化
     * @throws IllegalArgumentException      如果<code>servletName</code>是空的或空字符串
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    ServletRegistration.Dynamic addServlet(String servletName, String className);

    /**
     * 将具有给定名称和类名的servlet添加到此servlet的上下文中
     * <p>
     * 注册的servlet可以通过返回的{@link ServletRegistration}对象进一步配置
     * <p>
     * 如果这个ServletContext已经包含了一个servlet的初步ServletRegistration对象，并具有给定的<tt>servletName</tt>，
     * 那么它将被完成(通过将给定的<tt>className</tt>分配给它)并返回
     *
     * @param servletName servlet的名称
     * @param servlet     要注册的servlet实例
     * @return 返回一个ServletRegistration对象，该对象可以用于进一步的操作配置已注册的servlet，
     * 否则如果ServletContext已经包含了一个完整的ServletRegistration，并且该servlet具有给定的<tt>servletName</tt>，则返回<code>null</code>
     * @throws IllegalStateException         如果这个ServletContext已经初始化
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @throws IllegalArgumentException      如果给的servlet实例实现了{@link SingleThreadModel}或<code>servletName</code>是空的或空字符串
     * @since 3.0
     */
    ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet);

    /**
     * 将具有给定名称和类名的servlet添加到此servlet的上下文中
     * <p>
     * 注册的servlet可以通过返回的{@link ServletRegistration}对象进一步配置
     * <p>
     * 如果这个ServletContext已经包含了一个servlet的初步ServletRegistration对象，并具有给定的<tt>servletName</tt>，
     * 那么它将被完成(通过将给定的<tt>className</tt>分配给它)并返回
     * <p>
     * 这个方法用给定的<tt>className</tt>自检类：
     * <ol>
     * <li>{@link com.hyf.servlet.annotation.ServletSecurity}</li>
     * <li>{@link com.hyf.servlet.annotation.MultipartConfig}</li>
     * <li><tt>javax.annotation.security.RunAs</tt></li>
     * <li><tt>javax.annotation.security.DeclareRoles</tt></li>
     * </ol>
     * 此外，如果具有给定<tt>类名</tt>的类表示托管Bean，则此方法支持资源注入
     *
     * @param servletName  servlet的名称
     * @param servletClass servlet所在的类对象实例
     * @return 返回一个ServletRegistration对象，该对象可以用于进一步的操作配置已注册的servlet，
     * 否则如果ServletContext已经包含了一个完整的ServletRegistration，并且该servlet具有给定的<tt>servletName</tt>，则返回<code>null</code>
     * @throws IllegalStateException         如果这个ServletContext已经初始化
     * @throws IllegalArgumentException      如果<code>servletName</code>是空的或空字符串
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass);

    /**
     * 实例化给定的Servlet类
     * <p>
     * 返回的Servlet实例可以进一步定制在 调用注册到这个ServletContext的{@link #addServlet(String, Servlet)}方法之前
     * <p>
     * 给定的Servlet类必须定义一个零参数构造函数用来实例化它
     * <p>
     * 这个方法用给定的<tt>className</tt>自检类：
     * <ol>
     * <li>{@link com.hyf.servlet.annotation.ServletSecurity}</li>
     * <li>{@link com.hyf.servlet.annotation.MultipartConfig}</li>
     * <li><tt>javax.annotation.security.RunAs</tt></li>
     * <li><tt>javax.annotation.security.DeclareRoles</tt></li>
     * </ol>
     * 此外，如果具有给定<tt>类名</tt>的类表示托管Bean，则此方法支持资源注入
     *
     * @param clazz 要实例化的Servlet类
     * @return 返回新的Servlet实例
     * @throws ServletException              如果给定的<tt>clazz</tt>实例化失败
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException;

    /**
     * 获取与给定<tt>servletName</tt>的servlet对应的ServletRegistration
     *
     * @param servletName servlet的名称
     * @return 通过给定的<tt>servletName</tt>返回servlet的(完整或初步)ServletRegistration，
     * 如果该名称不存在ServletRegistration，则返回null
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    ServletRegistration getServletRegistration(String servletName);

    /**
     * 获取与此ServletContext中注册的所有servlet相对应的ServletRegistration对象（以servlet名称为键）的映射（可能是空的）
     * <p>
     * 返回的映射包括与所有声明的和标注的servlet对应的ServletRegistration对象，
     * 以及通过一个addServlet方法添加的与所有servlet对应的ServletRegistration对象
     * <p>
     * 如果允许，对返回映射的任何更改都不能影响这个ServletContext
     *
     * @return 返回与当前在此ServletContext中注册的所有servlet对应的（完整的和初步的）ServletRegistration 对象的映射
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    Map<String, ? extends ServletRegistration> getServletRegistrations();

    /**
     * 将给定名称和类名称的过滤器添加到这个servlet上下文中
     * <p>
     * 可以通过返回的{@link FilterRegistration}对象进一步配置已注册的过滤器
     * <p>
     * 指定的类名将使用与此ServletContext所表示的应用程序相关联的类加载器来加载
     * <p>
     * 如果这个ServletContext已经包含了具有给定文件名的过滤器的初步的FilterRegistration，那么它将被完成（通过为它分配给定的类名）并返回
     *
     * @param filterName 过滤器的名称
     * @param className  过滤器全类名
     * @return 返回一个FilterRegistration对象，该对象可用于进一步配置已注册的过滤器，
     * 如果这个ServletContext已经包含了一个具有给定名称的过滤器的完整的FilterRegistration，则返回null
     * @throws IllegalStateException         如果这个ServletContext已经被初始化
     * @throws IllegalArgumentException      如果filterName为null或一个空字符串
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    FilterRegistration.Dynamic addFilter(String filterName, String className);

    /**
     * 将给定名称和类名称的过滤器添加到这个servlet上下文中
     * <p>
     * 可以通过返回的{@link FilterRegistration}对象进一步配置已注册的过滤器
     * <p>
     * 如果这个ServletContext已经包含了具有给定文件名的过滤器的初步的FilterRegistration，那么它将被完成（通过为它分配给定的类名）并返回
     *
     * @param filterName 过滤器的名称
     * @param filter     注册的过滤器实例
     * @return 返回一个FilterRegistration对象，该对象可用于进一步配置已注册的过滤器，
     * 如果这个ServletContext已经包含了一个具有给定名称的过滤器的完整的FilterRegistration，则返回null
     * @throws IllegalStateException         如果这个ServletContext已经被初始化
     * @throws IllegalArgumentException      如果filterName为null或一个空字符串
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    FilterRegistration.Dynamic addFilter(String filterName, Filter filter);

    /**
     * 将给定名称和类名称的过滤器添加到这个servlet上下文中
     * <p>
     * 可以通过返回的{@link FilterRegistration}对象进一步配置已注册的过滤器
     * <p>
     * 如果这个ServletContext已经包含了具有给定文件名的过滤器的初步的FilterRegistration，那么它将被完成（通过为它分配给定的类名）并返回
     *
     * @param filterName  过滤器的名称
     * @param filterClass 将要被实例化的过滤器的类对象
     * @return 返回一个FilterRegistration对象，该对象可用于进一步配置已注册的过滤器，
     * 如果这个ServletContext已经包含了一个具有给定名称的过滤器的完整的FilterRegistration，则返回null
     * @throws IllegalStateException         如果这个ServletContext已经被初始化
     * @throws IllegalArgumentException      如果filterName为null或一个空字符串
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass);

    /**
     * 实例化给定的过滤器类
     * <p>
     * 返回的过滤器实例可以通过调用{@link #addFilter(String, Filter)}在此ServletContext注册之前被进一步定制
     * <p>
     * 给定的Filter类必须定义一个无参构造函数，用于实例化它
     *
     * @param clazz 要实例化的过滤器类
     * @return 新过滤器实例
     * @throws ServletException              如果给定的<code>Class</code>对象初始化失败
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    <T extends Filter> T createFilter(Class<T> clazz) throws ServletException;

    /**
     * 获取与给定<tt>过滤器名称</tt>对应的过滤器的FilterRegistration
     *
     * @param filterName 过滤器的名称
     * @return 返回给定<tt>filterName</tt>的过滤器的（完整或初步的）FilterRegistration对象，
     * 如果在该名称下不存在FilterRegistration，则返回null
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    FilterRegistration getFilterRegistration(String filterName);

    /**
     * 获取与此ServletContext注册的所有servlet对应的ServletRegistration对象的映射(可能是空的)
     * <p>
     * 返回的映射包括所有声明和注释的servlet对应的ServletRegistration对象，
     * 以及通过<tt>addServlet</tt>方法添加的所有servlet对应的ServletRegistration对象
     * <p>
     * 如果允许，对返回映射的任何更改都不能影响这个ServletContext
     *
     * @return 返回FilterRegistration对象的映射(完整的和初步的)，对应于当前在这个ServletContext中注册的所有过滤器
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    Map<String, ? extends FilterRegistration> getFilterRegistrations();

    /**
     * 获取{@link SessionCookieConfig}对象，可以通过该对象配置 代表此<tt>ServletContext</tt>创建的会话跟踪cookie 的各种属性
     * <p>
     * 重复调用此方法将返回相同的<tt>SessionCookieConfig</tt>实例
     *
     * @return 可以通过<tt>SessionCookieConfig</tt>对象配置代表该<tt>ServletContext</tt>的会话跟踪cookie的各种属性
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    SessionCookieConfig getSessionCookieConfig();

    /**
     * 设置会话跟踪模式，使其对<tt>ServletContext</tt>容器有效
     * <p>
     * 给定的<tt>sessionTrackingModes</tt>替换了之前在这个<tt>ServletContext</tt>上设置的任何会话跟踪模式
     *
     * @param sessionTrackingModes 一组会话跟踪模式，对<tt>ServletContext</tt>有效
     * @throws IllegalStateException         如果当前ServletContext已经被初始化
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @throws IllegalArgumentException      如果SessionTrackingModes指定了一个与SessionTrackingMode.SSL不同的，却带有SessionTrackingMode.SSL的SessionTrackingMode的组合。
     *                                       或者如果<tt>sessionTrackingModes</tt>指定了servlet容器不支持的会话跟踪模式
     * @since 3.0
     */
    void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes);

    /**
     * 获取此<tt>ServletContext</tt>默认支持的会话跟踪模式
     *
     * @return 此<tt>ServletContext</tt>默认支持的会话跟踪模式集合
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    Set<SessionTrackingMode> getDefaultSessionTrackingModes();

    /**
     * 获取此<tt>ServletContext</tt>有效的会话跟踪模式
     * <p>
     * 有效的会话跟踪模式是通过{@link #setSessionTrackingModes}提供的
     * <p>
     * 默认情况下，{@link #getDefaultSessionTrackingModes}返回的会话跟踪模式是有效的
     *
     * @return 此<tt>ServletContext</tt>有效的会话跟踪模式集合
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    Set<SessionTrackingMode> getEffectiveSessionTrackingModes();

    /**
     * 将具有给定类名的监听器添加到此ServletContext
     * <p>
     * 具有给定名称的类将使用与此ServletContext表示的应用程序相关联的类加载器加载，并且必须实现以下一个或多个接口:
     * <ul>
     * <li>{@link ServletContextAttributeListener}</li>
     * <li>{@link ServletRequestListener}</li>
     * <li>{@link ServletRequestAttributeListener}</li>
     * <li>{@link com.hyf.servlet.http.HttpSessionAttributeListener}</li>
     * <li>{@link com.hyf.servlet.http.HttpSessionIdListener}</li>
     * <li>{@link com.hyf.servlet.http.HttpSessionListener}</li>
     * </ul>
     * <p>
     * 如果这个ServletContext被传递给{@link ServletContainerInitializer#onStartup}，那么除了上面列出的接口外，具有给定名称的类还需要实现{@link ServletContextListener}
     * <p>
     * 作为这个方法调用的一部分，容器必须通过指定的类名加载类，以确保它实现了其中的一个所需的接口
     * <p>
     * 如果具有给定名称的类实现了一个监听器接口，该接口的调用顺序对应于声明顺序：
     * 即，如果它实现了{@link ServletRequestListener}、{@link ServletContextListener}或{@link javax.servlet.http.HttpSessionListener}，
     * 则新侦听器将被添加到该接口的有序侦听器列表的末尾
     * <p>
     * 此外，如果具有给定<tt>className</tt>的类表示托管Bean，则此方法支持资源注入
     *
     * @param className 监听器的全类名
     * @throws IllegalArgumentException      如果给定名称的类没有实现上述的任何接口，
     *                                       或者实现{@link ServletContextListener}而这个ServletContext没有传递给{@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         如果这个ServletContext已经被初始化
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    void addListener(String className);

    /**
     * 将给定的监听器添加到此ServletContext
     * <p>
     * 给定的监听器必须实现以下一个或多个接口:
     * <ul>
     * <li>{@link ServletContextAttributeListener}</tt>
     * <li>{@link ServletRequestListener}</tt>
     * <li>{@link ServletRequestAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionIdListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionListener}</tt>
     * </ul>
     * <p>
     * 如果这个ServletContext被传递给{@link ServletContainerInitializer#onStartup}，那么除了上面列出的接口外，具有给定名称的类还需要实现{@link ServletContextListener}
     * <p>
     * 如果具有给定名称的类实现了一个监听器接口，该接口的调用顺序对应于声明顺序：
     * 即，如果它实现了{@link ServletRequestListener}、{@link ServletContextListener}或{@link javax.servlet.http.HttpSessionListener}，
     * 则新侦听器将被添加到该接口的有序侦听器列表的末尾
     *
     * @param t 要添加的监听器对象
     * @throws IllegalArgumentException      如果给定名称的类没有实现上述的任何接口，
     *                                       或者实现{@link ServletContextListener}而这个ServletContext没有传递给{@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         如果这个ServletContext已经被初始化
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    <T extends EventListener> void addListener(T t);

    /**
     * 将给定的监听器添加到此ServletContext
     * <p>
     * 给定的监听器必须实现以下一个或多个接口:
     * <ul>
     * <li>{@link ServletContextAttributeListener}</tt>
     * <li>{@link ServletRequestListener}</tt>
     * <li>{@link ServletRequestAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionAttributeListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionIdListener}</tt>
     * <li>{@link javax.servlet.http.HttpSessionListener}</tt>
     * </ul>
     * <p>
     * 如果这个ServletContext被传递给{@link ServletContainerInitializer#onStartup}，那么除了上面列出的接口外，具有给定名称的类还需要实现{@link ServletContextListener}
     * <p>
     * 如果具有给定名称的类实现了一个监听器接口，该接口的调用顺序对应于声明顺序：
     * 即，如果它实现了{@link ServletRequestListener}、{@link ServletContextListener}或{@link javax.servlet.http.HttpSessionListener}，
     * 则新侦听器将被添加到该接口的有序侦听器列表的末尾
     * <p>
     * 此外，如果具有给定<tt>className</tt>的类表示托管Bean，则此方法支持资源注入
     *
     * @param listenerClass 监听器的全类名
     * @throws IllegalArgumentException      如果给定名称的类没有实现上述的任何接口，
     *                                       或者实现{@link ServletContextListener}而这个ServletContext没有传递给{@link ServletContainerInitializer#onStartup}
     * @throws IllegalStateException         如果这个ServletContext已经被初始化
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.0
     */
    void addListener(Class<? extends EventListener> listenerClass);

    /**
     * 实例化给定的EventListener类
     * <p>
     * 指定的EventListener类必须实现至少一个
     * <code>{@link ServletContextListener}</code>
     * <code>{@link ServletContextAttributeListener}</code>
     * <code>{@link ServletRequestListener}</code>
     * <code>{@link ServletRequestAttributeListener}</code>
     * <code>{@link com.hyf.servlet.http.HttpSessionListener}</code>
     * <code>{@link com.hyf.servlet.http.HttpSessionIdListener}</code>
     * <code>{@link com.hyf.servlet.http.HttpSessionAttributeListener}</code>
     * <p>
     * 可以对返回的<code>EventListener</code>实例可以进一步定制，在调用{@link #addListener(EventListener)}将监听器注册到这个ServletContext之前
     * <p>
     * 给定的<code>EventListener</code>类必须定义一个无参构造函数，用于实例化它
     * <p>
     * 此外，如果具有给定<tt>className</tt>的类表示托管Bean，则此方法支持资源注入
     *
     * @param clazz 要实例化的EventListener类
     * @return 返回新的EventListener实例
     * @throws ServletException              如果给定的<tt>clazz</tt>实例化失败
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @throws IllegalArgumentException      如果给定的类<i>没有</i>实现任何一个接口：
     *                                       <code>{@link ServletContextListener}</code>
     *                                       <code>{@link ServletContextAttributeListener}</code>
     *                                       <code>{@link ServletRequestListener}</code>
     *                                       <code>{@link ServletRequestAttributeListener}</code>
     *                                       <code>{@link com.hyf.servlet.http.HttpSessionListener}</code>
     *                                       <code>{@link com.hyf.servlet.http.HttpSessionIdListener}</code>
     *                                       <code>{@link com.hyf.servlet.http.HttpSessionAttributeListener}</code>
     * @since 3.0
     */
    <T extends EventListener> T createListener(Class<T> clazz) throws ServletException;

    /**
     * 通过ServletContext 获取web应用程序中<code>web.xml</code>和<code>web-fragment.xml</code>描述文件中的带<code>jsp-config</code>标签的配置信息
     *
     * @return 通过ServletContext 获取web应用程序中<code>web.xml</code>和<code>web-fragment.xml</code>描述文件中的带<code>jsp-config</code>标签的配置信息，
     * 如果没有该配置，则返回<code>null</code>
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @see JspConfigDescriptor
     * @since 3.0
     */
    JspConfigDescriptor getJspConfigDescriptor();

    /**
     * 获取此ServletContext表示的web应用程序的类加载器
     * <p>
     * 如果安全管理器存在,并且和调用者的类加载器不一样的，
     * 或是请求的类装入器的祖先，
     * 那么安全管理器会调用<code>checkPermission</code>方法和<code>RuntimePermission(“getClassLoader”)</code>来检查是否应该允许访问所请求的类加载器
     *
     * @return 返回由ServletContext表示的web应用程序的类装入器
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @throws SecurityException             如果安全管理器拒绝访问请求的类装入器
     * @since 3.0
     */
    ClassLoader getClassLoader();

    /**
     * 声明使用<code>isUserInRole</code>测试的角色名
     * <p>
     * 角色被隐式的声明为一个结果，
     * 在{@link ServletRegistration}接口的{@link ServletRegistration.Dynamic#setServletSecurity}方法
     * 和{@link ServletRegistration.Dynamic#setRunAsRole}方法中不需要声明
     *
     * @param roleNames 声明的角色名
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @throws IllegalArgumentException      如果参数roleNames为null或空字符串
     * @throws IllegalStateException         如果ServletContext已经初始化
     * @since 3.0
     */
    void declareRoles(String... roleNames);

    /**
     * 返回部署ServletContext的逻辑主机的配置名
     * <p>
     * Servlet容器可能支持多个逻辑主机。getNamedDispatcher
     * <p>
     * 对于部署在逻辑主机上的所有servlet上下文，此方法必须返回相同的名称，
     * 并且此方法返回的名称必须是独立的、每个逻辑主机稳定的、适合用于将服务器配置信息与逻辑主机关联的名称。
     * <p>
     * 返回的值不期望或要求与逻辑主机的网络地址或主机名相等
     *
     * @return 返回一个字符串，其中包含部署servlet上下文的逻辑主机的配置名，如：Catalina/localhost
     * @throws UnsupportedOperationException 如果这个ServletContext被传给{@link ServletContextListener}的子类的{@link ServletContextListener#contextInitialized()}方法，
     *                                       但是该子类没有在<code>web.xml</code>或<code>web-fragment.xml</code>中声明，
     *                                       并且也没有用{@link com.hyf.servlet.annotation.WebListener}注解注释
     * @since 3.1
     */
    String getVirtualServerName();
}
