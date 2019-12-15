package com.hyf.servlet;

import java.util.EventListener;

/**
 * 在ServletRequest上启动的异步操作
 * (通过调用{@link ServletRequest#startAsync}或
 * {@link ServletRequest#startAsync(ServletRequest, ServletResponse)})
 * 完成、超时或产生错误时触发的事件
 *
 * @since 3.0
 */
public class AsyncEvent implements EventListener {

    private AsyncContext context;
    private ServletRequest request;
    private ServletResponse response;
    private Throwable throwable;

    /**
     * 从给定的AsyncContext构造一个AsyncEvent
     *
     * @param context 使用AsyncContext来传递这个AsyncEvent
     */
    public AsyncEvent(AsyncContext context) {
        this(context, context.getRequest(), context.getResponse(), null);
    }

    /**
     * 从给定的AsyncContext、ServletRequest和ServletResponse构造一个AsyncEvent
     *
     * @param context  使用AsyncContext来传递这个AsyncEvent
     * @param request  ServletRequest与这个AsyncEvent一起传递
     * @param response ServletResponse与这个AsyncEvent一起传递
     */
    public AsyncEvent(AsyncContext context, ServletRequest request, ServletResponse response) {
        this(context, request, response, null);
    }

    /**
     * 从给定的AsyncContext和Throwable构造一个AsyncEvent
     *
     * @param context   使用AsyncContext来传递这个AsyncEvent
     * @param throwable 使用这个AsyncEvent传递throwable
     */
    public AsyncEvent(AsyncContext context, Throwable throwable) {
        this(context, context.getRequest(), context.getResponse(), throwable);
    }

    /**
     * 从给定的AsyncContext、ServletRequest、ServletResponse和Throwable构造一个AsyncEvent
     *
     * @param context   使用AsyncContext来传递这个AsyncEvent
     * @param request   ServletRequest与这个AsyncEvent一起传递
     * @param response  ServletResponse与这个AsyncEvent一起传递
     * @param throwable 使用这个AsyncEvent传递throwable
     */
    public AsyncEvent(AsyncContext context, ServletRequest request, ServletResponse response, Throwable throwable) {
        this.context = context;
        this.request = request;
        this.response = response;
        this.throwable = throwable;
    }

    /**
     * 从这个AsyncEvent获取AsyncContext
     *
     * @return 用于初始化这个AsyncEvent的AsyncContext
     */
    AsyncContext getAsyncContext() {
        return this.context;
    }

    /**
     * 从这个AsyncEvent获取ServletRequest
     * <p>
     * 如果使用{@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}
     * 添加传递这个AsyncListener的AsyncListener，返回的ServletRequest将与提供给上述方法的ServletRequest相同。
     * 如果AsyncListener是通过{@link AsyncContext#addListener(AsyncListener)}添加的，那么这个方法必须返回null
     *
     * @return 返回用于初始化这个AsyncEvent的ServletRequest，
     * 如果这个AsyncEvent在没有任何ServletRequest的情况下初始化，则返回null
     */
    ServletRequest getSuppliedRequest() {
        return this.request;
    }

    /**
     * 从这个AsyncEvent获取ServletResponse
     * <p>
     * 如果使用{@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}
     * 添加传递这个AsyncListener的AsyncListener，返回的ServletRequest将与提供给上述方法的ServletRequest相同。
     * 如果AsyncListener是通过{@link AsyncContext#addListener(AsyncListener)}添加的，那么这个方法必须返回null
     *
     * @return 返回用于初始化这个AsyncEvent的ServletResponse，
     * 如果这个AsyncEvent在没有任何ServletResponse的情况下初始化，则返回null
     */
    ServletResponse getSuppliedResponse() {
        return this.response;
    }

    /**
     * 从这个AsyncEvent获取Throwable
     *
     * @return 用于初始化这个AsyncEvent的Throwable，如果这个AsyncEvent初始化时没有任何Throwable，则返回null
     */
    Throwable getThrowable() {
        return this.throwable;
    }
}
