package com.hyf.servlet.servletlearn;

import java.text.MessageFormat;

public class TestASCII {
    public static void main(String[] args) {
//        for (int i = -1; i <= 257; i++)
//            System.out.print((i & 0xff00) + " ");
        char c = 257;
        String errMsg = "这不是一个ISO8859-1编码的字符：{0}";
        Object[] errArgs = new Object[1];
        errArgs[0] = c;
        errMsg = MessageFormat.format(errMsg, errArgs);
        System.out.println(errMsg);
    }
}
