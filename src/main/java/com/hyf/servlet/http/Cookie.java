package com.hyf.servlet.http;

public interface Cookie {

    String getName();

    String getDomain();

    void setDomain(String domain);

    String getPath();

    void setPath(String path);

    String getComment();

    void setComment(String common);

    String getVersion();

    boolean isHttpOnly();

    void setHttpOnly(boolean httpOnly);

    boolean getSecure();

    void setSecure(boolean secure);

    int getMaxAge();

    void setMaxAge(int maxAge);
}
