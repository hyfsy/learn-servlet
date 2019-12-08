package com.hyf.servlet;

import java.io.IOException;
import java.io.InputStream;

/**
 * 提供用于从客户端读取二进制数据的输入流请求，包括一个有效的<code>readLine</code>方法每次读取一行数据
 * 对于某些协议，比如作为HTTP POST和PUT，一个<code>ServletInputStream</code>对象可用于读取从客户端发送的数据
 * 一个 <code>ServletInputStream</code> 对象通常通过{@link ServletRequest#getInputStream}方法获取
 * 这是一个servlet容器实现的抽象类
 * 这个类的子类必须实现<code>java.io.InputStream.read()</code>方法
 */
public abstract class ServletInputStream extends InputStream {

    /**
     * 什么也不做，因为这是一个抽象类
     */
    protected ServletInputStream() {

    }

    /**
     * 读取输入流，一次一行。从一个偏移量开始，将字节读入一个数组，直到它读取到指定的长度或一个换行符，并将其读入数组
     * <p>
     * 在读取到最大字节数（缓冲区）之前 读取到输入的末端，则该方法返回<code>-1</code>
     *
     * @param b   将数据读入的字节数组
     * @param off 这个方法从该变量指定的位置开始读取
     * @param len 一个整数，指定的最大数值需要读取的字节
     * @return 返回指定实际字节数的整数读取，如果到达流的末端，则为-1
     * @throws IOException 如果发生了输入或输出异常
     */
    public int readLine(byte[] b, int off, int len) throws IOException {
        if (len <= 0) {
            return 0;
        }
        int count = 0, c;
        while ((c = read()) != -1) {
            b[off++] = (byte) c;
            count++;
            if (c == '\n' || count == len) {
                break;
            }
        }
        return count > 0 ? count : -1;
    }

    /**
     * 当流中的所有数据都已被读取时，返回<code>true</code>，否则返回<code>false</code>
     *
     * @return 当此特定请求的所有数据已被读取，返回<code>true</code>，否则返回<code>false</code>
     * @since 3.1
     */
    public abstract boolean isFinished();

    /**
     * 如果可以在不阻塞的情况下读取数据，则返回<code>true</code>，否则返回<code>false</code>
     *
     * @return 如果没有阻塞，返回<code>true</code>，否则返回<code>false</code>
     * @since 3.1
     */
    public abstract boolean isReady();

    /**
     * 当可以读取时指示<code>ServletInputStream</code>调用提供的{@link ReadListener}
     *
     * @param readListener 当可以读取的时候应该被通知的{@link ReadListener}
     * @throws IllegalStateException 关联的请求既没有升级也没有异步启动
     *                               setReadListener在同一个请求的范围内被多次调用
     * @throws NullPointerException  如果 ReadListener 为 null
     * @since 3.1
     */
    public abstract void setReadListener(ReadListener readListener);
}
