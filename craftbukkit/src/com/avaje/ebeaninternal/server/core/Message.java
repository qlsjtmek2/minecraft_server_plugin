// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.text.MessageFormat;

public class Message
{
    private static final String bundle = "com.avaje.ebeaninternal.api.message";
    
    public static String msg(final String key, final Object arg) {
        final Object[] args = { arg };
        return MessageFormat.format(getPattern(key), args);
    }
    
    public static String msg(final String key, final Object arg, final Object arg2) {
        final Object[] args = { arg, arg2 };
        return MessageFormat.format(getPattern(key), args);
    }
    
    public static String msg(final String key, final Object arg, final Object arg2, final Object arg3) {
        final Object[] args = { arg, arg2, arg3 };
        return MessageFormat.format(getPattern(key), args);
    }
    
    public static String msg(final String key, final Object[] args) {
        return MessageFormat.format(getPattern(key), args);
    }
    
    public static String msg(final String key) {
        return MessageFormat.format(getPattern(key), new Object[0]);
    }
    
    private static String getPattern(final String key) {
        try {
            final ResourceBundle myResources = ResourceBundle.getBundle("com.avaje.ebeaninternal.api.message");
            return myResources.getString(key);
        }
        catch (MissingResourceException e) {
            return "MissingResource com.avaje.ebeaninternal.api.message:" + key;
        }
    }
}
