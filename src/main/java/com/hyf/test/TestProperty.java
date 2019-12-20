package com.hyf.test;

public class TestProperty {
    public static void main(String[] args) {
        final String property = System.getProperty("org.glassfish.web.rfc2109_cookie_names_enforced");
        System.out.println(property);

        for (int i = 0x20; i <= 0x7f; ) {
            i++;
            System.out.print((char) i + " ");
        }
        System.out.println();
        System.out.println("1" + new StringBuilder().toString() + "!");

        System.out.println(31 - 32 > 0);
    }
}
