// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_5_R3.EntityMinecartFurnace;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.PoweredMinecart;

public class CraftMinecartFurnace extends CraftMinecart implements PoweredMinecart
{
    public CraftMinecartFurnace(final CraftServer server, final EntityMinecartFurnace entity) {
        super(server, entity);
    }
    
    public String toString() {
        return "CraftMinecartFurnace";
    }
    
    public EntityType getType() {
        return EntityType.MINECART_FURNACE;
    }
}
