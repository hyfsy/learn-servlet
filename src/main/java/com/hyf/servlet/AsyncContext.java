package com.hyf.servlet;

/**
 * 该类表示在ServletRequest上启动的异步操作的执行上下文
 * <p>
 * 通过调用{@link ServletRequest#startAsync()}
 * 或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}来创建和初始化一个AsyncContext。
 * 对这些方法的重复调用将返回相同的AsyncContext实例，并根据需要重新初始化
 * <p>
 * 如果异步操作超时，容器必须执行以下步骤:
 * <ol>
 * <li>在他们的{@link AsyncListener#onTimeout}方法中，
 * 所有的{@link AsyncListener}实例都注册到ServletRequest中，
 * 异步操作就是在这个ServletRequest上发起的</li>
 * <li>如果没有任何监听器调用{@link #complete}或任何的{@link #dispatch}方法，
 * 则执行带着状态码为<tt>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</tt>（500）的错误分发</li>
 * <li>如果没有找到匹配的错误页面，或者错误页面没有调用{@link #complete}或任何{@link #dispatch}方法，
 * 则调用{@link #complete}</li>
 * </ol>
 *
 * @since 3.0
 */
public interface AsyncContext {

    /**
     * 将原始 请求URI 提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext, String)}的目标的请求属性的名称
     */
    String ASYNC_REQUEST_URI = "javax.servlet.async.request_uri";

    /**
     * 将原始 上下文路径 提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext, String)}的目标的请求属性的名称
     */
    String ASYNC_CONTEXT_PATH = "javax.servlet.async.context_path";

    /**
     * 将原始 路径信息 提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext, String)}的目标的请求属性的名称
     */
    String ASYNC_PATH_INFO = "javax.servlet.async.path_info";

    /**
     * 将原始 servlet路径 提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext, String)}的目标的请求属性的名称
     */
    String ASYNC_SERVLET_PATH = "javax.servlet.async.servlet_path";

    /**
     * 将原始 查询字符串 提供给{@link #dispatch(String)}或{@link #dispatch(ServletContext, String)}的目标的请求属性的名称
     */
    String ASYNC_QUERY_STRING = "javax.servlet.async.query_string";

    /**
     * 通过调用{@link ServletRequest#startAsync()}或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}
     * 获取用于初始化此AsyncContext的请求
     *
     * @return 用于初始化AsyncContext的请求
     * @throws IllegalStateException 如果在异步循环中调用了{@link #complete}或任何{@link #dispatch}方法
     */
    ServletRequest getRequest();

    /**
     * 通过调用{@link ServletRequest#startAsync()}或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}
     * 获取用于初始化此AsyncContext的响应
     *
     * @return 用于初始化AsyncContext的响应
     * @throws IllegalStateException 如果在异步循环中调用了{@link #complete}或任何{@link #dispatch}方法
     */
    ServletResponse getResponse();

    /**
     * 检查此AsyncContext是否已用原始或应用程序包装的请求和响应对象初始化
     * <p>
     * 在一个请求被放入异步模式之后，过滤器通过调用<i>出站</i>方向可以使用这些信息，
     * 以确定它们在<i>入站</i>调用期间添加的任何请求 和/或 响应包装器是否需要在异步操作期间保留，或者可以释放
     *
     * @return 如果这AsyncContext的初始化是通过原生的请求和响应对象通过调用 {@link ServletRequest#startAsync()},
     * 或如果它初始化是通过调用{@link ServletRequest#startAsync(ServletRequest, ServletResponse)},
     * 和ServletRequest和ServletResponse参数都没有携带任何应用程序提供的包装器，则返回<code>true</code>，
     * 否则，为<code>false</code>
     */
    boolean hasOriginalRequestAndResponse();

