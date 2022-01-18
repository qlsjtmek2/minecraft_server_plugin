// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityFireball;
import net.minecraft.server.v1_5_R3.EntityWitherSkull;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.WitherSkull;

public class CraftWitherSkull extends CraftFireball implements WitherSkull
{
    public CraftWitherSkull(final CraftServer server, final EntityWitherSkull entity) {
        super(server, entity);
    }
    
    public EntityWitherSkull getHandle() {
        return (EntityWitherSkull)this.entity;
    }
    
    public String toString() {
        return "CraftWitherSkull";
    }
    
    public EntityType getType() {
        return EntityType.WITHER_SKULL;
    }
}
