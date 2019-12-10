package com.hyf.servlet;

/**
 * @see ServletContext#getServletRegistrations()
 */
public interface ServletRegistration {
    interface Dynamic {
        void setServletSecurity();

        void setRunAsRole();
    }
}
