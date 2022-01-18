// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityWaterAnimal;
import net.minecraft.server.v1_5_R3.EntitySquid;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Squid;

public class CraftSquid extends CraftWaterMob implements Squid
{
    public CraftSquid(final CraftServer server, final EntitySquid entity) {
        super(server, entity);
    }
    
    public EntitySquid getHandle() {
        return (EntitySquid)this.entity;
    }
    
    public String toString() {
        return "CraftSquid";
    }
    
    public EntityType getType() {
        return EntityType.SQUID;
    }
}
