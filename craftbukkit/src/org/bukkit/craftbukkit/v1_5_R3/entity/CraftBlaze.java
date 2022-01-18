// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityBlaze;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Blaze;

public class CraftBlaze extends CraftMonster implements Blaze
{
    public CraftBlaze(final CraftServer server, final EntityBlaze entity) {
        super(server, entity);
    }
    
    public EntityBlaze getHandle() {
        return (EntityBlaze)this.entity;
    }
    
    public String toString() {
        return "CraftBlaze";
    }
    
    public EntityType getType() {
        return EntityType.BLAZE;
    }
}
