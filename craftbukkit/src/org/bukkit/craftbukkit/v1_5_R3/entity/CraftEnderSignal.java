// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityEnderSignal;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.EnderSignal;

public class CraftEnderSignal extends CraftEntity implements EnderSignal
{
    public CraftEnderSignal(final CraftServer server, final EntityEnderSignal entity) {
        super(server, entity);
    }
    
    public EntityEnderSignal getHandle() {
        return (EntityEnderSignal)this.entity;
    }
    
    public String toString() {
        return "CraftEnderSignal";
    }
    
    public EntityType getType() {
        return EntityType.ENDER_SIGNAL;
    }
}
