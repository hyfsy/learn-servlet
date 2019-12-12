package com.hyf.servlet;

/**
 * 确保servlet一次只处理一个请求。这个接口没有方法。
 * <p>
 * 如果一个servlet实现了这个接口，是<i>保证</i>方法中没有两个线程将同时执行servlet的<code>service</code>方法
 * <p>
 * servlet容器可以通过同步访问一个单一保证的servlet的实例，或通过维护一个servlet实例池，并将每个新请求发送到一个空闲的servlet
 * <p>
 * 注意，单线程模型并不能解决所有的线程安全问题。例如，会话属性和静态变量仍然可以被多个线程上的多个请求访问，
 * 同时，即使使用了单线程模型的servlet，也建议开发者采取其他方式解决这些问题而不是实现这个接口，
 * 例如避免使用实例变量或同步访问这些资源的代码块
 *
 * @see ServletContext#addServlet(String, String)
 * @deprecated 2.4 没有直接替换方案
 */
public interface SingleThreadModel {
}
