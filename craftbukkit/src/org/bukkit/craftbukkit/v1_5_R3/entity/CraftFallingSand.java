// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityFallingBlock;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.FallingSand;

public class CraftFallingSand extends CraftEntity implements FallingSand
{
    public CraftFallingSand(final CraftServer server, final EntityFallingBlock entity) {
        super(server, entity);
    }
    
    public EntityFallingBlock getHandle() {
        return (EntityFallingBlock)this.entity;
    }
    
    public String toString() {
        return "CraftFallingSand";
    }
    
    public EntityType getType() {
        return EntityType.FALLING_BLOCK;
    }
    
    public Material getMaterial() {
        return Material.getMaterial(this.getBlockId());
    }
    
    public int getBlockId() {
        return this.getHandle().id;
    }
    
    public byte getBlockData() {
        return (byte)this.getHandle().data;
    }
    
    public boolean getDropItem() {
        return this.getHandle().dropItem;
    }
    
    public void setDropItem(final boolean drop) {
        this.getHandle().dropItem = drop;
    }
}
