// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityFlying;
import net.minecraft.server.v1_5_R3.EntityGhast;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Ghast;

public class CraftGhast extends CraftFlying implements Ghast
{
    public CraftGhast(final CraftServer server, final EntityGhast entity) {
        super(server, entity);
    }
    
    public EntityGhast getHandle() {
        return (EntityGhast)this.entity;
    }
    
    public String toString() {
        return "CraftGhast";
    }
    
    public EntityType getType() {
        return EntityType.GHAST;
    }
}
