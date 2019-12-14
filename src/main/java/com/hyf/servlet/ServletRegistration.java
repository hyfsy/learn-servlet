package com.hyf.servlet;

import java.util.Collection;
import java.util.Set;

/**
 * 通过该接口可以进一步配置{@link Servlet}
 *
 * @see ServletContext#getServletRegistrations()
 * @since 3.0
 */
public interface ServletRegistration extends Registration {

    /**
     * 使用给定的URL模式为servlet添加servlet映射，该servlet由ServletRegistration表示
     * <p>
     * 如果指定的URL模式已经映射到另一个Servlet，则不会执行任何更新
     * <p>
     * 如果这个方法被多次调用，每个连续的调用都会增加前一个方法的效果
     *
     * @param urlPatterns servlet映射的URL模式
     * @return (可能是空的)一组URL模式，这些模式已经被映射到另一个Servlet
     * @throws IllegalArgumentException 如果<tt>urlPatterns</tt>是空的或null
     * @throws IllegalStateException    如果获取这个ServletRegistration的ServletContext已经被初始化
     */
    Set<String> addMapping(String... urlPatterns);

    /**
     * 通过<code>ServletRegistration</code>获取当前可用的Servlet代表的映射
     * <p>
     * 如果允许，对返回的<code>Collection</code>做的任何修改都不影响这个<code>ServletRegistration</code>
     *
     * @return 通过<code>ServletRegistration</code>返回一个(可能是空的)
     * <code>Collection</code>，代表当前可用的Servlet的映射集合
     */
    Collection<String> getMappings();

    /**
     * 获取此<code>ServletRegistration</code>所表示的Servlet的runAs角色的名称
     *
     * @return 返回runAs角色的名称，如果Servlet被配置作为调用者运行，则返回null
     */
    String getRunAsRole();

    /**
     * 可以进一步配置{@link ServletContext}上通过<tt>addServlet</tt>方法之一注册的{@link Servlet}
     */
    interface Dynamic extends ServletRegistration, Registration.Dynamic {

        /**
         * 设置此动态ServletRegistration表示的Servlet上的<code>loadOnStartup</code>的优先级
         * <p>
         * 一个<tt>loadOnStartup</tt>值大于或等于0向容器表明Servlet的初始化优先级。
         * 在本例中,容器必须实例化和在ServletContext初始化阶段初始化Servlet，
         * 也就是说,在它调用了ServletContext配置的所有的ServletContextListener对象的{@link ServletContextListener#contextInitialized}方法之后
         * <p>
         * 如果<tt>loadOnStartup</tt>是一个负整数，容器可以自由地实例化和初始化Servlet
         * <p>
         * <tt>loadOnStartup</tt>的默认值是<code>-1</code>
         * <p>
         * 此方法的调用将覆盖以前的任何设置
         *
         * @param loadOnStartUp Servlet的初始化优先级
         * @throws IllegalStateException 如果获取这个ServletRegistration的ServletContext已经被初始化
         */
        void setLoadOnStartUp(int loadOnStartUp);

        /**
         * 将{@link ServletSecurityElement}应用于为<code>ServletRegistration</code>定义的映射
         * <p>
         * 此方法适用于添加到此<code>ServletRegistration</code>的所有映射，直到初始化从中获取它的<code>ServletContext</code>为止。
         * <p>
         * 如果此ServletRegistration的URL模式是通过便携部署描述符<code>security-constraint</code>建立的确切目标，
         * 则此方法不会改变该模式的<code>security-constraint</code>，并且该模式将包含在返回值中
         * <p>
         * 如果此ServletRegistration的URL模式是通过{@link  com.hyf.servlet.annotation.ServletSecurity}注解建立的安全约束的确切目标，
         * 或在此之前调用了该方法，然后此方法将替换该模式的安全约束
         * <p>
         * 如果这个ServletRegistration的URL模式不是通过{@link com.hyf.servlet.annotation.ServletSecurity}注解建立的安全约束的确切目标，
         * 或之前对该方法的调用，也不是便携部署描述符中<code>security-constraint</code>的确切目标，
         * 然后该方法通过参数<code>ServletSecurityElement</code>为该模式建立安全约束
         *
         * @param constraint {@link ServletSecurityElement}应用于映射到这个ServletRegistration的模式
         * @return 一组URL模式(可能是空的)，它们已经是通过便携部署描述符建立的<code>security-constraint</code>的确切目标。
         * 此方法对返回集中包含的模式没有影响
         * @throws IllegalArgumentException 如果<tt>constraint</tt>为null
         * @throws IllegalStateException    如果获取这个ServletRegistration的ServletContext已经被初始化
         */
        Set<String> setServletSecurity(ServletSecurityElement constraint);

        /**
         * 将{@link MultipartConfigElement}应用于 为<code>ServletRegistration</code>定义的映射。
         * 如果多次调用此方法，则每个后续调用都将覆盖前者的效果
         *
         * @param multipartConfig {@link MultipartConfigElement}将被应用到<tt>Registration</tt>的模式映射
         * @throws IllegalArgumentException 如果<tt>multipartConfig</tt>为null
         * @throws IllegalStateException    如果获取这个ServletRegistration的ServletContext已经被初始化
         */
        void setMultipartConfig(MultipartConfigElement multipartConfig);

        /**
         * 为此设置<code>runAs</code>角色的名称
         *
         * @param roleName <code>runAs</code>角色的名称
         * @throws IllegalArgumentException 如果<tt>roleName</tt>为null
         * @throws IllegalStateException    如果获取这个ServletRegistration的ServletContext已经被初始化
         */
        void setRunAsRole(String roleName);
    }
}
