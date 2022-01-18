// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityExperienceOrb;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.ExperienceOrb;

public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb
{
    public CraftExperienceOrb(final CraftServer server, final EntityExperienceOrb entity) {
        super(server, entity);
    }
    
    public int getExperience() {
        return this.getHandle().value;
    }
    
    public void setExperience(final int value) {
        this.getHandle().value = value;
    }
    
    public EntityExperienceOrb getHandle() {
        return (EntityExperienceOrb)this.entity;
    }
    
    public String toString() {
        return "CraftExperienceOrb";
    }
    
    public EntityType getType() {
        return EntityType.EXPERIENCE_ORB;
    }
}
