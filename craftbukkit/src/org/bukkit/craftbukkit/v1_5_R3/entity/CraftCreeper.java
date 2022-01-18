// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreeperPowerEvent;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityCreeper;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Creeper;

public class CraftCreeper extends CraftMonster implements Creeper
{
    public CraftCreeper(final CraftServer server, final EntityCreeper entity) {
        super(server, entity);
    }
    
    public boolean isPowered() {
        return this.getHandle().isPowered();
    }
    
    public void setPowered(final boolean powered) {
        final CraftServer server = this.server;
        final Creeper entity = (Creeper)this.getHandle().getBukkitEntity();
        if (powered) {
            final CreeperPowerEvent event = new CreeperPowerEvent(entity, CreeperPowerEvent.PowerCause.SET_ON);
            server.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.getHandle().setPowered(true);
            }
        }
        else {
            final CreeperPowerEvent event = new CreeperPowerEvent(entity, CreeperPowerEvent.PowerCause.SET_OFF);
            server.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
                this.getHandle().setPowered(false);
            }
        }
    }
    
    public EntityCreeper getHandle() {
        return (EntityCreeper)this.entity;
    }
    
    public String toString() {
        return "CraftCreeper";
    }
    
    public EntityType getType() {
        return EntityType.CREEPER;
    }
}
