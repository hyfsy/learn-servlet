package com.hyf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * 提供了ServletResponse接口的方便实现，开发人员可以将其子类化，以适应来自Servlet的响应
 * 这个类实现了包装器或装饰器模式，方法默认调用包装的响应对象的方法
 *
 * @see ServletResponse
 */
public class ServletResponseWrapper implements ServletResponse {

    private ServletResponse response;

    /**
     * 创建一个包装给定响应对象的ServletResponse
     *
     * @throws IllegalArgumentException 如果response对象为null
     */
    ServletResponseWrapper(ServletResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Response cannot be null");
        }
        this.response = response;
    }

    /**
     * 返回一个<code>ServletResponse</code>对象
     */
    public ServletResponse getResponse() {
        return response;
    }

    /**
     * 设置正在包装的响应
     *
     * @throws IllegalArgumentException 如果response对象为null
     */
    public void setResponse(ServletResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Response cannot be null");
        }
        this.response = response;
    }

    /**
     * 以下方法的默认行为是调用包装的响应对象上的对应的方法
     */
    @Override
    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        response.setCharacterEncoding(charset);
    }

    @Override
    public String getContentType() {
        return response.getContentType();
    }

    @Override
    public void setContentType(String type) {
        response.setContentType(type);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    @Override
    public void setContentLength(int len) {
        response.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long len) {
        response.setContentLengthLong(len);
    }

    @Override
    public int getBufferSize() {
        return response.getBufferSize();
    }

    @Override
    public void setBufferSize(int size) {
        response.setBufferSize(size);
    }

    @Override
    public void flushBuffer() throws IOException {
        response.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        response.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return response.isCommitted();
    }

    @Override
    public void reset() {
        response.reset();
    }

    @Override
    public Locale getLocale() {
        return response.getLocale();
    }

    @Override
    public void setLocale(Locale locale) {
        response.setLocale(locale);
    }

    /**
     * 检查(递归地)这个ServletResponseWrapper是否包装了给定的{@link ServletResponse}实例
     *
     * @param wrapped 封装了要搜索的ServletResponse实例
     * @return 如果这个ServletResponseWrapper包装了给定ServletResponse实例，返回true，否则为false
     * @since 3.0
     */
    public boolean isWrapperFor(ServletResponse wrapped) {
        if (response == wrapped) {
            return true;
        } else if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrapped);
        } else {
            return false;
        }
    }

    /**
     * @param wrappedType 封装了要搜索的ServletResponse实例
     * @return 如果这个ServletResponseWrapper包装了给定ServletResponse实例，返回true，否则为false
     * @throws IllegalArgumentException 如果给定的类不是<code>ServletResponse</code>实例
     * @since 3.0
     */
    public boolean isWrapperFor(Class<?> wrappedType) {
        if (!ServletResponse.class.isAssignableFrom(wrappedType)) {
            throw new IllegalStateException("Given class " + wrappedType.getName() + "is not s subInterface of " + ServletResponse.class.getName());
        }
        if (wrappedType.isAssignableFrom(response.getClass())) {
            return true;
        } else if (response instanceof ServletResponseWrapper) {
            return ((ServletResponseWrapper) response).isWrapperFor(wrappedType);
        } else {
            return false;
        }
    }

}
