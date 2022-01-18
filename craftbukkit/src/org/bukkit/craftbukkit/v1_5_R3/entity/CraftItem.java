// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import net.minecraft.server.v1_5_R3.EntityItem;
import org.bukkit.entity.Item;

public class CraftItem extends CraftEntity implements Item
{
    private final EntityItem item;
    
    public CraftItem(final CraftServer server, final net.minecraft.server.v1_5_R3.Entity entity, final EntityItem item) {
        super(server, entity);
        this.item = item;
    }
    
    public CraftItem(final CraftServer server, final EntityItem entity) {
        this(server, entity, entity);
    }
    
    public ItemStack getItemStack() {
        return CraftItemStack.asCraftMirror(this.item.getItemStack());
    }
    
    public void setItemStack(final ItemStack stack) {
        this.item.setItemStack(CraftItemStack.asNMSCopy(stack));
    }
    
    public int getPickupDelay() {
        return this.item.pickupDelay;
    }
    
    public void setPickupDelay(final int delay) {
        this.item.pickupDelay = delay;
    }
    
    public String toString() {
        return "CraftItem";
    }
    
    public EntityType getType() {
        return EntityType.DROPPED_ITEM;
    }
}
