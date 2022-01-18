// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityCow;
import net.minecraft.server.v1_5_R3.EntityMushroomCow;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.MushroomCow;

public class CraftMushroomCow extends CraftCow implements MushroomCow
{
    public CraftMushroomCow(final CraftServer server, final EntityMushroomCow entity) {
        super(server, entity);
    }
    
    public EntityMushroomCow getHandle() {
        return (EntityMushroomCow)this.entity;
    }
    
    public String toString() {
        return "CraftMushroomCow";
    }
    
    public EntityType getType() {
        return EntityType.MUSHROOM_COW;
    }
}
