package com.hyf.test;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testservlet")
public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = 7628067123955616710L;

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

        System.out.println(" req localName: " + req.getLocalName());
        System.out.println(" req localAddr: " + req.getLocalAddr());
        System.out.println(" req localPort: " + req.getLocalPort());
        System.out.println(" req isSecure: " + req.isSecure());
        System.out.println("*req scheme: " + req.getScheme());
        System.out.println("*req protocol: " + req.getProtocol());
        System.out.println("*req serverName: " + req.getServerName());
        System.out.println("*req serverPort: " + req.getServerPort());
        System.out.println(" req remoteAddr: " + req.getRemoteAddr());
        System.out.println(" req remoteHost: " + req.getRemoteHost());
        System.out.println(" req remotePort: " + req.getRemotePort());
        System.out.println(" req remoteUser: " + req.getRemoteUser());
        System.out.println("*req characterEncoding: " + req.getCharacterEncoding());
        System.out.println("*resp characterEncoding: " + resp.getCharacterEncoding());
        System.out.println(" resp contentType: " + resp.getContentType());
        System.out.println(" context virtual_name: " + getServletContext().getVirtualServerName());
        System.out.println(" context context_name: " + getServletContext().getServletContextName());
        System.out.println("*context context_path: " + getServletContext().getContextPath());
        System.out.println("*context real_path: " + getServletContext().getRealPath("/asdf"));
        System.out.println(" http_req requested_session_id: " + req.getRequestedSessionId());
        System.out.println(" http_req is_requested_from_cookie: " + req.isRequestedSessionIdFromCookie());
        System.out.println(" http_req is_requested_from_URL: " + req.isRequestedSessionIdFromURL());
        System.out.println("-http_req is_requested_from_url: " + req.isRequestedSessionIdFromUrl());
        System.out.println(" http_req query_string: " + req.getQueryString());
        System.out.println("-http_req context_path: " + req.getContextPath());
        System.out.println(" http_req translated: " + req.getPathTranslated());
        System.out.println("*http_req path_info: " + req.getPathInfo());
        System.out.println("*http_req url: " + req.getRequestURL());
        System.out.println("*http_req uri: " + req.getRequestURI());
        System.out.println("*session id: " + req.getSession().getId());
        System.out.println();
//        设置字符集
        resp.setContentType("text/html;charset=utf-8");
//        设置自动刷新页面???无效
        resp.setHeader("refresh", "3;url=http://www.baidu.com");
//        设置不缓存
        resp.setDateHeader("Expires", 0L);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");
//        resp.getWriter().write("{\"rtn\":\"success\"}");

        doTrace(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("servlet销毁");
    }

}
