// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import java.util.Set;
import org.bukkit.event.Event;
import java.util.Map;
import org.bukkit.event.Listener;
import java.util.regex.Pattern;
import java.io.File;

public interface PluginLoader
{
    Plugin loadPlugin(final File p0) throws InvalidPluginException, UnknownDependencyException;
    
    PluginDescriptionFile getPluginDescription(final File p0) throws InvalidDescriptionException;
    
    Pattern[] getPluginFileFilters();
    
    Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(final Listener p0, final Plugin p1);
    
    void enablePlugin(final Plugin p0);
    
    void disablePlugin(final Plugin p0);
}
