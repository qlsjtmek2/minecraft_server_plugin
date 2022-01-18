// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Defaults
{
    private static final Map<Class<?>, Object> DEFAULTS;
    
    private static <T> void put(final Map<Class<?>, Object> map, final Class<T> type, final T value) {
        map.put(type, value);
    }
    
    public static <T> T defaultValue(final Class<T> type) {
        return (T)Defaults.DEFAULTS.get(type);
    }
    
    static {
        final Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
        put(map, Boolean.TYPE, false);
        put(map, Character.TYPE, '\0');
        put(map, Byte.TYPE, (byte)0);
        put(map, Short.TYPE, (short)0);
        put(map, Integer.TYPE, 0);
        put(map, Long.TYPE, 0L);
        put(map, Float.TYPE, 0.0f);
        put(map, Double.TYPE, 0.0);
        DEFAULTS = Collections.unmodifiableMap((Map<? extends Class<?>, ?>)map);
    }
}
