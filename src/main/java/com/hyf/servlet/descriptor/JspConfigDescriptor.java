package com.hyf.servlet.descriptor;

import com.hyf.servlet.ServletContext;

import java.util.Collection;

/**
 * 该接口提供了对web应用程序的<code>&lt;jsp-config&gt;</code> 相关配置的访问
 * <p>
 * 配置是从<code>web.xml</code>和<code>web-fragment.web</code>应用程序的描述符文件中聚合
 *
 * @see ServletContext#getJspConfigDescriptor()
 * @since 3.0
 */
public interface JspConfigDescriptor {

    /**
     * 获取由此JspConfigDescriptor表示的<code>&lt;jsp-config&gt;</code>元素的
     * 子元素<code>&lt;taglib&gt;</code>
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>JspConfigDescriptor</code>
     *
     * @return 由这个JspConfigDescriptor表示的<code>&lt;jsp-config&gt;</code>元素的
     * <code>&lt;taglib&gt;</code>子元素的（可能为空）集合
     */
    Collection<TaglibDescriptor> getTaglibs();

    /**
     * 获取由此JspConfigDescriptor表示的<code>&lt;jsp-property-group&gt;</code>元素的
     * 子元素<code>&lt;taglib&gt;</code>
     * <p>
     * 对返回的<code>Collection</code>的任何修改都不能影响这个<code>JspConfigDescriptor</code>
     *
     * @return 由这个JspConfigDescriptor表示的<code>&lt;jsp-property-group&gt;</code>元素的
     * <code>&lt;taglib&gt;</code>子元素的（可能为空）集合
     */
    Collection<JspPropertyGroupDescriptor> getJspPropertyGroups();
}
