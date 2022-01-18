// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration.file;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemoryConfigurationOptions;

public class FileConfigurationOptions extends MemoryConfigurationOptions
{
    private String header;
    private boolean copyHeader;
    
    protected FileConfigurationOptions(final MemoryConfiguration configuration) {
        super(configuration);
        this.header = null;
        this.copyHeader = true;
    }
    
    public FileConfiguration configuration() {
        return (FileConfiguration)super.configuration();
    }
    
    public FileConfigurationOptions copyDefaults(final boolean value) {
        super.copyDefaults(value);
        return this;
    }
    
    public FileConfigurationOptions pathSeparator(final char value) {
        super.pathSeparator(value);
        return this;
    }
    
    public String header() {
        return this.header;
    }
    
    public FileConfigurationOptions header(final String value) {
        this.header = value;
        return this;
    }
    
    public boolean copyHeader() {
        return this.copyHeader;
    }
    
    public FileConfigurationOptions copyHeader(final boolean value) {
        this.copyHeader = value;
        return this;
    }
}
