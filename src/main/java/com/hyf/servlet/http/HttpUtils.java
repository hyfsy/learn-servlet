package com.hyf.servlet.http;

import com.hyf.servlet.ServletInputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @deprecated 2.3 这些方法只在默认编码时有用，并且已经转移到请求接口
 */
public class HttpUtils {

    private static final String LSTRING_FILE = "com.hyf.servlet.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    /**
     * 构造一个空的<code>HttpUtils</code>对象
     */
    public HttpUtils() {
    }

    /**
     * 解析从客户端传递到服务器的查询字符串，并使用键-值对构建<code>HashTable</code>对象。
     * 查询字符串应该是由GET或POST方法打包的字符串形式，也就是说，
     * 它应该具有形式<i>key=value</i>的键-值对，每对之间用&分隔的性格。
     * <p>
     * 一个键可以在查询字符串中多次出现，具有不同的值。
     * 但是，键在hashtable中只出现一次，它的值是包含查询字符串发送的多个值的字符串数组。
     * 散列表中的键和值以解码的形式存储，
     * 因此任何 + 字符都转换为空格，
     * 以十六进制表示法发送的字符(如<i>%xx</i>)转换为ASCII字符
     *
     * @param s 一个包含要解析的查询的字符串
     * @return 一个<code>HashTable</code>对象，由解析的键值对构建
     * @throws IllegalArgumentException 如果查询字符串无效
     */
    public static Hashtable<String, String[]> getQueryString(String s) {
        String[] varArray = null;
        if (s == null) {
            throw new IllegalArgumentException();
        }
        Hashtable<String, String[]> ht = new Hashtable<>();
        StringTokenizer st = new StringTokenizer(s, "&");
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String pair = st.nextToken();
            int pos = pair.indexOf('=');
            if (pos == -1) {
                throw new IllegalArgumentException();
            }
            String key = parseName(s.substring(0, pos), sb);
            String value = parseName(s.substring(pos + 1, pair.length()), sb);

            if (ht.containsKey(key)) {
                String[] oldVals = ht.get(key);
                varArray = new String[oldVals.length + 1];
                System.arraycopy(oldVals, 0, varArray, 0, oldVals.length);
                varArray[oldVals.length] = value;
            } else {
                varArray = new String[1];
                varArray[0] = value;
            }
            ht.put(key, varArray);
        }
        return ht;
    }

    /*
     * 解析查询字符串中的名称
     */
    private static String parseName(String str, StringBuilder sb) {
        sb.setLength(0);

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '+':
                    sb.append(' ');
                    break;
                case '%':
                    try {
                        sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                        i += 2;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(e);

                    } catch (StringIndexOutOfBoundsException e) {
                        String rest = str.substring(i);
                        sb.append(rest);
                        if (rest.length() == 2) {
                            i++;
                        }
                    }
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 解析客户端使用HTTP POST方法和<i>application/x-www-form-urlencoded</i> MIME类型发送给服务器的HTML表单中的数据
     * <p>
     * POST方法发送的数据包含键值对。一个键可以在不同值的POST数据中出现多次。
     * 但是，键在hashtable中只出现一次，它的值是一个字符串数组，包含POST方法发送的多个值
     * <p>
     * 散列表中的键和值以解码的形式存储，
     * 因此任何+字符都转换为空格，
     * 以十六进制表示法发送的字符(如<i>%xx</i>)转换为ASCII字符
     *
     * @param len 一个整数，用字符指定<code>ServletInputStream</code>对象的长度，该对象也传递给这个方法
     * @param is  在<code>ServletInputStream</code>对象中，该对象包含从客户端发送的数据
     * @return 一个<code>HashTable</code>对象，由解析的键值对构建
     * @throws IllegalArgumentException 如果POST方法发送的数据无效
     */
    public static Hashtable<String, String[]> getPostValue(int len, ServletInputStream is) {
        if (len <= 0) {
            return new Hashtable<>();
        }
        if (is == null) {
            throw new IllegalArgumentException();
        }
        byte[] postedBytes = new byte[len];
        int offset = 0;

        try {
            do {
                int inputLen = is.read(postedBytes, offset, len - offset);
                if (inputLen <= 0) {
                    String msg = lStrings.getString("err.short_read");
                    throw new IllegalArgumentException(msg);
                }
                offset += len - inputLen;
            } while (len > offset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 我们不应该假设唯一的POST主体是使用ASCII或ISO Latin/1编码的表单数据…或者应该始终将正文视为表单数据
        try {
            String postedBody = new String(postedBytes, 0, postedBytes.length, "8859_1");
            return getQueryString(postedBody);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * 使用<code>HttpServletRequest</code>对象中的信息，重新构造客户机用于发出请求的URL。
     * 返回的URL包含协议、服务器名、端口号和服务器路径，但不包含查询字符串参数
     * <p>
     * 因为这个方法返回一个<code>StringBuffer</code>，而不是一个字符串，所以您可以很容易地修改URL，例如，添加查询参数
     * <p>
     * 此方法用于创建重定向消息和报告错误
     *
     * @param request <code>HttpServletRequest</code>对象，包含客户端的请求
     * @return 一个包含重建URL的<code>StringBuffer</code>对象
     * @see HttpServletRequest#getRequestURI()
     */
    public static StringBuffer getRequestURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        String scheme = request.getScheme();
        int port = request.getServerPort();

        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getRequestURI());
        return url;
    }
}
