package com.hyf.servlet.annotation;

import com.hyf.servlet.http.HttpServletRequest;

/**
 * 可以在{@link com.hyf.servlet.Servlet}类中指定的注释。
 * 指示<tt>Servlet</tt>的实例期望符合<tt>multipart/form-data</tt>MIME类型的请求
 * <p>
 * 使用<tt>MultipartConfig</tt>注释的servlet可以检索{@link com.hyf.servlet.http.Part}组件，
 * 通过调用{@link com.hyf.servlet.http.HttpServletRequest#getPart}或{@link HttpServletRequest#getParts}
 * 获取一个给定的<tt>multipart/form-data</tt>请求
 *
 * @see com.hyf.servlet.ServletContext
 * @see com.hyf.servlet.ServletRegistration
 */
public @interface MultipartConfig {

    /**
     * 存储文件的目录位置
     */
    String location() default "";

    /**
     * 允许上传文件的最大大小
     * <p>
     * 默认值<tt>-1L</tt>，表示无限大
     */
    long maxFileSize() default -1L;

    /**
     * <tt>multipart/form-data</tt>请求允许的最大大小
     * <p>
     * 默认值<tt>-1L</tt>，表示无限大
     */
    long maxRequestSize() default -1L;

    /**
     * 将文件写入磁盘的阈值大小
     */
    int fileSizeThreshold() default 0;
}
