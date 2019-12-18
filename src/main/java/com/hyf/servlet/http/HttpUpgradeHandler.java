package com.hyf.servlet.http;

/**
 * 这个接口封装了升级协议处理。HttpUpgradeHandler实现将允许servlet容器与之通信
 *
 * @since 3.1
 */
public interface HttpUpgradeHandler {

    /**
     * 一旦HTTP升级过程完成，并且升级后的连接准备好开始使用新协议，就会调用它
     *
     * @param wc 与此升级请求关联的WebConnection对象
     */
    void init(WebConnection wc);

    /**
     * 在客户端断开连接时调用它
     */
    void destroy();
}
