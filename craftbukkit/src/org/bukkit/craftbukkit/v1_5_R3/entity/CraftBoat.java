// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityBoat;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Boat;

public class CraftBoat extends CraftVehicle implements Boat
{
    public CraftBoat(final CraftServer server, final EntityBoat entity) {
        super(server, entity);
    }
    
    public double getMaxSpeed() {
        return this.getHandle().maxSpeed;
    }
    
    public void setMaxSpeed(final double speed) {
        if (speed >= 0.0) {
            this.getHandle().maxSpeed = speed;
        }
    }
    
    public double getOccupiedDeceleration() {
        return this.getHandle().occupiedDeceleration;
    }
    
    public void setOccupiedDeceleration(final double speed) {
        if (speed >= 0.0) {
            this.getHandle().occupiedDeceleration = speed;
        }
    }
    
    public double getUnoccupiedDeceleration() {
        return this.getHandle().unoccupiedDeceleration;
    }
    
    public void setUnoccupiedDeceleration(final double speed) {
        this.getHandle().unoccupiedDeceleration = speed;
    }
    
    public boolean getWorkOnLand() {
        return this.getHandle().landBoats;
    }
    
    public void setWorkOnLand(final boolean workOnLand) {
        this.getHandle().landBoats = workOnLand;
    }
    
    public EntityBoat getHandle() {
        return (EntityBoat)this.entity;
    }
    
    public String toString() {
        return "CraftBoat";
    }
    
    public EntityType getType() {
        return EntityType.BOAT;
    }
}
