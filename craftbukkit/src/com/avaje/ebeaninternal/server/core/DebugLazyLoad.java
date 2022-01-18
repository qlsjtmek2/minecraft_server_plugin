// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.List;
import com.avaje.ebean.config.GlobalProperties;
import java.util.ArrayList;

public class DebugLazyLoad
{
    private final String[] ignoreList;
    private final boolean debug;
    
    public DebugLazyLoad(final boolean lazyLoadDebug) {
        this.ignoreList = this.buildLazyLoadIgnoreList();
        this.debug = lazyLoadDebug;
    }
    
    public boolean isDebug() {
        return this.debug;
    }
    
    public StackTraceElement getStackTraceElement(final Class<?> beanType) {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length; ++i) {
            if (this.isStackLine(stackTrace[i], beanType)) {
                return stackTrace[i];
            }
        }
        return null;
    }
    
    private boolean isStackLine(final StackTraceElement element, final Class<?> beanType) {
        final String stackClass = element.getClassName();
        if (this.isBeanClass(beanType, stackClass)) {
            return false;
        }
        for (int i = 0; i < this.ignoreList.length; ++i) {
            if (stackClass.startsWith(this.ignoreList[i])) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isBeanClass(final Class<?> beanType, final String stackClass) {
        if (stackClass.startsWith(beanType.getName())) {
            return true;
        }
        final Class<?> superCls = beanType.getSuperclass();
        return !superCls.equals(Object.class) && this.isBeanClass(superCls, stackClass);
    }
    
    private String[] buildLazyLoadIgnoreList() {
        final List<String> ignore = new ArrayList<String>();
        ignore.add("com.avaje.ebean");
        ignore.add("java");
        ignore.add("sun.reflect");
        ignore.add("org.codehaus.groovy.runtime.");
        final String extraIgnore = GlobalProperties.get("debug.lazyload.ignore", null);
        if (extraIgnore != null) {
            final String[] split = extraIgnore.split(",");
            for (int i = 0; i < split.length; ++i) {
                ignore.add(split[i].trim());
            }
        }
        return ignore.toArray(new String[ignore.size()]);
    }
}
