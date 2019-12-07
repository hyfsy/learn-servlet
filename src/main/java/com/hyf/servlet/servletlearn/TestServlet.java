package com.hyf.servlet.servletlearn;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        System.out.println("isSecure：" + req.isSecure());
        System.out.println("scheme：" + req.getScheme());
        System.out.println("protocol：" + req.getProtocol());
        System.out.println("serverName：" + req.getServerName());
        System.out.println("serverPort：" + req.getServerPort());
        System.out.println("remoteAddr：" + req.getRemoteAddr());
        System.out.println("remoteHost：" + req.getRemoteHost());
        System.out.println("remotePort：" + req.getRemotePort());
        System.out.println("remoteUser：" + req.getRemoteUser());
        resp.getWriter().write("{\"rtn\":\"success\"}");
    }

    @Override
    public void destroy() {
        System.out.println("servlet销毁");
    }
}
