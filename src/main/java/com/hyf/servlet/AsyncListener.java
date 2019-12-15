package com.hyf.servlet;

import java.io.IOException;

/**
 * 在添加监听器的ServletRequest上启动的异步操作已经完成、超时或导致错误时，侦听器将得到通知
 *
 * @since 3.0
 */
public interface AsyncListener {

    /**
     * 通知这个AsyncListener一个异步操作已经完成
     * <p>
     * 已经完成的异步操作对应的{@link AsyncContext}可以通过在给定的<tt>event</tt>上
     * 调用{@link AsyncEvent#getAsyncContext}来获得
     * <p>
     * 此外,如果这个AsyncListener通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}注册，
     * 提供的ServletRequest和ServletResponse对象可以通过分别在给定的<tt>event</tt>上调用
     * {@link AsyncEvent#getSuppliedRequest}和{@link AsyncEvent#getSuppliedResponse}获取
     *
     * @param event 指示异步操作已完成的AsyncEvent
     * @throws IOException 如果在处理给定的AsyncEvent期间发生了与I/O相关的错误
     */
    void onComplete(AsyncEvent event) throws IOException;

    /**
     * 通知这个AsyncListener一个异步操作超时
     * <p>
     * 超时的异步操作对应的{@link AsyncContext}可以通过在给定的<tt>event</tt>上
     * 调用{@link AsyncEvent#getAsyncContext}来获得
     * <p>
     * 此外,如果这个AsyncListener通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}注册，
     * 提供的ServletRequest和ServletResponse对象可以通过分别在给定的<tt>event</tt>上调用
     * {@link AsyncEvent#getSuppliedRequest}和{@link AsyncEvent#getSuppliedResponse}获取
     *
     * @param event 指示异步操作超时的AsyncEvent
     * @throws IOException 如果在处理给定的AsyncEvent期间发生了与I/O相关的错误
     */
    void onTimeout(AsyncEvent event) throws IOException;

    /**
     * 通知这个AsyncListener一个异步操作未能完成
     * <p>
     * 未能完成的异步操作对应的{@link AsyncContext}可以通过在给定的<tt>event</tt>上
     * 调用{@link AsyncEvent#getAsyncContext}来获得
     * <p>
     * 此外,如果这个AsyncListener通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}注册，
     * 提供的ServletRequest和ServletResponse对象可以通过分别在给定的<tt>event</tt>上调用
     * {@link AsyncEvent#getSuppliedRequest}和{@link AsyncEvent#getSuppliedResponse}获取
     *
     * @param event 指示异步操作未能完成的AsyncEvent
     * @throws IOException 如果在处理给定的AsyncEvent期间发生了与I/O相关的错误
     */
    void onError(AsyncEvent event) throws IOException;

    /**
     * 通过调用{@link ServletRequest#startAsync}方法之一，通知这个AsyncListener一个新的异步周期正在启动
     * <p>
     * 超时的异步操作对应的{@link AsyncContext}可以通过在给定的<tt>event</tt>上
     * 调用{@link AsyncEvent#getAsyncContext}来获得
     * <p>
     * 此外,如果这个AsyncListener通过调用
     * {@link AsyncContext#addListener(AsyncListener, ServletRequest, ServletResponse)}注册，
     * 提供的ServletRequest和ServletResponse对象可以通过分别在给定的<tt>event</tt>上调用
     * {@link AsyncEvent#getSuppliedRequest}和{@link AsyncEvent#getSuppliedResponse}获取
     * <p>
     * 这个AsyncListener将不会接收任何与新的异步周期相关的事件，
     * 除非它通过<code>AsyncContext</code>自己注册(通过调用{@link AsyncContext#addListener})
     * 作为给定AsyncEvent的一部分传递
     *
     * @param event 指示新异步周期正在启动的AsyncEvent
     * @throws IOException 如果在处理给定的AsyncEvent期间发生了与I/O相关的错误
     */
    void onStartAsync(AsyncEvent event) throws IOException;
}
