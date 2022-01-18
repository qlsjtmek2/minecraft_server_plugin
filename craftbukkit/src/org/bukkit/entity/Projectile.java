// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Projectile extends Entity
{
    LivingEntity getShooter();
    
    void setShooter(final LivingEntity p0);
    
    boolean doesBounce();
    
    void setBounce(final boolean p0);
}
