// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson;

import java.lang.reflect.Type;

public interface JsonSerializationContext
{
    JsonElement serialize(final Object p0);
    
    JsonElement serialize(final Object p0, final Type p1);
}
