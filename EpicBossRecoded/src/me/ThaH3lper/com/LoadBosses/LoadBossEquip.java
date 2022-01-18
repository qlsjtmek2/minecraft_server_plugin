// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.LoadBosses;

import org.bukkit.inventory.EntityEquipment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import me.ThaH3lper.com.Boss.Boss;
import me.ThaH3lper.com.EpicBoss;

public class LoadBossEquip
{
    public EpicBoss eb;
    
    public LoadBossEquip(final EpicBoss boss) {
        this.eb = boss;
    }
    
    public void SetEqupiment(final Boss b) {
        if (b.getItems() != null && b.getLivingEntity() != null) {
            for (final String s : b.getItems()) {
                final String[] parts = s.split(" ");
                if (!parts[0].equals("exp") && !parts[0].equals("hexp")) {
                    this.equip(s, this.eb.loaditems.getItem(s), b.getLivingEntity());
                }
            }
        }
    }
    
    public void equip(final String s, final ItemStack stack, final LivingEntity l) {
        final String[] parts = s.split(" ");
        final String[] itemParts = parts[0].split(":");
        if (itemParts.length == 4) {
            final EntityEquipment eq = l.getEquipment();
            if (itemParts[3].equals("0")) {
                eq.setItemInHand(stack);
            }
            if (itemParts[3].equals("1")) {
                eq.setBoots(stack);
            }
            if (itemParts[3].equals("2")) {
                eq.setLeggings(stack);
            }
            if (itemParts[3].equals("3")) {
                eq.setChestplate(stack);
            }
            if (itemParts[3].equals("4")) {
                eq.setHelmet(stack);
            }
        }
    }
}
