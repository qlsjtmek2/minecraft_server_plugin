// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import org.bukkit.permissions.Permissible;
import java.util.Set;
import org.bukkit.permissions.Permission;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.Event;
import java.io.File;

public interface PluginManager
{
    void registerInterface(final Class<? extends PluginLoader> p0) throws IllegalArgumentException;
    
    Plugin getPlugin(final String p0);
    
    Plugin[] getPlugins();
    
    boolean isPluginEnabled(final String p0);
    
    boolean isPluginEnabled(final Plugin p0);
    
    Plugin loadPlugin(final File p0) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;
    
    Plugin[] loadPlugins(final File p0);
    
    void disablePlugins();
    
    void clearPlugins();
    
    void callEvent(final Event p0) throws IllegalStateException;
    
    void registerEvents(final Listener p0, final Plugin p1);
    
    void registerEvent(final Class<? extends Event> p0, final Listener p1, final EventPriority p2, final EventExecutor p3, final Plugin p4);
    
    void registerEvent(final Class<? extends Event> p0, final Listener p1, final EventPriority p2, final EventExecutor p3, final Plugin p4, final boolean p5);
    
    void enablePlugin(final Plugin p0);
    
    void disablePlugin(final Plugin p0);
    
    Permission getPermission(final String p0);
    
    void addPermission(final Permission p0);
    
    void removePermission(final Permission p0);
    
    void removePermission(final String p0);
    
    Set<Permission> getDefaultPermissions(final boolean p0);
    
    void recalculatePermissionDefaults(final Permission p0);
    
    void subscribeToPermission(final String p0, final Permissible p1);
    
    void unsubscribeFromPermission(final String p0, final Permissible p1);
    
    Set<Permissible> getPermissionSubscriptions(final String p0);
    
    void subscribeToDefaultPerms(final boolean p0, final Permissible p1);
    
    void unsubscribeFromDefaultPerms(final boolean p0, final Permissible p1);
    
    Set<Permissible> getDefaultPermSubscriptions(final boolean p0);
    
    Set<Permission> getPermissions();
    
    boolean useTimings();
}
