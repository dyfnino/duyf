package com.samton.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * 作者: duyingfeng
 * 日期: 17-9-6
 * 说明:
 * To change this template use File | Settings | File Templates.
 */
public class Log4jConfigListener implements ServletContextListener {
    public static final String CONFIG_LOCATION_PARAM = "log4jConfigLocation";
    public static final String XML_FILE_EXTENSION = ".xml";

    public void contextDestroyed(ServletContextEvent event) {
        // TODO Auto-generated method stub
        LogManager.shutdown();
    }

    public void contextInitialized(ServletContextEvent event) {
        // TODO Auto-generated method stub
        String location = event.getServletContext().getInitParameter(CONFIG_LOCATION_PARAM);        if (location != null) {
            if (!location.startsWith("/")) {
                location = "/" + location;
            }
            location = event.getServletContext().getRealPath(location);

            //如果是xml结尾就用DOM解析，否则就用properties解析
            if (location.toLowerCase().endsWith(XML_FILE_EXTENSION)) {
                DOMConfigurator.configure(location);
            }else {
                PropertyConfigurator.configure(location);
            }
        }
    }
}
