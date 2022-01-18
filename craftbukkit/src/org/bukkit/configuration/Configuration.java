// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration;

import java.util.Map;

public interface Configuration extends ConfigurationSection
{
    void addDefault(final String p0, final Object p1);
    
    void addDefaults(final Map<String, Object> p0);
    
    void addDefaults(final Configuration p0);
    
    void setDefaults(final Configuration p0);
    
    Configuration getDefaults();
    
    ConfigurationOptions options();
}
