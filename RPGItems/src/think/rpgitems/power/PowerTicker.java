// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power;

import think.rpgitems.item.RPGItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import think.rpgitems.item.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class PowerTicker extends BukkitRunnable
{
    public void run() {
        final Player[] players = Bukkit.getOnlinePlayers();
        Player[] array;
        for (int length = (array = players).length, i = 0; i < length; ++i) {
            final Player player = array[i];
            final ItemStack[] armour = player.getInventory().getArmorContents();
            ItemStack[] array2;
            for (int length2 = (array2 = armour).length, j = 0; j < length2; ++j) {
                final ItemStack part = array2[j];
                final RPGItem item = ItemManager.toRPGItem(part);
                if (item != null) {
                    item.tick(player);
                }
            }
            final ItemStack part = player.getItemInHand();
            final RPGItem item2 = ItemManager.toRPGItem(part);
            if (item2 != null) {
                item2.tick(player);
            }
        }
    }
}
