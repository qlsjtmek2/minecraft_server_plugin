// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntitySpider;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Spider;

public class CraftSpider extends CraftMonster implements Spider
{
    public CraftSpider(final CraftServer server, final EntitySpider entity) {
        super(server, entity);
    }
    
    public EntitySpider getHandle() {
        return (EntitySpider)this.entity;
    }
    
    public String toString() {
        return "CraftSpider";
    }
    
    public EntityType getType() {
        return EntityType.SPIDER;
    }
}
