// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import javax.servlet.ServletContext;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.lib.ShutdownManager;
import javax.servlet.ServletContextEvent;
import java.util.logging.Logger;

public class ServletContextListener implements javax.servlet.ServletContextListener
{
    private static final Logger logger;
    
    public void contextDestroyed(final ServletContextEvent event) {
        ShutdownManager.shutdown();
    }
    
    public void contextInitialized(final ServletContextEvent event) {
        try {
            final ServletContext servletContext = event.getServletContext();
            GlobalProperties.setServletContext(servletContext);
            if (servletContext != null) {
                final String servletRealPath = servletContext.getRealPath("");
                GlobalProperties.put("servlet.realpath", servletRealPath);
                ServletContextListener.logger.info("servlet.realpath=[" + servletRealPath + "]");
            }
            Ebean.getServer(null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    static {
        logger = Logger.getLogger(ServletContextListener.class.getName());
    }
}
