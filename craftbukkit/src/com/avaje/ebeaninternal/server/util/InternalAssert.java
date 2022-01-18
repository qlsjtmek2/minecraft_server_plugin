// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.util;

public class InternalAssert
{
    public static void notNull(final Object o, final String msg) {
        if (o == null) {
            throw new IllegalStateException(msg);
        }
    }
    
    public static void isTrue(final boolean b, final String msg) {
        if (!b) {
            throw new IllegalStateException(msg);
        }
    }
}
