package com.hyf.servlet.http;

import com.hyf.servlet.ServletInputStream;
import com.hyf.servlet.ServletOutputStream;

import java.io.IOException;

/**
 * 这个接口封装了升级请求的连接。它允许协议处理程序向容器发送服务请求和状态查询
 *
 * @since 3.1
 */
public interface WebConnection {

    /**
     * 返回此web连接的输入流
     *
     * @return 返回用于读取二进制数据的ServletInputStream对象
     * @throws IOException 如果I/O错误发生
     */
    ServletInputStream getInputStream() throws IOException;

    /**
     * 返回此web连接的输出流
     *
     * @return 用于写入二进制数据的ServletOutputStream对象
     * @throws IOException 如果I/O错误发生
     */
    ServletOutputStream getOutputStream() throws IOException;
}
