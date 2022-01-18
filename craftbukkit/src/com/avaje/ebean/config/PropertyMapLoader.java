// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import javax.servlet.ServletContext;
import java.util.logging.Logger;

final class PropertyMapLoader
{
    private static Logger logger;
    private static ServletContext servletContext;
    
    public static ServletContext getServletContext() {
        return PropertyMapLoader.servletContext;
    }
    
    public static void setServletContext(final ServletContext servletContext) {
        PropertyMapLoader.servletContext = servletContext;
    }
    
    public static PropertyMap load(final PropertyMap p, final String fileName) {
        final InputStream is = findInputStream(fileName);
        if (is == null) {
            PropertyMapLoader.logger.severe(fileName + " not found");
            return p;
        }
        return load(p, is);
    }
    
    private static PropertyMap load(PropertyMap p, final InputStream in) {
        final Properties props = new Properties();
        try {
            props.load(in);
            in.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (p == null) {
            p = new PropertyMap();
        }
        for (final Map.Entry<Object, Object> entry : props.entrySet()) {
            final String key = entry.getKey().toLowerCase();
            String val = entry.getValue();
            if (val != null) {
                val = val.trim();
            }
            p.put(key, val);
        }
        p.evaluateProperties();
        String otherProps = p.remove("load.properties");
        if (otherProps == null) {
            otherProps = p.remove("load.properties.override");
        }
        if (otherProps != null) {
            otherProps = otherProps.replace("\\", "/");
            final InputStream is = findInputStream(otherProps);
            if (is != null) {
                PropertyMapLoader.logger.fine("loading properties from " + otherProps);
                load(p, is);
            }
            else {
                PropertyMapLoader.logger.severe("load.properties " + otherProps + " not found.");
            }
        }
        return p;
    }
    
    private static InputStream findInputStream(final String fileName) {
        if (fileName == null) {
            throw new NullPointerException("fileName is null?");
        }
        if (PropertyMapLoader.servletContext == null) {
            PropertyMapLoader.logger.fine("No servletContext so not looking in WEB-INF for " + fileName);
        }
        else {
            final InputStream in = PropertyMapLoader.servletContext.getResourceAsStream("/WEB-INF/" + fileName);
            if (in != null) {
                PropertyMapLoader.logger.fine(fileName + " found in WEB-INF");
                return in;
            }
        }
        try {
            final File f = new File(fileName);
            if (f.exists()) {
                PropertyMapLoader.logger.fine(fileName + " found in file system");
                return new FileInputStream(f);
            }
            final InputStream in2 = findInClassPath(fileName);
            if (in2 != null) {
                PropertyMapLoader.logger.fine(fileName + " found in classpath");
            }
            return in2;
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static InputStream findInClassPath(final String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }
    
    static {
        PropertyMapLoader.logger = Logger.getLogger(PropertyMapLoader.class.getName());
    }
}
