// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import think.rpgitems.data.Locale;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import think.rpgitems.power.types.PowerRightClick;

public class PowerConsume extends Power implements PowerRightClick
{
    @Override
    public void rightClick(final Player player) {
        final ItemStack item = player.getInventory().getItemInHand();
        final int count = item.getAmount() - 1;
        if (count == 0) {
            item.setAmount(0);
            player.setItemInHand((ItemStack)null);
        }
        else {
            item.setAmount(count);
        }
    }
    
    @Override
    public void init(final ConfigurationSection s) {
    }
    
    @Override
    public void save(final ConfigurationSection s) {
    }
    
    @Override
    public String getName() {
        return "consume";
    }
    
    @Override
    public String displayText(final String locale) {
        return ChatColor.GREEN + Locale.get("power.consume", locale);
    }
}
