// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.messaging;

import org.bukkit.entity.Player;

public interface PluginMessageListener
{
    void onPluginMessageReceived(final String p0, final Player p1, final byte[] p2);
}
