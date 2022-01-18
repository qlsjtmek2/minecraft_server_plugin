// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import java.util.Collection;
import java.util.List;

public interface ServicesManager
{
     <T> void register(final Class<T> p0, final T p1, final Plugin p2, final ServicePriority p3);
    
    void unregisterAll(final Plugin p0);
    
    void unregister(final Class<?> p0, final Object p1);
    
    void unregister(final Object p0);
    
     <T> T load(final Class<T> p0);
    
     <T> RegisteredServiceProvider<T> getRegistration(final Class<T> p0);
    
    List<RegisteredServiceProvider<?>> getRegistrations(final Plugin p0);
    
     <T> Collection<RegisteredServiceProvider<T>> getRegistrations(final Class<T> p0);
    
    Collection<Class<?>> getKnownServices();
    
     <T> boolean isProvidedFor(final Class<T> p0);
}
