// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import net.minecraft.server.v1_5_R3.EntityPig;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Pig;

public class CraftPig extends CraftAnimals implements Pig
{
    public CraftPig(final CraftServer server, final EntityPig entity) {
        super(server, entity);
    }
    
    public boolean hasSaddle() {
        return this.getHandle().hasSaddle();
    }
    
    public void setSaddle(final boolean saddled) {
        this.getHandle().setSaddle(saddled);
    }
    
    public EntityPig getHandle() {
        return (EntityPig)this.entity;
    }
    
    public String toString() {
        return "CraftPig";
    }
    
    public EntityType getType() {
        return EntityType.PIG;
    }
}
