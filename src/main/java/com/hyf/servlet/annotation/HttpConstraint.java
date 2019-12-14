package com.hyf.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 该注解在{@link ServletSecurity}注解中使用，表示将应用于所有HTTP协议方法的安全约束，
 * 而在{@link ServletSecurity}注释中没有对应的{@link HttpMethodConstraint}元素
 * <p>
 * 用于特殊情况，其中<code>@HttpConstraint</code>返回所有默认值出现再结合至少一个{@link HttpMethodConstraint}，
 * 返回所有默认值以外,在<code>@HttpConstraint</code>代表没有安全约束是适用于任何HTTP协议的安全约束否则应用方法。
 * 此异常是为了确保<code>@HttpConstraint</code>的这种潜在的非特定使用不会产生显式地为此类方法建立无保护访问的约束;
 * 假设它们不会被约束覆盖。
 *
 * @since 3.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpConstraint {

    /**
     * 默认的授权语义。
     * 当<code>rolesAllowed</code>返回一个非空数组时，此值则无关紧要，
     * 当<tt>rolesAllowed<tt>指定一个非空数组时，不应指定此值
     *
     * @return {@link ServletSecurity.EmptyRoleSemantic}将在<code>rolesAllowed</code>返回一个空(即零长度)数组时应用
     */
    ServletSecurity.EmptyRoleSemantic value() default ServletSecurity.EmptyRoleSemantic.PERMIT;

    /**
     * 保障数据的规定(即，不论是否需要SSL/TLS)，这些服务必须由请求到达的连接来满足
     *
     * @return {@link ServletSecurity.TransportGuarantee}指示连接必须提供的数据保护
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
    String[] rolesAllowed() default {};
}
