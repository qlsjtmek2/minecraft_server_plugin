// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityFireball;
import net.minecraft.server.v1_5_R3.EntitySmallFireball;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.SmallFireball;

public class CraftSmallFireball extends CraftFireball implements SmallFireball
{
    public CraftSmallFireball(final CraftServer server, final EntitySmallFireball entity) {
        super(server, entity);
    }
    
    public EntitySmallFireball getHandle() {
        return (EntitySmallFireball)this.entity;
    }
    
    public String toString() {
        return "CraftSmallFireball";
    }
    
    public EntityType getType() {
        return EntityType.SMALL_FIREBALL;
    }
}
