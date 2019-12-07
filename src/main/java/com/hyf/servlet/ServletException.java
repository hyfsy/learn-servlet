package com.hyf.servlet;

public class ServletException extends Exception {

    private Throwable rootCause;

    public ServletException() {
        super();
    }

    public ServletException(String message) {
        super(message);
    }

    public ServletException(String message, Throwable rootCause) {
        super(message, rootCause);
        this.rootCause = rootCause;
    }

    public ServletException(Throwable rootCause){
        this.rootCause = rootCause;
    }

    public Throwable getRootCause(){
        return rootCause;
    }


}
