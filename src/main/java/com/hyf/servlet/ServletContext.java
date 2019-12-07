package com.hyf.servlet;

/**
 * servlet上下文环境对象
 */
public interface ServletContext {

    RequestDispatcher getRequestDispatcher(String path);

    String getRealPath();

}
