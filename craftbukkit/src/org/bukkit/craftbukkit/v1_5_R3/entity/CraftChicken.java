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
import net.minecraft.server.v1_5_R3.EntityChicken;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Chicken;

public class CraftChicken extends CraftAnimals implements Chicken
{
    public CraftChicken(final CraftServer server, final EntityChicken entity) {
        super(server, entity);
    }
    
    public EntityChicken getHandle() {
        return (EntityChicken)this.entity;
    }
    
    public String toString() {
        return "CraftChicken";
    }
    
    public EntityType getType() {
        return EntityType.CHICKEN;
    }
}
