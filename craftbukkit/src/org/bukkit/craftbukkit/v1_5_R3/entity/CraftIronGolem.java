// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityGolem;
import net.minecraft.server.v1_5_R3.EntityIronGolem;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.IronGolem;

public class CraftIronGolem extends CraftGolem implements IronGolem
{
    public CraftIronGolem(final CraftServer server, final EntityIronGolem entity) {
        super(server, entity);
    }
    
    public EntityIronGolem getHandle() {
        return (EntityIronGolem)this.entity;
    }
    
    public String toString() {
        return "CraftIronGolem";
    }
    
    public boolean isPlayerCreated() {
        return this.getHandle().p();
    }
    
    public void setPlayerCreated(final boolean playerCreated) {
        this.getHandle().f(playerCreated);
    }
    
    public EntityType getType() {
        return EntityType.IRON_GOLEM;
    }
}
