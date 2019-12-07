package com.hyf.servlet;

/**
 * 过滤分发类型的枚举
 *
 * @since 3.0
 */
public enum DispatcherType {
    REQUEST,
    FORWARD,
    INCLUDE,
    ASYNC,
    ERROR
}
