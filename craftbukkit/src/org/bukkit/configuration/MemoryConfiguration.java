// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration;

import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang.Validate;

public class MemoryConfiguration extends MemorySection implements Configuration
{
    protected Configuration defaults;
    protected MemoryConfigurationOptions options;
    
    public MemoryConfiguration() {
    }
    
    public MemoryConfiguration(final Configuration defaults) {
        this.defaults = defaults;
    }
    
    public void addDefault(final String path, final Object value) {
        Validate.notNull(path, "Path may not be null");
        if (this.defaults == null) {
            this.defaults = new MemoryConfiguration();
        }
        this.defaults.set(path, value);
    }
    
    public void addDefaults(final Map<String, Object> defaults) {
        Validate.notNull(defaults, "Defaults may not be null");
        for (final Map.Entry<String, Object> entry : defaults.entrySet()) {
            this.addDefault(entry.getKey(), entry.getValue());
        }
    }
    
    public void addDefaults(final Configuration defaults) {
        Validate.notNull(defaults, "Defaults may not be null");
        this.addDefaults(defaults.getValues(true));
    }
    
    public void setDefaults(final Configuration defaults) {
        Validate.notNull(defaults, "Defaults may not be null");
        this.defaults = defaults;
    }
    
    public Configuration getDefaults() {
        return this.defaults;
    }
    
    public ConfigurationSection getParent() {
        return null;
    }
    
    public MemoryConfigurationOptions options() {
        if (this.options == null) {
            this.options = new MemoryConfigurationOptions(this);
        }
        return this.options;
    }
}
