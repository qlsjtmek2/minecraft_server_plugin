// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple.internal;

public final class Objects
{
    public static void ensureNotNull(final Object target) {
        if (target == null) {
            throw new NullPointerException();
        }
    }
    
    static {
        new Objects();
    }
}
