// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.subclass;

public class SubClassUtil implements GenSuffix
{
    public static boolean isSubClass(final String className) {
        return className.lastIndexOf("$$EntityBean") != -1;
    }
    
    public static String getSuperClassName(final String className) {
        final int dPos = className.lastIndexOf("$$EntityBean");
        if (dPos > -1) {
            return className.substring(0, dPos);
        }
        return className;
    }
}
