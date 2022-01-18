// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.messaging;

import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface PluginMessageRecipient
{
    void sendPluginMessage(final Plugin p0, final String p1, final byte[] p2);
    
    Set<String> getListeningPluginChannels();
}
