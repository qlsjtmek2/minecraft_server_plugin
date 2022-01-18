// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.inventory.Inventory;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_5_R3.EntityMinecartHopper;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.entity.minecart.HopperMinecart;

final class CraftMinecartHopper extends CraftMinecart implements HopperMinecart
{
    private final CraftInventory inventory;
    
    CraftMinecartHopper(final CraftServer server, final EntityMinecartHopper entity) {
        super(server, entity);
        this.inventory = new CraftInventory(entity);
    }
    
    public String toString() {
        return "CraftMinecartHopper{inventory=" + this.inventory + '}';
    }
    
    public EntityType getType() {
        return EntityType.MINECART_HOPPER;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
}
