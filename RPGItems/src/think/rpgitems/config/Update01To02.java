// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.config;

import java.util.Iterator;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import think.rpgitems.Plugin;
import org.bukkit.configuration.ConfigurationSection;

public class Update01To02 implements Updater
{
    @Override
    public void update(final ConfigurationSection section) {
        final File iFile = new File(Plugin.plugin.getDataFolder(), "items.yml");
        final YamlConfiguration itemStorage = YamlConfiguration.loadConfiguration(iFile);
        final ConfigurationSection iSection = itemStorage.getConfigurationSection("items");
        if (iSection != null) {
            for (final String key : iSection.getKeys(false)) {
                final ConfigurationSection item = iSection.getConfigurationSection(key);
                if (item.contains("armour")) {
                    final int dam = item.getInt("armour");
                    item.set("armour", (Object)(int)(dam / 20.0 * 100.0));
                }
            }
        }
        section.set("version", (Object)"0.2");
        try {
            itemStorage.save(iFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Plugin.plugin.saveConfig();
    }
}
