// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration.file;

import org.bukkit.configuration.ConfigurationOptions;
import org.bukkit.configuration.MemoryConfigurationOptions;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.bukkit.configuration.InvalidConfigurationException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileWriter;
import com.google.common.io.Files;
import org.apache.commons.lang.Validate;
import java.io.File;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.MemoryConfiguration;

public abstract class FileConfiguration extends MemoryConfiguration
{
    public FileConfiguration() {
    }
    
    public FileConfiguration(final Configuration defaults) {
        super(defaults);
    }
    
    public void save(final File file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        Files.createParentDirs(file);
        final String data = this.saveToString();
        final FileWriter writer = new FileWriter(file);
        try {
            writer.write(data);
        }
        finally {
            writer.close();
        }
    }
    
    public void save(final String file) throws IOException {
        Validate.notNull(file, "File cannot be null");
        this.save(new File(file));
    }
    
    public abstract String saveToString();
    
    public void load(final File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");
        this.load(new FileInputStream(file));
    }
    
    public void load(final InputStream stream) throws IOException, InvalidConfigurationException {
        Validate.notNull(stream, "Stream cannot be null");
        final InputStreamReader reader = new InputStreamReader(stream);
        final StringBuilder builder = new StringBuilder();
        final BufferedReader input = new BufferedReader(reader);
        try {
            String line;
            while ((line = input.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        }
        finally {
            input.close();
        }
        this.loadFromString(builder.toString());
    }
    
    public void load(final String file) throws FileNotFoundException, IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");
        this.load(new File(file));
    }
    
    public abstract void loadFromString(final String p0) throws InvalidConfigurationException;
    
    protected abstract String buildHeader();
    
    public FileConfigurationOptions options() {
        if (this.options == null) {
            this.options = new FileConfigurationOptions(this);
        }
        return (FileConfigurationOptions)this.options;
    }
}
