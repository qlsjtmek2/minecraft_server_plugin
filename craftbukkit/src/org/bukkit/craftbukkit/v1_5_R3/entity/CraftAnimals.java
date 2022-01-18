// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Animals;

public class CraftAnimals extends CraftAgeable implements Animals
{
    public CraftAnimals(final CraftServer server, final EntityAnimal entity) {
        super(server, entity);
    }
    
    public EntityAnimal getHandle() {
        return (EntityAnimal)this.entity;
    }
    
    public String toString() {
        return "CraftAnimals";
    }
}
