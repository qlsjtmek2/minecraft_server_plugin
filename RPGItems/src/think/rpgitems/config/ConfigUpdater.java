// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.config;

import think.rpgitems.Plugin;
import org.bukkit.configuration.ConfigurationSection;
import java.util.HashMap;

public class ConfigUpdater
{
    static final String CONFIG_VERSION = "0.4";
    static HashMap<String, Updater> updates;
    
    static {
        (ConfigUpdater.updates = new HashMap<String, Updater>()).put("0.1", new Update01To02());
        ConfigUpdater.updates.put("0.2", new Update02To03());
        ConfigUpdater.updates.put("0.3", new Update03To04());
    }
    
    public static void updateConfig(final ConfigurationSection conf) {
        while (!conf.getString("version", "0.0").equals("0.4")) {
            if (!conf.contains("version")) {
                if (!conf.contains("autoupdate")) {
                    conf.set("autoupdate", (Object)true);
                }
                if (!conf.contains("defaults.hand")) {
                    conf.set("defaults.hand", (Object)"One handed");
                }
                if (!conf.contains("defaults.sword")) {
                    conf.set("defaults.sword", (Object)"Sword");
                }
                if (!conf.contains("defaults.damage")) {
                    conf.set("defaults.damage", (Object)"Damage");
                }
                if (!conf.contains("defaults.armour")) {
                    conf.set("defaults.armour", (Object)"Armour");
                }
                if (!conf.contains("support.worldguard")) {
                    conf.set("support.worldguard", (Object)false);
                }
                conf.set("version", (Object)"0.1");
                Plugin.plugin.saveConfig();
            }
            else {
                if (!ConfigUpdater.updates.containsKey(conf.get("version"))) {
                    break;
                }
                ConfigUpdater.updates.get(conf.get("version")).update(conf);
            }
        }
        ConfigUpdater.updates.clear();
    }
}
