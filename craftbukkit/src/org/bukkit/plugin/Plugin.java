// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import java.util.logging.Logger;
import org.bukkit.generator.ChunkGenerator;
import com.avaje.ebean.EbeanServer;
import org.bukkit.Server;
import java.io.InputStream;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import org.bukkit.command.TabExecutor;

public interface Plugin extends TabExecutor
{
    File getDataFolder();
    
    PluginDescriptionFile getDescription();
    
    FileConfiguration getConfig();
    
    InputStream getResource(final String p0);
    
    void saveConfig();
    
    void saveDefaultConfig();
    
    void saveResource(final String p0, final boolean p1);
    
    void reloadConfig();
    
    PluginLoader getPluginLoader();
    
    Server getServer();
    
    boolean isEnabled();
    
    void onDisable();
    
    void onLoad();
    
    void onEnable();
    
    boolean isNaggable();
    
    void setNaggable(final boolean p0);
    
    EbeanServer getDatabase();
    
    ChunkGenerator getDefaultWorldGenerator(final String p0, final String p1);
    
    Logger getLogger();
    
    String getName();
}
