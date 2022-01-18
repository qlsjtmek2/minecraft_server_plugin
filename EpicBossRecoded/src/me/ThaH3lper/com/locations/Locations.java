// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.locations;

import org.bukkit.Location;

public class Locations
{
    private Location location;
    private String name;
    private String World;
    
    public Locations(final Location loc, final String newname, final String worldname) {
        this.location = loc;
        this.name = newname;
        this.World = worldname;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getWorldName() {
        return this.World;
    }
}
