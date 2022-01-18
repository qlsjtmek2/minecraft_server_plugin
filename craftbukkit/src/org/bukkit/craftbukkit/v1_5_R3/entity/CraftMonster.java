// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityMonster;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Monster;

public class CraftMonster extends CraftCreature implements Monster
{
    public CraftMonster(final CraftServer server, final EntityMonster entity) {
        super(server, entity);
    }
    
    public EntityMonster getHandle() {
        return (EntityMonster)this.entity;
    }
    
    public String toString() {
        return "CraftMonster";
    }
}
