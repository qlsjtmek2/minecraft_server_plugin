// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.config;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.IOException;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ExTConfig
{
    protected final JavaPlugin plugin;
    private final String basePath;
    private final String fileName;
    private final String resourceFileName;
    protected FileConfiguration config;
    protected File configFile;
    
    public ExTConfig(final JavaPlugin plugin, final String fileName) {
        this.config = null;
        this.configFile = null;
        this.plugin = plugin;
        this.basePath = "";
        this.fileName = fileName;
        this.resourceFileName = fileName;
    }
    
    public ExTConfig(final JavaPlugin plugin, final String basePath, final String fileName, final String resourceFileName) {
        this.config = null;
        this.configFile = null;
        this.plugin = plugin;
        this.basePath = basePath;
        this.fileName = fileName;
        this.resourceFileName = resourceFileName;
    }
    
    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), this.getConfigFullPath());
        }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
        File defConfigStream = new File(plugin.getDataFolder(), this.resourceFileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            this.config.setDefaults((Configuration)defConfig);
        }
    }
    
    public FileConfiguration getConfig() {
        if (this.config == null) {
            this.reloadConfig();
        }
        return this.config;
    }
    
    public void saveConfig() {
        if (this.config == null || this.configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        }
        catch (IOException ex) {}
    }
    
    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), this.getConfigFullPath());
        }
        if (!this.configFile.exists()) {
            final File baseFolder = new File(this.plugin.getDataFolder(), this.basePath);
            if (!baseFolder.exists()) {
                baseFolder.mkdir();
            }
            final InputStream is = this.plugin.getResource(this.resourceFileName);
            this.saveFileFromInputSteam(is, new File(this.plugin.getDataFolder(), this.getConfigFullPath()));
        }
    }
    
    protected String getConfigFullPath() {
        if (this.basePath.equalsIgnoreCase("")) {
            return this.fileName;
        }
        return Paths.get(this.basePath, this.fileName).toString();
    }
    
    private void saveFileFromInputSteam(final InputStream is, final File toFile) {
        try {
            final OutputStream os = new FileOutputStream(toFile);
            final byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.flush();
            os.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
