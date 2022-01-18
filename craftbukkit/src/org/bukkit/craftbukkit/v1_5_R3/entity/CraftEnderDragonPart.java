// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EnderDragon;
import net.minecraft.server.v1_5_R3.EntityComplexPart;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.EnderDragonPart;

public class CraftEnderDragonPart extends CraftComplexPart implements EnderDragonPart
{
    public CraftEnderDragonPart(final CraftServer server, final EntityComplexPart entity) {
        super(server, entity);
    }
    
    public EnderDragon getParent() {
        return (EnderDragon)super.getParent();
    }
    
    public EntityComplexPart getHandle() {
        return (EntityComplexPart)this.entity;
    }
    
    public String toString() {
        return "CraftEnderDragonPart";
    }
    
    public void damage(final int amount) {
        this.getParent().damage(amount);
    }
    
    public void damage(final int amount, final Entity source) {
        this.getParent().damage(amount, source);
    }
    
    public int getHealth() {
        return this.getParent().getHealth();
    }
    
    public void setHealth(final int health) {
        this.getParent().setHealth(health);
    }
    
    public int getMaxHealth() {
        return this.getParent().getMaxHealth();
    }
    
    public void setMaxHealth(final int health) {
        this.getParent().setMaxHealth(health);
    }
    
    public void resetMaxHealth() {
        this.getParent().resetMaxHealth();
    }
}
