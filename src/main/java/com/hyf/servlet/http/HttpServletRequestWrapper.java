package com.hyf.servlet.http;

import com.hyf.servlet.ServletException;
import com.hyf.servlet.ServletRequestWrapper;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;

/**
 * 提供了HttpServletRequest接口的一个方便实现，开发人员可以将该接口子类化，使请求适应Servlet
 * <p>
 * 这个类实现包装或装饰模式。方法默认调用包装的请求对象
 *
 * @see com.hyf.servlet.http.HttpServletRequest
 */
public class HttpServletRequestWrapper extends ServletRequestWrapper implements HttpServletRequest {

    /**
     * 构造包装给定请求的请求对象
     *
     * @throws IllegalArgumentException 如果<code>ServletRequest</code>对象为<code>null</code>
     */
    public HttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private HttpServletRequest _getHttpServletRequest() {
        return (HttpServletRequest) super.getRequest();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getAuthType()方法
     */
    @Override
    public String getAuthType() {
        return this._getHttpServletRequest().getAuthType();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getCookies()方法
     */
    @Override
    public Cookie[] getCookies() {
        return this._getHttpServletRequest().getCookies();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getDateHeader(String name)方法
     */
    @Override
    public long getDateHeader(String name) {
        return this._getHttpServletRequest().getDateHeader(name);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getHeader(String name)方法
     */
    @Override
    public String getHeader(String name) {
        return this._getHttpServletRequest().getHeader(name);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getHeaders(String name)方法
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
        return this._getHttpServletRequest().getHeaders(name);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getHeaderNames()方法
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return this._getHttpServletRequest().getHeaderNames();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getIntHeader(String name)方法
     */
    @Override
    public int getIntHeader(String name) {
        return this._getHttpServletRequest().getIntHeader(name);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getMethod()方法
     */
    @Override
    public String getMethod() {
        return this._getHttpServletRequest().getMethod();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getPathInfo()方法
     */
    @Override
    public String getPathInfo() {
        return this._getHttpServletRequest().getPathInfo();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getPathTranslated()方法
     */
    @Override
    public String getPathTranslated() {
        return this._getHttpServletRequest().getPathTranslated();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getContextPath()方法
     */
    @Override
    public String getContextPath() {
        return this._getHttpServletRequest().getContextPath();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getQueryString()方法
     */
    @Override
    public String getQueryString() {
        return this._getHttpServletRequest().getQueryString();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getRemoteUser()方法
     */
    @Override
    public String getRemoteUser() {
        return this._getHttpServletRequest().getRemoteUser();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的isUserInRole(String role)方法
     */
    @Override
    public boolean isUserInRole(String role) {
        return this._getHttpServletRequest().isUserInRole(role);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getUserPrincipal()方法
     */
    @Override
    public Principal getUserPrincipal() {
        return this._getHttpServletRequest().getUserPrincipal();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getRequestedSessionId()方法
     */
    @Override
    public String getRequestedSessionId() {
        return this._getHttpServletRequest().getRequestedSessionId();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getRequestURI()方法
     */
    @Override
    public String getRequestURI() {
        return this._getHttpServletRequest().getRequestURI();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getRequestURL()方法
     */
    @Override
    public StringBuffer getRequestURL() {
        return this._getHttpServletRequest().getRequestURL();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getServletPath()方法
     */
    @Override
    public String getServletPath() {
        return this._getHttpServletRequest().getServletPath();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getSession(boolean create)方法
     */
    @Override
    public HttpSession getSession(boolean create) {
        return this._getHttpServletRequest().getSession(create);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的getSession()方法
     */
    @Override
    public HttpSession getSession() {
        return this._getHttpServletRequest().getSession();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的changeSessionId()方法
     */
    @Override
    public String changeSessionId() {
        return this._getHttpServletRequest().changeSessionId();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的isRequestedSessionIdValid()方法
     */
    @Override
    public boolean isRequestedSessionIdValid() {
        return this._getHttpServletRequest().isRequestedSessionIdValid();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的isRequestedSessionIdFromCookie()方法
     */
    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this._getHttpServletRequest().isRequestedSessionIdFromCookie();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的isRequestedSessionIdFromURL()方法
     */
    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this._getHttpServletRequest().isRequestedSessionIdFromURL();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象的isRequestedSessionIdFromUrl()方法
     *
     * @deprecated 2.1 用{@link #isRequestedSessionIdFromURL()} 代替
     */
    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return this._getHttpServletRequest().isRequestedSessionIdFromUrl();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象上的authenticate(HttpServletResponse response)方法
     *
     * @since 3.0
     */
    @Override
    public boolean authenticate(HttpServletResponse response) throws ServletException, IOException {
        return this._getHttpServletRequest().authenticate(response);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象上的login(String name, String password)方法
     *
     * @since 3.0
     */
    @Override
    public void login(String name, String password) throws ServletException {
        this._getHttpServletRequest().login(name, password);
    }

    /**
     * 此方法的默认行为是调用包装的请求对象上的logout()方法
     *
     * @since 3.0
     */
    @Override
    public void logout() throws ServletException {
        this._getHttpServletRequest().logout();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象上的getParts()方法
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>HttpServletRequestWrapper</code>
     *
     * @since 3.0
     */
    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        return this._getHttpServletRequest().getParts();
    }

    /**
     * 此方法的默认行为是调用包装的请求对象上的getPart(String name)方法
     *
     * @since 3.0
     */
    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return this._getHttpServletRequest().getPart(name);
    }

    /**
     * 为给定的类创建一个<code>HttpUpgradeHandler</code>的实例，并将其用于http协议升级处理
     *
     * @since 3.1
     */
    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
        return this._getHttpServletRequest().upgrade(handlerClass);
    }
}
