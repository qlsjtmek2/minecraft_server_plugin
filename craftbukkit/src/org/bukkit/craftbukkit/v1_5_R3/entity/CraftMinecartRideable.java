// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.minecart.RideableMinecart;

public class CraftMinecartRideable extends CraftMinecart implements RideableMinecart
{
    public CraftMinecartRideable(final CraftServer server, final EntityMinecartAbstract entity) {
        super(server, entity);
    }
    
    public String toString() {
        return "CraftMinecartRideable";
    }
    
    public EntityType getType() {
        return EntityType.MINECART;
    }
}
