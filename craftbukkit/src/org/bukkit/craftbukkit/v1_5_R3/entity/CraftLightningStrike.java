// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLightning;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.LightningStrike;

public class CraftLightningStrike extends CraftEntity implements LightningStrike
{
    public CraftLightningStrike(final CraftServer server, final EntityLightning entity) {
        super(server, entity);
    }
    
    public boolean isEffect() {
        return ((EntityLightning)super.getHandle()).isEffect;
    }
    
    public EntityLightning getHandle() {
        return (EntityLightning)this.entity;
    }
    
    public String toString() {
        return "CraftLightningStrike";
    }
    
    public EntityType getType() {
        return EntityType.LIGHTNING;
    }
}
