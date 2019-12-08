package com.hyf.servlet;

import java.io.IOException;

public interface RequestDispatcher {

    void forward(ServletRequest req, ServletResponse resp) throws ServletException, IOException;

    void include(ServletRequest req, ServletResponse resp) throws ServletException, IOException;
}
