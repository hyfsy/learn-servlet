package com.hyf.servlet;

import java.io.IOException;

/**
 * FilterChain是servlet容器提供给开发人员的对象,提供对资源的过滤请求的调用链的视图
 * 过滤器使用FilterChain调用链中的下一个过滤器
 * 或者调用的过滤器是链中的最后一个过滤器，获取链末端的资源
 *
 * @see Filter
 */
public interface FilterChain {

    /**
     * 调用链中的下一个过滤器，或者调用的过滤器是最后一个过滤器，调用链末端的资源
     *
     * @param req  请求沿链传递
     * @param resp 响应沿链传递
     */
    void doFilter(ServletRequest req, ServletResponse resp) throws IOException, ServletException;
}
