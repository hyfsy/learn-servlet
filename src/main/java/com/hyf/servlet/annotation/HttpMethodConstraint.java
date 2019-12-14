package com.hyf.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 此注释在{@link ServletSecurity}注解中使用,以表示针对特定HTTP协议消息的安全约束
 *
 * @since 3.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethodConstraint {

    /**
     * Http协议方法名
     *
     * @return Http协议方法的名称。<code>value</code>不能为null或空字符串，必须是RFC2616定义的合法HTTP方法名
     */
    String value();

    /**
     * 默认的授权语义。当<code>rolesAllowed</code>返回一个非空数组时，此值无效，
     * 当<tt>rolesAllowed</tt>指定一个非空数组时，不应指定此值
     *
     * @return {@link ServletSecurity.EmptyRoleSemantic}当<code>rolesAllowed</code>返回一个空(即零长度)数组时应用。
     */
    ServletSecurity.EmptyRoleSemantic emptyRoleSemantic() default ServletSecurity.EmptyRoleSemantic.PERMIT;

    /**
     * 保障资料的规定(即，不论是否需要SSL/TLS)，这些服务必须由请求到达的连接来满足
     *
     * @return {@link ServletSecurity.TransportGuarantee}表示必须由连接提供的数据保护
     */
    ServletSecurity.TransportGuarantee transportGuarantee() default ServletSecurity.TransportGuarantee.NONE;

    /**
     * 授权角色的名称
     * <p>
     * 在角色允许中出现的重复角色名是无关紧要的，在注释的运行时处理期间可能被丢弃
     * <tt>"*"</tt>作为角色名没有特殊意义(如果它出现在rolesAllowed)
     *
     * @return 返回一个零或多个角色名的数组。
     * 当数组没有元素，其意义取决于<code>EmptyRoleSemantic</code>，由<code>value</code>方法返回。
     * 如果<code>value</code>返回<tt>EmptyRoleSemantic.DENY</tt>，
     * <code>rolesAllowed</code>返回一个零长度的数组，拒绝独立于身份验证状态和标识的访问，
     * 反之，如果<code>value</code>返回<code>EmptyRoleSemantic.PERMIT</code>，则允许独立于身份验证的访问状态和身份。
     * 当数组包含一个或多个名称时，它表明访问取决于至少一个指定角色的成员资格(独立于<code>EmptyRoleSemantic</code>由<code>value</code>方法返回)
     */
    String[] roleAllowed() default {};
}
