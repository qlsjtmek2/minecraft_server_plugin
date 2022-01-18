// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Locale;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages
{
    private static final String BUNDLE_NAME = "com.mysql.jdbc.LocalizedErrorMessages";
    private static final ResourceBundle RESOURCE_BUNDLE;
    
    public static String getString(final String key) {
        if (Messages.RESOURCE_BUNDLE == null) {
            throw new RuntimeException("Localized messages from resource bundle 'com.mysql.jdbc.LocalizedErrorMessages' not loaded during initialization of driver.");
        }
        try {
            if (key == null) {
                throw new IllegalArgumentException("Message key can not be null");
            }
            String message = Messages.RESOURCE_BUNDLE.getString(key);
            if (message == null) {
                message = "Missing error message for key '" + key + "'";
            }
            return message;
        }
        catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static String getString(final String key, final Object[] args) {
        return MessageFormat.format(getString(key), args);
    }
    
    static {
        ResourceBundle temp = null;
        try {
            temp = ResourceBundle.getBundle("com.mysql.jdbc.LocalizedErrorMessages", Locale.getDefault(), Messages.class.getClassLoader());
        }
        catch (Throwable t) {
            try {
                temp = ResourceBundle.getBundle("com.mysql.jdbc.LocalizedErrorMessages");
            }
            catch (Throwable t2) {
                final RuntimeException rt = new RuntimeException("Can't load resource bundle due to underlying exception " + t.toString());
                rt.initCause(t2);
                throw rt;
            }
        }
        finally {
            RESOURCE_BUNDLE = temp;
        }
    }
}
