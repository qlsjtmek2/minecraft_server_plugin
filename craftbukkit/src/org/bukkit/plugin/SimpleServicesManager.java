// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import java.util.Collection;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import com.google.common.collect.ImmutableList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.bukkit.event.server.ServiceUnregisterEvent;
import org.bukkit.event.Event;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.Bukkit;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleServicesManager implements ServicesManager
{
    private final Map<Class<?>, List<RegisteredServiceProvider<?>>> providers;
    
    public SimpleServicesManager() {
        this.providers = new HashMap<Class<?>, List<RegisteredServiceProvider<?>>>();
    }
    
    public <T> void register(final Class<T> service, final T provider, final Plugin plugin, final ServicePriority priority) {
        RegisteredServiceProvider<T> registeredProvider = null;
        synchronized (this.providers) {
            List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
            if (registered == null) {
                registered = new ArrayList<RegisteredServiceProvider<?>>();
                this.providers.put(service, registered);
            }
            registeredProvider = new RegisteredServiceProvider<T>(service, provider, priority, plugin);
            final int position = Collections.binarySearch(registered, registeredProvider);
            if (position < 0) {
                registered.add(-(position + 1), registeredProvider);
            }
            else {
                registered.add(position, registeredProvider);
            }
        }
        Bukkit.getServer().getPluginManager().callEvent(new ServiceRegisterEvent(registeredProvider));
    }
    
    public void unregisterAll(final Plugin plugin) {
        final ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList<ServiceUnregisterEvent>();
        synchronized (this.providers) {
            final Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
            try {
                while (it.hasNext()) {
                    final Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = it.next();
                    final Iterator<RegisteredServiceProvider<?>> it2 = entry.getValue().iterator();
                    try {
                        while (it2.hasNext()) {
                            final RegisteredServiceProvider<?> registered = it2.next();
                            if (registered.getPlugin().equals(plugin)) {
                                it2.remove();
                                unregisteredEvents.add(new ServiceUnregisterEvent(registered));
                            }
                        }
                    }
                    catch (NoSuchElementException ex) {}
                    if (entry.getValue().size() == 0) {
                        it.remove();
                    }
                }
            }
            catch (NoSuchElementException ex2) {}
        }
        for (final ServiceUnregisterEvent event : unregisteredEvents) {
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }
    
    public void unregister(final Class<?> service, final Object provider) {
        final ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList<ServiceUnregisterEvent>();
        synchronized (this.providers) {
            final Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
            try {
                while (it.hasNext()) {
                    final Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = it.next();
                    if (entry.getKey() != service) {
                        continue;
                    }
                    final Iterator<RegisteredServiceProvider<?>> it2 = entry.getValue().iterator();
                    try {
                        while (it2.hasNext()) {
                            final RegisteredServiceProvider<?> registered = it2.next();
                            if (registered.getProvider() == provider) {
                                it2.remove();
                                unregisteredEvents.add(new ServiceUnregisterEvent(registered));
                            }
                        }
                    }
                    catch (NoSuchElementException ex) {}
                    if (entry.getValue().size() != 0) {
                        continue;
                    }
                    it.remove();
                }
            }
            catch (NoSuchElementException ex2) {}
        }
        for (final ServiceUnregisterEvent event : unregisteredEvents) {
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }
    
    public void unregister(final Object provider) {
        final ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList<ServiceUnregisterEvent>();
        synchronized (this.providers) {
            final Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it = this.providers.entrySet().iterator();
            try {
                while (it.hasNext()) {
                    final Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = it.next();
                    final Iterator<RegisteredServiceProvider<?>> it2 = entry.getValue().iterator();
                    try {
                        while (it2.hasNext()) {
                            final RegisteredServiceProvider<?> registered = it2.next();
                            if (registered.getProvider().equals(provider)) {
                                it2.remove();
                                unregisteredEvents.add(new ServiceUnregisterEvent(registered));
                            }
                        }
                    }
                    catch (NoSuchElementException ex) {}
                    if (entry.getValue().size() == 0) {
                        it.remove();
                    }
                }
            }
            catch (NoSuchElementException ex2) {}
        }
        for (final ServiceUnregisterEvent event : unregisteredEvents) {
            Bukkit.getServer().getPluginManager().callEvent(event);
        }
    }
    
    public <T> T load(final Class<T> service) {
        synchronized (this.providers) {
            final List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
            if (registered == null) {
                return null;
            }
            return service.cast(registered.get(0).getProvider());
        }
    }
    
    public <T> RegisteredServiceProvider<T> getRegistration(final Class<T> service) {
        synchronized (this.providers) {
            final List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
            if (registered == null) {
                return null;
            }
            return (RegisteredServiceProvider<T>)registered.get(0);
        }
    }
    
    public List<RegisteredServiceProvider<?>> getRegistrations(final Plugin plugin) {
        final ImmutableList.Builder<RegisteredServiceProvider<?>> ret = ImmutableList.builder();
        synchronized (this.providers) {
            for (final List<RegisteredServiceProvider<?>> registered : this.providers.values()) {
                for (final RegisteredServiceProvider<?> provider : registered) {
                    if (provider.getPlugin().equals(plugin)) {
                        ret.add(provider);
                    }
                }
            }
        }
        return ret.build();
    }
    
    public <T> List<RegisteredServiceProvider<T>> getRegistrations(final Class<T> service) {
        final ImmutableList.Builder<RegisteredServiceProvider<T>> ret;
        synchronized (this.providers) {
            final List<RegisteredServiceProvider<?>> registered = this.providers.get(service);
            if (registered == null) {
                return (List<RegisteredServiceProvider<T>>)ImmutableList.of();
            }
            ret = ImmutableList.builder();
            for (final RegisteredServiceProvider<?> provider : registered) {
                ret.add((RegisteredServiceProvider<T>)provider);
            }
        }
        return ret.build();
    }
    
    public Set<Class<?>> getKnownServices() {
        synchronized (this.providers) {
            return (Set<Class<?>>)ImmutableSet.copyOf((Collection<?>)this.providers.keySet());
        }
    }
    
    public <T> boolean isProvidedFor(final Class<T> service) {
        synchronized (this.providers) {
            return this.providers.containsKey(service);
        }
    }
}
