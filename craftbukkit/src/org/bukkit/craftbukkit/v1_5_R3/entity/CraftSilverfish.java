// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntitySilverfish;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Silverfish;

public class CraftSilverfish extends CraftMonster implements Silverfish
{
    public CraftSilverfish(final CraftServer server, final EntitySilverfish entity) {
        super(server, entity);
    }
    
    public EntitySilverfish getHandle() {
        return (EntitySilverfish)this.entity;
    }
    
    public String toString() {
        return "CraftSilverfish";
    }
    
    public EntityType getType() {
        return EntityType.SILVERFISH;
    }
}
