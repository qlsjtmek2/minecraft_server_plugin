// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

public class RegisteredServiceProvider<T> implements Comparable<RegisteredServiceProvider<?>>
{
    private Class<T> service;
    private Plugin plugin;
    private T provider;
    private ServicePriority priority;
    
    public RegisteredServiceProvider(final Class<T> service, final T provider, final ServicePriority priority, final Plugin plugin) {
        this.service = service;
        this.plugin = plugin;
        this.provider = provider;
        this.priority = priority;
    }
    
    public Class<T> getService() {
        return this.service;
    }
    
    public Plugin getPlugin() {
        return this.plugin;
    }
    
    public T getProvider() {
        return this.provider;
    }
    
    public ServicePriority getPriority() {
        return this.priority;
    }
    
    public int compareTo(final RegisteredServiceProvider<?> other) {
        if (this.priority.ordinal() == other.getPriority().ordinal()) {
            return 0;
        }
        return (this.priority.ordinal() < other.getPriority().ordinal()) ? 1 : -1;
    }
}
