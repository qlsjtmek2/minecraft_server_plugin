// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Arrow extends Projectile
{
    Spigot spigot();
    
    public static class Spigot
    {
        public double getDamage() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        public void setDamage(final double damage) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
