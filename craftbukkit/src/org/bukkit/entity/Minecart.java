// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.util.Vector;

public interface Minecart extends Vehicle
{
    void setDamage(final int p0);
    
    int getDamage();
    
    double getMaxSpeed();
    
    void setMaxSpeed(final double p0);
    
    boolean isSlowWhenEmpty();
    
    void setSlowWhenEmpty(final boolean p0);
    
    Vector getFlyingVelocityMod();
    
    void setFlyingVelocityMod(final Vector p0);
    
    Vector getDerailedVelocityMod();
    
    void setDerailedVelocityMod(final Vector p0);
}
