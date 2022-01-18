// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityFireball;
import net.minecraft.server.v1_5_R3.EntityLargeFireball;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.LargeFireball;

public class CraftLargeFireball extends CraftFireball implements LargeFireball
{
    public CraftLargeFireball(final CraftServer server, final EntityLargeFireball entity) {
        super(server, entity);
    }
    
    public void setYield(final float yield) {
        super.setYield(yield);
        this.getHandle().e = (int)yield;
    }
    
    public EntityLargeFireball getHandle() {
        return (EntityLargeFireball)this.entity;
    }
    
    public String toString() {
        return "CraftLargeFireball";
    }
    
    public EntityType getType() {
        return EntityType.FIREBALL;
    }
}
