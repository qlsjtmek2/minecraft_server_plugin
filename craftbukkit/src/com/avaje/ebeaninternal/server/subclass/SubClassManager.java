// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.subclass;

import java.security.PrivilegedActionException;
import javax.persistence.PersistenceException;
import java.security.AccessController;
import com.avaje.ebeaninternal.api.ClassUtil;
import java.security.PrivilegedExceptionAction;
import com.avaje.ebean.config.ServerConfig;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import com.avaje.ebean.enhance.agent.EnhanceConstants;

public class SubClassManager implements EnhanceConstants
{
    private static final Logger logger;
    private final ConcurrentHashMap<String, Class<?>> clzMap;
    private final SubClassFactory subclassFactory;
    private final String serverName;
    private final int logLevel;
    
    public SubClassManager(final ServerConfig serverConfig) {
        final String s = serverConfig.getProperty("subClassManager.preferContextClassloader", "true");
        final boolean preferContext = "true".equalsIgnoreCase(s);
        this.serverName = serverConfig.getName();
        this.logLevel = serverConfig.getEnhanceLogLevel();
        this.clzMap = new ConcurrentHashMap<String, Class<?>>();
        try {
            this.subclassFactory = AccessController.doPrivileged((PrivilegedExceptionAction<SubClassFactory>)new PrivilegedExceptionAction() {
                public Object run() {
                    final ClassLoader cl = ClassUtil.getClassLoader(this.getClass(), preferContext);
                    SubClassManager.logger.info("SubClassFactory parent ClassLoader [" + cl.getClass().getName() + "]");
                    return new SubClassFactory(cl, SubClassManager.this.logLevel);
                }
            });
        }
        catch (PrivilegedActionException e) {
            throw new PersistenceException(e);
        }
    }
    
    public Class<?> resolve(final String name) {
        synchronized (this) {
            final String superName = SubClassUtil.getSuperClassName(name);
            Class<?> clz = this.clzMap.get(superName);
            if (clz == null) {
                clz = this.createClass(superName);
                this.clzMap.put(superName, clz);
            }
            return clz;
        }
    }
    
    private Class<?> createClass(final String name) {
        try {
            final Class<?> superClass = Class.forName(name, true, this.subclassFactory.getParent());
            return this.subclassFactory.create(superClass, this.serverName);
        }
        catch (Exception ex) {
            final String m = "Error creating subclass for [" + name + "]";
            throw new PersistenceException(m, ex);
        }
    }
    
    static {
        logger = Logger.getLogger(SubClassManager.class.getName());
    }
}
