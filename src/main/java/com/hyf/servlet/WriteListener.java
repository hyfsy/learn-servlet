package com.hyf.servlet;


import java.io.IOException;
import java.util.EventListener;

/**
 * 当可以写内容且不阻塞时，调用的通知机制，向开发人员发出信号
 *
 * @since 3.1
 */
public interface WriteListener extends EventListener {

    /**
     * 当WriteListener的一个实例通过{@link ServletOutputStream}注册时,该方法将在可能的情况下第一次被容器调用编写数据
     * 随后容器将在{@link ServletOutputStream #isReady()}方法被调用并返回<code>false</code>时调用该方法
     *
     * @throws IOException 在处理过程中发生I/O相关错误时
     */
    void onWritePossible() throws IOException;

    /**
     * 在使用非阻塞API写入数据时发生错误时调用
     */
    void onError(final Throwable t);
}
