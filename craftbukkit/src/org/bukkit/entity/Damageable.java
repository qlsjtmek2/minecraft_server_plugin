// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Damageable extends Entity
{
    void damage(final int p0);
    
    void damage(final int p0, final Entity p1);
    
    int getHealth();
    
    void setHealth(final int p0);
    
    int getMaxHealth();
    
    void setMaxHealth(final int p0);
    
    void resetMaxHealth();
}
