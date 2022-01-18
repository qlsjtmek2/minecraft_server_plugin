// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import net.minecraft.server.v1_5_R3.EntityEnderDragon;
import org.bukkit.entity.ComplexLivingEntity;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityComplexPart;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.ComplexEntityPart;

public class CraftComplexPart extends CraftEntity implements ComplexEntityPart
{
    public CraftComplexPart(final CraftServer server, final EntityComplexPart entity) {
        super(server, entity);
    }
    
    public ComplexLivingEntity getParent() {
        return (ComplexLivingEntity)((EntityEnderDragon)this.getHandle().owner).getBukkitEntity();
    }
    
    public void setLastDamageCause(final EntityDamageEvent cause) {
        this.getParent().setLastDamageCause(cause);
    }
    
    public EntityDamageEvent getLastDamageCause() {
        return this.getParent().getLastDamageCause();
    }
    
    public EntityComplexPart getHandle() {
        return (EntityComplexPart)this.entity;
    }
    
    public String toString() {
        return "CraftComplexPart";
    }
    
    public EntityType getType() {
        return EntityType.COMPLEX_PART;
    }
}
