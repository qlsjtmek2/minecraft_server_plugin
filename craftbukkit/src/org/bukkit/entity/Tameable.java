// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Tameable
{
    boolean isTamed();
    
    void setTamed(final boolean p0);
    
    AnimalTamer getOwner();
    
    void setOwner(final AnimalTamer p0);
}
