package com.hyf.servlet.descriptor;

/**
 * 该接口提供了对web应用程序的<code>&lt;taglib&gt;</code> 相关配置的访问
 * <p>
 * 配置是从<code>web.xml</code>和<code>web-fragment.web</code>应用程序的描述符文件中聚合
 *
 * @since 3.0
 */
public interface TaglibDescriptor {

    /**
     * 获取此TaglibDescriptor表示的标记库的唯一标识符
     *
     * @return 返回此TaglibDescriptor表示的标记库的唯一标识符
     */
    String getTaglibURI();

    /**
     * 获取由此TaglibDescriptor表示的标记库的位置
     *
     * @return 返回由此TaglibDescriptor表示的标记库的位置
     */
    String getTaglibLocation();
}
