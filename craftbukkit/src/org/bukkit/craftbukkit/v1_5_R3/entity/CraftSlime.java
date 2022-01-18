// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntitySlime;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Slime;

public class CraftSlime extends CraftLivingEntity implements Slime
{
    public CraftSlime(final CraftServer server, final EntitySlime entity) {
        super(server, entity);
    }
    
    public int getSize() {
        return this.getHandle().getSize();
    }
    
    public void setSize(final int size) {
        this.getHandle().setSize(size);
    }
    
    public EntitySlime getHandle() {
        return (EntitySlime)this.entity;
    }
    
    public String toString() {
        return "CraftSlime";
    }
    
    public EntityType getType() {
        return EntityType.SLIME;
    }
}
