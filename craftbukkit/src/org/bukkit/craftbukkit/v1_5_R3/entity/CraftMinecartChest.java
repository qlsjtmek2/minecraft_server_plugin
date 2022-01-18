// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_5_R3.EntityMinecartChest;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.entity.StorageMinecart;

public class CraftMinecartChest extends CraftMinecart implements StorageMinecart
{
    private final CraftInventory inventory;
    
    public CraftMinecartChest(final CraftServer server, final EntityMinecartChest entity) {
        super(server, entity);
        this.inventory = new CraftInventory(entity);
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public String toString() {
        return "CraftMinecartChest{inventory=" + this.inventory + '}';
    }
    
    public EntityType getType() {
        return EntityType.MINECART_CHEST;
    }
}