    /**
     * 分派此AsyncContext的请求和响应对象到servlet容器
     * <p>
     * 如果异步循环是用{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}启动，
     * 并且传递的请求是HttpServletRequest的一个实例，
     * 然后分派到由{@link com.hyf.servlet.http.HttpServletRequest#getRequestURI}返回的URI，
     * 否则，分派 容器最后一次分派请求的 的URI
     * <p>
     * 以下的序列说明这将如何工作：
     * <p>
     * <code><pre>
     * 请求分发到 /url/A
     * AsyncContext ac = request.startAsync();
     * ...
     * ac.dispatch(); // 异步分发到 /url/A
     * <p>
     * 请求 /url/A
     * 转发到 /url/B
     * request.getDispatcher("/url/B").forward(request, response);
     * ac = request.startAsync(); // 从转发的目标中启动异步操作
     * ...
     * ac.dispatch(); // 异步转发到 /url/A
     * <p>
     * 请求 /url/A
     * 转发到 /url/B
     * request.getDispatcher("/url/B").forward(request, response);
     * ac = request.startAsync(); // 从转发的目标中启动异步操作
     * ...
     * ac.dispatch(request, response); // 异步转发到 /url/B
     * </pre></code>
     * <p>
     * 此方法在将请求和响应对象传递给容器管理的线程后立即返回，将在该线程上执行分派操作
     * 如果在容器初始化时 通过调用<tt>startAsync</tt>的分派并返回到容器 之前调用此方法，
     * 则分派操作将延迟到 容器发起的分派返回到容器之后
     * <p>
     * 请求的dispatcher类型被设置为<tt>DispatcherType.ASYNC</tt>。
     * 与{@link RequestDispatcher#forward(ServletRequest, ServletResponse)}不同，
     * 响应缓冲区和报头不会被重置，即使响应已经提交，也可以合法地进行调度
     * <p>
     * 对请求和响应的控制被委托给调度目标，当调度目标完成执行时，响应将被关闭，除非
     * 调用{@link ServletRequest#startAsync()}
     * 或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}
     * <p>
     * 执行过程中可能出现的任何错误或异常，容器必须捕获并处理此方法的，如下：
     * <ol>
     * <li>在他们的{@link AsyncListener#onError onError}方法中，
     * 所有的{@link AsyncListener}实例都在创建这个AsyncContext的ServletRequest中注册，
     * 并通过{@link AsyncEvent#getThrowable}使捕获的<tt>Throwable</tt>可用</li>
     * <li>如果没有任何监听器调用{@link #complete}或任何的 {@link #dispatch}方法，
     * 则执行带着状态码为<tt>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</tt>（500）的错误分发</li>
     * <li>如果没有找到匹配的错误页面，或者错误页面没有调用{@link #complete}或任何{@link #dispatch}方法，
     * 则调用{@link #complete}</li>
     * </ol>
     * <p>
     * 每个异步周期最多可以有一个异步调度操作，它是通过调用{@link ServletRequest#startAsync}方法之一启动的。
     * 在同一个异步循环中执行额外的异步分派操作的任何尝试都将导致IllegalStateException。
     * 如果随后对分派请求调用startAsync，则可以调用任何{@link #dispatch}或{@link #complete}方法
     *
     * @throws IllegalStateException 如果调用了一个分派方法，并且在结果分派期间没有调用startAsync方法，
     *                               或者调用了{@link #complete}
     * @see ServletRequest#getDispatcherType
     */
    void dispatch();

    /**
     * 将此AsyncContext的请求和响应对象分派到给定的<tt>path</tt>
     * <p>
     * 在初始化异步上下文的{@link ServletContext}范围内，
     * <tt>path</tt>参数的解释方式与{@link ServletRequest#getRequestDispatcher(String)}中的相同
     * <p>
     * 请求的所有路径相关的查询方法必须反射分派目标，
     * 而原始的请求URI、上下文路径、路径信息、servlet路径和查询字符串可以从请求的
     * {@link #ASYNC_REQUEST_URI},{@link #ASYNC_CONTEXT_PATH},{@link #ASYNC_PATH_INFO},
     * {@link #ASYNC_SERVLET_PATH},{@link #ASYNC_QUERY_STRING}属性中获取。
     * 这些属性总是反射原始的path元素，即使在重复的分派。
     * <p>
     * 每个异步周期最多可以有一个异步调度操作，它是通过调用{@link ServletRequest#startAsync}方法之一启动的。
     * 在同一个异步循环中执行额外的异步分派操作的任何尝试都将导致IllegalStateException。
     * 如果随后对分派请求调用startAsync，则可以调用任何{@link #dispatch}或{@link #complete}方法
     * <p>
     * 参见{@link #dispatch()}了解更多细节，包括错误处理
     *
     * @param path 分派目标的路径，作用域为初始化AsyncContext的ServletContext
     * @throws IllegalStateException 如果调用了一个分派方法，并且在结果分派期间没有调用startAsync方法，
     *                               或者调用了{@link #complete}
     * @see javax.servlet.ServletRequest#getDispatcherType
     */
    void dispatch(String path);

