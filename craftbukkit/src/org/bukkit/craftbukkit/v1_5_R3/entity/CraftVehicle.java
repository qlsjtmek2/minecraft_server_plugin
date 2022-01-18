// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Vehicle;

public abstract class CraftVehicle extends CraftEntity implements Vehicle
{
    public CraftVehicle(final CraftServer server, final net.minecraft.server.v1_5_R3.Entity entity) {
        super(server, entity);
    }
    
    public String toString() {
        return "CraftVehicle{passenger=" + this.getPassenger() + '}';
    }
}
