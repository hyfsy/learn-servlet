package com.hyf.servlet;

import com.hyf.servlet.annotation.HttpMethodConstraint;
import com.hyf.servlet.annotation.ServletSecurity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * 一个{@link ServletSecurity}注解值的Java类表示
 *
 * @see ServletRegistration.Dynamic#setServletSecurity
 * @since 3.0
 */
public class ServletSecurityElement extends HttpConstraintElement {

    private Collection<String> methodNames;
    private Collection<HttpMethodConstraintElement> methodConstraints;

    /**
     * 使用缺省<code>HttpConstraintElement</code>值作为缺省约束元素，并且没有HTTP方法特定的约束元素，构造一个实例
     */
    public ServletSecurityElement() {
        methodNames = new HashSet<>();
        methodConstraints = Collections.emptySet();
    }

    /**
     * 使用默认约束元素构造实例，但不使用HTTP方法特定的约束元素
     *
     * @param constraintElement HttpConstraintElement应用于所有HTTP方法，除了<tt>methodConstraints</tt>中表示的方法
     */
    public ServletSecurityElement(HttpConstraintElement constraintElement) {
        super(constraintElement.getSemantic(), constraintElement.getGuarantee(), constraintElement.getRolesAllowed());
        this.methodNames = new HashSet<>();
        methodConstraints = Collections.emptySet();
    }

    /**
     * 使用缺省<code>HttpConstraintElement</code>值作为缺省约束元素并使用HTTP方法特定约束元素集合构造一个实例
     *
     * @param methodConstraints HTTP方法特定约束元素的集合
     * @throws IllegalArgumentException 如果检测到重复的方法名
     */
    public ServletSecurityElement(Collection<HttpMethodConstraintElement> methodConstraints) {
        this.methodConstraints = methodConstraints == null ? new HashSet<>() : methodConstraints;
        this.methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * 使用默认约束元素和HTTP方法特定约束元素集合构造实例
     *
     * @param constraintElement HttpConstraintElement应用于所有HTTP方法，除了<tt>methodConstraints</tt>中表示的方法
     * @param methodConstraints HTTP方法特定约束元素的集合
     * @throws IllegalArgumentException 如果检测到重复的方法名
     */
    public ServletSecurityElement(HttpConstraintElement constraintElement, Collection<HttpMethodConstraintElement> methodConstraints) {
        super(constraintElement.getSemantic(), constraintElement.getGuarantee(), constraintElement.getRolesAllowed());
        this.methodConstraints = methodConstraints == null ? new HashSet<>() : methodConstraints;
        this.methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * 从{@link ServletSecurity}注解值构造一个实例
     *
     * @param annotation 注解值
     * @throws IllegalArgumentException 如果检测到重复的方法名
     */
    public ServletSecurityElement(ServletSecurity annotation) {
        super(annotation.value().value(), annotation.value().transportGuarantee(), annotation.value().rolesAllowed());
        this.methodConstraints = new HashSet<>();
        for (HttpMethodConstraint methodConstraint : annotation.httpMethodConstraints()) {
            this.methodConstraints.add(new HttpMethodConstraintElement(methodConstraint.value(),
                    new HttpConstraintElement(methodConstraint.emptyRoleSemantic(), methodConstraint.transportGuarantee(), methodConstraint.roleAllowed())));
        }
        this.methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * 获取HTTP方法特定约束元素的集合(可能为空)
     * <p>
     * 如果允许，对返回的<code>Collection</code>做的任何修改都不能影响这个<code>ServletSecurityElement</code>
     *
     * @return HttpMethodConstraintElement对象的集合(可能是空的)
     */
    public Collection<String> getMethodNames() {
        return Collections.unmodifiableCollection(methodNames);
    }

    /**
     * 获取由HttpMethodConstraints指定的一组HTTP方法名
     * <p>
     * 如果允许，对返回的<code>Collection</code>做的任何修改都不能影响这个<code>ServletSecurityElement</code>
     *
     * @return 方法名字符串的集合
     */
    public Collection<HttpMethodConstraintElement> getMethodConstraints() {
        return Collections.unmodifiableCollection(methodConstraints);
    }

    /**
     * 检查方法约束中的重复方法名
     *
     * @param methodConstraints HTTP方法约束元素
     * @return 方法名集合
     * @throws IllegalArgumentException 如果检测到重复的方法名
     */
    private Collection<String> checkMethodNames(Collection<HttpMethodConstraintElement> methodConstraints) {
        Collection<String> methodNames = new HashSet<>();
        for (HttpMethodConstraintElement methodConstraint : methodConstraints) {
            String methodName = methodConstraint.getMethodName();
            if (!methodNames.add(methodName)) {
                throw new IllegalArgumentException("Duplicate method name" + methodName);
            }
        }
        return methodNames;
    }

}