    /**
     * 将这个AsyncContext的请求和响应对象分派到给定的<tt>context</tt>作用域的给定的<tt>path</tt>
     *
     * <tt>path</tt>参数的解释方法与{@link ServletRequest#getRequestDispatcher(String)}相同，
     * 不同之处在于它的作用域是给定的<tt>context</tt>
     * <p>
     * 请求的所有路径相关的查询方法必须反射分派目标，
     * 而原始的请求URI、上下文路径、路径信息、servlet路径和查询字符串可以从请求的
     * {@link #ASYNC_REQUEST_URI},{@link #ASYNC_CONTEXT_PATH},{@link #ASYNC_PATH_INFO},
     * {@link #ASYNC_SERVLET_PATH},{@link #ASYNC_QUERY_STRING}属性中获取。
     * 这些属性总是反射原始的path元素，即使在重复的分派。
     * <p>
     * 每个异步周期最多可以有一个异步调度操作，它是通过调用{@link ServletRequest#startAsync}方法之一启动的。
     * 在同一个异步循环中执行额外的异步分派操作的任何尝试都将导致IllegalStateException。
     * 如果随后对分派请求调用startAsync，则可以调用任何{@link #dispatch}或{@link #complete}方法
     * <p>
     * 参见{@link #dispatch()}了解更多细节，包括错误处理
     *
     * @param context 调度目标的ServletContext
     * @param path    分派目标的路径，作用域为给定的ServletContext
     * @throws IllegalStateException 如果调用了一个分派方法，并且在结果分派期间没有调用startAsync方法，
     *                               或者调用了{@link #complete}
     * @see javax.servlet.ServletRequest#getDispatcherType
     */
    void dispatch(ServletContext context, String path);

    /**
     * 完成通过request初始化的AsyncContext的异步操作，
     * 关闭通过response初始化的AsyncContext的异步操作
     * <p>
     * 任何类型为{@link AsyncListener}的监听器都将被调用，
     * 通过{@link AsyncListener#onComplete(AsyncEvent) onComplete}方法
     * <p>
     * 在调用{@link ServletRequest#startAsync()}
     * 或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}之后，
     * 以及在调用该类的一个<tt>dispatch</tt>方法之前，调用此方法都是合法的。
     * 如果 container-initiated 调度前调用此方法,调用<tt>startAsync</tt>返回到容器中，
     * 然后调用不会生效(和任何调用{@link AsyncListener#onComplete(AsyncEvent)}将推迟)
     * 直到 container-initiated 调度后返回到容器中
     */
    void complete();

    /**
     * 导致容器分派一个线程(可能来自托管线程池)来运行指定的<tt>Runnable</tt>。
     * 容器可以将适当的上下文信息传播到<tt>Runnable</tt>
     *
     * @param run 运行异步处理程序
     */
    void start(Runnable run);

    /**
     * 将给定的{@link AsyncListener}注册到最近的异步循环中，
     * 该异步循环是通过调用{@link ServletRequest#startAsync}方法之一启动的
     * <p>
     * 当异步周期成功完成，超时，导致错误，
     * 或者通过{@link ServletRequest#startAsync}方法之一启动一个新的异步周期时，
     * 给定的AsyncListener将收到{@link AsyncEvent}
     * <p>
     * AsyncListener实例将按照它们被添加的顺序被通知
     * <p>
     * 如果{@link ServletRequest#startAsync()}
     * 或{@link ServletRequest#startAsync(ServletRequest, ServletResponse)}被调用，
     * 当{@link AsyncEvent}通知{@link AsyncListener}时，可以从{@link AsyncEvent}获得完全相同的请求和响应对象
     *
     * @param listener 要注册的AsyncListener
     * @throws IllegalStateException 如果在容器发起的分派之后
     *                               (在此期间调用了{@link ServletRequest#startAsync}方法之一，并返回到容器)，
     *                               调用此方法
     */
    void addListener(AsyncListener listener);

