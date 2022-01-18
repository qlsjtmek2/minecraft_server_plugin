// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.joptsimple;

public interface ValueConverter<V>
{
    V convert(final String p0);
    
    Class<V> valueType();
    
    String valuePattern();
}
