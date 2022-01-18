// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.util.Vector;

public interface Fireball extends Projectile, Explosive
{
    void setDirection(final Vector p0);
    
    Vector getDirection();
}
