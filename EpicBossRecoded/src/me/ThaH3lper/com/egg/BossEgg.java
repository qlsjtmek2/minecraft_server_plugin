// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.egg;

import org.bukkit.inventory.meta.ItemMeta;
import me.ThaH3lper.com.LoadBosses.LoadBoss;
import java.util.List;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import me.ThaH3lper.com.EpicBoss;

public class BossEgg
{
    public EpicBoss eb;
    
    public BossEgg(final EpicBoss boss) {
        this.eb = boss;
    }
    
    public void giveBossEgg(final Player p, final String s, final int amount) {
        final LoadBoss lb = this.eb.loadconfig.getLoadBoss(s);
        if (lb != null) {
            final List<String> lores = new ArrayList<String>();
            final ItemStack stack = new ItemStack(383, amount);
            final ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(new StringBuilder().append(ChatColor.RED).append(ChatColor.BOLD).append(lb.getName()).append(" SpawnEgg").toString());
            stackMeta.addEnchant(Enchantment.DURABILITY, 10, true);
            lores.add(new StringBuilder().append(ChatColor.DARK_PURPLE).append(ChatColor.ITALIC).append("A very mysterious egg").toString());
            lores.add(new StringBuilder().append(ChatColor.DARK_PURPLE).append(ChatColor.ITALIC).append("that can be used to bring").toString());
            lores.add(new StringBuilder().append(ChatColor.DARK_PURPLE).append(ChatColor.ITALIC).append(lb.getName()).toString());
            lores.add(new StringBuilder().append(ChatColor.DARK_PURPLE).append(ChatColor.ITALIC).append("back from the dead!").toString());
            lores.add(ChatColor.GRAY + "Right-Click to Spawn Boss");
            stackMeta.setLore((List)lores);
            stack.setItemMeta(stackMeta);
            p.getInventory().addItem(new ItemStack[] { stack });
        }
        else {
            p.sendMessage(ChatColor.DARK_RED + "There is no Boss with that name!");
        }
    }
}
