// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityMonster;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntitySpider;
import net.minecraft.server.v1_5_R3.EntityCaveSpider;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.CaveSpider;

public class CraftCaveSpider extends CraftSpider implements CaveSpider
{
    public CraftCaveSpider(final CraftServer server, final EntityCaveSpider entity) {
        super(server, entity);
    }
    
    public EntityCaveSpider getHandle() {
        return (EntityCaveSpider)this.entity;
    }
    
    public String toString() {
        return "CraftCaveSpider";
    }
    
    public EntityType getType() {
        return EntityType.CAVE_SPIDER;
    }
}
