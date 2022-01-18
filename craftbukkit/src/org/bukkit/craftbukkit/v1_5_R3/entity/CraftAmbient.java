// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityAmbient;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Ambient;

public class CraftAmbient extends CraftLivingEntity implements Ambient
{
    public CraftAmbient(final CraftServer server, final EntityAmbient entity) {
        super(server, entity);
    }
    
    public EntityAmbient getHandle() {
        return (EntityAmbient)this.entity;
    }
    
    public String toString() {
        return "CraftAmbient";
    }
    
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
