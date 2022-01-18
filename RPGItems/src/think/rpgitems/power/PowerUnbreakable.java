// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerHit;

public class PowerUnbreakable extends Power implements PowerHit
{
    @Override
    public void hit(final Player player, final LivingEntity e) {
        player.getItemInHand().setDurability((short)0);
        player.updateInventory();
    }
    
    @Override
    public void init(final ConfigurationSection s) {
    }
    
    @Override
    public void save(final ConfigurationSection s) {
    }
    
    @Override
    public String getName() {
        return "unbreakable";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + Locale.get("power.unbreakable", locale);
    }
}
