package com.hyf.servlet;


import java.io.IOException;
import java.util.EventListener;

/**
 * 当HTTP请求数据变得可读无阻塞时，该类将通知实现的回调机制
 *
 * @see ServletInputStream#setReadListener(ReadListener)
 * @since 3.1
 */
public interface ReadListener extends EventListener {

    /**
     * 当<code>ReadListener</code>的一个实例用{@link ServletInputStream}注册时，容器将第一次调用此方法读取数据
     * 随后，容器将仅在{@link ServletInputStream#isReady()}方法被调用并返回<code>false</code>时调用此方法
     *
     * @throws IOException 如果在处理过程中发生了I/O相关错误
     */
    void onDataAvailable() throws IOException;

    /**
     * 读取当前请求的所有数据时调用
     *
     * @throws IOException 如果在处理过程中发生了I/O相关错误
     */
    void onAllDataRead() throws IOException;

    /**
     * 处理请求发生错误时调用
     */
    void onError(Throwable t);
}
