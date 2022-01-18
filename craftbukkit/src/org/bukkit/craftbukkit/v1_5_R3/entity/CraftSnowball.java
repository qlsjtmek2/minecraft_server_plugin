// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.EntityProjectile;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntitySnowball;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Snowball;

public class CraftSnowball extends CraftProjectile implements Snowball
{
    public CraftSnowball(final CraftServer server, final EntitySnowball entity) {
        super(server, entity);
    }
    
    public EntitySnowball getHandle() {
        return (EntitySnowball)this.entity;
    }
    
    public String toString() {
        return "CraftSnowball";
    }
    
    public EntityType getType() {
        return EntityType.SNOWBALL;
    }
}
