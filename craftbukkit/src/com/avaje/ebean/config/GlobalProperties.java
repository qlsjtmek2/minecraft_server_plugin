// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import java.util.Iterator;
import java.util.Map;
import com.avaje.ebeaninternal.api.ClassUtil;
import javax.servlet.ServletContext;

public final class GlobalProperties
{
    private static PropertyMap globalMap;
    private static boolean skipPrimaryServer;
    
    public static synchronized void setSkipPrimaryServer(final boolean skip) {
        GlobalProperties.skipPrimaryServer = skip;
    }
    
    public static synchronized boolean isSkipPrimaryServer() {
        return GlobalProperties.skipPrimaryServer;
    }
    
    public static String evaluateExpressions(final String val) {
        return getPropertyMap().eval(val);
    }
    
    public static synchronized void evaluateExpressions() {
        getPropertyMap().evaluateProperties();
    }
    
    public static synchronized void setServletContext(final ServletContext servletContext) {
        PropertyMapLoader.setServletContext(servletContext);
    }
    
    public static synchronized ServletContext getServletContext() {
        return PropertyMapLoader.getServletContext();
    }
    
    private static void initPropertyMap() {
        String fileName = System.getenv("EBEAN_PROPS_FILE");
        if (fileName == null) {
            fileName = System.getProperty("ebean.props.file");
            if (fileName == null) {
                fileName = "ebean.properties";
            }
        }
        GlobalProperties.globalMap = PropertyMapLoader.load(null, fileName);
        if (GlobalProperties.globalMap == null) {
            GlobalProperties.globalMap = new PropertyMap();
        }
        final String loaderCn = GlobalProperties.globalMap.get("ebean.properties.loader");
        if (loaderCn != null) {
            try {
                final Runnable r = (Runnable)ClassUtil.newInstance(loaderCn);
                r.run();
            }
            catch (Exception e) {
                final String m = "Error creating or running properties loader " + loaderCn;
                throw new RuntimeException(m, e);
            }
        }
    }
    
    private static synchronized PropertyMap getPropertyMap() {
        if (GlobalProperties.globalMap == null) {
            initPropertyMap();
        }
        return GlobalProperties.globalMap;
    }
    
    public static synchronized String get(final String key, final String defaultValue) {
        return getPropertyMap().get(key, defaultValue);
    }
    
    public static synchronized int getInt(final String key, final int defaultValue) {
        return getPropertyMap().getInt(key, defaultValue);
    }
    
    public static synchronized boolean getBoolean(final String key, final boolean defaultValue) {
        return getPropertyMap().getBoolean(key, defaultValue);
    }
    
    public static synchronized String put(final String key, final String value) {
        return getPropertyMap().putEval(key, value);
    }
    
    public static synchronized void putAll(final Map<String, String> keyValueMap) {
        for (final Map.Entry<String, String> e : keyValueMap.entrySet()) {
            getPropertyMap().putEval(e.getKey(), e.getValue());
        }
    }
    
    public static PropertySource getPropertySource(final String name) {
        return new ConfigPropertyMap(name);
    }
    
    public interface PropertySource
    {
        String getServerName();
        
        String get(final String p0, final String p1);
        
        int getInt(final String p0, final int p1);
        
        boolean getBoolean(final String p0, final boolean p1);
        
         <T extends Enum<T>> T getEnum(final Class<T> p0, final String p1, final T p2);
    }
}
