// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.weather;

import org.bukkit.World;
import org.bukkit.event.Event;

public abstract class WeatherEvent extends Event
{
    protected World world;
    
    public WeatherEvent(final World where) {
        this.world = where;
    }
    
    public final World getWorld() {
        return this.world;
    }
}
