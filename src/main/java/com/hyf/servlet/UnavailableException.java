package com.hyf.servlet;

/**
 * 定义servlet或过滤器抛出的异常，以指示它是永久或临时不可用的
 * <p>
 * 当servlet或过滤器永久不可用时，它就会出问题，在采取某些操作之前无法处理请求。
 * 例如，servlet可能配置不正确，或者过滤器的状态可能损坏。组件应该记录错误和所需的纠正操作
 * <p>
 * servlet或过滤器暂时是不可用的，如果它暂时不能处理请求由于一些系统范围的问题。
 * 例如，可能无法访问第三层服务器，或者可能没有足够的内存或磁盘存储来处理请求。系统管理员可能需要采取纠正措施
 * <p>
 * Servlet容器可以用相同的方式安全地处理这两种类型的不可用异常。但是，有效地处理临时的不可用性使servlet容器更加健壮。
 * 具体来说，servlet容器可能会在异常建议的一段时间内阻塞对servlet或过滤器的请求，而不是在servlet容器重新启动之前拒绝它们
 */
public class UnavailableException extends ServletException {

    private static final long serialVersionUID = -8708123762838723205L;

    private Servlet servlet;        // 不能使用的servlet
    private boolean permanent;      // 是否需要管理员，即是否 永久性的不可用
    private int seconds;            // 可用性评估

    /**
     * @param servlet 不可用的<code>servlet</code>实例
     * @param msg     指定描述性消息
     * @deprecated 2.2 使用{@link #UnavailableException(String)}代替
     */
    public UnavailableException(Servlet servlet, String msg) {
        super(msg);
        this.servlet = servlet;
        permanent = true;
    }

    /**
     * @param seconds 指定servlet预期不可用的秒数的整数;如果为0或负数，则表示servlet无法进行估计
     * @param msg     一个指定描述性消息的<code>字符串</code>，可以写入日志文件或显示给用户
     * @param servlet 不可用的<code>servlet</code>实例
     * @deprecated 2.2 使用{@link #UnavailableException(String, int)}代替
     */
    public UnavailableException(int seconds, String msg, Servlet servlet) {
        super(msg);
        this.servlet = servlet;
        this.seconds = seconds <= 0 ? -1 : seconds;
        this.permanent = false;
    }

    /**
     * 使用 说明servlet永久不可用的描述性消息 构造新的异常
     *
     * @param msg 指定描述性消息
     */
    public UnavailableException(String msg) {
        super(msg);
        permanent = true;
    }

    /**
     * 用一个描述性消息构造一个新的异常，该消息指示servlet暂时不可用，并给出它将不可用多长时间的估计
     * <p>
     * 在某些情况下，servlet无法进行估计。例如，servlet可能知道它需要的服务器没有运行，但是无法报告需要多长时间才能恢复功能。
     * 这可以用<code>seconds</code>参数>的负值或零值来表示
     *
     * @param message 一个指定描述性消息的<code>字符串</code>，可以写入日志文件或显示给用户
     * @param seconds 指定servlet预期不可用的秒数的整数;如果为0或负数，则表示servlet无法进行估计
     */
    public UnavailableException(String message, int seconds) {
        super(message);
        this.seconds = seconds <= 0 ? -1 : seconds;
        permanent = false;
    }

    /**
     * 返回一个<code>boolean</code>，指示servlet是否永久不可用。
     * 如果是这样，servlet就有问题了，系统管理员必须采取一些纠正措施
     *
     * @return 如果servlet永久不可用，返回<code>true</code>，
     * 如果servlet可用或暂时不可用，则返回<code>false</code>
     */
    public boolean isPermanent() {
        return permanent;
    }

    /**
     * 返回报告其不可用性的servlet
     *
     * @return 抛出<code>UnavailableException</code>的<code>Servlet</code>对象
     * @deprecated 2.2 无替代方法
     */
    public Servlet getServlet() {
        return servlet;
    }

    /**
     * 返回servlet预计暂时不可用的秒数
     * <p>
     * 如果这个方法返回一个负数，servlet将永久不可用，或者无法提供它将不可用多长时间的估计。
     * 自首次报告异常以来，未对经过的时间进行任何更正
     *
     * @return 指定servlet将暂时不可用的秒数的整数，如果servlet永久不可用或无法进行估计，则为负数
     */
    public int getUnavailableSeconds() {
        return permanent ? -1 : seconds;
    }
}
