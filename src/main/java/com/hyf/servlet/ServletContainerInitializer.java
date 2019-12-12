package com.hyf.servlet;

import java.util.Set;

/**
 * 该该接口允许在web应用程序的启动/运行阶段通知库，并执行servlet、过滤器和侦听器的任何必需的程序化注册来响应它。
 * 此接口可以使用{@link com.hyf.servlet.annotation.HandlesTypes}进行注释，
 * 这些应用程序类的实现、扩展将用此注解注释，在{@link #onStartup}方法中接收这些应用程序类
 * <p>
 * 如果此接口的实现没有使用<tt>HandlesTypes</tt>注释，或者没有一个应用程序类与注释指定的类匹配，
 * 则容器必须将<tt>null</tt>类集传递给{@link #onStartup}
 * <p>
 * 在检查应用程序的类以查看它们是否符合<tt>HandlesTypes</tt>对<tt>ServletContainerInitializer</tt>的解析中指定的任何标准时，
 * 如果应用程序的任何可选JAR文件丢失，容器可能会遇到类装入问题。
 * 因为容器不能决定这些类型的类加载失败是否会阻止应用程序正确工作，所以它必须忽略它们，同时提供一个配置选项来记录它们
 * <p>
 * 这个接口的实现必须声明一个JAR文件资源位于<tt>META-INF/services</tt>并通过这个接口的完全限定类名命名，
 * 并将发现使用运行时的服务提供者查找机制或容器特定的机制，在语义上是等价。
 * 在任何一种情况下，<tt>ServletContainerInitializer</tt>都必须忽略web片段JAR文件中被排除在绝对顺序之外的服务，
 * 并且发现这些服务的顺序必须遵循应用程序的类加载委托模型
 *
 * @see com.hyf.servlet.annotation.HandlesTypes
 * @since 3.0
 */
public interface ServletContainerInitializer {
    /**
     * 通知此<tt>ServletContainerInitializer</tt>，指定的<tt>ServletContext</tt>表示应用程序的启动
     * <p>
     * 如果这个<tt>ServletContainerInitializer</tt>被绑定到应用程序的<tt>WEB-INF/lib</tt>目录中的JAR文件中，
     * 那么它的<tt>onStartup</tt>方法将在绑定应用程序启动期间只调用一次。
     * 如果这个<tt>ServletContainerInitializer</tt>被绑定到任何<tt>WEB-INF/lib</tt>目录之外的JAR文件中，
     * 但是仍然可以像上面描述的那样发现它，那么它的<tt>onStartup</tt>方法将在每次启动应用程序时被调用
     *
     * @param c   扩展、实现或使用{@link com.hyf.servlet.annotation.HandlesTypes}注解指定的类类型的一组应用程序类，
     *            或者如果没有匹配，或者这个<tt>ServletContainerInitializer</tt>没有被<tt>HandlesTypes</tt>注释，
     *            则此参数为<tt>null</tt>
     * @param ctx 正在启动的web应用程序的<tt>ServletContext</tt>，其中找到了<tt>c</tt>中包含的类
     * @throws ServletException 如果发生了错误
     */
    void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException;
}
