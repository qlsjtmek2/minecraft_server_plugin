// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin.messaging;

import org.bukkit.entity.Player;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface Messenger
{
    public static final int MAX_MESSAGE_SIZE = 32766;
    public static final int MAX_CHANNEL_SIZE = 16;
    
    boolean isReservedChannel(final String p0);
    
    void registerOutgoingPluginChannel(final Plugin p0, final String p1);
    
    void unregisterOutgoingPluginChannel(final Plugin p0, final String p1);
    
    void unregisterOutgoingPluginChannel(final Plugin p0);
    
    PluginMessageListenerRegistration registerIncomingPluginChannel(final Plugin p0, final String p1, final PluginMessageListener p2);
    
    void unregisterIncomingPluginChannel(final Plugin p0, final String p1, final PluginMessageListener p2);
    
    void unregisterIncomingPluginChannel(final Plugin p0, final String p1);
    
    void unregisterIncomingPluginChannel(final Plugin p0);
    
    Set<String> getOutgoingChannels();
    
    Set<String> getOutgoingChannels(final Plugin p0);
    
    Set<String> getIncomingChannels();
    
    Set<String> getIncomingChannels(final Plugin p0);
    
    Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final Plugin p0);
    
    Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final String p0);
    
    Set<PluginMessageListenerRegistration> getIncomingChannelRegistrations(final Plugin p0, final String p1);
    
    boolean isRegistrationValid(final PluginMessageListenerRegistration p0);
    
    boolean isIncomingChannelRegistered(final Plugin p0, final String p1);
    
    boolean isOutgoingChannelRegistered(final Plugin p0, final String p1);
    
    void dispatchIncomingMessage(final Player p0, final String p1, final byte[] p2);
}
