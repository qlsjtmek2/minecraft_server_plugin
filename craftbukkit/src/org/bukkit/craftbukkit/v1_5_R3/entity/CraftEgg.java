// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.EntityProjectile;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityEgg;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Egg;

public class CraftEgg extends CraftProjectile implements Egg
{
    public CraftEgg(final CraftServer server, final EntityEgg entity) {
        super(server, entity);
    }
    
    public EntityEgg getHandle() {
        return (EntityEgg)this.entity;
    }
    
    public String toString() {
        return "CraftEgg";
    }
    
    public EntityType getType() {
        return EntityType.EGG;
    }
}
