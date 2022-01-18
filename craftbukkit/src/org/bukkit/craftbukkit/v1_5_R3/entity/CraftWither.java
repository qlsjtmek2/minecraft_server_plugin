// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityWither;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Wither;

public class CraftWither extends CraftMonster implements Wither
{
    public CraftWither(final CraftServer server, final EntityWither entity) {
        super(server, entity);
    }
    
    public EntityWither getHandle() {
        return (EntityWither)this.entity;
    }
    
    public String toString() {
        return "CraftWither";
    }
    
    public EntityType getType() {
        return EntityType.WITHER;
    }
}
