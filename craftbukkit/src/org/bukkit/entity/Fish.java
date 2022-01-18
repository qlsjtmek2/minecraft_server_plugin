// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Fish extends Projectile
{
    double getBiteChance();
    
    void setBiteChance(final double p0) throws IllegalArgumentException;
}
