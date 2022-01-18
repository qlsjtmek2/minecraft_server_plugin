// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.util.logging.Level;
import java.util.logging.Logger;

class ClassLoadContext
{
    private static final Logger logger;
    private final ClassLoader callerLoader;
    private final ClassLoader contextLoader;
    private final boolean preferContext;
    private boolean ambiguous;
    
    public static ClassLoadContext of(final Class<?> caller, final boolean preferContext) {
        return new ClassLoadContext(caller, preferContext);
    }
    
    ClassLoadContext(final Class<?> caller, final boolean preferContext) {
        if (caller == null) {
            throw new IllegalArgumentException("caller is null");
        }
        this.callerLoader = caller.getClassLoader();
        this.contextLoader = Thread.currentThread().getContextClassLoader();
        this.preferContext = preferContext;
    }
    
    public Class<?> forName(final String name) throws ClassNotFoundException {
        final ClassLoader defaultLoader = this.getDefault(this.preferContext);
        try {
            return Class.forName(name, true, defaultLoader);
        }
        catch (ClassNotFoundException e) {
            if (this.callerLoader == defaultLoader) {
                throw e;
            }
            return Class.forName(name, true, this.callerLoader);
        }
    }
    
    public ClassLoader getDefault(final boolean preferContext) {
        if (this.contextLoader == null) {
            if (ClassLoadContext.logger.isLoggable(Level.FINE)) {
                ClassLoadContext.logger.fine("No Context ClassLoader, using " + this.callerLoader.getClass().getName());
            }
            return this.callerLoader;
        }
        if (this.contextLoader == this.callerLoader) {
            if (ClassLoadContext.logger.isLoggable(Level.FINE)) {
                ClassLoadContext.logger.fine("Context and Caller ClassLoader's same instance of " + this.contextLoader.getClass().getName());
            }
            return this.callerLoader;
        }
        if (this.isChild(this.contextLoader, this.callerLoader)) {
            if (ClassLoadContext.logger.isLoggable(Level.FINE)) {
                ClassLoadContext.logger.info("Caller ClassLoader " + this.callerLoader.getClass().getName() + " child of ContextLoader " + this.contextLoader.getClass().getName());
            }
            return this.callerLoader;
        }
        if (this.isChild(this.callerLoader, this.contextLoader)) {
            if (ClassLoadContext.logger.isLoggable(Level.FINE)) {
                ClassLoadContext.logger.info("Context ClassLoader " + this.contextLoader.getClass().getName() + " child of Caller ClassLoader " + this.callerLoader.getClass().getName());
            }
            return this.contextLoader;
        }
        ClassLoadContext.logger.info("Ambiguous ClassLoader choice preferContext:" + preferContext + " Context:" + this.contextLoader.getClass().getName() + " Caller:" + this.callerLoader.getClass().getName());
        this.ambiguous = true;
        return preferContext ? this.contextLoader : this.callerLoader;
    }
    
    public boolean isAmbiguous() {
        return this.ambiguous;
    }
    
    public ClassLoader getCallerLoader() {
        return this.callerLoader;
    }
    
    public ClassLoader getContextLoader() {
        return this.contextLoader;
    }
    
    public ClassLoader getThisLoader() {
        return this.getClass().getClassLoader();
    }
    
    private boolean isChild(final ClassLoader loader1, ClassLoader loader2) {
        while (loader2 != null) {
            if (loader2 == loader1) {
                return true;
            }
            loader2 = loader2.getParent();
        }
        return false;
    }
    
    static {
        logger = Logger.getLogger(ClassLoadContext.class.getName());
    }
}
