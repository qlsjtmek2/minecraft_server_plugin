// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.ItemStack;
import net.minecraft.server.v1_5_R3.Item;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityFireworks;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import java.util.Random;
import org.bukkit.entity.Firework;

public class CraftFirework extends CraftEntity implements Firework
{
    private static final int FIREWORK_ITEM_INDEX = 8;
    private final Random random;
    private final CraftItemStack item;
    
    public CraftFirework(final CraftServer server, final EntityFireworks entity) {
        super(server, entity);
        this.random = new Random();
        ItemStack item = this.getHandle().getDataWatcher().getItemStack(8);
        if (item == null) {
            item = new ItemStack(Item.FIREWORKS);
            this.getHandle().getDataWatcher().watch(8, item);
        }
        this.item = CraftItemStack.asCraftMirror(item);
        if (this.item.getType() != Material.FIREWORK) {
            this.item.setType(Material.FIREWORK);
        }
    }
    
    public EntityFireworks getHandle() {
        return (EntityFireworks)this.entity;
    }
    
    public String toString() {
        return "CraftFirework";
    }
    
    public EntityType getType() {
        return EntityType.FIREWORK;
    }
    
    public FireworkMeta getFireworkMeta() {
        return (FireworkMeta)this.item.getItemMeta();
    }
    
    public void setFireworkMeta(final FireworkMeta meta) {
        this.item.setItemMeta(meta);
        this.getHandle().expectedLifespan = 10 * (1 + meta.getPower()) + this.random.nextInt(6) + this.random.nextInt(7);
        this.getHandle().getDataWatcher().h(8);
    }
}
