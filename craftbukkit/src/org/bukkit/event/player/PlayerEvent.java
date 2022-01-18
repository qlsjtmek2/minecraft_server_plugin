// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class PlayerEvent extends Event
{
    protected Player player;
    
    public PlayerEvent(final Player who) {
        this.player = who;
    }
    
    PlayerEvent(final Player who, final boolean async) {
        super(async);
        this.player = who;
    }
    
    public final Player getPlayer() {
        return this.player;
    }
}
