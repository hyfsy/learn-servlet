package com.hyf.servlet.http;

import com.hyf.servlet.ServletResponseWrapper;

import java.io.IOException;
import java.util.Collection;

/**
 * 提供了HttpServletResponse接口的一个方便的实现，开发人员可以将其子类化，以适应来自Servlet的响应。
 * 该类实现包装器或装饰器模式。
 * 方法默认调用包装的响应对象
 *
 * @see HttpServletResponse
 */
public class HttpServletResponseWrapper extends ServletResponseWrapper implements HttpServletResponse {

    /**
     * 构造包装给定响应的响应适配器
     *
     * @throws IllegalArgumentException 如果response对象为null
     */
    public HttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用addCookie(Cookie Cookie)
     */
    public HttpServletResponse _getHttpServletResponse() {
        return (HttpServletResponse) super.getResponse();
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用addCookie(Cookie cookie)
     */
    @Override
    public void addCookie(Cookie cookie) {
        this._getHttpServletResponse().addCookie(cookie);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用containsHeader(String name)
     */
    @Override
    public boolean containsHeader(String name) {
        return this._getHttpServletResponse().containsHeader(name);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用encodeURL(String url)
     */
    @Override
    public String encodeURL(String url) {
        return this._getHttpServletResponse().encodeURL(url);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用encodeRedirectURL(String url)
     */
    @Override
    public String encodeRedirectURL(String url) {
        return this._getHttpServletResponse().encodeRedirectURL(url);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用encodeUrl(String url)
     *
     * @deprecated 2.1 用{@link #encodeURL(String)} 代替
     */
    @Override
    public String encodeUrl(String url) {
        return this._getHttpServletResponse().encodeUrl(url);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用encodeRedirectUrl(String url)
     *
     * @deprecated 2.1 用{@link #encodeRedirectURL(String)} 代替
     */
    @Override
    public String encodeRedirectUrl(String url) {
        return this._getHttpServletResponse().encodeRedirectUrl(url);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用sendError(int sc, String msg)
     */
    @Override
    public void sendError(int sc, String msg) throws IOException {
        this._getHttpServletResponse().sendError(sc, msg);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用sendError(int sc)
     */
    @Override
    public void sendError(int sc) throws IOException {
        this._getHttpServletResponse().sendError(sc);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用sendRedirect(String location)
     */
    @Override
    public void sendRedirect(String location) throws IOException {
        this._getHttpServletResponse().sendRedirect(location);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用setDateHeader(String name, long date)
     */
    @Override
    public void setDateHeader(String name, long date) {
        this._getHttpServletResponse().setDateHeader(name, date);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用addDateHeader(String name, long date)
     */
    @Override
    public void addDateHeader(String name, long date) {
        this._getHttpServletResponse().addDateHeader(name, date);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用setHeader(String name, String value)
     */
    @Override
    public void setHeader(String name, String value) {
        this._getHttpServletResponse().setHeader(name, value);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用addHeader(String name, String value)
     */
    @Override
    public void addHeader(String name, String value) {
        this._getHttpServletResponse().addHeader(name, value);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用setIntHeader(String name, int value)
     */
    @Override
    public void setIntHeader(String name, int value) {
        this._getHttpServletResponse().setIntHeader(name, value);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用addIntHeader(String name, int value)
     */
    @Override
    public void addIntHeader(String name, int value) {
        this._getHttpServletResponse().addIntHeader(name, value);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用setStatus(int sc, String sm)
     *
     * @deprecated 2.1 由于消息参数的含义不明确。
     * 设置状态码 使用{@link #setStatus(int)}，
     * 发送带有描述的错误使用{@link #sendError(int, String)}
     */
    @Override
    public void setStatus(int sc, String sm) {
        this._getHttpServletResponse().setStatus(sc, sm);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用getStatus()
     *
     * @return 包装响应的当前状态码
     */
    @Override
    public int getStatus() {
        return this._getHttpServletResponse().getStatus();
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用setStatus(int sc)
     */
    @Override
    public void setStatus(int sc) {
        this._getHttpServletResponse().setStatus(sc);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用getHeader(String name)
     *
     * @param name 要返回其值的响应标头的名称
     * @return 具有给定名称的响应标头的值，或者<tt>null</tt>(如果包装的响应没有设置具有给定名称的标头)
     * @since 3.0
     */
    @Override
    public String getHeader(String name) {
        return this._getHttpServletResponse().getHeader(name);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用getHeaders(String name)
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>HttpServletResponseWrapper</code>
     *
     * @param name 指定要返回其值的响应标头的名称
     * @return 一个(可能是空的)<code>Collection</code>集合，包含给定名称的响应头的值
     * @since 3.0
     */
    @Override
    public Collection<String> getHeaders(String name) {
        return this._getHttpServletResponse().getHeaders(name);
    }

    /**
     * 此方法的默认行为是在包装的响应对象上调用getHeaderNames()
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>HttpServletResponseWrapper</code>
     *
     * @return 一个(可能是空的)<code>Collection</code>集合，包含响应头的所有名称
     * @since 3.0
     */
    @Override
    public Collection<String> getHeaderNames() {
        return this._getHttpServletResponse().getHeaderNames();
    }
}
