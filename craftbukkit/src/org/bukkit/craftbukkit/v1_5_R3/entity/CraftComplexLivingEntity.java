// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.ComplexLivingEntity;

public abstract class CraftComplexLivingEntity extends CraftLivingEntity implements ComplexLivingEntity
{
    public CraftComplexLivingEntity(final CraftServer server, final EntityLiving entity) {
        super(server, entity);
    }
    
    public EntityLiving getHandle() {
        return (EntityLiving)this.entity;
    }
    
    public String toString() {
        return "CraftComplexLivingEntity";
    }
}
