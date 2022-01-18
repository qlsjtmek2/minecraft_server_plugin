// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import com.avaje.ebeaninternal.api.ClassUtil;
import java.util.logging.Level;
import com.avaje.ebean.config.GlobalProperties;
import javax.persistence.PersistenceException;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.common.BootupEbeanManager;
import java.util.logging.Logger;

public class EbeanServerFactory
{
    private static final Logger logger;
    private static BootupEbeanManager serverFactory;
    
    public static EbeanServer create(final String name) {
        final EbeanServer server = EbeanServerFactory.serverFactory.createServer(name);
        return server;
    }
    
    public static EbeanServer create(final ServerConfig config) {
        if (config.getName() == null) {
            throw new PersistenceException("The name is null (it is required)");
        }
        final EbeanServer server = EbeanServerFactory.serverFactory.createServer(config);
        if (config.isDefaultServer()) {
            GlobalProperties.setSkipPrimaryServer(true);
        }
        if (config.isRegister()) {
            Ebean.register(server, config.isDefaultServer());
        }
        return server;
    }
    
    private static BootupEbeanManager createServerFactory() {
        final String dflt = "com.avaje.ebeaninternal.server.core.DefaultServerFactory";
        final String implClassName = GlobalProperties.get("ebean.serverfactory", dflt);
        final int delaySecs = GlobalProperties.getInt("ebean.start.delay", 0);
        if (delaySecs > 0) {
            try {
                final String m = "Ebean sleeping " + delaySecs + " seconds due to ebean.start.delay";
                EbeanServerFactory.logger.log(Level.INFO, m);
                Thread.sleep(delaySecs * 1000);
            }
            catch (InterruptedException e) {
                final String i = "Interrupting debug.start.delay of " + delaySecs;
                EbeanServerFactory.logger.log(Level.SEVERE, i, e);
            }
        }
        try {
            return (BootupEbeanManager)ClassUtil.newInstance(implClassName);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    static {
        logger = Logger.getLogger(EbeanServerFactory.class.getName());
        EbeanServerFactory.serverFactory = createServerFactory();
    }
}
