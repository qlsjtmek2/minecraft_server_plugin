// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.io.Serializable;

public class ExTLocation implements Serializable
{
    private static final long serialVersionUID = -3814718244233400225L;
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    
    public ExTLocation(final Location loc) {
        this.world = loc.getWorld().getName();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
    }
    
    public String getWorld() {
        return this.world;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public Location toLocation() {
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
    }
    
    @Override
    public String toString() {
        return this.world + "~" + this.x + "~" + this.y + "~" + this.z;
    }
}
