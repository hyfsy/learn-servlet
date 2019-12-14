package com.hyf.servlet;

import java.util.Map;
import java.util.Set;

/**
 * 通过该接口可以进一步配置{@link Servlet}或{@link Filter}
 * <p>
 * 如果注册对象的{@link #getClassName}方法返回null，则认为该注册对象<i>preliminary</i>。
 * servlet和过滤器的实现类是特定于容器实现的，可以在不使用任何<tt>servlet-class</tt>或<tt>filter-class</tt>
 * 元素的情况下声明，并将其表示为初始注册对象。
 * 初步注册必须通过调用{@link ServletContext}对象的<tt>addServlet</tt>或<tt>addFilter</tt>方法中其中的一个来完成，
 * 并传入Servlet或过滤器名称(通过{@link #getName}获得) 以及 支持的Servlet或过滤器的类名、类对象或实例。
 * 在大多数情况下，初步注册将由一个适当的，容器{@link ServletContainerInitializer}提供
 *
 * @see FilterRegistration
 * @see ServletRegistration
 */
public interface Registration {

    /**
     * 获取由该<code>Registration</code>表示的Servlet或过滤器的名称
     *
     * @return 由该<code>Registration</code>表示的Servlet或过滤器的名称
     */
    String getName();

    /**
     * 获取由该<code>Registration</code>表示的Servlet或过滤器的完全限定类名
     *
     * @return 返回由该注册表示的Servlet或过滤器的完全限定类名，如果该注册是初步的，则为null
     */
    String getClassName();

    /**
     * 在此注册表示的Servlet或过滤器上使用给定的名称和值设置初始化参数
     *
     * @param name  指定初始化参数的名称
     * @param value 指定初始化参数值
     * @return 如果更新成功，则返回<code>true</code>，即,一个初始化指定名称的参数对于此<code>Registration</code>表示的Servlet
     * 或过滤器来说还不存在，否则为<code>false</code>
     * @throws IllegalStateException    如果ServletContext已经从这个<code>Registration</code>初始化
     * @throws IllegalArgumentException 如果给的<tt>name</tt>或<tt>value</tt>为<code>null</code>
     */
    boolean setInitParameter(String name, String value);

    /**
     * 通过这个<code>Registration</code>对象，获取用于初始化所表示的Servlet或过滤器的具有给定名称的初始化参数的值
     *
     * @param name 请求其值的初始化参数的名称
     * @return 返回初始化参数的值，如果不存在具有给定名称的初始化参数，则为null
     */
    String getInitParameter(String name);

    /**
     * 设置由这个注册表示的Servlet或过滤器上给定的初始化参数
     * <p>
     * 处理给定的初始化参数映射，即，对于在Map中包含的每个初始化参数，此方法调用{@link #setInitParameter(String, String)}方法。
     * 如果任何一次执行返回false，则不会有任何更新。
     * 同样，如果Map包含一个为<tt>null</tt>的<tt>name</tt>或<tt>value</tt>参数，同样不会有任何更新执行，
     * 并抛出一个IllegalArgumentException异常
     *
     * @param initParameters 初始化参数Map
     * @return 返回冲突的初始化参数名集合(可能是空的)
     * @throws IllegalStateException    如果ServletContext已经从这个<code>Registration</code>初始化
     * @throws IllegalArgumentException 如果Map包含一个为<tt>null</tt>的<tt>name</tt>或<tt>value</tt>参数
     */
    Set<String> setInitParameters(Map<String, String> initParameters);

    /**
     * 获取一个不可变(可能为空)的映射，其中包含当前可用的初始化参数（初始化此<code>Registration</code>对象所表示的Servlet或过滤器）
     *
     * @return 返回由该<code>Registration</code>对象表示包含当前可用于初始化Servlet或过滤器的参数的映射
     */
    Map<String, String> getInitParameters();

    /**
     * 调用一个<tt>addServlet</tt>或<tt>addFilter</tt>方法，通过该接口注册{@link Servlet}或{@link Filter}，
     * 分别在{@link ServletContext}上可以进一步配置
     */
    interface Dynamic extends Registration {

        /**
         * 配置此动态<code>Registration</code>支表示的Servlet或过滤器是否支持异步操作
         * <p>
         * 默认情况下，servlet和过滤器不支持异步操作
         * <p>
         * 对此方法的调用将覆盖以前的任何设置
         *
         * @param isAsyncSupported 如果Servlet或过滤器被表示通过这种动态<code>Registration</code>支持异步操作，
         *                         则为<code>true</code>，否则为<code>false</code>
         * @throws IllegalStateException 如果这个动态<code>Registration</code>的ServletContext已经初始化
         */
        void setAsyncSupported(boolean isAsyncSupported);
    }
}