    /**
     * 将给定的{@link AsyncListener}注册到最近的异步循环中，
     * 该异步循环是通过调用{@link ServletRequest#startAsync}方法之一启动的
     * <p>
     * 当异步周期成功完成，超时，导致错误，
     * 或者通过{@link ServletRequest#startAsync}方法之一启动一个新的异步周期时，
     * 给定的AsyncListener将收到{@link AsyncEvent}
     * <p>
     * AsyncListener实例将按照它们被添加的顺序被通知
     * <p>
     * 给定的ServletRequest和ServletResponse对象将通过交付给它的{@link AsyncEvent}的
     * {@link AsyncEvent#getSuppliedRequest}和{@link AsyncEvent#getSuppliedResponse}方法
     * 对让给定的AsyncListener可以使用。
     * 这些对象在传递给AsyncEvent时，不应该分别从异步事件中读取或写入异步事件，
     * 因为在注册了给定的AsyncListener之后，可能发生了额外的包装，但是可以使用它来释放与之相关的任何资源
     *
     * @param listener 要注册的AsyncListener
     * @param request  将包含在AsyncEvent中的ServletRequest
     * @param response 将包含在AsyncEvent中的ServletResponse
     * @throws IllegalStateException 如果这个方法是在容器发起的分派之后调用的，
     *                               在此期间调用了{@link ServletRequest#startAsync}方法中的一个，
     *                               该方法已经返回到容器
     */
    void addListener(AsyncListener listener, ServletRequest request, ServletResponse response);

    /**
     * 实例化给定的{@link AsyncListener}类
     * <p>
     * 返回的AsyncListener实例在通过调用一个<code>addListener</code>方法注册到这个AsyncContext之前可以进一步定制
     * <p>
     * 给定的AsyncListener类必须定义一个零参数构造函数，用于实例化它
     * <p>
     * 如果给定的<tt>clazz</tt>表示一个托管Bean，则此方法支持资源注入
     * <p>
     * 此方法支持任何适用于AsyncListener的注解
     *
     * @param clazz 实例化AsyncListener类
     * @return 新的AsyncListener实例
     * @throws ServletException 如果给定的<tt>clazz</tt>未能实例化
     */
    <T extends AsyncListener> T createListener(Class<?> clazz) throws ServletException;

    /**
     * 获取该异步上下文的超时(毫秒)
     * <p>
     * 此方法返回容器异步操作的默认超时时间，或为最近一次传递给{@link #setTimeout}的超时值
     * <p>
     * 0或更少的超时值表示没有超时
     *
     * @return 超时时间(以毫秒为单位)
     */
    long getTimeout();

    /**
     * 设置此AsyncContext的超时(以毫秒为单位)
     * <p>
     * 一旦容器发起的分派调用了{@link ServletRequest#startAsync}方法之一并返回到容器中，
     * 超时将应用于这个AsyncContext
     * <p>
     * 如果{@link #complete}方法和任何分派方法都没有被调用，超时将过期。超时值为0或更少表示没有超时
     * <p>
     * 如果{@link #setTimeout}没有被调用，那么容器的默认超时时间(通过调用{@link #getTimeout}获取)将被应用
     * <p>
     * 默认值 <code>30000</code> ms
     *
     * @param timeout 超时时间(以毫秒为单位)
     * @throws IllegalStateException 如果这个方法是在容器发起的分派之后调用的，
     *                               在此期间调用了{@link ServletRequest#startAsync}方法中的一个，
     *                               该方法已经返回到容器
     */
    void setTimeout(long timeout);
}
