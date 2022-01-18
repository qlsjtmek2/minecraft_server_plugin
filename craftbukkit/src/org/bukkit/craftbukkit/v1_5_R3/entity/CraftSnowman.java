// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityGolem;
import net.minecraft.server.v1_5_R3.EntitySnowman;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Snowman;

public class CraftSnowman extends CraftGolem implements Snowman
{
    public CraftSnowman(final CraftServer server, final EntitySnowman entity) {
        super(server, entity);
    }
    
    public EntitySnowman getHandle() {
        return (EntitySnowman)this.entity;
    }
    
    public String toString() {
        return "CraftSnowman";
    }
    
    public EntityType getType() {
        return EntityType.SNOWMAN;
    }
}
