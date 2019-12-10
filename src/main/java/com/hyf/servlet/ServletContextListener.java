package com.hyf.servlet;

import java.util.EventListener;

/**
 * @see ServletContext#addListener
 */
public interface ServletContextListener extends EventListener {
    void contextInitialized();
}
