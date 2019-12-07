package com.hyf.servlet;

public interface RequestDispatcher {
    void forward(ServletRequest req, ServletResponse resp);

    void include(ServletRequest req, ServletResponse resp);
}
