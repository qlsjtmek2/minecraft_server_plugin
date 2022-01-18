package me.espoo.pvp.yml;

import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomConfig
{
    private JavaPlugin plugin;
    private FileConfiguration config;
    private File configfile;
    private String filename;
    private boolean outfolder;
    
    public CustomConfig(JavaPlugin plugin, String filename) {
        this.config = null;
        this.configfile = null;
        this.filename = new String();
        this.outfolder = false;
        this.plugin = plugin;
        this.filename = filename;
        this.reloadConfig();
    }
    
    public CustomConfig(JavaPlugin plugin, String filename, boolean outfolder) {
        this.config = null;
        this.configfile = null;
        this.filename = new String();
        this.outfolder = false;
        this.plugin = plugin;
        this.filename = filename;
        this.outfolder = outfolder;
        this.reloadConfig();
    }
    
    public void reloadConfig() {
        if (this.configfile == null) {
            if (this.outfolder) {
                this.configfile = new File(String.valueOf(this.filename) + ".yml");
            }
            else {
                this.configfile = new File(this.plugin.getDataFolder(), String.valueOf(this.filename) + ".yml");
            }
        }
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configfile);
        InputStream defconfigstream = this.plugin.getResource(String.valueOf(this.filename) + ".yml");
        if (defconfigstream != null) {
            YamlConfiguration defconfig = YamlConfiguration.loadConfiguration(defconfigstream);
            this.config.setDefaults((Configuration)defconfig);
        }
    }
    
    public void saveConfig() {
        if (this.config == null || this.configfile == null) {
            return;
        }
        try {
            this.config.save(this.configfile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void copyConfig(String filename) {
        try {
            if (this.outfolder) {
                this.config.save(new File(String.valueOf(filename) + ".yml"));
            }
            else {
                this.config.save(new File(this.plugin.getDataFolder(), String.valueOf(filename) + ".yml"));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void copyConfig(String path, String filename) {
        this.copyConfig(String.valueOf(path) + "\\" + filename);
    }
    
    public void saveDefaultConfig(boolean replace) {
        try {
            if (!this.outfolder) {
                this.plugin.saveResource(String.valueOf(this.filename) + ".yml", replace);
                this.reloadConfig();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isExists() {
        return this.configfile.exists();
    }
    
    public FileConfiguration getConfig() {
        return this.config;
    }
    
    public Object get(String key) {
        return this.config.get(key);
    }
    
    public Object get(String key, Object def) {
        return this.config.get(key, def);
    }
    
    public String getString(String path) {
        return this.config.getString(path);
    }
    
    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }
    
    public List<String> getStringList(String path) {
        return (List<String>)this.config.getStringList(path);
    }
    
    public int getInt(String path) {
        return this.config.getInt(path);
    }
    
    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }
    
    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }
    
    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }
    
    public void createSection(String path) {
        this.config.createSection(path);
    }
    
    public ConfigurationSection getConfigurationSection(String path) {
        return this.config.getConfigurationSection(path);
    }
    
    public double getDouble(String path) {
        return this.config.getDouble(path);
    }
    
    public double getDouble(String path, double def) {
        return this.config.getDouble(path, def);
    }
    
    public long getLong(String path) {
        return this.config.getLong(path);
    }
    
    public long getLong(String path, long def) {
        return this.config.getLong(path, def);
    }
    
    public List<?> getList(String path) {
        return (List<?>)this.config.getList(path);
    }
    
    public List<?> getList(String path, List<String> def) {
        return this.config.getList(path, (List<String>)def);
    }
    
    public boolean contains(String path) {
        return this.config.contains(path);
    }
    
    public void removeKey(String path) {
        this.config.set(path, (Object)null);
    }
    
    public void set(String path, Object value) {
        this.config.set(path, value);
    }
    
    public Set<String> getKeys(boolean deep) {
        return (Set<String>) this.config.getKeys(deep);
    }
    
    public boolean isInt(String path) {
        return this.config.isInt(path);
    }
    
    public boolean isString(String path) {
        return this.config.isString(path);
    }
    
    public boolean isBoolean(String path) {
        return this.config.isBoolean(path);
    }
    
    public boolean isList(String path) {
        return this.config.isList(path);
    }
}
