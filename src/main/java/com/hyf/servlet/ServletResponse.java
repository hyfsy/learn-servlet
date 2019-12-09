package com.hyf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * 定义一个对象来帮助servlet向客户机发送响应。
 * servlet容器创建一个<code>ServletResponse</code> 对象并且将其作为参数传递给servlet的<code>service</code>方法
 * 发送二进制数据在一个MIME主体响应，使用{@link #getOutputStream}返回的{@link ServletOutputStream}
 * 发送字符数据，使用<code>PrintWriter</code>对象，由{@link #getWriter}返回，混合二进制和文本数据
 * 例如，要创建一个多部分响应，使用一个<code>ServletOutputStream</code>和管理字符
 * <p>
 * 可以指定MIME主体响应的字符集，显式使用{@link #setCharacterEncoding}和{@link #setContentType}方法，或隐式方法使用{@link #setLocale}方法
 * 显式规范优先于隐式的规范
 * 如果没有指定字符集，则ISO-8859-1将被指定使用
 * <code>setCharacterEncoding</code>、<code>setContentType</code>、或<code>setLocale</code>
 * 必须在<code>getWriter</code>之前调用，并在提交之前调用使用字符编码的响应
 */
public interface ServletResponse {

    /**
     * 返回字符编码的名称(MIME字符集)用于此响应中发送的正文
     * *字符编码可能已经明确指定使用{@link #setCharacterEncoding}或{@link #setContentType}，或隐式使用{@link #setLocale}方法
     * 显式规范要优先于隐式规范
     * 在调用<code>getWriter</code>方法后调用了这些（设置编码的）方法对字符编码没有任何影响
     * 没有指定编码则默认返回<code>ISO8859-1</code>
     *
     * @return 返回一个<code>String</code>指定的名称字符编码，例如<code>UTF-8</code>
     */
    String getCharacterEncoding();

    /**
     * 如果没有指定<code>Content-Type</code>，则返回 null
     *
     * @return 返回一个<code>String</code>指定内容类型，例如<code>text/html; charset=UTF-8</code>，或 null
     */
    String getContentType();

    /**
     * 返回一个适合编写二进制数据文件的{@link ServletOutputStream}响应，因为servlet容器不能对二进制数据编码
     * 调用 <code>ServletOutputStream</code> 上的 flush() 来提交响应
     * 不能同时调用这个方法和 {@link #getWriter}，除非调用 {@link #reset}
     *
     * @return 一个 {@link ServletOutputStream}用于写入二进制数据
     * @throws IOException           如果发生输入或输出异常
     * @throws IllegalStateException 如果<code>getWriter</code>方法已被调用
     * @see #getWriter
     * @see #reset
     */
    ServletOutputStream getOutputStream() throws IOException;

    /**
     * 返回一个<code>PrintWriter</code>对象,可以发送字符文本到客户端
     * <code>PrintWriter</code>使用由{@link #getCharacterEncoding}返回的编码
     * *如果响应的字符编码没有在<code>getCharacterEncoding</code>中指定(即：该方法只返回默认值<code>ISO8859-1</code>)，
     * <code>getWriter()</code>编码更新为<code>ISO-8859-1</code>
     * 调用 <code>PrintWriter</code> 上的 flush() 来提交响应
     *
     * @return 返回一个可以向客户端返回字符数据的 <code>PrintWriter</code> 对象
     * @throws java.io.UnsupportedEncodingException 如果调用<code>getCharacterEncoding</code>返回的字符编码不能被使用
     * @throws IllegalStateException                如果<code>getOutputStream</code>方法已被调用
     * @throws IOException                          如果发生输入或输出异常
     * @see #setCharacterEncoding
     * @see #getOutputStream
     * @see #reset
     */
    PrintWriter getWriter() throws IOException;

    /**
     * 设置响应的字符编码(MIME字符集)被发送到客户端，例如UTF-8
     * 如果字符编码已经通过{@link #setContentType}或{@link #setLocale}设置，这个方法覆盖它
     * 使用<code>String</code>类型的<code>text/html; charset=UTF-8</code>调用{@link #setContentType}
     * 和这个方法可以反复调用来改变字符
     * <p>
     * 如果在调用了<code>getWriter</code>方法后或该请求已经提交了，再调用此方法，则此方法无效
     * 文本媒体类型的标题，注意字符编码
     * 如果协议提供了这样做的方法，容器必须要有<code>Content-Type</code>和字符编码用于servlet响应客户端
     *
     * @param charset 一个只指定字符集的字符串，由IANA字符集定义(http://www.iana.org/assignments/character-sets)
     * @see #setContentType
     * @see #setLocale
     */
    void setCharacterEncoding(String charset);

    /**
     * 设置响应中内容主体的长度在HTTP servlet中，此方法设置HTTP内容长度报头
     *
     * @param len 返回给客户端的内容的长度，用来设置响应头的<code>Content-Length</code>
     */
    void setContentLength(int len);

    /**
     * 设置响应中内容主体的长度在HTTP servlet中，此方法设置HTTP内容长度报头
     *
     * @param len 返回给客户端的内容的长度，用来设置响应头的<code>Content-Length</code>
     * @since 3.1
     */
    void setContentLengthLong(long len);

    /**
     * 如果尚未提交响应，则设置要发送的响应的内容类型给客户端
     * 给定的内容类型可能包含字符编码规范，例如<code>text/html;charset=UTF-8</code>
     * 如果在调用了<code>getWriter</code>方法后或该请求已经提交了，再调用此方法，则此方法无效
     * 这个方法可以反复调用来改变内容类型和字符编码
     * 如果协议提供了这样做的方法，容器必须要有<code>Content-Type</code>和字符编码用于servlet响应客户端
     * 在HTTP的情况下，使用<code>Content-Type</code>响应头
     *
     * @param type 指定MIME内容的类型
     * @see #setCharacterEncoding
     * @see #setLocale
     * @see #getOutputStream
     * @see #getWriter
     */
    void setContentType(String type);

    /**
     * 设置响应主体的首选缓冲区大小。servlet容器将使用至少与所设置的大小一样
     * 使用{@link #getBufferSize}可以找到实际使用的缓冲区大小
     * 一个更大的缓冲区允许写更多的内容之前，因此为servlet提供了更多的时间设置适当的状态代码和标题
     * 一个较小的缓冲区减少服务器内存加载，客户端接收数据更快
     * <p>
     * 必须在写入任何响应体内容之前调用此方法，如果内容已被写入或响应对象已被提交，此方法抛出一个<code>IllegalStateException</code>
     *
     * @param size 设置优先缓冲区的大小
     * @throws IllegalStateException 如果已经写入内容
     *                               如果写入已被提交
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     * @see #reset
     */
    void setBufferSize(int size);

    /**
     * 返回用于响应的实际缓冲区大小，如果没有缓冲使用，则此方法返回 0
     *
     * @return 实际使用的缓冲区大小
     * @see #setBufferSize
     * @see #flushBuffer
     * @see #isCommitted
     * @see #reset
     */
    int getBufferSize();

    /**
     * 强制将缓冲区中的任何内容写入客户机
     * 调用此方法自动提交响应，即状态代码和标题将被写入
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset
     */
    void flushBuffer() throws IOException;

    /**
     * 清除响应中底层缓冲区的内容，而不清除标题或状态代码
     * 如果响应已提交，此方法抛出一个<code>IllegalStateException</code>
     *
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #isCommitted
     * @see #reset
     */
    void resetBuffer();

    /**
     * 返回一个布尔值，指示是否提交了响应。一个提交的响应已经编写了状态代码和标题
     *
     * @return 一个布尔值，指示是否响应已被提交
     * @see #setBufferSize
     * @see #getBufferSize
     * @see #flushBuffer
     * @see #reset
     */
    boolean isCommitted();

    /**
     * 清除缓冲区中存在的任何数据以及状态码，标题
     * 调用{@link #getWriter}或{@link #getOutputStream}的状态也被清除
     *
     * @throws IllegalStateException 如果该响应已被提交
     */
    void reset();

    /**
     * 设置响应的语言环境及字符编码
     * 只有在下列方法/情况没调用前：
     * <ol>
     * <li>{@link #setContentType}</li>
     * <li>{@link #setCharacterEncoding}</li>
     * <li>{@link #getWriter}</li>
     * <li>请求被提交也不能设置成功</li>
     * </ol>
     * <p>
     * 如果协议提供了方法，容器必须设置语言环境和字符编码用于servlet响应的客户端进行通信
     * 对于HTTP，区域设置是通过<code>Content-Language</code>响应头，字符编码是通过<code>Content-Type</code>响应头
     * 文本媒体类型的标题，注意字符编码
     * 如果servlet没有指定内容类型，则不能通过HTTP头信息进行通信，然而，它仍然通过servlet响应被用来编码文本
     *
     * @param locale 响应的语言环境
     * @see #setCharacterEncoding
     * @see #setContentType
     */
    void setLocale(Locale locale);

    /**
     * 使用{@link #setLocale}方法为此响应指定的区域设置
     * 提交响应后调用了<code>setLocale</code>没有效果
     * 如果没有指定区域设置，返回容器的默认区域设置
     *
     * @return 返回一个响应的语言环境，如果没有指定区域设置，返回容器的默认区域设置
     * @see #setLocale
     */
    Locale getLocale();
}
