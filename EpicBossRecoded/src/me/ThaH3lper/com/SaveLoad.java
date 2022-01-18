// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com;

import java.io.IOException;
import java.util.logging.Level;
import java.io.InputStream;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class SaveLoad
{
    private FileConfiguration DataConfig;
    private File data;
    private EpicBoss eb;
    private String file;
    private File thefile;
    
    public SaveLoad(final EpicBoss boss, final String newfile) {
        this.DataConfig = null;
        this.data = null;
        this.eb = boss;
        this.file = newfile;
        this.thefile = new File(this.eb.getDataFolder(), newfile);
        if (this.thefile.exists()) {
            this.data = this.thefile;
        }
        this.reloadCustomConfig();
        this.saveCustomConfig();
    }
    
    public void reloadCustomConfig() {
        if (this.data == null) {
            this.data = new File(this.eb.getDataFolder(), this.file);
            this.DataConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.data);
            final InputStream defConfigStream = this.eb.getResource(this.file);
            if (defConfigStream != null) {
                final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                this.DataConfig.setDefaults((Configuration)defConfig);
            }
            this.getCustomConfig().options().copyDefaults(true);
            this.eb.logger.info(String.valueOf(this.file) + " did not exist! Generated a new one!");
        }
        else {
            this.DataConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.data);
        }
    }
    
    public FileConfiguration getCustomConfig() {
        if (this.DataConfig == null) {
            this.reloadCustomConfig();
        }
        return this.DataConfig;
    }
    
    public void saveCustomConfig() {
        if (this.DataConfig == null || this.data == null) {
            return;
        }
        try {
            this.getCustomConfig().save(this.data);
        }
        catch (IOException ex) {
            this.eb.getLogger().log(Level.SEVERE, "Could not save config to " + this.data, ex);
        }
    }
}
