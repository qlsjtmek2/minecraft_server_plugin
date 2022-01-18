// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.config;

import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Map;
import java.util.List;
import com.onikur.changebackitem.ChangeBackItem;

public enum MainConfig
{
    @SuppressWarnings("serial")
	Disable_Worlds((Object)new ArrayList<String>() {
        {
            this.add("activity");
        }
    });
    
    private final String _path;
    private Object _val;
    
    private MainConfig(final Object defval) {
        this._path = this.name().replaceAll("__", ".");
        this._val = ChangeBackItem.get().getConfig().get(this._path, defval);
    }
    
    public String getPath() {
        return this._path;
    }
    
    public Object getObject() {
        return this._val;
    }
    
    public String getString() {
        return (String)this._val;
    }
    
    @SuppressWarnings("unchecked")
	public List<String> getStringList() {
        return (List<String>)this._val;
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> getMap() {
        if (this._val instanceof Map) {
            return (Map<String, Object>)this._val;
        }
        return (Map<String, Object>)((ConfigurationSection)this._val).getValues(true);
    }
    
    public boolean getBool() {
        return (boolean)this._val;
    }
    
    public int getInteger() {
        return (int)this._val;
    }
    
    public void setVal(final Object val) {
        ChangeBackItem.get().getConfig().set(this._path, val);
    }
    
    public static void forceSave() {
        final FileConfiguration config = ChangeBackItem.get().getConfig();
        for (final MainConfig set : values()) {
            if (!config.contains(set.getPath())) {
                config.set(set.getPath(), set.getObject());
            }
        }
        ChangeBackItem.get().saveConfig();
    }
    
    public static void refreshAll() {
        for (final MainConfig set : values()) {
            set._val = ChangeBackItem.get().getConfig().get(set._path);
        }
    }
}
