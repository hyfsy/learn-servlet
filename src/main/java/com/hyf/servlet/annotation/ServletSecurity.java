package com.hyf.servlet.annotation;

import java.lang.annotation.*;

/**
 * 此注释用于Servlet实现类，以指定Servlet容器将对HTTP协议消息实施的安全约束。
 * Servlet容器将对映射到Servlet的url模式强制执行这些约束
 *
 * @see com.hyf.servlet.ServletContext
 * @see com.hyf.servlet.ServletRegistration
 * @since 3.0
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServletSecurity {

    /**
     * 获取{@link HttpConstraint}，它定义了对所有HTTP方法的保护，
     * 这些方法在<tt>httpMethodConstraints</tt>返回的数组中没有表示
     *
     * @return 一个<code>HttpConstraint</code>对象
     */
    HttpConstraint value() default @HttpConstraint;

    /**
     * 获取HTTP方法的特定约束。每个{@link HttpMethodConstraint}命名一个HTTP协议方法并定义保护来使用它
     *
     * @return 一个 {@link HttpMethodConstraint}元素数组，每个元素定义要应用于一个HTTP协议方法的保护。
     * 对于任何HTTP方法名，在返回的数组中最多只能有一个对应的元素。
     * 如果返回的数组长度为零，则表示没有定义任何HTTP方法特定的约束
     */
    HttpMethodConstraint[] httpMethodConstraints() default {};

    /**
     * 定义要应用于空rolesAllowed数组的访问语义
     */
    enum EmptyRoleSemantic {
        /**
         * 允许独立于身份验证状态和标识的访问
         */
        PERMIT,
        /**
         * 拒绝独立于身份验证状态和标识的访问
         */
        DENY
    }

    /**
     * 定义传输必须满足的数据保护要求
     */
    enum TransportGuarantee {
        /**
         * 传输时不能对用户数据进行任何保护
         */
        NONE,
        /**
         * 所有用户数据都必须通过传输加密(通常使用SSL/TLS)
         */
        CONFIDENTIAL
    }
}
