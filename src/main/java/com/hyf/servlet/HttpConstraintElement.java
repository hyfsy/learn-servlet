package com.hyf.servlet;

import com.hyf.servlet.annotation.HttpConstraint;
import com.hyf.servlet.annotation.ServletSecurity;

/**
 * 一个{@link HttpConstraint}注释值的Java类表示
 *
 * @since 3.0
 */
public class HttpConstraintElement {

    private ServletSecurity.EmptyRoleSemantic semantic;
    private ServletSecurity.TransportGuarantee guarantee;
    private String[] rolesAllowed;

    /**
     * 构造默认的HTTP约束元素
     */
    public HttpConstraintElement() {
        this(ServletSecurity.EmptyRoleSemantic.PERMIT);
    }

    /**
     * 方便构造器建立<tt>EmptyRoleSemantic.DENY</tt>
     *
     * @param semantic 应该是EmptyRoleSemantic.DENY
     */
    public HttpConstraintElement(ServletSecurity.EmptyRoleSemantic semantic) {
        this(semantic, ServletSecurity.TransportGuarantee.NONE);
    }

    /**
     * 构造函数建立非空的getRoleSemantic 和/或 <tt>TransportGuarantee.CONFIDENTIAL</tt>
     *
     * @param guarantee    <tt>TransportGuarantee.NONE</tt>或<tt>TransportGuarantee.CONFIDENTIAL</tt>
     * @param rolesAllowed 允许访问的角色的名称
     */
    public HttpConstraintElement(ServletSecurity.TransportGuarantee guarantee, String... rolesAllowed) {
        this(ServletSecurity.EmptyRoleSemantic.PERMIT, guarantee, rolesAllowed);
    }

    /**
     * 构造函数建立非空的getEmptyRoleSemantic,getRoleSemantic,getTransportGuarantee
     *
     * @param semantic     <tt>EmptyRoleSemantic.DENY</tt>或<tt>EmptyRoleSemantic.PERMIT</tt>
     * @param guarantee    <tt>TransportGuarantee.NONE</tt>或<tt>TransportGuarantee.CONFIDENTIAL</tt>
     * @param rolesAllowed 允许访问的角色的名称，或为丢失 如果语义为<tt>EmptyRoleSemantic.DENY</tt>
     */
    public HttpConstraintElement(ServletSecurity.EmptyRoleSemantic semantic, ServletSecurity.TransportGuarantee guarantee, String... rolesAllowed) {
        if (semantic == ServletSecurity.EmptyRoleSemantic.DENY && rolesAllowed.length > 0) {
            throw new IllegalArgumentException("deny semantic with rolesAllowed");
        }
        this.semantic = semantic;
        this.guarantee = guarantee;
        this.rolesAllowed = copyStrings(rolesAllowed);
    }

    /**
     * 获取默认授权语义。
     * 当<code>getRolesAllowed</code>返回一个非空数组时，该值无效
     * 当<tt>getRolesAllowed<tt>指定一个非空数组时，不应指定该值
     *
     * @return 当<code>getRolesAllowed</code>返回一个空(即零长度)数组时，
     * {@link ServletSecurity.EmptyRoleSemantic}将被应用
     */
    public ServletSecurity.EmptyRoleSemantic getSemantic() {
        return semantic;
    }

    /**
     * 取得资料保障规定(即，无论是否需要SSL/TLS)，传输连接必须满足这些条件
     *
     * @return {@link ServletSecurity.TransportGuarantee}表示必须由连接提供的数据保护
     */
    public ServletSecurity.TransportGuarantee getGuarantee() {
        return guarantee;
    }

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
    public String[] getRolesAllowed() {
        return copyStrings(rolesAllowed);
    }

    private String[] copyStrings(String[] strings) {
        String[] arr = null;
        if (strings != null) {
            int len = strings.length;
            arr = new String[len];
            if (len > 0) {
                System.arraycopy(strings, 0, arr, 0, len);
            }
        }
        return arr != null ? arr : new String[0];
    }
}
