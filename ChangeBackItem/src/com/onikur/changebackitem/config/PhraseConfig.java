// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.config;

import org.bukkit.configuration.file.FileConfiguration;

public enum PhraseConfig
{
    No_Permission("&cYou don't have permission to do this!"), 
    Args_Incorrect("&cArgs value incorrect!"), 
    Command_Not_Find("&cCommand not find!"), 
    Only_Player_Use("&cThis command only can use by a player"), 
    Data_Has_Been_Saved("&aData has been saved successfully!"), 
    Plugin_Reloaded_All("&aAll configs are reloaded!"), 
    Help__Cmd_PluginInfo("Show the plugin informations"), 
    Help__Cmd_Help("Show this help"), 
    Help__Cmd_Save("Force save data.bdata file"), 
    Help__Cmd_Reload("Reload the plugin");
    
    private static ExTConfig _conf;
    private final String _path;
    private final String _msg;
    
    private PhraseConfig(final String msg) {
        this._path = this.name().replaceAll("__", ".");
        this._msg = msg;
    }
    
    public static void useConfig(final ExTConfig _conf) {
        PhraseConfig._conf = _conf;
    }
    
    public String getPath() {
        return this._path;
    }
    
    public Object getObject() {
        return this._msg;
    }
    
    public static void reloadConfig() {
        PhraseConfig._conf.reloadConfig();
    }
    
    public String val() {
        return PhraseConfig._conf.getConfig().getString(this._path, this._msg);
    }
    
    public static void forceSave() {
        final FileConfiguration config = PhraseConfig._conf.getConfig();
        for (final PhraseConfig set : values()) {
            if (!config.contains(set.getPath())) {
                config.set(set.getPath(), set.getObject());
            }
        }
        PhraseConfig._conf.saveConfig();
    }
}
