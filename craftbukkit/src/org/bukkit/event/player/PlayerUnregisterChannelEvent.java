// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;

public class PlayerUnregisterChannelEvent extends PlayerChannelEvent
{
    public PlayerUnregisterChannelEvent(final Player player, final String channel) {
        super(player, channel);
    }
}
