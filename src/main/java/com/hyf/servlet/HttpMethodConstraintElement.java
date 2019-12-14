package com.hyf.servlet;

import com.hyf.servlet.annotation.HttpMethodConstraint;

/**
 * 一个{@link HttpMethodConstraint}注释值的Java类表示
 *
 * @since 3.0
 */
public class HttpMethodConstraintElement extends HttpConstraintElement {

    private String methodName;

    /**
     * 用默认的{@link HttpConstraintElement}值构造一个实例
     *
     * @param methodName HTTP协议方法的名称。名称不能为null或空字符串，必须是RFC 2616定义的合法HTTP方法名
     */
    public HttpMethodConstraintElement(String methodName) {
        if (methodName == null || methodName.length() <= 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.methodName = methodName;
    }

    /**
     * 用指定的{@link HttpConstraintElement}值构造一个实例
     *
     * @param methodName HTTP协议方法的名称。名称不能为null或空字符串，必须是RFC 2616定义的合法HTTP方法名
     * @param constraint 将HttpConstraintElement值分配给指定的HTTP方法
     */
    public HttpMethodConstraintElement(String methodName, HttpConstraintElement constraint) {
        super(constraint.getSemantic(), constraint.getGuarantee(), constraint.getRolesAllowed());
//        this(methodName);
        if (methodName == null || methodName.length() <= 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.methodName = methodName;
    }

    /**
     * 获取HTTP方法名称
     *
     * @return HTTP方法名称
     */
    public String getMethodName() {
        return methodName;
    }
}
