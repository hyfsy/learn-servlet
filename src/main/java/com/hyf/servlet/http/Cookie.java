package com.hyf.servlet.http;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 创建一个cookie，这是servlet发送给Web浏览器的少量信息，由浏览器保存，然后发送回服务器。
 * cookie的值可以唯一地标识客户机，因此cookie通常用于会话管理
 * <p>
 * 一个cookie有一个名字，一个单一的值，和可选的属性，如评论，路径和域限定符，一个最大的年龄，和一个版本号。
 * 一些Web浏览器在处理可选属性方面有缺陷，所以要谨慎使用它们来改进servlet的互操作性。
 * <p>
 * servlet通过使用{@link HttpServletResponse#addCookie}方法向浏览器发送cookie，
 * 该方法将字段添加到HTTP响应报头中，每次向浏览器发送一个cookie。
 * 该浏览器预计将为每个Web服务器支持20个cookie，总共支持300个cookie，并可能限制每个cookie的大小为 4 KB。
 * <p>
 * 浏览器通过向HTTP请求头添加字段向servlet返回cookie。
 * 可以使用{@link HttpServletRequest#getCookies}方法从请求中检索cookie。
 * 多个cookie可能具有相同的名称但不同的路径属性。
 * <p>
 * Cookies影响使用它们的网页的缓存。
 * HTTP 1.0不缓存使用此类创建的cookie的页面。该类不支持用HTTP 1.1定义的缓存控制
 * <p>
 * 这个类同时支持版本0 (Netscape)和版本1 (RFC 2109)的cookie规范。
 * 默认情况下，cookie是使用版本0创建的，以确保最佳的互操作性
 *
 * @see com.hyf.servlet.SessionCookieConfig
 */
public class Cookie implements Cloneable, Serializable {

    private static final String TSPECIALS;
    private static final String LSTRING_FILE = "com.hyf.servlet.http.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);
    private static final long serialVersionUID = 2783247130662169237L;

    static {
        if (Boolean.valueOf(System.getProperty("org.glassfish.web.rfc2109_cookie_names_enforced", "true"))) {
            TSPECIALS = "/()<>@,;:\\\"[]?={} \t";
        } else {
            TSPECIALS = ",; ";
        }
    }

    // cookie本身的值

    private String name; // 保留“$Name”样式
    private String value; // 值的名字

    //在标头的cookie字段中编码的属性

    private String comment; // 描述cookie的使用
    private String domain; // 可以看到cookie的域 以点开头，表示客户端可访问的域名
    private int maxAge = -1; // cookie自动过期的时间
    private String path; // 可以使用cookie的url 以/结尾，表示服务器可访问的路径
    private boolean secure; // 比如使用SSL
    private int version = 0; // version=1 表示RFC 2109++样式
    private boolean isHttpOnly = false;

    /**
     * 构造具有指定名称和值的cookie
     * <p>
     * 名称必须符合RFC 2109。但是，供应商可以提供一个配置选项，允许接受符合原始 Netscape cookie 规范的cookie名称
     * <p>
     * 一旦创建了cookie，就不能更改cookie的名称
     * <p>
     * cookie值可以是任何服务器选择发送。它的值可能只有服务器才感兴趣。
     * 创建后可以使用<code>setValue</code>方法更改cookie的值。
     * <p>
     * 默认情况下，cookie是根据Netscape cookie规范创建的。
     * 可以使用<code>setVersion</code>方法更改版本
     *
     * @param name  命名cookie的名称
     * @param value cookie的值
     * @throws IllegalArgumentException 如果cookie名是null或空的，
     *                                  或者包含任何非法字符(例如逗号、空格或分号)，
     *                                  或者匹配cookie协议保留的令牌
     */
    public Cookie(String name, String value) {
        if (name == null || name.length() <= 0) {
            throw new IllegalArgumentException(lStrings.getString("err.cookie_name_blank"));
        }
        if (!isToken(name) ||
                name.equalsIgnoreCase("Comment") || // rfc2019
                name.equalsIgnoreCase("Discard") || // 2019++
                name.equalsIgnoreCase("Domain") ||
                name.equalsIgnoreCase("Expires") || // (old cookies)
                name.equalsIgnoreCase("Max-Age") || // rfc2019
                name.equalsIgnoreCase("Path") ||
                name.equalsIgnoreCase("Secure") ||
                name.equalsIgnoreCase("Version") ||
                name.startsWith("$")) {
            String errMsg = lStrings.getString("err.cookie_name_is_token");
            Object[] errArgs = new Object[1];
            errArgs[0] = name;
            errMsg = MessageFormat.format(errMsg, errArgs);
            throw new IllegalArgumentException(errMsg);
        }
        this.name = name;
        this.value = value;
    }

    /**
     * 返回描述此cookie用途的注释，或者<code>null</code>(如果cookie没有注释)
     *
     * @return 返回cookie的注释，或者<code>null</code>(如果未指定)
     * @see #setComment(String)
     */
    public String getComment() {
        return comment;
    }

    /**
     * 指定描述cookie用途的注释。如果浏览器将cookie呈现给用户，则注释非常有用。
     * Netscape版本0 cookie不支持注释
     *
     * @param purpose 指定要显示给用户的注释
     * @see #getComment()
     */
    public void setComment(String purpose) {
        this.comment = purpose;
    }

    /**
     * 获取此Cookie的域名
     * <p>
     * 域名按照RFC 2109进行格式化
     *
     * @return 返回此Cookie的域名
     * @see #setDomain(String)
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 指定应该在其中显示此cookie的域
     * <p>
     * 域名的形式由RFC 2109指定。
     * 域名以一个点(<code>.foo.com</code>)开始，
     * 这意味着cookie对于位于指定域名系统(DNS)区域的服务器是可见的(例如，<code>www.foo.com</code>，
     * 而不是<code>a.b.f e.com </code>)
     * <p>
     * 默认情况下，cookie只返回给发送它们的服务器
     *
     * @param domain 该cookie可见的域名;按照 RFC 2109
     * @see #getDomain()
     */
    public void setDomain(String domain) {
        this.domain = domain.toLowerCase(Locale.ENGLISH); // IE 据说需要这个
    }

    /**
     * 获取此Cookie的最大年龄(以秒为单位)
     * <p>
     * 默认情况下，<code>-1</code>会返回，这表示cookie会一直保存到浏览器关闭
     *
     * @return 指定cookie的最大年龄(以秒为单位)的整数;如果是负数，表示cookie将一直存在，直到浏览器关闭
     * @see #setMaxAge(int)
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * 设置此Cookie的最大年龄(以秒为单位)
     * <p>
     * 一个正的值表示cookie将在许多秒之后过期。
     * 注意，该值是cookie过期时的<i>最大值</i>年龄，而不是cookie的当前年龄
     * <p>
     * 一个负值意味着cookie不是持久存储的，当Web浏览器退出时将被删除
     * <p>
     * 零值会导致cookie被删除
     *
     * @param expiry 指定cookie的最大年龄(以秒为单位)的整数;如果是负数，表示cookie没有被存储;如果为0，则删除cookie
     * @see #getMaxAge()
     */
    public void setMaxAge(int expiry) {
        this.maxAge = expiry;
    }

    /**
     * 返回浏览器返回此cookie的服务器上的路径。
     * cookie对于服务器上的所有子路径都是可见的
     *
     * @return 一个<code>字符串</code>，指定包含servlet名称的路径，例如<i>/catalog</i>
     * @see #setPath(String)
     */
    public String getPath() {
        return path;
    }

    /**
     * 为客户端应该返回的cookie指定一个路径
     * <p>
     * cookie对于您指定的目录中的所有页面以及该目录的子目录中的所有页面都是可见的。
     * cookie的路径必须包括设置cookie的servlet，
     * 例如，<i>/catalog</i>，这使得cookie对于服务器上<i>/catalog</i>下的所有目录都可见
     * <p>
     * 咨询RFC 2109(可在互联网上获得)关于设置cookie路径名的更多信息
     *
     * @param uri 指定路径
     * @see #getPath()
     */
    public void setPath(String uri) {
        this.path = uri;
    }

    /**
     * 如果浏览器仅通过安全协议发送cookie，则返回<code>true</code>，
     * 如果浏览器可以使用任何协议发送cookie，则返回<code>false</code>
     *
     * @return 如果浏览器使用安全协议，返回<code>true</code>，否则返回<code>false</code>
     * @see #setSecure(boolean)
     */
    public boolean getSecure() {
        return secure;
    }

    /**
     * 指示浏览器是否只应使用安全协议(如HTTPS或SSL)发送cookie
     * <p>
     * 默认值是<code>false</code>
     *
     * @param secure 如果<code>true</code>，只有在使用安全协议时才从浏览器发送cookie到服务器，
     *               如果<code>false</code>，在任何协议上发送
     * @see #getSecure()
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    /**
     * 返回cookie的名称。名称在创建后不能更改
     *
     * @return 返回cookie的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取此Cookie的当前值
     *
     * @return 返回此Cookie的当前值
     * @see #setValue(String)
     */
    public String getValue() {
        return value;
    }

    /**
     * 为此Cookie分配一个新值
     * <p>
     * 如果使用二进制值，可能需要使用 BASE64 编码
     * <p>
     * 对于 0 版本的cookie，值不应该包含空格、方括号、圆括号、等号、逗号、双引号、斜杠、问号、@符号、冒号和分号
     * <p>
     * 空值在所有浏览器上的行为可能不一样
     *
     * @param newValue cookie的新值
     * @see #getValue()
     */
    public void setValue(String newValue) {
        this.value = newValue;
    }

    /**
     * 返回此cookie遵守的协议的版本。
     * 版本1符合RFC 2109，版本0符合Netscape起草的原始cookie规范。
     * 使用浏览器提供的cookie并识别浏览器的cookie版本
     *
     * @return 如果cookie符合原来的 Netscape 规范则返回<code>0</code>，
     * 如果cookie符合 RFC 2109 规范则返回<code>1</code>
     * @see #setVersion(int)
     */
    public int getVersion() {
        return version;
    }

    /**
     * 设置此cookie遵守的cookie协议的版本
     * <p>
     * 版本0符合原来的 Netscape cookie 规范。版本1符合 RFC 2109 规范
     * <p>
     * 因为RFC 2109还是有点新，所以将版本1视为实验版本；不要在生产站点上使用它
     *
     * @param version 如果cookie符合原来的 Netscape 规范则为<code>0</code>，
     *                如果cookie符合 RFC 2109 规范则为<code>1</code>
     * @see #getVersion()
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * 测试一个字符串，如果该字符串被视为Java语言中的保留令牌，则返回true
     *
     * @param value 要测试的字符串
     * @return 如果字符串是保留令牌，返回<code>true</code>，否则，返回<code>false</code>
     */
    private boolean isToken(String value) {
        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (c < 0x20 || c >= 7f || TSPECIALS.indexOf(c) != -1) {
                return true;
            }
        }
        return true;
    }

    /**
     * 重写标准<code>java.lang.Object.clone</code>方法来返回此Cookie的副本
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 检查此Cookie是否被标记为<i>HttpOnly</i>
     *
     * @return 如果这个Cookie被标记为<i>HttpOnly</i>，返回<code>true</code>，否则为<code>false</code>
     * @since 3.0
     */
    public boolean isHttpOnly() {
        return isHttpOnly;
    }

    /**
     * 将此Cookie标记或取消标记为HttpOnly
     * <p>
     * 如果isHttpOnly设置为true，则通过添加HttpOnly属性将此cookie标记为HttpOnly
     * <p>
     * HttpOnly cookie不应该暴露在客户端脚本代码中，因此可能有助于缓解某些类型的跨站点脚本攻击
     *
     * @param isHttpOnly 如果此cookie被标记为HttpOnly，则isHttpOnly为真，否则为假
     * @since 3.0
     */
    public void setHttpOnly(boolean isHttpOnly) {
        this.isHttpOnly = isHttpOnly;
    }
}
