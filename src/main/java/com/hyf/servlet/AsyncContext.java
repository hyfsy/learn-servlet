package com.hyf.servlet;

public interface AsyncContext {

    void complete();

    boolean hasOriginalRequestAndResponse();

    void dispatch();

    ServletRequest getRequest();
}
