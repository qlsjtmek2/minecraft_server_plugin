// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_5_R3.EntityMinecartTNT;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.minecart.ExplosiveMinecart;

final class CraftMinecartTNT extends CraftMinecart implements ExplosiveMinecart
{
    CraftMinecartTNT(final CraftServer server, final EntityMinecartTNT entity) {
        super(server, entity);
    }
    
    public String toString() {
        return "CraftMinecartTNT";
    }
    
    public EntityType getType() {
        return EntityType.MINECART_TNT;
    }
}
