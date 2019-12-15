package com.hyf.servlet;

/**
 * 定义一个servlet遇到困难时可以抛出的通用异常
 */
public class ServletException extends Exception {

    private static final long serialVersionUID = 3936953561068733436L;

    private Throwable rootCause;

    /**
     * 构造一个新的servlet异常
     */
    public ServletException() {
        super();
    }

    /**
     * 用指定的消息构造一个新的servlet异常。可以将消息写入服务器日志 并/或 显示给用户
     *
     * @param message 指定异常消息的文本
     */
    public ServletException(String message) {
        super(message);
    }

    /**
     * 当servlet需要抛出一个异常并包含一条关于干扰其正常操作的 “根本原因” 异常的消息(包括一条描述消息)时，
     * 构造一个新的servlet异常
     *
     * @param message   包含异常消息的文本
     * @param rootCause 干扰servlet正常操作的<code>Throwable</code>异常，使servlet异常成为必要
     */
    public ServletException(String message, Throwable rootCause) {
        super(message, rootCause);
        this.rootCause = rootCause;
    }

    /**
     * 当servlet需要抛出一个异常并包含一条关于干扰其正常操作的 “根本原因” 异常的消息时，
     * 构造一个新的servlet异常。异常的消息基于底层异常的本地化消息
     * <p>
     * 该方法调用<code>getLocalizedMessage</code>方法对<code>Throwable</code> exception获取本地化的异常消息。
     * 当子类化<code>ServletException</code>时，可以重写此方法来创建为特定地区设计的异常消息
     *
     * @param rootCause 干扰servlet正常操作的<code>Throwable</code>异常，使servlet异常成为必要
     */
    public ServletException(Throwable rootCause) {
        this.rootCause = rootCause;
    }

    /**
     * 返回导致servlet异常的异常
     *
     * @return 导致servlet异常的<code>Throwable</code>对象
     */
    public Throwable getRootCause() {
        return rootCause;
    }


}
