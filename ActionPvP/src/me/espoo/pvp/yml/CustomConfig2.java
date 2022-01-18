// 
// Decompiled by Procyon v0.5.30
// 

package me.espoo.pvp.yml;

import java.io.InputStream;
import org.bukkit.configuration.Configuration;
import org.bukkit.Bukkit;
import java.io.IOException;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfig2 extends YamlConfiguration
{
    private JavaPlugin plugin;
    private File configfile;
    private String filename;
    private boolean outfolder;
    
    public CustomConfig2(JavaPlugin plugin, String filename) {
        this(plugin, filename, false);
    }
    
    public CustomConfig2(JavaPlugin plugin, String filename, boolean outfolder) {
        this.filename = String.valueOf(filename) + ".yml";
        this.plugin = plugin;
        this.outfolder = outfolder;
        if (outfolder) {
            this.configfile = new File(this.filename);
        }
        else {
            this.configfile = new File(plugin.getDataFolder(), this.filename);
            if (!this.configfile.exists()) {
                this.configfile.getParentFile().mkdir();
                try {
                    this.configfile.createNewFile();
                }
                catch (IOException ex) {}
            }
        }
        this.reloadConfig();
        this.setDefaultConfig();
    }
    
    public CustomConfig2(String pluginname, String filename) {
        this((JavaPlugin)Bukkit.getPluginManager().getPlugin(pluginname), filename, false);
    }
    
    public CustomConfig2(String pluginname, String filename, boolean outfolder) {
        this((JavaPlugin)Bukkit.getPluginManager().getPlugin(pluginname), filename, outfolder);
    }
    
    public void reloadConfig() {
        this.clearConfig();
        try {
            if (this.configfile.exists()) {
                super.load(this.configfile);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveConfig() {
        try {
            super.save(this.configfile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void copyConfig(String filename) {
        try {
            super.save(new File(this.configfile.getParent(), filename));
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
            if (this.outfolder) {
                File file = new File(this.plugin.getDataFolder(), this.filename);
                if (file.exists()) {
                    File file2 = new File(this.plugin.getDataFolder(), "TEMP\\" + this.filename);
                    file2.getParentFile().mkdir();
                    file.renameTo(file2);
                    this.plugin.saveResource(this.filename, replace);
                    this.configfile.delete();
                    file.renameTo(this.configfile);
                    file2.renameTo(file);
                    file2.getParentFile().delete();
                }
                else {
                    this.plugin.saveResource(this.filename, replace);
                    this.configfile.delete();
                    file.renameTo(this.configfile);
                }
            }
            else {
                this.plugin.saveResource(this.filename, replace);
            }
            this.reloadConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean isExists() {
        return this.configfile.exists();
    }
    
    public void clearConfig() {
        super.map.clear();
    }
    
    private void setDefaultConfig() {
        InputStream defconfigstream = this.plugin.getResource(String.valueOf(this.filename) + ".yml");
        if (defconfigstream != null) {
            YamlConfiguration defconfig = YamlConfiguration.loadConfiguration(defconfigstream);
            super.setDefaults((Configuration)defconfig);
        }
    }
    
    public void removeKey(String path) {
        super.set(path, (Object) null);
    }
}
