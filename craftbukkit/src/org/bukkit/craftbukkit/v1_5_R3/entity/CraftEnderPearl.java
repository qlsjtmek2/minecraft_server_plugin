// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.EntityProjectile;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityEnderPearl;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.EnderPearl;

public class CraftEnderPearl extends CraftProjectile implements EnderPearl
{
    public CraftEnderPearl(final CraftServer server, final EntityEnderPearl entity) {
        super(server, entity);
    }
    
    public EntityEnderPearl getHandle() {
        return (EntityEnderPearl)this.entity;
    }
    
    public String toString() {
        return "CraftEnderPearl";
    }
    
    public EntityType getType() {
        return EntityType.ENDER_PEARL;
    }
}
