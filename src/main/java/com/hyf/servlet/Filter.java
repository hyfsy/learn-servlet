package com.hyf.servlet;

import java.io.IOException;

/**
 * 过滤器执行过滤在<code>doFilter</code>方法。每个过滤器都可以访问FilterConfig对象，并从中获取它的初始化参数，以及对ServletContext的引用
 * 例如，它可以用来加载过滤任务所需的资源
 * 过滤器是在web的部署描述符中配置的应用程序
 * 例子：
 * <ol>
 * <li>身份验证过滤器</li>
 * <li>日志和审计过滤器</li>
 * <li>图像转换过滤器</li>
 * <li>数据压缩过滤器</li>
 * <li>加密过滤器</li>
 * <li>令牌过滤器</li>
 * <li>触发资源访问事件</li>
 * <li>Mime-type过滤器</li>
 * </ol>
 *
 * @see ServletContext#addFilter(String, String)
 */
public interface Filter {

    /**
     * 由web容器调用，以指示过滤器投入服务
     * servlet容器调用init方法实例化筛选器后精确地执行一次
     * 初始化方法必须成功完成，然后过滤器被要求做任何过滤工作
     *
     * @param config 过滤器配置对象
     * @throws ServletException 如果初始化时，web容器不能将过滤器放入服务中
     */
    void init(FilterConfig config) throws ServletException;

    /**
     * 每当一个请求/响应对通过，调用该过滤器的<code>doFilter</code>方法
     * 客户端在链的末端请求资源，传递给这个方法的过滤器链允许过滤器传递对链中的下一个实体的请求和响应
     * <p>
     * 此方法的典型实现如下：
     * <ol>
     * <li>检查请求</li>
     * <li>选择性地用自定义实现包装请求对象过滤输入的内容或请求头</li>
     * <li>选择性地用自定义实现包装响应对象过滤输出的内容或请求头</li>
     * <ul>
     * <li>调用链中的下一个实体使用FilterChain对象(<code>chain.doFilter()</code>)</li>
     * <li>不通过请求/响应将在过滤器链中的下一个实体阻塞请求处理</li>
     * <li>在过滤器链中的下一个实体设置响应头</li>
     * </ul>
     * </ol>
     */
    void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException;

    /**
     * 由web容器调用，以向过滤器表明它将停止服务
     * 此方法只调用一次当过滤器内的所有线程doFilter方法已退出或超时时间已过
     * 当web容器调用此方法后，它将不会在过滤器的这个实例上再次调用doFilter方法
     * <p>
     * 此方法给过滤器一个机会来清除任何正在保存的资源(例如：内存、文件句柄、线程，确保任何在内存中的持久状态都是同步的
     */
    void destroy();
}
