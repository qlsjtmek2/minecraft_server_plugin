// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.HashMap;

public final class InternString
{
    private static HashMap<String, String> map;
    
    public static String intern(final String s) {
        if (s == null) {
            return null;
        }
        synchronized (InternString.map) {
            final String v = InternString.map.get(s);
            if (v != null) {
                return v;
            }
            InternString.map.put(s, s);
            return s;
        }
    }
    
    static {
        InternString.map = new HashMap<String, String>();
    }
}
