// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.config;

import java.util.Iterator;
import think.rpgitems.power.PowerUnbreaking;
import think.rpgitems.power.PowerUnbreakable;
import think.rpgitems.power.Power;
import think.rpgitems.item.RPGItem;
import think.rpgitems.item.ItemManager;
import think.rpgitems.Plugin;
import org.bukkit.configuration.ConfigurationSection;

public class Update03To04 implements Updater
{
    @Override
    public void update(final ConfigurationSection section) {
        final Plugin plugin = Plugin.plugin;
        ItemManager.load(plugin);
        for (final RPGItem item : ItemManager.itemByName.values()) {
            final Iterator<Power> it = item.powers.iterator();
            while (it.hasNext()) {
                final Power power = it.next();
                if (power instanceof PowerUnbreakable) {
                    item.setMaxDurability(-1, false);
                    it.remove();
                }
                if (power instanceof PowerUnbreaking) {
                    final PowerUnbreaking ub = (PowerUnbreaking)power;
                    item.setMaxDurability((int)(item.getMaxDurability() * (1.0 + ub.level / 2.0)));
                    it.remove();
                }
            }
        }
        ItemManager.save(plugin);
        ItemManager.itemByName.clear();
        ItemManager.itemById.clear();
        section.set("version", (Object)"0.4");
    }
}
