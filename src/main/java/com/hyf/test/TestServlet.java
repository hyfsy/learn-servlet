package com.hyf.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/testservlet")
public class TestServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("开始初始化");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final HttpSession session = req.getSession();
        System.out.println(session);
        System.out.println("requested_session_id: " + req.getRequestedSessionId());
        System.out.println("req is_requested_from_cookie: "+req.isRequestedSessionIdFromCookie());
        System.out.println("req is_requested_from_URL: "+req.isRequestedSessionIdFromURL());
        System.out.println("req is_requested_from_url: "+req.isRequestedSessionIdFromUrl());
        System.out.println("req query_string: " + req.getQueryString());
        System.out.println("req context_path: " + req.getContextPath());
        System.out.println("translated: " + req.getPathTranslated());
        System.out.println("request path_info: " + req.getPathInfo());
        System.out.println("virtual_name: " + getServletContext().getVirtualServerName());
        System.out.println("context_name: " + getServletContext().getServletContextName());
        System.out.println("context_path: " + getServletContext().getContextPath());
        System.out.println("req_context_path: " + req.getContextPath());
        System.out.println("resp_characterEncoding: " + resp.getCharacterEncoding());
        System.out.println("resp_contentType: " + resp.getContentType());
        System.out.println("localName: " + req.getLocalName());
        System.out.println("localAddr: " + req.getLocalAddr());
        System.out.println("localPort: " + req.getLocalPort());
        System.out.println("isSecure: " + req.isSecure());
        System.out.println("scheme: " + req.getScheme());
        System.out.println("protocol: " + req.getProtocol());
        System.out.println("serverName: " + req.getServerName());
        System.out.println("serverPort: " + req.getServerPort());
        System.out.println("remoteAddr: " + req.getRemoteAddr());
        System.out.println("remoteHost: " + req.getRemoteHost());
        System.out.println("remotePort: " + req.getRemotePort());
        System.out.println("remoteUser: " + req.getRemoteUser());
//        设置字符集
        resp.setContentType("text/html;charset=utf-8");
//        设置自动刷新页面???无效
        resp.setHeader("refresh", "3;url=http://www.baidu.com");
//        设置不缓存
        resp.setDateHeader("Expires", 0L);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
        resp.getWriter().write("{\"rtn\":\"success\"}");
    }

    @Override
    public void destroy() {
        System.out.println("servlet销毁");
    }

}