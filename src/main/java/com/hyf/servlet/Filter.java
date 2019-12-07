package com.hyf.servlet;

import java.io.IOException;

public interface Filter {

    void init(FilterConfig config) throws ServletException;

    void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException;

    void destroy();
}
