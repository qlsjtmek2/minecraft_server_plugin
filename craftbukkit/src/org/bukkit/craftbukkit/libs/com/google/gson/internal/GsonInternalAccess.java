// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.internal;

import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapterFactory;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;

public abstract class GsonInternalAccess
{
    public static GsonInternalAccess INSTANCE;
    
    public abstract <T> TypeAdapter<T> getNextAdapter(final Gson p0, final TypeAdapterFactory p1, final TypeToken<T> p2);
}
