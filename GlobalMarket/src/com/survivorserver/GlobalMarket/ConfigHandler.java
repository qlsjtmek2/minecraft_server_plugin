// 
// Decompiled by Procyon v0.5.30
// 

package com.survivorserver.GlobalMarket;

import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler
{
    Market market;
    private FileConfiguration listingsConfig;
    private File listingsFile;
    private FileConfiguration mailConfig;
    private File mailFile;
    private FileConfiguration historyConfig;
    private File historyFile;
    private FileConfiguration localeConfig;
    private File localeFile;
    private FileConfiguration queueConfig;
    private File queueFile;
    
    public ConfigHandler(final Market market) {
        this.market = market;
    }
    
    public void reloadListingsYML() {
        if (this.listingsFile == null) {
            this.listingsFile = new File(this.market.getDataFolder(), "listings.yml");
        }
        this.listingsConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.listingsFile);
    }
    
    public FileConfiguration getListingsYML() {
        if (this.listingsConfig == null) {
            this.reloadListingsYML();
        }
        return this.listingsConfig;
    }
    
    public void saveListingsYML() {
        if (this.listingsConfig == null) {
            return;
        }
        try {
            this.getListingsYML().save(this.listingsFile);
        }
        catch (Exception e) {
            this.market.getLogger().log(Level.SEVERE, "Coult not save listings: ", e);
        }
    }
    
    public void reloadMailYML() {
        if (this.mailFile == null) {
            this.mailFile = new File(this.market.getDataFolder(), "mail.yml");
        }
        this.mailConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.mailFile);
    }
    
    public FileConfiguration getMailYML() {
        if (this.mailConfig == null) {
            this.reloadMailYML();
        }
        return this.mailConfig;
    }
    
    public void saveMailYML() {
        if (this.mailConfig == null) {
            return;
        }
        try {
            this.getMailYML().save(this.mailFile);
        }
        catch (Exception e) {
            this.market.getLogger().log(Level.SEVERE, "Coult not save mail: ", e);
        }
    }
    
    public void reloadHistoryYML() {
        if (this.historyFile == null) {
            this.historyFile = new File(this.market.getDataFolder(), "history.yml");
        }
        this.historyConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.historyFile);
    }
    
    public FileConfiguration getHistoryYML() {
        if (this.historyConfig == null) {
            this.reloadHistoryYML();
        }
        return this.historyConfig;
    }
    
    public void saveHistoryYML() {
        if (this.historyConfig == null) {
            return;
        }
        try {
            this.getHistoryYML().save(this.historyFile);
        }
        catch (Exception e) {
            this.market.getLogger().log(Level.SEVERE, "Coult not save history: ", e);
        }
    }
    
    public void reloadLocaleYML() {
        if (this.localeFile == null) {
            this.localeFile = new File(this.market.getDataFolder(), "locale.yml");
            if (!this.localeFile.exists()) {
                this.market.saveResource("locale.yml", false);
            }
        }
        this.localeConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.localeFile);
        if (!this.localeConfig.getString("version").equalsIgnoreCase(this.market.getDescription().getVersion())) {
            final File oldLocale = new File(String.valueOf(this.market.getDataFolder().getName()) + "/locale_old.yml");
            if (oldLocale.exists()) {
                oldLocale.delete();
            }
            this.localeFile.renameTo(new File(this.market.getDataFolder(), "locale_old.yml"));
            this.market.saveResource("locale.yml", false);
            this.localeConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.localeFile);
            this.market.log.warning("Locale version didn't match, loaded new file and moved the old one to \"local_old.yml\"");
        }
    }
    
    public FileConfiguration getLocaleYML() {
        if (this.localeConfig == null) {
            this.reloadLocaleYML();
        }
        return this.localeConfig;
    }
    
    public void saveLocaleYML() {
        if (this.localeConfig == null) {
            return;
        }
        try {
            this.getLocaleYML().save(this.localeFile);
        }
        catch (Exception e) {
            this.market.getLogger().log(Level.SEVERE, "Coult not save locale: ", e);
        }
    }
    
    public void reloadQueueYML() {
        if (this.queueFile == null) {
            this.queueFile = new File(this.market.getDataFolder(), "queue.yml");
        }
        this.queueConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(this.queueFile);
    }
    
    public FileConfiguration getQueueYML() {
        if (this.queueConfig == null) {
            this.reloadQueueYML();
        }
        return this.queueConfig;
    }
    
    public void saveQueueYML() {
        if (this.queueConfig == null) {
            return;
        }
        try {
            this.getQueueYML().save(this.queueFile);
        }
        catch (Exception e) {
            this.market.getLogger().log(Level.SEVERE, "Coult not save queue: ", e);
        }
    }
}
