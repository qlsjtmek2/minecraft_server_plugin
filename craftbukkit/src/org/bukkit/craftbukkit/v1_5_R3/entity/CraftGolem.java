// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityGolem;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Golem;

public class CraftGolem extends CraftCreature implements Golem
{
    public CraftGolem(final CraftServer server, final EntityGolem entity) {
        super(server, entity);
    }
    
    public EntityGolem getHandle() {
        return (EntityGolem)this.entity;
    }
    
    public String toString() {
        return "CraftGolem";
    }
}
