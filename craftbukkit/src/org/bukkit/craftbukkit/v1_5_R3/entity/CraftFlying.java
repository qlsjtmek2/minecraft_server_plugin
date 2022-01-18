// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityFlying;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Flying;

public class CraftFlying extends CraftLivingEntity implements Flying
{
    public CraftFlying(final CraftServer server, final EntityFlying entity) {
        super(server, entity);
    }
    
    public EntityFlying getHandle() {
        return (EntityFlying)this.entity;
    }
    
    public String toString() {
        return "CraftFlying";
    }
}
