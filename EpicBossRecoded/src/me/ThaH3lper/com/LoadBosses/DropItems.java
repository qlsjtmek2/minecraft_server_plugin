// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.LoadBosses;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import me.ThaH3lper.com.EpicBoss;
import java.util.Random;

public class DropItems
{
    Random r;
    EpicBoss eb;
    
    public DropItems(final EpicBoss neb) {
        this.r = new Random();
        this.eb = neb;
    }
    
    public void dropItems(final List<ItemStack> items, final Boss b, final int exp) {
        for (final ItemStack s : items) {
            b.getLocation().getWorld().dropItem(b.getLocation(), s);
        }
        if (exp != 0) {
            int i = 0;
            final int x = exp / 20;
            while (i < 20) {
                final ExperienceOrb ob = (ExperienceOrb)b.getLocation().getWorld().spawnEntity(b.getLocation(), EntityType.EXPERIENCE_ORB);
                ob.setExperience(x + 1);
                ++i;
            }
        }
    }
    
    public List<ItemStack> getDrops(final Boss b) {
        final List<ItemStack> items = new ArrayList<ItemStack>();
        if (b.getItems() != null) {
            for (final String s : b.getItems()) {
                final String[] part = s.split(" ");
                if (!part[0].equalsIgnoreCase("exp") && !part[0].equalsIgnoreCase("hexp")) {
                    final ItemStack sta = this.eb.dropitems.getDropsItems(this.eb.loaditems.getItem(s), this.eb.loaditems.getItemChance(s), this.eb.loaditems.getDisplayName(s));
                    if (sta == null) {
                        continue;
                    }
                    items.add(sta);
                }
            }
        }
        return items;
    }
    
    public int getExp(final Boss b) {
        if (b.getItems() != null) {
            for (final String s : b.getItems()) {
                final String[] part = s.split(" ");
                if (part[0].equalsIgnoreCase("exp")) {
                    final int i = Integer.parseInt(part[1]);
                    return i;
                }
            }
        }
        return 0;
    }
    
    public int getHeroesExp(final Boss b) {
        if (b.getItems() != null) {
            for (final String s : b.getItems()) {
                final String[] part = s.split(" ");
                if (part[0].equalsIgnoreCase("hexp")) {
                    final int i = Integer.parseInt(part[1]);
                    return i;
                }
            }
        }
        return 0;
    }
    
    public ItemStack getDropsItems(final ItemStack stack, final float f, final String s) {
        if (s != null) {
            final ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(s);
            stack.setItemMeta(stackMeta);
        }
        if (this.r.nextFloat() <= f) {
            return stack;
        }
        return null;
    }
}
