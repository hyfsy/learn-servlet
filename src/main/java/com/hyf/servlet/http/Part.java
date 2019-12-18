package com.hyf.servlet.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * 这个类表示在一个<code>multipart/form-data</code> POST请求中接收到的部件或表单项
 *
 * @since 3.0
 */
public interface Part {

    /**
     * 以<tt>InputStream</tt>的形式获取此部分的内容
     *
     * @return 以<tt>InputStream</tt>的形式获取此部分的内容
     * @throws IOException 如果用<tt>InputStream</tt>检索文件内容时发生错误
     */
    InputStream getInputStream() throws IOException;

    /**
     * 获取<code>Part</code>对象的内容类型
     *
     * @return <code>Part</code>对象的内容类型
     */
    String getContentType();

    /**
     * 获取<code>Part</code>对象的名称
     *
     * @return <code>Part</code>对象的名称
     */
    String getName();

    /**
     * 获取客户端指定的文件名
     *
     * @return 提交的文件名
     * @since 3.1
     */
    String getSubmittedFileName();

    /**
     * 获取文件的大小
     *
     * @return 一个<code>long</code>指定此部分的大小，以字节为单位
     */
    long getSize();

    /**
     * 一个方便的方法来写这个上传项目到磁盘
     * <p>
     * 此方法不能保证在同一<code>Part</code>对象被多次调用时成功。
     * 这允许特定的实现在可能的情况下使用文件重命名，而不是复制所有底层数据，从而获得显著的性能优势
     *
     * @param fileName 将流写入的文件的名称。文件是根据MultipartConfig中指定的位置创建的
     * @throws IOException 如果发生错误
     */
    void write(String fileName) throws IOException;

    /**
     * 删除文件项的基础存储，包括删除任何关联的临时磁盘文件
     *
     * @throws IOException 如果发生错误
     */
    void delete() throws IOException;

    /**
     * 以<code>String</code>的形式返回指定mime标头的值。
     * 如果<code>Part</code>不包含指定名称的标头，则此方法返回<code>null</code>。
     * 如果有多个具有相同名称的标头，则此方法将返回<code>Part</code>中的第一个标头。
     * 标题名称不区分大小写。您可以将此方法用于任何请求标头
     *
     * @param name 指定一个<code>String</code>指定头的名称
     * @return 一个<code>String</code>，其中包含请求头的值，
     * 或者<code>null</code>，如果<code>Part</code>中没有该名称的 header 的话
     */
    String getHeader(String name);

    /**
     * 获取具有给定名称的<code>Part</code>标头的值
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响此<code>Part</code>
     * <p>
     * 部分标头名称不区分大小写
     *
     * @param name 指定要返回其值的标题名
     * @return 一个(可能是空的)<code>Collection</code>指定名称的标头的值
     */
    Collection<String> getHeaders(String name);

    /**
     * 一些servlet容器不允许servlet使用此方法访问标头，在这种情况下，此方法返回<code>null</code>
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响此<code>Part</code>
     *
     * @return 一个(可能是空的)<code>Collection</code>的<code>Part</code>标头名
     */
    Collection<String> getHeaderNames();
}
