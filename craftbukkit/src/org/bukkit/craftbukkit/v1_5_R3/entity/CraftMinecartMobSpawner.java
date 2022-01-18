// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_5_R3.EntityMinecartMobSpawner;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.minecart.SpawnerMinecart;

final class CraftMinecartMobSpawner extends CraftMinecart implements SpawnerMinecart
{
    CraftMinecartMobSpawner(final CraftServer server, final EntityMinecartMobSpawner entity) {
        super(server, entity);
    }
    
    public String toString() {
        return "CraftMinecartMobSpawner";
    }
    
    public EntityType getType() {
        return EntityType.MINECART_MOB_SPAWNER;
    }
}
