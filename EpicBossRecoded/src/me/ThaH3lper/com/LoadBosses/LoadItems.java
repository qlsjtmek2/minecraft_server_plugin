// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.LoadBosses;

import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class LoadItems
{
    Random r;
    
    public LoadItems() {
        this.r = new Random();
    }
    
    public ItemStack getItem(final String s) {
        int data2 = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        final String[] parts = s.split(" ");
        final String first = parts[0];
        final String[] PartItems = first.split(":");
        final Integer amount = Integer.parseInt(PartItems[2]);
        final Integer id = Integer.parseInt(PartItems[0]);
        if ((id == 298 || id == 299 || id == 300 || id == 301) && PartItems[1].contains(",")) {
            final String[] spits = PartItems[1].split(",");
            if (spits[0].equals("r")) {
                red = this.r.nextInt(255);
            }
            else {
                red = Integer.parseInt(spits[0]);
            }
            if (spits[1].equals("r")) {
                green = this.r.nextInt(255);
            }
            else {
                green = Integer.parseInt(spits[1]);
            }
            if (spits[2].equals("r")) {
                blue = this.r.nextInt(255);
            }
            else {
                blue = Integer.parseInt(spits[2]);
            }
        }
        else {
            final Integer data3 = Integer.parseInt(PartItems[1]);
            data2 = data3;
        }
        ItemStack stack = new ItemStack((int)id, (int)amount, (short)data2);
        final ItemMeta em = stack.getItemMeta();
        if ((id == 298 || id == 299 || id == 300 || id == 301) && PartItems[1].contains(",")) {
            final LeatherArmorMeta lm = (LeatherArmorMeta)em;
            lm.setColor(Color.fromRGB(red, green, blue));
            stack.setItemMeta((ItemMeta)lm);
        }
        if (parts.length >= 3 && !parts[2].contains("$")) {
            final String[] Enchants = parts[2].split(",");
            String[] array;
            for (int length = (array = Enchants).length, i = 0; i < length; ++i) {
                final String enc = array[i];
                final String[] EnchantsParts = enc.split(":");
                final int level = Integer.parseInt(EnchantsParts[1]);
                final Enchantment enchantment = Enchantment.getByName(EnchantsParts[0]);
                if (id == 403) {
                    stack = this.addBookEnchantment(stack, enchantment, level);
                }
                else {
                    em.addEnchant(enchantment, level, true);
                }
            }
        }
        if (id != 403) {
            stack.setItemMeta(em);
        }
        return stack;
    }
    
    public float getItemChance(final String s) {
        final String[] parts = s.split(" ");
        final Float chance = Float.parseFloat(parts[1]);
        return chance;
    }
    
    public String getDisplayName(final String s) {
        final String[] parts = s.split(" ");
        if (parts.length == 3) {
            if (parts[2].contains("$")) {
                String str = parts[2];
                str = str.replace("$", "");
                str = ChatColor.translateAlternateColorCodes('&', str);
                str = str.replace("_", " ");
                return str;
            }
        }
        else if (parts.length == 4 && parts[3].contains("$")) {
            String str = parts[3];
            str = str.replace("$", "");
            str = ChatColor.translateAlternateColorCodes('&', str);
            str = str.replace("_", " ");
            return str;
        }
        return null;
    }
    
    public ItemStack addBookEnchantment(final ItemStack item, final Enchantment enchantment, final int level) {
        final EnchantmentStorageMeta meta = (EnchantmentStorageMeta)item.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        item.setItemMeta((ItemMeta)meta);
        return item;
    }
}
