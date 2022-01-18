// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityEnderman;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Enderman;

public class CraftEnderman extends CraftMonster implements Enderman
{
    public CraftEnderman(final CraftServer server, final EntityEnderman entity) {
        super(server, entity);
    }
    
    public MaterialData getCarriedMaterial() {
        return Material.getMaterial(this.getHandle().getCarriedId()).getNewData((byte)this.getHandle().getCarriedData());
    }
    
    public void setCarriedMaterial(final MaterialData data) {
        this.getHandle().setCarriedId(data.getItemTypeId());
        this.getHandle().setCarriedData(data.getData());
    }
    
    public EntityEnderman getHandle() {
        return (EntityEnderman)this.entity;
    }
    
    public String toString() {
        return "CraftEnderman";
    }
    
    public EntityType getType() {
        return EntityType.ENDERMAN;
    }
}
