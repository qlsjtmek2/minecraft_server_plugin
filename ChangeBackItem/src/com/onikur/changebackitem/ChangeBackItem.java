// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.onikur.changebackitem.command.CommandHandler;
import com.onikur.changebackitem.config.ExTConfig;
import com.onikur.changebackitem.config.MainConfig;
import com.onikur.changebackitem.config.PhraseConfig;
import com.onikur.changebackitem.listener.BaseValidator;
import com.onikur.changebackitem.listener.PlayerActionListener;
import com.onikur.changebackitem.util.Msg;

public class ChangeBackItem extends JavaPlugin
{
    private static ChangeBackItem self;
    private ExTConfig phrase;
    private BlockStorage blockStorage;
    public List<BaseValidator> validators;
    
    public void onEnable() {
        ChangeBackItem.self = this;
        Msg.setup(this.getDescription());
        this.initConfig();
        this.getCommand("changebackitem").setExecutor((CommandExecutor)new CommandHandler());
        this.blockStorage = new BlockStorage();
        new PlayerActionListener();
        this.hookValidatorPlugins();
        Msg.sendConsole("&a= Start =");
    }
    
    public void onDisable() {
        this.blockStorage.saveData();
        Msg.sendConsole("&bSaving data...");
        Msg.sendConsole("&c= Stop =");
    }
    
    private void initConfig() {
        MainConfig.forceSave();
        (this.phrase = new ExTConfig(this, "phrase.yml")).saveDefaultConfig();
        PhraseConfig.useConfig(this.phrase);
        PhraseConfig.forceSave();
    }
    
    public void hookValidatorPlugins() {
        if (this.validators == null) {
            this.validators = new ArrayList<BaseValidator>();
        }
        else {
            this.validators.clear();
        }
    }
    
    @SuppressWarnings("unused")
	private Plugin getHookPlugin(final String name) {
        final Plugin plugin = this.getServer().getPluginManager().getPlugin(name);
        return plugin;
    }
    
    public boolean isValidBlockForStoreItem(final Block b) {
        boolean valid = true;
        for (final BaseValidator vt : this.validators) {
            if (!vt.isValidToStoreItem(b)) {
                valid = false;
                break;
            }
        }
        return valid;
    }
    
    public void reloadAllConfig() {
        Msg.sendConsole("&bReloading config...");
        this.reloadConfig();
        MainConfig.refreshAll();
        this.phrase.reloadConfig();
        PhraseConfig.useConfig(this.phrase);
        this.hookValidatorPlugins();
        Msg.sendConsole("&aReload complete!");
    }
    
    public BlockStorage getBlockStorage() {
        return this.blockStorage;
    }
    
    @Deprecated
    public static ChangeBackItem getSelf() {
        return ChangeBackItem.self;
    }
    
    public static ChangeBackItem get() {
        return ChangeBackItem.self;
    }
}
