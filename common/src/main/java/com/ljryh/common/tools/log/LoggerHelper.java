package com.ljryh.common.tools.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHelper {

    private Logger log = null;

    public LoggerHelper(Class currentClass) {
        this.log = LoggerFactory.getLogger(currentClass);
    }

    public void info(String data) {
        this.log.info(data + "\r\n");
    }

    public void info(String message, Throwable t) {
        this.log.info(message, t);
    }

    public void info(String application, String clazzName, String message) {
        this.log.info(message(application, clazzName, message) + "\r\n");
    }

    public void info(String application, String clazzName, Integer code, String message) {
        this.log.info(message(application, clazzName, code, message) + "\r\n");
    }

    public void info(String application, String clazzName, Integer code, String message, String exception) {
        this.log.info(message(application, clazzName, code, message, exception) + "\r\n");
    }

    public void info(String application, String clazzName, String mark, String otherMark, Integer code, String message) {
        this.log.info(message(application, clazzName, mark, otherMark, code, message) + "\r\n");
    }

    public void info(String application, String clazzName, String mark, String otherMark, Integer code, String message, String exception) {
        this.log.info(message(application, clazzName, mark, otherMark, code, message, exception) + "\r\n");
    }

    public void debug(String data) {
        this.log.debug(data + "\r\n");
    }

    public void debug(String message, Throwable t) {
        this.log.debug(message, t);
    }

    public void debug(String application, String clazzName, String message) {
        this.log.debug(message(application, clazzName, message) + "\r\n");
    }

    public void debug(String application, String clazzName, Integer code, String message) {
        this.log.debug(message(application, clazzName, code, message) + "\r\n");
    }

    public void debug(String application, String clazzName, Integer code, String message, String exception) {
        this.log.debug(message(application, clazzName, code, message, exception) + "\r\n");
    }

    public void debug(String application, String clazzName, String mark, String otherMark, Integer code, String message) {
        this.log.debug(message(application, clazzName, mark, otherMark, code, message) + "\r\n");
    }

    public void debug(String application, String clazzName, String mark, String otherMark, Integer code, String message, String exception) {
        this.log.debug(message(application, clazzName, mark, otherMark, code, message, exception) + "\r\n");
    }

    public void error(String data) {
        this.log.error(data + "\r\n");
    }

    public void error(String message, Throwable t) {
        this.log.error(message, t);
    }

    public void error(String application, String clazzName, String message) {
        this.log.error(message(application, clazzName, message) + "\r\n");
    }

    public void error(String application, String clazzName, Integer code, String message) {
        this.log.error(message(application, clazzName, code, message) + "\r\n");
    }

    public void error(String application, String clazzName, Integer code, String message, String exception) {
        this.log.error(message(application, clazzName, code, message, exception) + "\r\n");
    }

    public void error(String application, String clazzName, Integer code, String message, Exception ex) {
        this.log.error(message(application, clazzName, code, message) + "\r\n", ex);
    }

    public void error(String application, String clazzName, String mark, String otherMark, Integer code, String message) {
        this.log.error(message(application, clazzName, mark, otherMark, code, message) + "\r\n");
    }

    public void error(String application, String clazzName, String mark, String otherMark, Integer code, String message, String exception) {
        this.log.error(message(application, clazzName, mark, otherMark, code, message, exception) + "\r\n");
    }

    public void error(String application, String clazzName, String mark, String otherMark, Integer code, String message, Exception ex) {
        this.log.error(message(application, clazzName, mark, otherMark, code, message) + "\r\n", ex);
    }

    private String message(String application, String clazzName, String message) {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(application).append("]").append(" ");
        sb.append("[").append(clazzName).append("]").append(" ");
        sb.append("[").append(1000).append("]").append(" ");
        sb.append("[").append(9999).append("]").append(" ");
        sb.append("##DEMO##");
        sb.append(message);
        return sb.toString();
    }

    private String message(String application, String clazzName, Integer code, String message) {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(application).append("]").append(" ");
        sb.append("[").append(clazzName).append("]").append(" ");
        sb.append("[").append(1000).append("]").append(" ");
        sb.append("[").append(9999).append("]").append(" ");
        sb.append(code).append("##DEMO##");
        sb.append(message);
        return sb.toString();
    }

    private String message(String application, String clazzName, Integer code, String message, String exception) {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(application).append("]").append(" ");
        sb.append("[").append(clazzName).append("]").append(" ");
        sb.append("[").append(1000).append("]").append(" ");
        sb.append("[").append(9999).append("]").append(" ");
        sb.append(code).append("####");
        sb.append(message).append("##DEMO##");
        sb.append(exception);
        return sb.toString();
    }

    private String message(String application, String clazzName, String sign, String otherSign, Integer code, String message) {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(application).append("]").append(" ");
        sb.append("[").append(clazzName).append("]").append(" ");
        sb.append("[").append(sign).append("]").append(" ");
        sb.append("[").append(otherSign).append("]").append(" ");
        sb.append(code).append("##DEMO##");
        sb.append(message);
        return sb.toString();
    }

    private String message(String application, String clazzName, String sign, String otherSign, Integer code, String message, String exception) {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(application).append("]").append(" ");
        sb.append("[").append(clazzName).append("]").append(" ");
        sb.append("[").append(sign).append("]").append(" ");
        sb.append("[").append(otherSign).append("]").append(" ");
        sb.append(code).append("####");
        sb.append(message).append("##DEMO##");
        sb.append(exception);
        return sb.toString();
    }

}
