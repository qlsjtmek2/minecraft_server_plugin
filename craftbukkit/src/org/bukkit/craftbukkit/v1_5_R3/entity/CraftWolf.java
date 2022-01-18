// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityTameableAnimal;
import net.minecraft.server.v1_5_R3.EntityWolf;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Wolf;

public class CraftWolf extends CraftTameableAnimal implements Wolf
{
    public CraftWolf(final CraftServer server, final EntityWolf wolf) {
        super(server, wolf);
    }
    
    public boolean isAngry() {
        return this.getHandle().isAngry();
    }
    
    public void setAngry(final boolean angry) {
        this.getHandle().setAngry(angry);
    }
    
    public EntityWolf getHandle() {
        return (EntityWolf)this.entity;
    }
    
    public EntityType getType() {
        return EntityType.WOLF;
    }
    
    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte)this.getHandle().getCollarColor());
    }
    
    public void setCollarColor(final DyeColor color) {
        this.getHandle().setCollarColor(color.getWoolData());
    }
}
