// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.net.URL;

public class UrlPathHelper
{
    private static final String PROTOCAL_PREFIX = "file:";
    
    public static URL[] convertToUrl(final String[] paths) {
        final ArrayList<URL> list = new ArrayList<URL>();
        for (int i = 0; i < paths.length; ++i) {
            final URL url = convertToUrl(paths[i]);
            if (url != null) {
                list.add(url);
            }
        }
        return list.toArray(new URL[list.size()]);
    }
    
    public static URL convertToUrl(final String path) {
        if (isEmpty(path)) {
            return null;
        }
        try {
            return new URL("file:" + convertUrlString(path));
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String convertUrlString(String classpath) {
        if (isEmpty(classpath)) {
            return "";
        }
        classpath = classpath.trim();
        if (classpath.length() < 2) {
            return "";
        }
        if (classpath.charAt(0) != '/' && classpath.charAt(1) == ':') {
            classpath = "/" + classpath;
        }
        if (!classpath.endsWith("/")) {
            final File file = new File(classpath);
            if (file.exists() && file.isDirectory()) {
                classpath = classpath.concat("/");
            }
        }
        return classpath;
    }
    
    private static boolean isEmpty(final String s) {
        return s == null || s.trim().length() == 0;
    }
}
