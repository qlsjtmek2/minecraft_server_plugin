// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MimeTypeHelper
{
    private static ResourceBundle resources;
    
    public static String getMimeType(String filePath) {
        final int lastPeriod = filePath.lastIndexOf(".");
        if (lastPeriod > -1) {
            filePath = filePath.substring(lastPeriod + 1);
        }
        try {
            return MimeTypeHelper.resources.getString(filePath.toLowerCase());
        }
        catch (MissingResourceException e) {
            return null;
        }
    }
    
    static {
        MimeTypeHelper.resources = ResourceBundle.getBundle("com.avaje.lib.util.mimetypes");
    }
}
