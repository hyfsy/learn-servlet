package com.hyf.servlet.descriptor;

import java.util.Collection;

/**
 * 该接口提供了对web应用程序中 <code>&lt;jsp-property-group&gt;</code> 相关配置的访问
 * <p>
 * 配置是从<code>web.xml</code>和<code>web-fragment.web</code>应用程序的描述符文件中聚合
 *
 * @since 3.0
 */
public interface JspPropertyGroupDescriptor {

    /**
     * 获取此<code>JspPropertyGroupDescriptor</code>所表示的JSP属性组的URL模式
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>JspConfigDescriptor</code>
     *
     * @return 一个（可能是空的）JSP属性组的URL模式的集合，由这个<code>JspPropertyGroupDescriptor</code表示>
     */
    Collection<String> getUrlPatterns();

    /**
     * 获取<code>el-ignored配置的值，
     * 该配置指定是否为映射到此<code>JspPropertyGroupDescriptor</code>
     * 表示的JSP属性组的任何JSP页启用表达式语言（el）求值
     *
     * @return 返回<code>el-ignore</code>配置的值，如果未指定则返回null
     */
    String getElIgnored();

    /**
     * 获取<code>page-encoding</code>配置，
     * 该配置指定映射到此<code>JspPropertyGroupDescriptor</code>
     * 所表示的JSP属性组的任何JSP页面的默认页面编码
     *
     * @return 返回<code>page-encoding</code>配置的值，如果未指定则返回null
     */
    String getPageEncoding();

    /**
     * 获取<code>scripting-invalid</code>配置，
     * 该配置指定映射到此<code>JspPropertyGroupDescriptor</code>
     * 所表示的JSP属性组的任何JSP页面启用脚本
     *
     * @return 返回<code>scripting-invalid</code>配置的值，如果未指定则返回null
     */
    String getScriptingInvalid();

    /**
     * 获取<code>is-xml</code>配置，
     * 该配置指定映射到此<code>JspPropertyGroupDescriptor</code>
     * 所表示的JSP属性组的任何JSP页面视为JSP文档(XML语法)
     *
     * @return 返回<code>is-xml</code>配置的值，如果未指定则返回null
     */
    String getIsXml();

    /**
     * 获获取由此<code>jspPropertyGroupDescriptor</code>表示的JSP属性组的<code>include-prelude</code>配置
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>JspConfigDescriptor</code>
     *
     * @return 返回<code>include-prelude<code>的集合的（可能为空）配置
     */
    Collection<String> getIncludePreludes();

    /**
     * 获获取由此<code>jspPropertyGroupDescriptor</code>表示的JSP属性组的<code>include-coda</code>配置
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>JspConfigDescriptor</code>
     *
     * @return 返回<code>include-coda<code>的集合的（可能为空）配置
     */
    Collection<String> getIncludeCodas();

    /**
     * 获取<code>deferred-syntax-allowed-as-literal</code>配置的值，
     * 该值指定通常为表达式语言（EL）表达式保留的字符序列<code>&quot;#{&quot;</code>，
     * 如果在任何映射到此<code>JspPropertyGroupDescriptor所表示的JSP属性组的JSP页面中，
     * 它显示为字符串文字，则将导致翻译错误
     *
     * @return 返回<code>deferred-syntax-allowed-as-literal</code>配置的值，如果未指定则返回null
     */
    String getDeferredSyntaxAllowedAsLiteral();

    /**
     * 获取<code>trim-directive-whitespaces</code>配置，
     * 该配置指定是否必须从映射到此<code>JspPropertyGroupDescriptor</code>
     * 所表示的JSP属性组的任何JSP页面的响应输出中删除只包含空白的模板文本
     *
     * @return 返回<code>trim-directive-whitespaces</code>配置的值，如果未指定则返回null
     */
    String getTrimDirectiveWhitespaces();

    /**
     * 获取<code>default-content-type</code>配置，
     * 该配置指定映射到此<code>JspPropertyGroupDescriptor</code>
     * 所表示的JSP属性组的任何JSP页面的默认响应内容类型
     *
     * @return 返回<code>default-content-type</code>配置的值，如果未指定则返回null
     */
    String getDefaultContentType();

    /**
     * 获取<code>buffer</code>配置，
     * 该配置指定映射到此<code>JspPropertyGroupDescriptor</code>
     * 所表示的JSP属性组的任何JSP页面的响应缓冲区的默认大小
     *
     * @return 返回<code>buffer</code>配置的值，如果未指定则返回null
     */
    String getBuffer();

    /**
     * 获取<code>error-on-undeclared-namespace</code>配置的值，
     * 该值指定如果在映射到此<code>JspPropertyGroupDescriptor
     * 表示的JSP属性组的任何JSP页中使用具有未声明命名空间的标记，则在转换时是否引发错误
     *
     * @return 返回<code>error-on-undeclared-namespace</code>配置的值，如果未指定则返回null
     */
    String getErrorOnUndeclaredNamespace();
}
