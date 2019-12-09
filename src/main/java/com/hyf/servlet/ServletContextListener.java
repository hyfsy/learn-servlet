package com.hyf.servlet;

import java.util.EventListener;

public interface ServletContextListener extends EventListener {
    void contextInitialized();
}
