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
import net.minecraft.server.v1_5_R3.EntityCow;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Cow;

public class CraftCow extends CraftAnimals implements Cow
{
    public CraftCow(final CraftServer server, final EntityCow entity) {
        super(server, entity);
    }
    
    public EntityCow getHandle() {
        return (EntityCow)this.entity;
    }
    
    public String toString() {
        return "CraftCow";
    }
    
    public EntityType getType() {
        return EntityType.COW;
    }
}
