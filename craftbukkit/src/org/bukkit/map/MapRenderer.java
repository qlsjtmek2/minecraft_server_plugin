// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.map;

import org.bukkit.entity.Player;

public abstract class MapRenderer
{
    private boolean contextual;
    
    public MapRenderer() {
        this(false);
    }
    
    public MapRenderer(final boolean contextual) {
        this.contextual = contextual;
    }
    
    public final boolean isContextual() {
        return this.contextual;
    }
    
    public void initialize(final MapView map) {
    }
    
    public abstract void render(final MapView p0, final MapCanvas p1, final Player p2);
}
