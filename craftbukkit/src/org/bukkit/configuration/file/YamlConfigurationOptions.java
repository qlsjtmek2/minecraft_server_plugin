// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration.file;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.MemoryConfigurationOptions;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.MemoryConfiguration;

public class YamlConfigurationOptions extends FileConfigurationOptions
{
    private int indent;
    
    protected YamlConfigurationOptions(final YamlConfiguration configuration) {
        super(configuration);
        this.indent = 2;
    }
    
    public YamlConfiguration configuration() {
        return (YamlConfiguration)super.configuration();
    }
    
    public YamlConfigurationOptions copyDefaults(final boolean value) {
        super.copyDefaults(value);
        return this;
    }
    
    public YamlConfigurationOptions pathSeparator(final char value) {
        super.pathSeparator(value);
        return this;
    }
    
    public YamlConfigurationOptions header(final String value) {
        super.header(value);
        return this;
    }
    
    public YamlConfigurationOptions copyHeader(final boolean value) {
        super.copyHeader(value);
        return this;
    }
    
    public int indent() {
        return this.indent;
    }
    
    public YamlConfigurationOptions indent(final int value) {
        Validate.isTrue(value >= 2, "Indent must be at least 2 characters");
        Validate.isTrue(value <= 9, "Indent cannot be greater than 9 characters");
        this.indent = value;
        return this;
    }
}
