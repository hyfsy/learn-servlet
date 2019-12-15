package com.hyf.servlet.http;

import com.hyf.servlet.ServletRequest;

import java.util.Collection;

public interface HttpServletRequest extends ServletRequest {

    String getRequestURI();

    String getContextPath();

    Part getPart(String name);

    Collection<Part> getParts();
}
