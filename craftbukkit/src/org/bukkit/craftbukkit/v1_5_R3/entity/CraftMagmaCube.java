// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntitySlime;
import net.minecraft.server.v1_5_R3.EntityMagmaCube;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.MagmaCube;

public class CraftMagmaCube extends CraftSlime implements MagmaCube
{
    public CraftMagmaCube(final CraftServer server, final EntityMagmaCube entity) {
        super(server, entity);
    }
    
    public EntityMagmaCube getHandle() {
        return (EntityMagmaCube)this.entity;
    }
    
    public String toString() {
        return "CraftMagmaCube";
    }
    
    public EntityType getType() {
        return EntityType.MAGMA_CUBE;
    }
}
