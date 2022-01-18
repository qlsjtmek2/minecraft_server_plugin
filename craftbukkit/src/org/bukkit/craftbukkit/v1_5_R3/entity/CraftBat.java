// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityAmbient;
import net.minecraft.server.v1_5_R3.EntityBat;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Bat;

public class CraftBat extends CraftAmbient implements Bat
{
    public CraftBat(final CraftServer server, final EntityBat entity) {
        super(server, entity);
    }
    
    public EntityBat getHandle() {
        return (EntityBat)this.entity;
    }
    
    public String toString() {
        return "CraftBat";
    }
    
    public EntityType getType() {
        return EntityType.BAT;
    }
}
