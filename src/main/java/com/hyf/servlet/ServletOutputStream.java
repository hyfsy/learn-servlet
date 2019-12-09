package com.hyf.servlet;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * 方法发送二进制数据的输出流到客户端
 * 一个<code>ServletOutputStream</code>对象通常通过{@link ServletResponse#getOutputStream}方法获取
 * 这是servlet容器实现的抽象类
 * 这个类的子类必须实现<code>java.io.OutputStream.write(int)</code>方法
 *
 * @see ServletResponse
 */
public abstract class ServletOutputStream extends OutputStream {

    private static final String LSTRING_FILE = "com.hyf.servlet.LocalStrings";
    private static ResourceBundle LStrings = ResourceBundle.getBundle(LSTRING_FILE);


    /**
     * 不做任何事，因为它是一个抽象类
     */
    protected ServletOutputStream() {
    }

    /**
     * 向客户端写入没有回车换行(CRLF)结尾的<code>String</code>字符
     *
     * @param s 将<code>String</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void print(String s) throws IOException {
        if (s == null) s = "null";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            // 不在 0~255 之间
            if ((c & 0xff00) != 0) { //高阶字节必须为零
                String errMsg = LStrings.getString("err.not_iso8859-1");
                Object[] errArgs = new Object[1];
                errArgs[0] = c;
                errMsg = MessageFormat.format(errMsg, errArgs);
                throw new CharConversionException(errMsg);
            }
            write(c);
        }
    }

    /**
     * 向客户端写入没有回车换行(CRLF)结尾的<code>boolean</code>字符
     *
     * @param b 将<code>boolean</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void print(boolean b) throws IOException {
        String msg;
        if (b) {
            msg = LStrings.getString("value.true");
        } else {
            msg = LStrings.getString("value.false");
        }
        print(msg);
    }

    /**
     * 向客户端写入没有回车换行(CRLF)结尾的<code>character</code>字符
     *
     * @param c 将<code>character</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void print(char c) throws IOException {
        print(String.valueOf(c));
    }

    /**
     * 向客户端写入没有回车换行(CRLF)结尾的<code>int</code>字符
     *
     * @param i 将<code>int</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void print(int i) throws IOException {
        print(String.valueOf(i));

    }

    /**
     * 向客户端写入没有回车换行(CRLF)结尾的<code>long</code>字符
     *
     * @param l 将<code>long</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void print(long l) throws IOException {
        print(String.valueOf(l));
    }

    /**
     * 向客户端写入没有回车换行(CRLF)结尾的<code>float</code>字符
     *
     * @param f 将<code>float</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void print(float f) throws IOException {
        print(String.valueOf(f));
    }

    /**
     * 向客户端写入没有回车换行(CRLF)结尾的<code>double</code>字符
     *
     * @param d 将<code>double</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void print(double d) throws IOException {
        print(String.valueOf(d));
    }

    /**
     * 向客户端写入回车换行(CRLF)字符
     *
     * @throws IOException 如果发生输入或输出异常
     */
    public void println() throws IOException {
        print("\r\n");
    }

    /**
     * 向客户端写入<code>String</code>字符，后跟回车换行(CRLF)结尾
     *
     * @param s 将<code>String</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void println(String s) throws IOException {
        print(s);
        println();
    }

    /**
     * 向客户端写入<code>boolean</code>字符，后跟回车换行(CRLF)结尾
     *
     * @param b 将<code>boolean</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void println(boolean b) throws IOException {
        print(b);
        println();
    }

    /**
     * 向客户端写入<code>character</code>字符，后跟回车换行(CRLF)结尾
     *
     * @param c 将<code>character</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void println(char c) throws IOException {
        print(c);
        println();
    }

    /**
     * 向客户端写入<code>int</code>字符，后跟回车换行(CRLF)结尾
     *
     * @param i 将<code>int</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void println(int i) throws IOException {
        print(i);
        println();
    }


    /**
     * 向客户端写入<code>long</code>字符，后跟回车换行(CRLF)结尾
     *
     * @param l 将<code>long</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void println(long l) throws IOException {
        print(l);
        println();
    }

    /**
     * 向客户端写入<code>float</code>字符，后跟回车换行(CRLF)结尾
     *
     * @param f 将<code>float</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void println(float f) throws IOException {
        print(f);
        println();
    }

    /**
     * 向客户端写入<code>double</code>字符，后跟回车换行(CRLF)结尾
     *
     * @param d 将<code>double</code>发送给客户端
     * @throws IOException 如果发生输入或输出异常
     */
    public void println(double d) throws IOException {
        print(d);
        println();
    }

    /**
     * 此方法可用于确定是否可以写入数据（在不阻塞的情况下）
     *
     * @return 如果写入这个<code>ServletOutputStream</code>成功，则返回<code>true</code>，否则返回<code>false</code>
     * @since 3.1
     */
    public abstract boolean isReady();

    /**
     * 当可以写入时，指示<code>ServletOutputStream</code>调用提供的{@link WriteListener}
     *
     * @param writeListener 当可以写作的时候应该被通知的{@link WriteListener}
     * @throws IllegalStateException 关联的请求既没有升级也没有异步启动
     *                               <code>setWriteListener</code>在同一个请求的范围内被多次调用
     * @throws NullPointerException  如果<code>WriteListener</code>为<code>null</code>
     * @since 3.1
     */
    public abstract void setWriteListener(WriteListener writeListener);


}
