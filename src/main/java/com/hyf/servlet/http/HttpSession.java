package com.hyf.servlet.http;

/**
 * 在访问tomcat服务器HttpServletRequest的getSession(true)的时候创建
 * tomcat的ManagerBase类提供创建sessionid的方法：随机数+时间+jvmid
 * （jvm的id值会根据服务器的硬件信息计算得来，因此不同jvm的id值都是唯一的）
 * <p>
 * tomcat的StandardManager类将session存储在内存中，也可以持久化到file，数据库，memcache，redis等
 * 客户端只保存sessionid到cookie中
 * <p>
 * session销毁只能通过invalidate或超时，关掉浏览器并不会关闭session
 */
public interface HttpSession {
}
