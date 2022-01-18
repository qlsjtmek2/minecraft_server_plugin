// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Boat extends Vehicle
{
    double getMaxSpeed();
    
    void setMaxSpeed(final double p0);
    
    double getOccupiedDeceleration();
    
    void setOccupiedDeceleration(final double p0);
    
    double getUnoccupiedDeceleration();
    
    void setUnoccupiedDeceleration(final double p0);
    
    boolean getWorkOnLand();
    
    void setWorkOnLand(final boolean p0);
}
