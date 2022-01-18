// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import org.bukkit.entity.EntityType;
import org.bukkit.DyeColor;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import net.minecraft.server.v1_5_R3.EntitySheep;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Sheep;

public class CraftSheep extends CraftAnimals implements Sheep
{
    public CraftSheep(final CraftServer server, final EntitySheep entity) {
        super(server, entity);
    }
    
    public DyeColor getColor() {
        return DyeColor.getByWoolData((byte)this.getHandle().getColor());
    }
    
    public void setColor(final DyeColor color) {
        this.getHandle().setColor(color.getWoolData());
    }
    
    public boolean isSheared() {
        return this.getHandle().isSheared();
    }
    
    public void setSheared(final boolean flag) {
        this.getHandle().setSheared(flag);
    }
    
    public EntitySheep getHandle() {
        return (EntitySheep)this.entity;
    }
    
    public String toString() {
        return "CraftSheep";
    }
    
    public EntityType getType() {
        return EntityType.SHEEP;
    }
}
