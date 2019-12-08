package com.hyf.servlet;

/**
 * 过滤分发类型的枚举
 * 具体每个的作用：{@link ServletRequest#getDispatcherType}
 * @since 3.0
 */
public enum DispatcherType {
    REQUEST,
    FORWARD,
    INCLUDE,
    ASYNC,
    ERROR
}
