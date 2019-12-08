package com.hyf.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * 提供了ServletRequest接口的方便实现，可以被希望将请求调整为Servlet的开发人员子类化
 * 这个类实现了装饰器模式
 * 方法默认为通过调用包装请求对象
 *
 * @see ServletRequest
 */
public class ServletRequestWrapper implements ServletRequest {

    private ServletRequest request;

    /**
     * 创建一个封装给定请求对象的ServletRequest适配器
     *
     * @throws IllegalArgumentException 如果<code>ServletRequest</code>对象为<code>null</code>
     */
    public ServletRequestWrapper(ServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        this.request = request;
    }

    /**
     * 返回一个包装的请求对象
     */
    public ServletRequest getRequest() {
        return this.request;
    }

    /**
     * 设置正在包装的请求对象
     *
     * @throws IllegalArgumentException 如果<code>ServletRequest</code>对象为<code>null</code>
     */
    public void setRequest(ServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        this.request = request;
    }

    /**
     * 以下大部分方法的默认行为是在包装的请求对象上调用对应的方法
     */
    @Override
    public Object getAttribute(String name) {
        return request.getAttribute(name);
    }

    @Override
    public Enumeration<Object> getAttributeNames() {
        return request.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
        request.setCharacterEncoding(env);
    }

    @Override
    public int getContentLength() {
        return request.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        return request.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return request.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return request.getInputStream();
    }

    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return request.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap(String name) {
        return request.getParameterMap(name);
    }

    @Override
    public String getProtocol() {
        return request.getProtocol();
    }

    @Override
    public String getScheme() {
        return request.getScheme();
    }

    @Override
    public String getServerName() {
        return request.getServerName();
    }

    @Override
    public int getServerPort() {
        return request.getServerPort();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return request.getReader();
    }

    @Override
    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return request.getRemoteHost();
    }

    @Override
    public void setAttribute(String name, Object o) {
        request.setAttribute(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        request.removeAttribute(name);
    }

    @Override
    public Locale getLocale() {
        return request.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return request.getLocales();
    }

    @Override
    public boolean isSecure() {
        return request.isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return request.getRequestDispatcher(path);
    }

    /**
     * @deprecated 2.1
     * use {@link ServletContext#getRealPath} instead
     */
    @Override
    public String getRealPath() {
        return request.getRealPath();
    }

    @Override
    public int getRemotePort() {
        return request.getRemotePort();
    }

    @Override
    public String getLocalName() {
        return request.getLocalName();
    }

    @Override
    public String getLocalAddr() {
        return request.getLocalAddr();
    }

    @Override
    public int getLocalPort() {
        return request.getLocalPort();
    }

    /**
     * @since 3.0
     */
    @Override
    public ServletContext getServletContext() {
        return request.getServletContext();
    }

    /**
     * @since 3.0
     */
    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return request.startAsync();
    }

    /**
     * @since 3.0
     */
    @Override
    public AsyncContext startAsync(ServletRequest req, ServletResponse resp) throws IllegalStateException {
        return request.startAsync(req, resp);
    }

    /**
     * @since 3.0
     */
    @Override
    public boolean isAsyncStarted() {
        return request.isAsyncStarted();
    }

    /**
     * @since 3.0
     */
    @Override
    public boolean isAsyncSupported() {
        return request.isAsyncSupported();
    }

    /**
     * @since 3.0
     */
    @Override
    public AsyncContext getAsyncContext() {
        return request.getAsyncContext();
    }

    /**
     * @since 3.0
     */
    @Override
    public DispatcherType getDispatcherType() {
        return request.getDispatcherType();
    }

    /**
     * (递归地)检查这个ServletRequestWrapper是否包装了给定的{@link ServletRequest}实例
     *
     * @param wrapped 封装了要搜索的ServletRequest实例
     * @return 如果这个ServletRequestWrapper包装了给定ServletRequest实例，返回true，否则为false
     * @since 3.0
     */
    public boolean isWrapperFor(ServletRequest wrapped) {
        if (this.request == wrapped) {
            return true;
        } else if (request instanceof ServletRequestWrapper) {
            return ((ServletRequestWrapper) request).isWrapperFor(wrapped);
        } else {
            return false;
        }
    }

    /**
     * (递归地)检查这个ServletRequestWrapper是否包装了一个{@link ServletRequest}指定的类类型
     *
     * @param wrapped 封装了要搜索的ServletRequest实例
     * @return 如果这个ServletRequestWrapper包装了给定ServletRequest实例，返回true，否则为false
     * @throws IllegalArgumentException 如果给的类没有实现<code>ServletRequest</code>
     * @since 3.0
     */
    public boolean isWrapperFor(Class<?> wrapped) {
        if (!ServletRequest.class.isAssignableFrom(wrapped)) {
            throw new IllegalArgumentException("Given class " + wrapped.getName() + "is not subInterface of " + ServletRequest.class.getName());
        }
        if (wrapped.isAssignableFrom(request.getClass())) {
            return true;
        } else if (request instanceof ServletRequestWrapper) {
            return ((ServletRequestWrapper) request).isWrapperFor(wrapped);
        } else {
            return false;
        }
    }
}
