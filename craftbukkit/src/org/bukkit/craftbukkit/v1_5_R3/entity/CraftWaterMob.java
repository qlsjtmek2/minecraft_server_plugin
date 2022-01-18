// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityWaterAnimal;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.WaterMob;

public class CraftWaterMob extends CraftCreature implements WaterMob
{
    public CraftWaterMob(final CraftServer server, final EntityWaterAnimal entity) {
        super(server, entity);
    }
    
    public EntityWaterAnimal getHandle() {
        return (EntityWaterAnimal)this.entity;
    }
    
    public String toString() {
        return "CraftWaterMob";
    }
}
